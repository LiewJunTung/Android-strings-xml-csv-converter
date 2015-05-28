package org.pandawarrior

import groovy.xml.MarkupBuilder

/**
 * Created by jt on 5/9/15.
 */
class WriteXml {

    final static String ARRAY_FILE = "arrays"
    final static String PLURALS_FILE = "plurals"

    static boolean parse(String csvPath, String moduleFolder) {
        //load and split the file
        List rows = getRows(csvPath)
        List head = getHead(rows)
        Map mainDict = getMainMap(rows, head)
        Map transDict = getTransMap(rows)
        return writeFile(moduleFolder, mainDict, transDict)
    }

    static boolean parseArray(String csvPath, String moduleFolder, String type) {
        //load and split the file
        List rows = getRows(csvPath)
        List head = getHead(rows)
        Map transDict = getTransMap(rows)
        List nameList = getArrayNameList(transDict)
        Map mainDict = getMainArrayMap(rows, head, nameList)
        return writeArrayFile(moduleFolder, mainDict, transDict, type)
    }

    protected static List getRows(String csvPath) {
        String csv = new File(csvPath).getText()
        String[] lines = csv.split('\n')
        List<String[]> rows = lines.collect { it.split(',') }
        return rows
    }

    protected static List getHead(List rows) {
        List head = rows.get(0)
        head = head - "name"
        head = head - "translatable"
        return head
    }

    protected static Map getTransMap(List rows) {
        Map transMap = [:]
        for (int i = 1; i < rows.size(); i++) {
            transMap[rows[i][0]] = rows[i][1].replaceAll("\\s", "")
        }
        return transMap
    }

    protected static Map getMainMap(List rows, List head) throws ArrayIndexOutOfBoundsException {

        Map mainDict = [:]
//        println rows
        for (int i = 0; i < head.size(); i++) {
            def tempMap = [:]
            for (int j = 1; j < rows.size(); j++) {
                def column = rows.get(j)
                def name = column[0]
                tempMap[name] = column[i + 2]
            }
            mainDict[head[i].replaceAll("\\s", "")] = tempMap
        }
        return mainDict
    }

    protected static List getArrayNameList(Map transMap) {
        return transMap.collect { it.key }
    }

    protected static Map getMainArrayMap(List rows, List head, List nameList) {
        Map mainArrayMap = [:]

        for (int i = 0; i < head.size(); i++) {
            def tempMap = [:]
            nameList.each { String name ->
                def tempList = []
                for (int j = 1; j < rows.size(); j++) {
                    def column = rows.get(j)
                    def tempName = column[0]
                    if (tempName == name) {
                        tempList.add column[i + 2]
                        tempMap[name] = tempList
                    }

                }
            }
            mainArrayMap[head[i].replaceAll("\\s", "")] = tempMap
        }
        return mainArrayMap
    }

    private static boolean writeFile(String destination, Map mainDict, Map transDict) {
        mainDict.each {
            def stringWriter = new StringWriter()
            def xml = new MarkupBuilder(stringWriter)
            def mainDictValue = it.value
            def fileName = it.key
            String dir = "${destination}/res/${fileName}/"
            File file = new File(dir, 'strings.xml')
            File folder = new File(dir)

            if (!folder.exists()) {
                folder.mkdirs()
            }
            xml.resources {
                mainDictValue.each {
                    def key = it.key
                    def value = it.value.replaceAll("@@", ",")
                    if (fileName.equals("values") && transDict[key].equals("false")) {
                        string(name: key, translatable: transDict[key], value)
                    } else if (transDict[key].equals("true") && !value.equals("null") && !value.equals(" ")) {
                        string(name: key, value)
                    }
                }
            }
            def records = stringWriter.toString()
            file.withWriter('utf-8') { writer ->
                writer.write(records)
            }
            return true
        }
    }

    private static boolean writeArrayFile(String destination, Map mainArrayMap, Map transDict, String type) {
        mainArrayMap.each {
            def stringWriter = new StringWriter()
            def xml = new MarkupBuilder(stringWriter)
            def mainDictValue = it.value
            def fileName = it.key
            def rowName
            String dir = "${destination}/res/${fileName}/"
            File file = new File(dir, "${type}.xml")
            File folder = new File(dir)

            if (!folder.exists()) {
                folder.mkdirs()
            }
            rowName = type == ARRAY_FILE ? "string-array" : type

            xml.resources {
                mainDictValue.each {
                    def key = it.key
                    def value = it.value.collect()
                    if (fileName.equals("values") && transDict[key].equals("false")) {
                        "${rowName}"(name: key, translatable: transDict[key]) {
                            value.each {
                                item(it)
                            }
                        }
                    } else if (transDict[key].equals("true")) {
                        "${rowName}"(name: key) {
                            value.each {
                                if (!it.equals("null") || !it.equals(" ")) {
                                    item(it)
                                }
                            }
                        }
                    }
                }
            }
            def records = stringWriter.toString()
            file.withWriter('utf-8') { writer ->
                writer.write(records)
            }
            return true
        }
    }
}
