package org.pandawarrior

import org.pandawarrior.ReadXml

/**
 * Created by jt on 5/10/15.
 */
class ReadXmlTest extends GroovyTestCase {

    private static final String FOLDER = "/home/jt/Dev/tableapp-restaurant-bookings/tableapp"
    private static final String DESTINATION = "/home/jt/Documents/new.csv"


    void testGetXmlFromFolder() {
        List assertionList = ["values", "values-zh-rTW", "values-th", "values-ja", "values-zh-rCN"]
        List testList = ReadXml.getXmlFromFolder(FOLDER).keySet().toList()
        assert assertionList == testList
    }

    void testGetMainMap(){
        Map stringXMLMap = ReadXml.getXmlFromFolder(FOLDER)
        Map transList = ReadXml.getTranslatable(stringXMLMap)
        assert transList["app_name"] == false
    }

    void testGetNameList(){
        Map map = ReadXml.getXmlFromFolder(FOLDER)
        List nameList = ReadXml.getNameList(map)
        assert nameList.get(0) == "app_name"
    }

    void testGetValueList(){
        Map stringXMLMap = ReadXml.getXmlFromFolder(FOLDER)
        Map mainMap = ReadXml.getMainMap(stringXMLMap)
        Map translatable = ReadXml.getTranslatable(stringXMLMap)
        List nameList = ReadXml.getNameList(stringXMLMap)
        List valueList = ReadXml.getValueList(mainMap, nameList, translatable)
        assert valueList[0][0] == "app_name"
    }

    void testParse(){
        ReadXml.parse(FOLDER, DESTINATION)
        assert new File(DESTINATION).absolutePath == DESTINATION
    }

}
