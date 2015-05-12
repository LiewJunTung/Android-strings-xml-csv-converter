package org.pandawarrior

import org.pandawarrior.WriteXml

/**
 * Created by jt on 5/10/15.
 */
class WriteXmlTest extends GroovyTestCase {
    private final static String csvPath = "/home/jt/Documents/new.csv"
    private final static String xmlPath = "/home/jt/Documents/"

    void testParse(){
        assert WriteXml.parse(csvPath, xmlPath)
    }

}
