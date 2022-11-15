package ui.listener;

import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.components.JBTabbedPane;
import models.bean.TestData;
import models.bean.context.Context;
import modules.PluginModule;
import modules.ProjectModule;
import ui.panels.ScorePanel;
import ui.viewResultTableModels.TreeTableModel;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;

public abstract class AbstractTableMouseListener extends MouseInputAdapter {
    protected final JTable resultTable;
    protected final TestData testData;
    protected final TreeTableModel tableModel;

    public AbstractTableMouseListener(JTable resultTable, TestData testData) {
        this.resultTable = resultTable;
        this.testData = testData;
        this.tableModel = null;
    }

    public AbstractTableMouseListener(JTable resultTable, TreeTableModel tableModel) {
        this.resultTable = resultTable;
        this.tableModel = tableModel;
        this.testData = null;
    }

    protected void updateIndicatorPanel(String path, String name, int lineNumber){
//        try {
            Component[] panels = (Component[]) getFloatingWindowComponent(0);
            ScorePanel cmp = (ScorePanel) panels[0];
            ScorePanel cls = (ScorePanel) panels[1];
            ScorePanel far = (ScorePanel) panels[2];
            ScorePanel oth = (ScorePanel) panels[3];

            cmp.setLabel(name + String.valueOf(lineNumber));

            cmp.calculateScore(TestData.getInstance().getElement(path, lineNumber), Context.COMPONENT, PluginModule.getSelectedFormula());
            cls.calculateScore(TestData.getInstance().getElement(path, lineNumber), Context.CLOSE_CONTEXT, PluginModule.getSelectedFormula());
            far.calculateScore(TestData.getInstance().getElement(path, lineNumber), Context.FAR_CONTEXT, PluginModule.getSelectedFormula());
            oth.calculateScore(TestData.getInstance().getElement(path, lineNumber), Context.OTHER, PluginModule.getSelectedFormula());
//        }catch(Exception e){
//            //This should fix any issue caused by non-existing components... for now at least...
//        }
    }

    protected static Object getFloatingWindowComponent(int order){
        ToolWindow window = ToolWindowManager.getInstance(ProjectModule.getProject()).getToolWindow("Scores");
        JBTabbedPane tp = (JBTabbedPane) window.getComponent().getComponent(0);
        JPanel panel = (JPanel) tp.getComponent(0);
        switch(order){
            default:    return null;
            case 0:     return  panel.getComponents();
            case 1:     return  tp.getComponent(1);
            case 2:     return  tp.getComponent(2);
        }
    }

}
