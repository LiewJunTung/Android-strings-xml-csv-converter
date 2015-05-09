package org.pandawarrior

import groovy.xml.MarkupBuilder

/**
 * Created by jt on 5/9/15.
 */
class WriteXml {

    static void parse( String csvPath, String moduleFolder) {
        //load and split the file
        String csv = new File(csvPath).getText()
        String[] lines = csv.split('\n')
        Map mainDict = [:]
        List<String[]> rows = lines.collect { it.split(',') }
        List head = rows.get(0)

        head = head - "name"
        for (int i = 0; i < head.size(); i++) {
            def tempMap = [:]

            for (int j = 1; j < rows.size(); j++) {
                def column = rows.get(j)
                def name = column[0]
                tempMap[name] = column[i + 1]
            }
            mainDict[head[i].replaceAll("\\s", "")] = tempMap
        }
        writeFile(moduleFolder, mainDict)

    }

    private static void writeFile(String destination, Map mainDict) {
        mainDict.each {
            def stringWriter = new StringWriter()
            def xml = new MarkupBuilder(stringWriter)
            def mainDictValue = it.value
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
                    string(name: key, value)
                }
            }
            def records = stringWriter.toString()
            file.withWriter('utf-8') { writer ->
                writer.write(records)
            }

        }
    }
}
