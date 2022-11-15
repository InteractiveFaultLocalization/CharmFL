package ui;

import com.intellij.openapi.ui.DialogWrapper;
import java.io.File;
import java.io.IOException;

import org.jetbrains.annotations.Nullable;

import javax.swing.*;

import modules.ProjectModule;

public class PopUpView extends DialogWrapper {
    private String filename;
    public PopUpView(String filename) {
        super(true);
        //setTitle(Resources.get("titles", ));
        this.filename = filename;
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        String url = ProjectModule.getProjectPath() + File.separator + filename;
        JOptionPane.showMessageDialog(null, url);
        System.out.println(url);
        JEditorPane htmlPane = null;
        try {
            htmlPane = new JEditorPane(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        htmlPane.setContentType("text/html");

        return htmlPane.getRootPane();
    }
}