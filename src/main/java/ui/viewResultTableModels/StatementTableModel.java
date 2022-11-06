package ui.viewResultTableModels;

import java.util.ArrayList;

import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import modules.PluginModule;
import org.jetbrains.annotations.Nls;

import models.bean.*;
import services.RankingService;
import services.Resources;

public class StatementTableModel extends AbstractTableModel {
    private static final String[] columnNames = {"File name", "Line", "Score", "Rank"};

    public static final int NAME_COLUMN_INDEX = 0;
    public static final int LINE_COLUMN_INDEX = 1;
    public static final int SCORE_COLUMN_INDEX = 2;
    public static final int RANK_COLUMN_INDEX = 3;

    private final ArrayList<TableData> tableDataList = new ArrayList<>();

    private final String selectedSpectraMetric;
    private final String selectedRankType;

    public StatementTableModel(TestData testData, String selectedSpectraMetric, String selectedRankType) {
        parseData(testData);
        setRanks();
        this.selectedSpectraMetric = selectedSpectraMetric;
        this.selectedRankType = selectedRankType;
    }

    private void parseData(TestData data) {
        for (ClassTestData classData : data.getClasses()) {
            String relativePath = classData.getRelativePath();

            for (MethodTestData methodData : classData.getMethods()) {
                for (StatementTestData statementData : methodData.getStatements()) {
                    TableData thirdData = new TableData();
                    thirdData.setName("");
                    thirdData.setPath(relativePath);
                    thirdData.setLine(statementData.getLine());
                    thirdData.setTarantulaScore(statementData.getTarantula());
                    thirdData.setOchiaiScore(statementData.getOchiai());
                    thirdData.setWong2Score(statementData.getWong2());
                    thirdData.setFaulty(statementData.isFaulty());
                    thirdData.setLevel(TableData.STATEMENT_LEVEL);
                    //thirdData.setHide(true);

                    this.tableDataList.add(thirdData);
                }
            }
        }
    }



    public void setRanks(){
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
        for (int i=0; i<this.tableDataList.size(); i++) {
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

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case NAME_COLUMN_INDEX:
                return String.class;
            case LINE_COLUMN_INDEX:
                return Integer.class;
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

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TableData tableDataAtRowIndex = tableDataList.get(rowIndex);
        switch (columnIndex) {
            case NAME_COLUMN_INDEX:
                if (tableDataAtRowIndex.getLevel() == TableData.STATEMENT_LEVEL) {
                    return tableDataAtRowIndex.getPath();
                } else {
                    return tableDataAtRowIndex.getName();
                }
            case LINE_COLUMN_INDEX:
                return tableDataAtRowIndex.getLine();
            case SCORE_COLUMN_INDEX:
                if (selectedSpectraMetric.equals(" (Tarantula)")) {
                    return tableDataAtRowIndex.getTarantulaScore();
                } else if (selectedSpectraMetric.equals(" (Ochiai)")) {
                    return tableDataAtRowIndex.getOchiaiScore();
                } else if (selectedSpectraMetric.equals(" (WongII)") || selectedSpectraMetric.equals(" (DStar)")) {
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

    @Override
    public void fireTableDataChanged() {
        super.fireTableDataChanged();
    }
}