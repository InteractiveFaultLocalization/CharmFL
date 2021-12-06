package ui;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.jetbrains.annotations.Nls;

import models.bean.TableData;
import models.bean.TestData;
import modules.PluginModule;
import services.FlServiceImpl;

public class ViewResultTableModel implements TableModel {
    private String[] columnNames = {"Name", "Line", "Score", "Rank", "Action"};
    private TableData[] data;

    public static final String TABLE_ROW_IDENT_PREFIX = "    ";

    public ViewResultTableModel() {
        data = parseDataIntoTable(new FlServiceImpl().getTestData());
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public TableData[] getData() {
        return data;
    }

    public void setData(TableData[] data) {
        this.data = data;
    }

    public TableData[] parseDataIntoTable(TestData data) {
        int count = 0;
        for(int i = 0; i < data.getClasses().size(); i++) {
            count++;
            for(int j = 0; j < data.getClasses().get(i).getMethods().size(); j++) {
                count++;
                for(int k = 0; k < data.getClasses().get(i).getMethods().get(j).getStatements().size(); k++) {
                    count++;
                }
            }
        }

        TableData[] tableData = new TableData[count];

        count = 0;
        String path = "";
        for(int i = 0; i < data.getClasses().size(); i++) {
            tableData[count] = new TableData();
            tableData[count].setName(data.getClasses().get(i).getName());
            path = data.getClasses().get(i).getPath();
            tableData[count].setPath(path);
            tableData[count].setLine(data.getClasses().get(i).getLine());
            tableData[count].setTarantula(data.getClasses().get(i).getTarantula());
            tableData[count].setOchiai(data.getClasses().get(i).getOchiai());
            tableData[count].setWong2(data.getClasses().get(i).getWong2());
            tableData[count].setRank(data.getClasses().get(i).getRank());
            tableData[count].setFaulty(data.getClasses().get(i).isFaulty());
            tableData[count].setIcon(new ImageIcon(getClass().getClassLoader().getResource("table-icons/arrow-down.png")));
            tableData[count].setLevel(1);
            count++;
            for(int j = 0; j < data.getClasses().get(i).getMethods().size(); j++) {
                tableData[count] = new TableData();
                tableData[count].setName(TABLE_ROW_IDENT_PREFIX + data.getClasses().get(i).getMethods().get(j).getName());
                tableData[count].setPath(path);
                tableData[count].setLine(data.getClasses().get(i).getMethods().get(j).getLine());
                tableData[count].setTarantula(data.getClasses().get(i).getMethods().get(j).getTarantula());
                tableData[count].setOchiai(data.getClasses().get(i).getMethods().get(j).getOchiai());
                tableData[count].setWong2(data.getClasses().get(i).getMethods().get(j).getWong2());
                tableData[count].setRank(data.getClasses().get(i).getMethods().get(j).getRank());
                tableData[count].setFaulty(data.getClasses().get(i).getMethods().get(j).isFaulty());
                tableData[count].setIcon(new ImageIcon(getClass().getClassLoader().getResource("table-icons/arrow-down.png")));
                tableData[count].setLevel(2);
                tableData[count].setHide(true);
                count++;
                for(int k = 0; k < data.getClasses().get(i).getMethods().get(j).getStatements().size(); k++) {
                    tableData[count] = new TableData();
                    tableData[count].setName("");
                    tableData[count].setPath(TABLE_ROW_IDENT_PREFIX + TABLE_ROW_IDENT_PREFIX + path);
                    tableData[count].setLine(data.getClasses().get(i).getMethods().get(j).getStatements().get(k).getLine());
                    tableData[count].setTarantula(data.getClasses().get(i).getMethods().get(j).getStatements().get(k).getTarantula());
                    tableData[count].setOchiai(data.getClasses().get(i).getMethods().get(j).getStatements().get(k).getOchiai());
                    tableData[count].setWong2(data.getClasses().get(i).getMethods().get(j).getStatements().get(k).getWong2());
                    tableData[count].setRank(data.getClasses().get(i).getMethods().get(j).getStatements().get(k).getRank());
                    tableData[count].setFaulty(data.getClasses().get(i).getMethods().get(j).getStatements().get(k).isFaulty());
                    tableData[count].setIcon(new ImageIcon(getClass().getClassLoader().getResource("table-icons/open-editor.png")));
                    tableData[count].setLevel(3);
                    tableData[count].setHide(true);
                    count++;
                }
            }
        }

        // Only for debug!
        /*for(int i = 0; i < tableData.length; i++) {
            System.out.println("i: " + i + " name: " + tableData[i].getName() + " path: " + tableData[i].getPath());
        }*/

        return tableData;
    }

    public boolean isSelectedRowOpenInEditor(int rowIndex) {
        int count = 0;

        int i;
        for(i = 0; i < data.length; i++) {
            if(count == rowIndex && !data[i].isHide()) {
                break;
            }
            if(!data[i].isHide()) {
                count++;
            }
        }

        if(data[i].getLevel() == 3) {
            return true;
        }
        else {
            return false;
        }
    }

    public void toggleDataShow(int rowIndex) {
        int count = 0;

        int i;
        for(i = 0; i < data.length; i++) {
            if(count == rowIndex && !data[i].isHide()) {
                break;
            }
            if(!data[i].isHide()) {
                count++;
            }
        }

        if(data[i].getLevel() > 2) {
            return;
        }

        int j = i + 1;

        if(j < data.length) {
            if(data[j].isHide()) {
                data[i].setIcon(new ImageIcon(getClass().getClassLoader().getResource("table-icons/arrow-up.png")));

                int k = j;
                while(k < data.length && data[j].getLevel() <= data[k].getLevel()) {
                    if(data[j].getLevel() == data[k].getLevel()) {
                        data[k].setHide(false);
                    }
                    k++;
                }
            }
            else {
                data[i].setIcon(new ImageIcon(getClass().getClassLoader().getResource("table-icons/arrow-down.png")));

                int k = j;
                while(k < data.length && data[j].getLevel() <= data[k].getLevel()) {
                    if(data[j].getLevel() <= data[k].getLevel()) {
                        data[k].setHide(true);
                    }
                    k++;
                }
            }
        }

        // Only for debug!
        /*for(i = 0; i < data.length; i++) {
            System.out.println("i: " + i + " name: " + data[i].getName() + " path: " + data[i].getPath() + " hide: " + data[i].isHide());
        }*/
    }

    @Override
    public int getRowCount() {
        int count = 0;
        for(int i = 0; i < data.length; i++) {
            if(!data[i].isHide()) {
                count++;
            }
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
    public Class getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0:
                return String.class;
            case 1:
                return int.class;
            case 2:
                return double.class;
            case 3:
                return int.class;
            case 4:
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

        int i;
        for(i = 0; i < data.length; i++) {
            if(count == rowIndex && !data[i].isHide()) {
                break;
            }
            if(!data[i].isHide()) {
                count++;
            }
        }

        switch(columnIndex) {
            case 0:
                if(data[i].getLevel() == 3) {
                    return data[i].getPath();
                }
                else {
                    return data[i].getName();
                }
            case 1:
                return data[i].getLine();
            case 2:
                if(PluginModule.isTarantulaSelected()) {
                    return data[i].getTarantula();
                }
                else if(PluginModule.isOchiaiSelected()) {
                    return data[i].getOchiai();
                }
                else if(PluginModule.isDStarSelected() || PluginModule.isWongIISelected()) {
                    return data[i].getWong2();
                }
                else {
                    return -1;
                }
            case 3:
                return data[i].getRank();
            case 4:
                return data[i].getIcon();
        }

        return "";
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        return;
    }

    @Override
    public void addTableModelListener(TableModelListener listener) {

    }

    @Override
    public void removeTableModelListener(TableModelListener listener) {

    }
}
