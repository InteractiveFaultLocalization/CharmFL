package ui;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

import modules.ProjectModule;

import com.intellij.ui.jcef.*;
import services.Resources;

public class CallGraphHighlightedView extends DialogWrapper {

    public CallGraphHighlightedView() {
        super(true);
        setTitle(Resources.get("titles", "call_graph_title"));
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        String url = ProjectModule.getProjectPath() + "\\static_call_graph_highlighted.html";
        return new JBCefBrowser(url).getComponent();

    }
}