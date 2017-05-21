package org.pandawarrior.androidXMLConverter;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.JBColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.pandawarrior.app.*

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
    private final static String ERROR_MSG1 = "Error! ";

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
                String folder = chooseFolder();
                if (folder != null)
                    xcReadField.setText(folder);
            }
        });
        xcWriteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String folder = chooseFolder();
                if (folder != null)
                    xcWriteField.setText(folder);
            }
        });
        xcOKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String folder = xcReadField.getText();
                String csv = xcWriteField.getText();
                readXML(folder, csv);
            }
        });
        cxReadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String csv = chooseCSV();
                if (csv != null)
                    cxReadField.setText(csv);
            }
        });
        cxWriteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String folder = chooseFolder();
                if (folder != null)
                    cxWriteField.setText(folder);
            }
        });
        cxOKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String csv = cxReadField.getText();
                String folder = cxWriteField.getText();
                writeXML(folder, csv);

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
            return null;
        }
    }

    private String saveCSV() {
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
            return null;
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
            return null;
        }
    }

    private void writeXML(final String folder, final String csv) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    if (selected.equals("strings.xml")) {
                        WriteXml.parse(csv, folder);
                    } else if (selected.equals("arrays.xml")) {
                        WriteXml.parseArray(csv, folder, WriteXml.getARRAY_FILE());
                    } else if (selected.equals("plurals.xml")) {
                        WriteXml.parseArray(csv, folder, WriteXml.getPLURALS_FILE());
                    }
                    cxReadField.setText("");
                    cxWriteField.setText("");
                    cxLabel.setText("SUCCESS!");
                    cxLabel.setForeground(JBColor.GREEN);
                } catch (ArrayIndexOutOfBoundsException e1) {
                    cxLabel.setText(ERROR_MSG1 + e1.getMessage());
                    cxLabel.setForeground(JBColor.RED);
                }

            }
        });

    }

    private void readXML(final String folder, final String csv) throws ArrayIndexOutOfBoundsException {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    xcReadField.setText("");
                    xcLabel.setText("SUCCESS!");
                    xcLabel.setForeground(JBColor.GREEN);
                } catch (Exception e) {
                    xcLabel.setText(ERROR_MSG1 + e.getMessage());
                    xcLabel.setForeground(JBColor.RED);
                }

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
