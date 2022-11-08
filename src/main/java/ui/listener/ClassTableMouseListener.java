package ui.listener;

import models.bean.ClassTestData;
import models.bean.TestData;
import ui.viewResultTableModels.ClassTableModel;


import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

public class ClassTableMouseListener extends AbstractTableMouseListener{

    public ClassTableMouseListener(JTable resultTable, TestData testData) {
        super(resultTable, testData);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int selectedRow = resultTable.getSelectedRow();
        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
            List<ClassTestData> classes = TestData.getInstance().getClasses();
            String fullName = resultTable.getValueAt(selectedRow, ClassTableModel.NAME_COLUMN_INDEX).toString();
            String[] temp = fullName.split("\\\\");
            String name = temp[temp.length-1];

            ClassTestData selected = classes.stream().filter(x -> name.equals(x.getName())).collect(Collectors.toList()).get(0);

            updateIndicatorPanel(resultTable.getValueAt(selectedRow,
                                                        ClassTableModel.FILE_NAME_COLUMN_INDEX).toString(),
                                                        name,
                                                        selected.getLine());
        }
    }
}
