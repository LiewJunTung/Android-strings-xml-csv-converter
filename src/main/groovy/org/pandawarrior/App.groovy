package org.pandawarrior
/**
 * Created by jt on 5/9/15.
 */
class App {
    public static void main(String[] args) {
        String readDestination = args[1]
        String writeDestination = args[2]

        if (args[0] == "-w"){
            WriteXml.parse(readDestination, writeDestination)
        }else if (args[0] == "-r"){
            ReadXml.parse(readDestination, writeDestination);
        }
    }
}
