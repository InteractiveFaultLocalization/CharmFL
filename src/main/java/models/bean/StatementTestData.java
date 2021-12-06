package models.bean;

import java.io.Serializable;

public class StatementTestData implements Serializable {
    private int line;
    private double tarantula;
    private double ochiai;
    private double wong2;
    private int rank;
    private boolean faulty;
    private static final long serialVersionUID = -8537801985043118842L;

    public StatementTestData() {
        line = 0;
        tarantula = 0;
        ochiai = 0;
        wong2 = 0;
        rank = 0;
        faulty = false;
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

    public boolean isFaulty() {
        return faulty;
    }

    public void setFaulty(boolean faulty) {
        this.faulty = faulty;
    }
}
