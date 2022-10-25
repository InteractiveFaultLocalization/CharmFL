package ui.viewResultTableModels;

import java.io.File;
import java.util.ArrayList;

import javax.swing.event.*;
import javax.swing.table.TableModel;

import modules.PluginModule;
import org.jetbrains.annotations.Nls;

import models.bean.*;
import services.RankingService;
import services.Resources;

public class ClassTableModel implements TableModel {
    private static final String[] columnNames = {"Name", "Score", "Rank"};

    public static final int NAME_COLUMN_INDEX = 0;
    public static final int SCORE_COLUMN_INDEX = 1;
    public static final int RANK_COLUMN_INDEX = 2;

    private final ArrayList<TableData> tableDataList = new ArrayList<>();

    private final String spectraMetrics;
    private final String selectedRankType;

    public ClassTableModel(TestData testData, String spectraMetrics, String selectedRankType) {
        parseData(testData);
        setRanks();
        this.spectraMetrics = spectraMetrics;
        this.selectedRankType = selectedRankType;
    }

    /**
     * From test data it converts to table data, therefore the tableview can display it.
     * @param data
     */
    private void parseData(TestData data) {


            for (ClassTestData classData : data.getClasses()) {
                String relativePath = classData.getRelativePath();

                TableData classTableData = new TableData();
                classTableData.setName(relativePath + File.separator + classData.getName());
                classTableData.setPath(relativePath);
                classTableData.setLine(classData.getLine());
                classTableData.setTarantulaScore(classData.getTarantula());
                classTableData.setOchiaiScore(classData.getOchiai());
                classTableData.setWong2Score(classData.getWong2());
                classTableData.setFaulty(classData.isFaulty());
                classTableData.setLevel(TableData.CLASS_LEVEL);
                if (classData.getName().equalsIgnoreCase("<not_class>"))
                    continue;
                this.tableDataList.add(classTableData);
            }
        }


    /**
     * Puts the scores into the score list and it ranks accordingly to the selected option.
     */
    private void setRanks(){
        ArrayList<Double> scoreList = new ArrayList<>();
        for(var tableData : this.tableDataList){
            if (PluginModule.isTarantulaSelected()) {
                scoreList.add(tableData.getTarantulaScore());
            } else if (PluginModule.isOchiaiSelected()) {
                scoreList.add(tableData.getOchiaiScore());
            } else if (PluginModule.isWongIISelected() || PluginModule.isDStarSelected()) {
                scoreList.add(tableData.getWong2Score());
            }
        }

        RankingService rankingService = new RankingService(scoreList);
        ArrayList<Double> minRankList = rankingService.minRanking();
        ArrayList<Double> maxRankList = rankingService.maxRanking();
        ArrayList<Double> avgRankList = rankingService.averageRanking();
        for (int i=0; i<this.tableDataList.size(); i++){
            this.tableDataList.get(i).setAvgRank(avgRankList.get(i));
            this.tableDataList.get(i).setMinRank(minRankList.get(i));
            this.tableDataList.get(i).setMaxRank(maxRankList.get(i));
        }
    }

    @Override
    public int getRowCount() {
        return tableDataList.size();
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

    /**
     * This provides the type of the selected column
     * @param columnIndex
     * @return
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case NAME_COLUMN_INDEX:
                return String.class;
            case SCORE_COLUMN_INDEX:
            case RANK_COLUMN_INDEX:
                return Double.class;
            default:
                return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    /**
     * This returns the value of the selected row
     * @param rowIndex The selected row index
     * @param columnIndex The selected column index
     * @return the value of the table data object
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TableData tableDataAtRowIndex = tableDataList.get(rowIndex);
        switch (columnIndex) {
            case NAME_COLUMN_INDEX:
                if (tableDataList.get(rowIndex).getLevel() == TableData.STATEMENT_LEVEL) {
                    return tableDataAtRowIndex.getPath();
                } else {
                    return tableDataAtRowIndex.getName();
                }
            case SCORE_COLUMN_INDEX:
                if (spectraMetrics.equals(" (Tarantula)")) {
                    return tableDataAtRowIndex.getTarantulaScore();
                } else if (spectraMetrics.equals(" (Ochiai)")) {
                    return tableDataAtRowIndex.getOchiaiScore();
                } else if (spectraMetrics.equals(" (WongII)") || spectraMetrics.equals(" (DStar)")) {
                    return tableDataAtRowIndex.getWong2Score();
                } else {
                    return -1;
                }
            case RANK_COLUMN_INDEX:
                if(selectedRankType.equals(Resources.get("titles", "average_button"))){
                    return tableDataAtRowIndex.getAvgRank();
                }else if (selectedRankType.equals(Resources.get("titles", "minimum_button"))){
                    return tableDataAtRowIndex.getMinRank();
                }else if(selectedRankType.equals(Resources.get("titles", "maximum_button"))){
                    return tableDataAtRowIndex.getMaxRank();
                }else {
                    return -1;
                }
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