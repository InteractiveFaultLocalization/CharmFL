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
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


/**
 * Factory class for the floating window
 */


public class ScoresFloatingWindow implements ToolWindowFactory, DumbAware{
    private Map<String, ScorePanel> indicators;


    public boolean isApplicable(@NotNull Project project) {
        return ToolWindowFactory.super.isApplicable(project);
    }
    JLabel line = new JLabel();
    Project project;
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {

        this.init(toolWindow);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,4));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setPreferredSize(new Dimension(600,400));

        indicators.put("Comp",new ScorePanel("Component",1.));
        indicators.put("Close",new ScorePanel("Close Context",1.));
        indicators.put("Far",new ScorePanel("Far Context",1.));
        indicators.put("Other",new ScorePanel("Other",1.));

        indicators.forEach((panelID,scorePanel)->mainPanel.add(scorePanel));

        toolWindow.getComponent().add(mainPanel);

    }

    @Override
    public void init(@NotNull ToolWindow toolWindow) {
        ToolWindowFactory.super.init(toolWindow);
        indicators = new HashMap<>();
    }

    @Override
    public boolean shouldBeAvailable(@NotNull Project project) {
        return ToolWindowFactory.super.shouldBeAvailable(project);
    }


}
