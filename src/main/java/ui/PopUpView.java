package ui;

import com.intellij.openapi.ui.DialogWrapper;
import java.io.File;
import java.nio.file.Path;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

import modules.ProjectModule;

import com.intellij.ui.jcef.*;
import services.Resources;

public class PopUpView extends DialogWrapper {
    private String filename;
    public PopUpView(String filename) {
        super(true);
        //setTitle(Resources.get("titles", ));
        this.filename = filename;
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        String url = ProjectModule.getProjectPath() + File.separator + filename;
        System.out.println(url);
        return new JBCefBrowser(url).getComponent();

    }
}