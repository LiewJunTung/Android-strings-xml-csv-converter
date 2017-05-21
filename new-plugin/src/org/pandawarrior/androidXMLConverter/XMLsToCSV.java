package org.pandawarrior.androidXMLConverter;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.project.Project;

/**
 * Created by jt on 5/12/15.
 */
public class XMLsToCSV extends AnAction {
    public void actionPerformed(AnActionEvent event) {
        final Project project = DataKeys.PROJECT.getData(event.getDataContext());
        if (project == null){
            return;
        }
        final String initialFolder = project.getBasePath();
        GUI gui = new GUI(project, initialFolder);
        gui.show();
    }

}


