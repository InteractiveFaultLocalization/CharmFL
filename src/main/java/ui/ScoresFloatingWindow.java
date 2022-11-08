package ui;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBTabbedPane;
import org.jetbrains.annotations.NotNull;
import ui.panels.ScorePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;
import java.util.List;


/**
 * Factory class for the floating window
 */


public class ScoresFloatingWindow implements ToolWindowFactory, DumbAware{
    private Map<String, ScorePanel> indicators;
    private List<JComponent> panels;

    @Override
    public boolean isApplicable(@NotNull Project project) {
        return ToolWindowFactory.super.isApplicable(project);
    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {

        this.init(toolWindow);
        JBTabbedPane mainPanel = new JBTabbedPane();
        mainPanel.setBounds(50,50,220,200);
        mainPanel.add("Scores",createIndicatorPanel());
        //TODO: Here comes the one of the remaining two components
        // TODO: Also do not forget to add it to the panels list
        mainPanel.add("Byte my shiny metal ass",new JPanel());
        //TODO: And here the other
        //TODO: same todo here!
        mainPanel.add("Cica",new CallGraphView().createCenterPanel());

        toolWindow.getComponent().add(mainPanel);
    }

    @Override
    public void init(@NotNull ToolWindow toolWindow) {
        ToolWindowFactory.super.init(toolWindow);
        indicators = new LinkedHashMap<>();
        panels = new ArrayList<>();
    }

    @Override
    public boolean shouldBeAvailable(@NotNull Project project) {
        return ToolWindowFactory.super.shouldBeAvailable(project);
    }

    private JComponent createIndicatorPanel(){
        JPanel indicatorPanel = new JPanel();
        indicatorPanel.setLayout(new GridLayout(1,4));
        indicatorPanel.setBorder(new EmptyBorder(10, 5, 10, 5));
        indicatorPanel.setPreferredSize(new Dimension(620,400));
        //todo
        indicators.put("Comp",new ScorePanel("Component"));
        indicators.put("Close",new ScorePanel("Close Context"));
        indicators.put("Far",new ScorePanel("Far Context"));
        indicators.put("Other",new ScorePanel("Other"));
        indicators.forEach((panelID,scorePanel)->indicatorPanel.add(scorePanel));
        panels.add(indicatorPanel);

        return indicatorPanel;
    }

    public Map<String, ScorePanel> getIndicators() {
        return indicators;
    }
}
