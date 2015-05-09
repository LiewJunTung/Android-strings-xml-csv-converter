package org.pandawarrior

/**
 * Created by jt on 5/9/15.
 */
class ReadXml {
    static boolean parse(String moduleFolder, String csvDestination){
        try{String pattern = ~/strings.xml/
            String dirname = moduleFolder
            String csv
            Map stringsXMLMap = [:]
            Map mainMap = [:]
            List headerList = ["name"]
            List nameList = []
            List valueList = []

            //scan the Android module recursively for strings.xml
            new File("$dirname").eachDirRecurse { dir ->
                dir.eachFileMatch(pattern) { myfile ->
                    String xmlString = new File(myfile.path).getText()
                    stringsXMLMap[myfile.getParentFile().getName()] = xmlString
                }
            }

            //create a map with all the necessary stuff
            stringsXMLMap.each {
                def tempMap = [:]
                String xmlFile = it.value
                def tempXml = new XmlSlurper().parseText(xmlFile)
                def tempResources = tempXml.string
                tempResources.each {
                    if (it.@translatable != false){
                        tempMap[it.@name as String] = it.text()
                    }
                }
                mainMap[it.key] = tempMap
//                println mainMap
            }

            //create a map for the default names
            String mainValues = stringsXMLMap["values"]
            def xml = new XmlSlurper().parseText mainValues
            xml.string.each {
                if (it.@translatable != false){
                    nameList.add(it.@name as String)
                }
            }
            //reorder the data into list
            nameList.each {
                def tempName = it
                def tempList = []
                tempList.add tempName
                mainMap.each {
                    def tempMainMap = it.value
                    tempList.add tempMainMap[tempName]
                }
                valueList.add tempList
            }
            headerList.addAll mainMap.keySet()
            csv = headerList.join(", ") + "\n"

            valueList.each {
                csv += it.join(", ")
                csv += "\n"
            }

//        println(csv)
            new File(csvDestination).withWriter('utf-8') { writer ->
                writer.write(csv)
            }
            return true;
        }catch (Exception e){
            println e.toString()
            return false;
        }

    }
}
