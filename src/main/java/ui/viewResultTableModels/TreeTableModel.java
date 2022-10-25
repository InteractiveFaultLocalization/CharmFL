package ui.viewResultTableModels;

import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.jetbrains.annotations.Nls;

import models.bean.*;

public class TreeTableModel implements TableModel {
    private static final ImageIcon TABLE_ARROW_DOWN_ICON = new ImageIcon(TreeTableModel.class.getClassLoader().getResource("table-icons/arrow-down.png"));
    private static final ImageIcon TABLE_OPEN_EDITOR_ICON = new ImageIcon(TreeTableModel.class.getClassLoader().getResource("table-icons/open-editor.png"));
    private static final String[] columnNames = {"Name", "Line", "Action"};
    private ArrayList<String> files = new ArrayList<>();

    public static final int NAME_COLUMN_INDEX = 0;
    public static final int LINE_COLUMN_INDEX = 1;
    public static final int ACTION_COLUMN_INDEX = 2;

    private final ArrayList<TableData> tableDataList = new ArrayList<>();


    public static final String TABLE_ROW_IDENT_PREFIX = "    ";

    public TreeTableModel(TestData testData) {
        parseData(testData);
    }

    public ArrayList<TableData> getTableDataList() {
        return tableDataList;
    }

    /**
     * This parses the TestDatas to TableData
     *
     * @param data
     */
    private void parseData(TestData data) {
        for (var fileData : data.getClasses()) {
            String fileRelativePath = fileData.getRelativePath();

            TableData fileTableData = new TableData();
            fileTableData.setName(fileRelativePath);
            fileTableData.setIcon(TABLE_ARROW_DOWN_ICON);

            if(!isFileInTheHierarchicalView(fileRelativePath)) {
                this.tableDataList.add(fileTableData);

                for (ClassTestData classData : data.getClasses()) {
                    String relativePath = classData.getRelativePath();

                    TableData classTableData = new TableData();
                    classTableData.setName(TABLE_ROW_IDENT_PREFIX + classData.getName());
                    classTableData.setPath(relativePath);
                    classTableData.setLine(classData.getLine());
                    classTableData.setTarantulaScore(classData.getTarantula());
                    classTableData.setOchiaiScore(classData.getOchiai());
                    classTableData.setWong2Score(classData.getWong2());
                    classTableData.setFaulty(classData.isFaulty());
                    classTableData.setIcon(TABLE_ARROW_DOWN_ICON);
                    classTableData.setLevel(TableData.CLASS_LEVEL);
                    classTableData.setMinRank(classData.getRank());

                    if (!relativePath.equalsIgnoreCase(fileRelativePath))
                        continue;
                    this.tableDataList.add(classTableData);

                    for (MethodTestData methodData : classData.getMethods()) {
                        TableData methodTableData = new TableData();
                        methodTableData.setName(TABLE_ROW_IDENT_PREFIX + TABLE_ROW_IDENT_PREFIX + methodData.getName());
                        methodTableData.setPath(relativePath);
                        methodTableData.setLine(methodData.getLine());
                        methodTableData.setTarantulaScore(methodData.getTarantula());
                        methodTableData.setOchiaiScore(methodData.getOchiai());
                        methodTableData.setWong2Score(methodData.getWong2());
                        methodTableData.setFaulty(methodData.isFaulty());
                        methodTableData.setIcon(TABLE_ARROW_DOWN_ICON);
                        methodTableData.setLevel(TableData.METHOD_LEVEL);
                        this.tableDataList.add(methodTableData);

                        for (StatementTestData statementData : methodData.getStatements()) {
                            TableData thirdData = new TableData();
                            thirdData.setName("");
                            thirdData.setPath(TABLE_ROW_IDENT_PREFIX + TABLE_ROW_IDENT_PREFIX + TABLE_ROW_IDENT_PREFIX + relativePath);
                            thirdData.setLine(statementData.getLine());
                            thirdData.setTarantulaScore(statementData.getTarantula());
                            thirdData.setOchiaiScore(statementData.getOchiai());
                            thirdData.setWong2Score(statementData.getWong2());
                            thirdData.setFaulty(statementData.isFaulty());
                            thirdData.setIcon(TABLE_OPEN_EDITOR_ICON);
                            thirdData.setLevel(TableData.STATEMENT_LEVEL);

                            this.tableDataList.add(thirdData);
                        }
                    }
                }
            }

        }
    }

    private boolean isFileInTheHierarchicalView(String relativePath){
        if (this.files.contains(relativePath)){
            return true;
        }
        else {
            this.files.add(relativePath);
            return false;
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
            case NAME_COLUMN_INDEX:
                return String.class;
            case LINE_COLUMN_INDEX:
                return Integer.class;
            case ACTION_COLUMN_INDEX:
                return ImageIcon.class;
            default:
                return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        int count = 0;

        int actualRowIndex;
        for (actualRowIndex = 0; actualRowIndex < tableDataList.size(); actualRowIndex++) {
            TableData actualRow = tableDataList.get(actualRowIndex);
            if (count == rowIndex && !actualRow.isHide()) {
                break;
            }
            if (!actualRow.isHide()) {
                count++;
            }
        }

        TableData actualRow = tableDataList.get(actualRowIndex);
        switch (columnIndex) {
            case NAME_COLUMN_INDEX:
                if (actualRow.getLevel() == TableData.STATEMENT_LEVEL) {
                    return actualRow.getPath();
                } else {
                    return actualRow.getName();
                }
            case LINE_COLUMN_INDEX:
                return actualRow.getLine();
            case ACTION_COLUMN_INDEX:
                return actualRow.getIcon();
            default:
                return "";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener listener) {

    }

    @Override
    public void removeTableModelListener(TableModelListener listener) {

    }
}