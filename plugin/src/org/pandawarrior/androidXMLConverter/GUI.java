package org.pandawarrior.androidXMLConverter;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.JBColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.pandawarrior.ReadXml;
import org.pandawarrior.WriteXml;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by jt on 5/13/15.
 */
public class GUI extends DialogWrapper {

    private JPanel rootPane;
    private JTabbedPane tabbedPane1;
    private JTextField xcReadField;
    private JTextField xcWriteField;
    private JButton xcWriteBtn;
    private JButton xcReadBtn;
    private JButton xcOKButton;
    private JButton cxReadBtn;
    private JButton cxWriteBtn;
    private JTextField cxReadField;
    private JTextField cxWriteField;
    private JButton cxOKButton;
    private JLabel xcLabel;
    private JLabel cxLabel;
    private JRadioButton stringsXmlRadioButton;
    private JRadioButton pluralsXmlRadioButton;
    private JRadioButton arraysXmlRadioButton;
    private String currentFolder;
    private String selected;
    private final static String ERROR_MSG1 = "Error! Your csv file might have a data that is empty. use 'null' or ' ' to replace it.";
    public GUI(Project project, String initialFolder) {
        super(project, true);
        this.setTitle("Android Parser");
        this.setSize(480, 300);
        this.setResizable(true);
       // setContentPane(rootPane);
       // setVisible(true);
        //setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        currentFolder = initialFolder;

        xcReadField.setText(currentFolder);
        xcReadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xcReadField.setText(chooseFolder());
            }
        });
        xcWriteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xcWriteField.setText(chooseFolder());
            }
        });
        xcOKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String folder = xcReadField.getText();
                    String csv = xcWriteField.getText();
                    readXML(folder, csv);
                    xcReadField.setText("");
                    xcWriteField.setText("");
                    xcLabel.setText("SUCCESS!");
                    xcLabel.setForeground(JBColor.GREEN);
                }catch (ArrayIndexOutOfBoundsException e1){
                    xcLabel.setText(ERROR_MSG1);
                    xcLabel.setForeground(JBColor.RED);
                }

            }
        });
        cxReadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cxReadField.setText(chooseCSV());
            }
        });
        cxWriteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cxWriteField.setText(chooseFolder());
            }
        });
        cxOKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String csv = cxReadField.getText();
                    String folder = cxWriteField.getText();
                    writeXML(folder, csv);
                    cxReadField.setText("");
                    cxWriteField.setText("");
                    cxLabel.setText("SUCCESS!");
                    cxLabel.setForeground(JBColor.GREEN);
                } catch (ArrayIndexOutOfBoundsException e1){
                    cxLabel.setText(ERROR_MSG1);
                    cxLabel.setForeground(JBColor.RED);
                }

            }
        });
        stringsXmlRadioButton.setSelected(true);
        selected = "strings.xml";
        stringsXmlRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selected = "strings.xml";
            }
        });
        pluralsXmlRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selected = "plurals.xml";
            }
        });
        arraysXmlRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selected = "arrays.xml";
            }
        });
        this.setOKActionEnabled(false);
        this.init();

    }
    private String chooseFolder() {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = jFileChooser.showOpenDialog(getContentPane());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return jFileChooser.getSelectedFile().getAbsolutePath();
        } else {
            return "";
        }
    }

    private String saveCSV(){
        JFileChooser jFileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV FILES", "csv", "comma separate value");
        jFileChooser.setFileFilter(filter);
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        File file = new File(currentFolder + "/untitled.txt");
        jFileChooser.setCurrentDirectory(file);
        int returnVal = jFileChooser.showSaveDialog(getContentPanel());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return jFileChooser.getSelectedFile().getAbsolutePath();
        } else {
            return "";
        }
    }

    private String chooseCSV() {
        JFileChooser jFileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV FILES", "csv", "comma separate value");
        jFileChooser.setFileFilter(filter);
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnVal = jFileChooser.showOpenDialog(getContentPane());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return jFileChooser.getSelectedFile().getAbsolutePath();
        } else {
            return "";
        }
    }

    private void writeXML(final String folder, final String csv) throws ArrayIndexOutOfBoundsException{
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (selected.equals("strings.xml")){
                    WriteXml.parse(csv, folder);
                }else if(selected.equals("arrays.xml")){
                    WriteXml.parseArray(csv, folder, WriteXml.getARRAY_FILE());
                }else if (selected.equals("plurals.xml")){
                    WriteXml.parseArray(csv, folder, WriteXml.getPLURALS_FILE());
                }
            }
        });

    }

    private void readXML(final String folder, final String csv) throws ArrayIndexOutOfBoundsException{
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ReadXml.parseAll(folder, csv);
            }
        });

    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return this.rootPane;
    }

    @NotNull
    protected Action[] createActions() {
        return new Action[0];
    }
}
