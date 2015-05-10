package org.pandawarrior

/**
 * Created by jt on 5/9/15.
 */
class ReadXml {

    static boolean parse(String moduleFolder, String csvDestination) {

        //scan the Android module recursively for strings.xml
        Map stringsXMLMap = getXmlFromFolder(moduleFolder)
        //create a map with all the necessary stuff
        Map mainMap = getMainMap(stringsXMLMap)
        //get a map of all translatables
        Map translatableMap = getTranslatable(stringsXMLMap)
        //create a map for the default names
        List nameList = getNameList(stringsXMLMap)
        //reorder the data into list
        List valueList = getValueList(mainMap, nameList, translatableMap)
        List headerList = getHeaderList(mainMap)
        String csv = createCSV(headerList, valueList)
//        println(csv)
        new File(csvDestination).withWriter('utf-8') { writer ->
            writer.write(csv)
        }
        return true;
    }

    protected static String createCSV(List headerList, List valueList) {
        String csv
        csv = headerList.join(",") + "\n"
        valueList.each {
            csv += it.join(",")
            csv += "\n"
        }
        return csv
    }

    protected static List getHeaderList(Map mainMap) {
        List headerList = ["name", "translatable"]
        headerList.addAll mainMap.keySet()
        return headerList
    }

    protected static List getValueList(Map mainMap, List nameList, Map translableMap) {
        List valueList = []
        nameList.each { it ->
            String tempName = it
            List tempList = []
            tempList.add tempName
            tempList.add translableMap[tempName]
            mainMap.each {
                //    println it.value
                def tempMainMap = it.value
                tempList.add tempMainMap[tempName]
            }
            valueList.add tempList
        }
        return valueList
    }

    protected static List getNameList(Map stringsXMLMap) {
        List nameList = []
        String mainValues = stringsXMLMap["values"]
        def xml = new XmlSlurper().parseText mainValues
        xml.string.each {
            nameList.add(it.@name as String)
        }
        return nameList
    }

    protected static Map getXmlFromFolder(String dirname) {
        String pattern = ~/strings.xml/
        Map stringsXMLMap = [:]
        new File(dirname).eachDirRecurse { dir ->
            dir.eachFileMatch(pattern) { myfile ->
                String xmlString = new File(myfile.path).getText()
                stringsXMLMap[myfile.getParentFile().getName()] = xmlString
            }
        }
        return stringsXMLMap;
    }

    protected static Map getMainMap(Map stringsXMLMap) {
        Map mainMap = [:]
        stringsXMLMap.each {
            def tempMap = [:]
            String xmlFile = it.value
            def tempXml = new XmlSlurper().parseText(xmlFile)
            def tempResources = tempXml.string
            tempResources.each {
                tempMap[it.@name as String] = it.text()
            }
            mainMap[it.key] = tempMap
//                println mainMap
        }
        return mainMap
    }

    protected static Map getTranslatable(Map stringsXMLMap) {
        Map resultMap = [:]
        String xml = stringsXMLMap["values"]
//        println xml
        def tempXml = new XmlSlurper().parseText(xml)
        def tempResources = tempXml.string
        tempResources.each {
            resultMap[it.@name as String] = it.@translatable.equals("false") ? it.@translatable : "true"
        }
        return resultMap
    }
}