package org.pandawarrior.androidXMLParser;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.project.Project;
import jetbrains.buildServer.messages.serviceMessages.Message;

import javax.swing.*;

/**
 * Created by jt on 5/12/15.
 */
public class XMLsToCSV extends AnAction {
    public void actionPerformed(AnActionEvent event) {
        final Project project = (Project) DataKeys.PROJECT.getData(event.getDataContext());
        final String initialFolder = project.getBasePath();
        GUI gui = new GUI(project, initialFolder);
        gui.show();
    }

}


