package ui.listener;


import models.bean.StatementTestData;
import models.bean.TestData;


import ui.viewResultTableModels.StatementTableModel;


import javax.swing.*;

import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class StatementTableMouseListener extends AbstractTableMouseListener {

    public StatementTableMouseListener(JTable resultTable, TestData testData) {
        super(resultTable,testData);
    }


    public void mouseClicked(MouseEvent e) {
        int selectedRow = resultTable.getSelectedRow();

        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                String name = resultTable.getValueAt(selectedRow, StatementTableModel.NAME_COLUMN_INDEX).toString();
                int line = (int) resultTable.getValueAt(selectedRow, StatementTableModel.LINE_COLUMN_INDEX);

                updateIndicatorPanel(name, line);
        }
    }
}
