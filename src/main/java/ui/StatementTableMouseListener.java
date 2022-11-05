package ui;


import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.components.JBTabbedPane;
import models.bean.StatementTestData;
import models.bean.TestData;

import models.bean.context.Context;
import modules.PluginModule;
import modules.ProjectModule;
import ui.panels.ScorePanel;
import ui.viewResultTableModels.StatementTableModel;


import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class StatementTableMouseListener extends MouseInputAdapter {
    private final JTable resultTable;
    private final TestData testData;
    private ArrayList<StatementTestData> statements = new ArrayList<>();

    public StatementTableMouseListener(JTable resultTable, TestData testData) {
        this.resultTable = resultTable;
        this.testData = testData;
    }

    /**
     * If we click once with the right button on the mouse we get a pop up window.
     * Also it collects the data from the table
     * @param e
     */
    public void mouseClicked(MouseEvent e) {
        int selectedRow = resultTable.getSelectedRow();
        int selectedColumn = resultTable.getSelectedColumn();

        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                ArrayList<String> nameList = new ArrayList<>();
                ArrayList<Integer> lineList = new ArrayList<>();
                ArrayList<Double> rankList = new ArrayList<>();
                for (int i = 0; i < resultTable.getRowCount(); i++) {
                    nameList.add(resultTable.getValueAt(i, StatementTableModel.NAME_COLUMN_INDEX).toString());
                    lineList.add((Integer) resultTable.getValueAt(i, StatementTableModel.LINE_COLUMN_INDEX));
                    rankList.add((Double) resultTable.getValueAt(i, StatementTableModel.RANK_COLUMN_INDEX));
                }
                dummy(selectedRow,nameList, lineList);
                StatementOptions dialog = new StatementOptions(nameList, lineList, rankList, testData, selectedRow);
                dialog.showAndGet();
            }

    }


    private void dummy(int currentRow, ArrayList<String>paths, ArrayList<Integer>lines){
        ToolWindow window = ToolWindowManager.getInstance(ProjectModule.getProject()).getToolWindow("Scores");
        JBTabbedPane tp = (JBTabbedPane) window.getComponent().getComponent(0);
        //System.out.println(tp.getTitleAt(0));//Scores
        //System.out.println(tp.getTitleAt(1));//Byte my shiny metal ass
        //System.out.println(tp.getTitleAt(2));//Cica

        JPanel panel = (JPanel) tp.getComponent(0);
        ScorePanel cmp = (ScorePanel)panel.getComponent(0);
        ScorePanel cls = (ScorePanel)panel.getComponent(1);
        ScorePanel far = (ScorePanel)panel.getComponent(2);
        ScorePanel oth = (ScorePanel)panel.getComponent(3);


        cmp.calculateScore(TestData.getInstance().getElement(paths.get(currentRow), lines.get(currentRow)), Context.COMPONENT, PluginModule.getSelectedFormula());
        cls.calculateScore(TestData.getInstance().getElement(paths.get(currentRow), lines.get(currentRow)), Context.CLOSE_CONTEXT, PluginModule.getSelectedFormula());
        far.calculateScore(TestData.getInstance().getElement(paths.get(currentRow), lines.get(currentRow)), Context.FAR_CONTEXT, PluginModule.getSelectedFormula());
        oth.calculateScore(TestData.getInstance().getElement(paths.get(currentRow), lines.get(currentRow)), Context.OTHER, PluginModule.getSelectedFormula());

        //Such a nice oneliner
        //ScorePanel myScorePanel = (ScorePanel)((JPanel)((JBTabbedPane)ToolWindowManager.getInstance(ProjectModule.getProject()).getToolWindow("Scores").getComponent().getComponent(0)).getComponent(0)).getComponent(0);
    }
}
