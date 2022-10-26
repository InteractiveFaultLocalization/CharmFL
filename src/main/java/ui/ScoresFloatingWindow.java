package ui;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Factory class for the floating window
 */

public class ScoresFloatingWindow implements ToolWindowFactory, DumbAware{
    public boolean isApplicable(@NotNull Project project) {
        return ToolWindowFactory.super.isApplicable(project);
    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ToolWindowFactory.super.init(toolWindow);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,4));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setPreferredSize(new Dimension(600,400));

        mainPanel.add(new ScorePanel("Component",1.));
        mainPanel.add(new ScorePanel("Close Context",1.));
        mainPanel.add(new ScorePanel("Far Context",1.));
        mainPanel.add(new ScorePanel("Other",1.));
        toolWindow.getComponent().add(mainPanel);
    }

    @Override
    public void init(@NotNull ToolWindow toolWindow) {

    }

    @Override
    public boolean shouldBeAvailable(@NotNull Project project) {
        return ToolWindowFactory.super.shouldBeAvailable(project);
    }
}
