package org.pandawarrior

import groovy.xml.MarkupBuilder

/**
 * Created by jt on 5/9/15.
 */
class WriteXml {

    static boolean parse(String csvPath, String moduleFolder) {
        //load and split the file
        List rows = getRows(csvPath)
        List head = getHead(rows)
        Map mainDict = getMainMap(rows, head)
        Map transDict = getTransMap(rows)
        println mainDict
        return writeFile(moduleFolder, mainDict, transDict)
    }

    protected static List getRows(String csvPath){
        String csv = new File(csvPath).getText()
        String[] lines = csv.split('\n')
        List<String[]> rows = lines.collect { it.split(',') }
        return rows
    }

    protected static List getHead(List rows){
        List head = rows.get(0)
        head = head - "name"
        head = head - "translatable"
        return head
    }

    protected static Map getTransMap(List rows){
        Map transMap = [:]
        for (int i=1; i<rows.size(); i++){
            transMap[rows[i][0]] = rows[i][1].replaceAll("\\s", "")
        }
        return transMap
    }

    protected static Map getMainMap(List rows, List head){
        Map mainDict = [:]

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

    private static boolean writeFile(String destination, Map mainDict, Map transDict) {
        mainDict.each {
            def stringWriter = new StringWriter()
            def xml = new MarkupBuilder(stringWriter)
            def mainDictValue = it.value
            def fileName = it.key
            String dir = "${destination}/res/${it.key}/"
            File file = new File(dir, 'strings.xml')
            File folder = new File(dir)

            if (!folder.exists()) {
                folder.mkdirs()
            }
            xml.resources {

                mainDictValue.each {
                    def key = it.key
                    def value = it.value

                    if (fileName.equals("values") && transDict[key].equals("false")){
                        string(name: key, translatable:transDict[key], value)
                    }else if (transDict[key].equals("true") && !value.equals("null")){
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
}
