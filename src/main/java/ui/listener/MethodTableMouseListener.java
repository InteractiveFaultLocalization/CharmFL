package ui.listener;

import models.bean.ITestData;
import models.bean.MethodTestData;
import models.bean.TestData;
import ui.viewResultTableModels.MethodTableModel;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MethodTableMouseListener extends AbstractTableMouseListener {

    public MethodTableMouseListener(JTable resultTable, TestData testData) {
        super(resultTable, testData);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int selectedRow = resultTable.getSelectedRow();
        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
            ArrayList<ITestData> methods = TestData.getInstance().getAllMethods();
            String fullName = resultTable.getValueAt(selectedRow, MethodTableModel.NAME_COLUMN_INDEX).toString();
            String[] temp = fullName.split("\\\\");
            String name = temp[temp.length - 1];
            System.out.println(methods.stream().anyMatch(x -> name.equals(x.getName())));
            MethodTestData selected = (MethodTestData) methods.stream().filter(x -> name.equals(x.getName())).collect(Collectors.toList()).get(0);
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            System.out.println(selected.getName());
            for (var a : selected.getFarContext()) {
                System.out.println(a.getName());
                System.out.println(a.getLine());
            }
            //System.out.println(selected.getName() + " " + selected.getLine());
            updateIndicatorPanel(resultTable.getValueAt(selectedRow,
                            MethodTableModel.FILE_NAME_COLUMN_INDEX).toString(),
                    name,
                    selected.getLine());
        }
    }

}
