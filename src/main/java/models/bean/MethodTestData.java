package models.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class MethodTestData implements Serializable {
    private String name;
    private int line;
    private double tarantula;
    private double ochiai;
    private double wong2;
    private int rank;
    private boolean faulty;
    private ArrayList<StatementTestData> statements;
    private static final long serialVersionUID = -4345480318727973268L;

    public MethodTestData() {
        name = "";
        line = 0;
        tarantula = 0;
        ochiai = 0;
        wong2 = 0;
        rank = 0;
        faulty = false;
        statements = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public ArrayList<StatementTestData> getStatements() {
        return statements;
    }

    public void setStatements(ArrayList<StatementTestData> statements) {
        this.statements = statements;
    }
}
