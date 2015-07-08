#Android String XML-CSV Converter
[![Build Status](https://travis-ci.org/pandawarrior91/Android-strings-xml-csv-converter.svg)](https://travis-ci.org/pandawarrior91/Android-strings-xml-csv-converter)
##What does it do?
####It will convert strings.xml, arrays.xml, plurals.xml
![Image of CSV](https://github.com/pandawarrior91/Android-strings-xml-csv-converter/blob/master/strings.png)
####to CSV
![Image of CSV](https://github.com/pandawarrior91/Android-strings-xml-csv-converter/blob/master/csv.png)
####and back!

##Why?
You want to translate strings, arrays, plurals in strings.xml, arrays.xml, and plurals.xml into different 
languages in csv so you can use as spreadsheet in Excel, Google Drive

##Requirement
Java JRE 8
or
Jetbrains IDE

##How to use it?
1. Download the file
   * [groovy-app.zip](https://github.com/pandawarrior91/Android-strings-xml-csv-converter/releases/download/v0.10.0/groovy-app.zip)
   * [groovy-app.tar](https://github.com/pandawarrior91/Android-strings-xml-csv-converter/releases/download/v0.10.0/groovy-app.tar)
2. Unzip or untar the file
3. Open groovy-app/bin/groovy-app (groovy-app.bat if you are on Windows)
4. From here, with the GUI onwards it's pretty self explanatory. Select Android folder for the value and the location of your saved csv.
![Image of CSV](https://github.com/pandawarrior91/Android-strings-xml-csv-converter/blob/master/gui.png)

####OR
Download and install the IntelliJ or Android Studio Plugin from the jetbrains repository or [here](https://github.com/pandawarrior91/Android-strings-xml-csv-converter/releases/download/v0.10.0/android.xml.csv.parser.plugin.zip)

##NOTE
1. ~~It won't convert arrays and plurals (yet), make sure you save them in other files such as array.xml~~

##TODO
- [x] Ability to convert arrays and plurals
- [x] IntelliJ and Android Studio plugin
- [] Clean up the (very ugly) codes
- [x] Enable the application to run in Java version less than 8
- [x] Added support for commas
