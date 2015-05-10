package org.pandawarrior

/**
 * Created by jt on 5/10/15.
 */
class WriteXmlTest extends GroovyTestCase {
    private final static String csvPath = "/home/jt/Documents/new.csv"
    private final static String xmlPath = "/home/jt/Documents/"

    void testTransDict() {
        List rows = WriteXml.getRows(csvPath)
        Map transDict = WriteXml.getTransMap(rows)
        assert transDict["app_name"].toString().equals("false")
    }

    void testParse(){
        assert WriteXml.parse(csvPath, xmlPath)
    }
}
