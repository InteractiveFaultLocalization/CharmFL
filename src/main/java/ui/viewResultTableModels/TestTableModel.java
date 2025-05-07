package ui.viewResultTableModels;

import models.bean.*;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.jetbrains.annotations.Nls;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Map;

public class TestTableModel  extends AbstractTableModel {

    private final ArrayList<TableData> tableDataList = new ArrayList<>();

    private static final String[] columnNames = {"Test Name", "Result", "Heuristic"};

    public static final int TESTNAME_COLUMN_INDEX = 0;
    public static final int RESULT_COLUMN_INDEX = 1;
    public static final int HEURISTIC_COLUMN_INDEX = 2;

    public TestTableModel(ViewTestData testData) {
        parseData(testData);
    }
    private void parseData(ViewTestData testData) {
        for (Map.Entry<String, ArrayList<String>> entry : testData.getTests().entrySet()) {
            String testname = entry.getKey();
            ArrayList<String> values = entry.getValue();

            if (values.size() < 2) {
                continue;
            }

            String result = values.get(0);
            String heuristic = values.get(1);

            TableData tableData = new TableData();
            tableData.setTestName(testname);
            tableData.setTestResult(result);
            tableData.setTestHeuristic(heuristic);

            this.tableDataList.add(tableData);
        }
    }

    @Override
    public int getRowCount() {
        int count = 0;
        for (TableData tableData : tableDataList) {
            if (!tableData.isHide()) count++;
        }
        return count;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Nls
    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case TESTNAME_COLUMN_INDEX:
                return String.class;
            case RESULT_COLUMN_INDEX:
                return String.class;
            case HEURISTIC_COLUMN_INDEX:
                return String.class;
            default:
                return Object.class;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TableData tableDataAtRowIndex = tableDataList.get(rowIndex);
        switch (columnIndex) {
            case TESTNAME_COLUMN_INDEX:
                return tableDataAtRowIndex.getTestName();

            case RESULT_COLUMN_INDEX:
                return tableDataAtRowIndex.getTestResult();
            case HEURISTIC_COLUMN_INDEX:
                return tableDataAtRowIndex.getTestHeuristic();
            default:
                return "";
        }
    }
}
