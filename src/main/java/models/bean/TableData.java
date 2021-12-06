package models.bean;

import javax.swing.*;
import java.io.Serializable;

public class TableData implements Serializable {
    private String name;
    private String path;
    private int line;
    private double tarantula;
    private double ochiai;
    private double wong2;
    private int rank;
    private ImageIcon icon;
    private boolean faulty;
    private int level;
    private boolean hide;
    private boolean opened;
    private static final long serialVersionUID = 4945049915710010913L;

    public TableData() {
        name = "";
        path = "";
        line = 0;
        tarantula = 0;
        ochiai = 0;
        wong2 = 0;
        rank = 0;
        faulty = false;
        icon = null;
        level = 0;
        hide = false;
        opened = false;
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

    public double getTarantula() {
        return tarantula;
    }

    public void setTarantula(double tarantula) {
        this.tarantula = tarantula;
    }

    public double getOchiai() {
        return ochiai;
    }

    public void setOchiai(double ochiai) {
        this.ochiai = ochiai;
    }

    public double getWong2() {
        return wong2;
    }

    public void setWong2(double wong2) {
        this.wong2 = wong2;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
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

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }
}
