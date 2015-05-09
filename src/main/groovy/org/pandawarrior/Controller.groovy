package org.pandawarrior

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TextField
import javafx.stage.DirectoryChooser
import javafx.stage.FileChooser
import javafx.stage.Stage

/**
 * Created by jt on 5/10/15.
 */
class Controller{

    @FXML
    public TextField x2c_txtXMLFolder
    @FXML
    public TextField x2c_txtCSVFile
    @FXML
    public TextField c2x_txtCSVFile
    @FXML
    public TextField c2x_txtXMLFolder

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
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV File (*.csv)", "*.csv")
        )
        chooser.setInitialFileName("new.csv")
        File file = chooser.showSaveDialog(new Stage());
        x2c_txtCSVFile.setText "${file.absolutePath}"
        println x2c_txtCSVFile.characters
    }

    public void x2c_runXMLtoCSV(ActionEvent actionEvent) {
        ReadXml.parse(x2c_txtXMLFolder.getText(), x2c_txtCSVFile.getText())
    }

    public void c2x_openCSVFile(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
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
        WriteXml.parse(c2x_txtCSVFile.getText(), c2x_txtXMLFolder.getText())
    }



}
