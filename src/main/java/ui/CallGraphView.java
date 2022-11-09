package ui;

import com.intellij.openapi.ui.DialogWrapper;
import modules.PluginModule;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

import modules.ProjectModule;

import com.intellij.ui.jcef.*;
import services.Resources;

public class CallGraphView extends DialogWrapper {

    public CallGraphView() {
        super(true);
        setTitle(Resources.get("titles", "call_graph_title"));
        setModal(false);
        init();

    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        String url = ProjectModule.getProjectPath() + "\\static_call_graph.html";
        return new JBCefBrowser(url).getComponent();

    }
}