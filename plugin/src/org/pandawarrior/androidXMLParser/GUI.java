package org.pandawarrior.androidXMLParser;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
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
    private String currentFolder;
    private Project jProject;

    public GUI(Project project, String initialFolder) {
        super(project, true);
        this.setTitle("Android Parser");
        this.setResizable(true);
        //setBounds(100, 100, 450, 300);
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
                xcWriteField.setText(saveCSV());
            }
        });
        xcOKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String folder = xcReadField.getText();
                String csv = xcWriteField.getText();
                readXML(folder, csv);
                xcReadField.setText("");
                xcWriteField.setText("");
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
                String csv = cxReadField.getText();
                String folder = cxWriteField.getText();
                writeXML(folder, csv);
                cxReadField.setText("");
                cxWriteField.setText("");
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

    private void writeXML(final String folder, final String csv){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                WriteXml.parse(csv, folder);
            }
        });

    }

    private void readXML(final String folder, final String csv){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ReadXml.parse(folder, csv);
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
