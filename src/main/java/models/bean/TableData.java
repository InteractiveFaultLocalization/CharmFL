package models.bean;

import javax.swing.*;

/**
 * This class represents the data that is in the table view.
 */
public class TableData {
    public static final int CLASS_LEVEL = 1;
    public static final int METHOD_LEVEL = 2;
    public static final int STATEMENT_LEVEL = 3;

    private String name;
    private String path;
    private int line;
    private double tarantulaScore;
    private double ochiaiScore;
    private double wong2Score;
    private double minRank;
    private double maxRank;
    private double avgRank;
    private ImageIcon icon;
    private boolean faulty;
    private int level;
    private boolean hide;

    public TableData() {
        name = "";
        path = "";
        line = 0;
        tarantulaScore = 0;
        ochiaiScore = 0;
        wong2Score = 0;
        minRank = 0;
        maxRank = 0;
        avgRank = 0;
        faulty = false;
        icon = null;
        level = 0;
        hide = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public double getTarantulaScore() {
        return tarantulaScore;
    }

    public void setTarantulaScore(double tarantula) {
        this.tarantulaScore = tarantula;
    }

    public double getOchiaiScore() {
        return ochiaiScore;
    }

    public void setOchiaiScore(double ochiai) {
        this.ochiaiScore = ochiai;
    }

    public double getWong2Score() {
        return wong2Score;
    }

    public void setWong2Score(double wong2Score) {
        this.wong2Score = wong2Score;
    }

    public double getMinRank() {
        return minRank;
    }

    public void setMinRank(double minRank) {
        this.minRank = minRank;
    }

    public double getMaxRank() {
        return maxRank;
    }

    public void setMaxRank(double maxRank) {
        this.maxRank = maxRank;
    }

    public double getAvgRank() {
        return avgRank;
    }

    public void setAvgRank(double avgRank) {
        this.avgRank = avgRank;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public boolean isFaulty() {
        return faulty;
    }

    public void setFaulty(boolean faulty) {
        this.faulty = faulty;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Tells you whether the row is hidden or not
     * @return true if the row is hidden
     */
    public boolean isHide() {
        return hide;
    }

    /**
     * Hids the row
     * @param hide true if you want the row to be hidden, false if you want it to be shown
     */
    public void setHide(boolean hide) {
        this.hide = hide;
    }
}
