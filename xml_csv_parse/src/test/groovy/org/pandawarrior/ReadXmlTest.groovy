package org.pandawarrior

/**
 * Created by jt on 5/26/15.
 */
class ReadXmlTest extends GroovyTestCase {
    static String XmlDir = "/home/jt/Dev/tableapp-restaurant-bookings/tableapp/src/main/res"
    static String PluralsDestination = "/home/jt/Desktop/plurals.csv"
    static String ArraysDestination = "/home/jt/Desktop/arrays.csv"
    static String StringsDestination = "/home/jt/Desktop/"
    def xml
    ReadXml readXml
    @Override
    void setUp() {
        super.setUp();
        readXml = new ReadXml();
        xml = readXml.getXmlFromFolder(XmlDir, ReadXml.STRINGS_PATTERN)
    }

    void testGetArrays(){
//        println "ArrayMap: " + readXml.getArrayMap(xml)
    }

    void testGetTranslatableArrays(){
//        def translatableMap = readXml.getTranslatableArray(xml)
//        println "TranlatableMap" + translatableMap
    }

    void testGetArrayHeader(){
        def arrayHeader = readXml.getHeaderList(xml)
        assert arrayHeader == ["name", "translatable", "values", "values-zh-rTW", "values-th", "values-ja", "values-zh-rCN"]
    }

    void testGetNameList(){
//        def nameList = readXml.getArrayNameList(xml)
//        println "NameList: " + nameList
    }

    void testGetArrayValueList(){
        def translatableMap = readXml.getTranslatableArray(xml, ReadXml.STRING_ARRAY)
        def arrayMap = readXml.getArrayMap(xml, ReadXml.STRING_ARRAY)
        def nameList = readXml.getArrayNameList(xml, ReadXml.STRING_ARRAY)
        def valueList = readXml.getValueList(arrayMap, nameList, translatableMap)
        println "Array Value List: " + readXml.getArrayValueList(arrayMap, nameList, translatableMap)
    }

    void testParseString()
    {
        ReadXml.parseAll(XmlDir, StringsDestination)
    }
}
