package ui;

import static ui.viewResultTableModels.TreeTableModel.LINE_COLUMN_INDEX;
import static ui.viewResultTableModels.TreeTableModel.NAME_COLUMN_INDEX;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import modules.ProjectModule;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import ui.viewResultTableModels.TreeTableModel;

/**
 * Factory class for the floating window
 */

public class ScoresFloatingWindow implements ToolWindowFactory, DumbAware {
    public boolean isApplicable(@NotNull Project project) {
        return ToolWindowFactory.super.isApplicable(project);
    }
    JLabel line = new JLabel();
    Project project;
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ToolWindowFactory.super.init(toolWindow);
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        this.project = project;
        toolWindow.setTitle("Context Window");
        line.setText(String.valueOf(editor.getCaretModel().getLogicalPosition().line));

        toolWindow.getComponent().add(line);
        toolWindow.setAvailable(true);
        toolWindow.show();
    }

    @Override
    public void init(@NotNull ToolWindow toolWindow) {

    }

    @Override
    public boolean shouldBeAvailable(@NotNull Project project) {
        return ToolWindowFactory.super.shouldBeAvailable(project);
    }


}
