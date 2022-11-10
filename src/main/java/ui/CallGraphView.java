package ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import modules.PluginModule;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

import modules.ProjectModule;

import com.intellij.ui.jcef.*;
import services.Resources;

import java.io.File;

public class CallGraphView extends DialogWrapper {
    private Project project;
    private String fileName;
    public CallGraphView(String fileName, Project project) {
        super(true);
        this.project = project;
        this.fileName = fileName;
        setTitle(Resources.get("titles", "call_graph_title"));
        setModal(false);
        init();

    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        StringBuilder url = new StringBuilder();
        url.append(project.getBasePath())
           .append(File.separator)
           .append(fileName);
        return new JBCefBrowser(url.toString()).getComponent();

    }
}