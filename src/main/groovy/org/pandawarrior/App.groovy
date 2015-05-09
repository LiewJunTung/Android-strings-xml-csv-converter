package org.pandawarrior

import org.apache.commons.csv.CSVFormat

/**
 * Created by jt on 5/9/15.
 */
class App {
    public static void main(String[] args) {
        def pattern = ~/strings.xml/
        String dirname = "/home/jt/Dev/tableapp-restaurant-bookings/tableapp/"
        def stringsXMLMap = [:]
        def mainMap = [:]
        def csv
        def headerList = ["name"]
        def nameList = []
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
            def xml = new XmlSlurper().parseText(xmlFile)
            def resource = xml.string
            resource.each {
                tempMap[it.@name as String] = it.text()
            }
            mainMap[it.key] = tempMap
        }

        headerList.addAll mainMap.keySet()
        csv = headerList.join(", ") + "\n"
        String mainValues = stringsXMLMap["values"]
        def xml = new XmlSlurper().parseText mainValues
        xml.string.each {
            nameList.add(it.@name as String)
        }
        mainMap.each {
            def mainMapValue = it.value
            nameList.each {
                println mainMapValue[it]
            }
        }

        println csv
        println mainMap
        println nameList
    }
}
