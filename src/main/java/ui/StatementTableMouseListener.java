package ui;

import models.bean.ClassTestData;
import models.bean.MethodTestData;
import models.bean.StatementTestData;
import models.bean.TestData;
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

        if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON3) {
            if (selectedColumn == StatementTableModel.NAME_COLUMN_INDEX) {
                ArrayList<String> nameList = new ArrayList<>();
                ArrayList<Integer> lineList = new ArrayList<>();
                ArrayList<Double> rankList = new ArrayList<>();
                for (int i = 0; i < resultTable.getRowCount(); i++) {
                    nameList.add(resultTable.getValueAt(i, StatementTableModel.NAME_COLUMN_INDEX).toString());
                    lineList.add((Integer) resultTable.getValueAt(i, StatementTableModel.LINE_COLUMN_INDEX));
                    rankList.add((Double) resultTable.getValueAt(i, StatementTableModel.RANK_COLUMN_INDEX));
                }
                StatementOptions dialog = new StatementOptions(nameList, lineList, rankList, testData, selectedRow);
                dialog.showAndGet();
            }
        }
    }
}
