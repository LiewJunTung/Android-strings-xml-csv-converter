package org.pandawarrior

import javafx.event.ActionEvent
import javafx.scene.control.Label
import javafx.scene.control.RadioButton
import javafx.scene.control.TextField
import javafx.scene.control.ToggleGroup
import javafx.scene.paint.Color
import javafx.stage.DirectoryChooser
import javafx.stage.FileChooser
import javafx.stage.Stage
import org.pandawarrior.app.CSVToXMLKt
import org.pandawarrior.app.TranslationType
import org.pandawarrior.app.XMLToCSVKt

/**
 * Created by jt on 5/10/15.
 */
class Controller {

    public TextField x2c_txtXMLFolder
    public TextField x2c_txtCSVFile
    public TextField c2x_txtCSVFile
    public TextField c2x_txtXMLFolder
    public Label c2x_indicator
    public Label x2c_indicator
    public RadioButton c2x_strings_radio
    public ToggleGroup myToggleGroup
    public RadioButton c2x_plurals_radio
    public RadioButton c2x_arrays_radio

    /**
     * XML to CSV
     * @param actionEvent
     */
    public void x2c_openXMLFolder(ActionEvent actionEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select folder");
        File folder = chooser.showDialog(new Stage());
        x2c_txtXMLFolder.setText folder.absolutePath
    }

    public void x2c_saveDestination(ActionEvent actionEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select Save Folder");
        File file = chooser.showDialog(new Stage())
        x2c_txtCSVFile.setText "${file.absolutePath}"

    }

    void x2c_runXMLtoCSV(ActionEvent actionEvent) {
        try {
            String folderPath = x2c_txtXMLFolder.getText()
            String filePath = x2c_txtCSVFile.getText()
            if (folderPath.length() > 0 && filePath.length() > 0) {
                XMLToCSVKt.processXMLToCSV(folderPath, filePath, TranslationType.NORMAL)
                XMLToCSVKt.processXMLToCSV(folderPath, filePath, TranslationType.PLURALS)
                XMLToCSVKt.processXMLToCSV(folderPath, filePath, TranslationType.ARRAYS)
                x2c_txtCSVFile.clear()
                x2c_txtXMLFolder.clear()
                x2c_indicator.setText("SUCCESS!")
                x2c_indicator.setTextFill(Color.GREEN)
            } else {
                x2c_indicator.setText("Please set folder or file location!")
                x2c_indicator.setTextFill(Color.RED)
            }
        } catch (Exception e) {
            if (e.message != null) {
                x2c_indicator.setText(e.message)
            } else {
                x2c_indicator.setText("Error! Try adding a space or null in the empty values")
            }
            x2c_indicator.setTextFill(Color.RED)
        }

    }

    public void c2x_openCSVFile(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File")
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV File (*.csv)", "*.csv")
        )
        File file = chooser.showOpenDialog(new Stage());
        c2x_txtCSVFile.setText "${file.absolutePath}"
    }

    public void c2x_saveDestination(ActionEvent actionEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select folder");
        File folder = chooser.showDialog(new Stage());
        c2x_txtXMLFolder.setText folder.absolutePath
    }

    public void c2x_runCSVtoXML(ActionEvent actionEvent) {
        try {
            String selected = (myToggleGroup.selectedToggle as RadioButton).text
            String csvFilePath = c2x_txtCSVFile.getText()
            String xmlFolderPath = c2x_txtXMLFolder.getText()

            boolean result
            switch (selected) {
                case "arrays.xml":
                    result = CSVToXMLKt.processCSVToXML(csvFilePath, xmlFolderPath, TranslationType.ARRAYS)
                    break;
                case "plurals.xml":
                    result = CSVToXMLKt.processCSVToXML(csvFilePath, xmlFolderPath, TranslationType.PLURALS)
                    // result = WriteXml.parseArray(c2x_txtCSVFile.getText(), c2x_txtXMLFolder.getText(), WriteXml.PLURALS_FILE)
                    break;
                default:
                    result = CSVToXMLKt.processCSVToXML(csvFilePath, xmlFolderPath, TranslationType.NORMAL)
            //result = WriteXml.parse(c2x_txtCSVFile.getText(), c2x_txtXMLFolder.getText())
            }

            c2x_txtCSVFile.clear()
            c2x_indicator.setText("SUCCESS!")
            c2x_indicator.setTextFill(Color.GREEN)

        } catch (Exception e) {
            if (e.message != null && e.message.length() > 0) {
                c2x_indicator.setText(e.message)
            } else {
                c2x_indicator.setText("Error! Try adding a space or null in the empty values")
            }
            c2x_indicator.setTextFill(Color.RED)
        }

    }


}
