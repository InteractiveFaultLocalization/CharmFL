package ui.listener;


import models.bean.StatementTestData;
import models.bean.TestData;

import models.bean.context.Context;
import modules.PluginModule;
import ui.StatementOptions;
import ui.panels.ScorePanel;
import ui.viewResultTableModels.StatementTableModel;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class StatementTableMouseListener extends AbstractTableMouseListener {

    private ArrayList<StatementTestData> statements = new ArrayList<>();

    public StatementTableMouseListener(JTable resultTable, TestData testData) {
        super(resultTable,testData);
    }

    /**
     * If we click once with the right button on the mouse we get a pop up window.
     * Also it collects the data from the table
     * @param e
     */
    public void mouseClicked(MouseEvent e) {
        int selectedRow = resultTable.getSelectedRow();

        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                String name = resultTable.getValueAt(selectedRow, StatementTableModel.NAME_COLUMN_INDEX).toString();
                int line = (int) resultTable.getValueAt(selectedRow, StatementTableModel.LINE_COLUMN_INDEX);

                updateIndicatorPanel(name, line);
        }
    }
}
