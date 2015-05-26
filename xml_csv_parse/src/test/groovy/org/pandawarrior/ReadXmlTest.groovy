package org.pandawarrior

/**
 * Created by jt on 5/26/15.
 */
class ReadXmlTest extends GroovyTestCase {
    static String XmlDir = "/home/jt/Dev/tableapp-restaurant-bookings/tableapp/src/main/res"
    def xml
    ReadXml readXml
    @Override
    void setUp() {
        super.setUp();
        readXml = new ReadXml();
        xml = readXml.getXmlFromFolder(XmlDir)
    }

    void testGetArrays(){
//        println "ArrayMap: " + readXml.getArrayMap(xml)
    }

    void testGetTranslatableArrays(){
        def translatableMap = readXml.getTranslatableArray(xml)
//        println "TranlatableMap" + translatableMap
    }

    void testGetArrayHeader(){
        def arrayHeader = readXml.getHeaderList(xml)
        assert arrayHeader == ["name", "translatable", "values", "values-zh-rTW", "values-th", "values-ja", "values-zh-rCN"]
    }

    void testGetNameList(){
        def nameList = readXml.getArrayNameList(xml)
//        println "NameList: " + nameList
    }

    void testGetArrayValueList(){
        def translatableMap = readXml.getTranslatableArray(xml)
        def arrayMap = readXml.getArrayMap(xml)
        def nameList = readXml.getArrayNameList(xml)
//        def valueList = readXml.getValueList(arr, nameList, translatableMap)
        println "Array Value List: " + readXml.getArrayValueList(arrayMap, nameList, translatableMap)
    }

    void testParseArray(){
        readXml.parseArray(XmlDir, "/home/jt/Desktop/newArray.csv")
    }

}
