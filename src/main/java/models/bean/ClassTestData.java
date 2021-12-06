package models.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class ClassTestData implements Serializable {
    private String name;
    private int line;
    private double tarantula;
    private double ochiai;
    private double wong2;
    private int rank;
    private boolean faulty;
    private String path;
    private ArrayList<MethodTestData> methods;
    private ArrayList<Boolean> showStatements;
    private static final long serialVersionUID = -5397522521808823170L;

    public ClassTestData(){
        name = "";
        line = 0;
        tarantula = 0;
        ochiai = 0;
        wong2 = 0;
        rank = 0;
        faulty = false;
        path = "";
        methods = new ArrayList<>();
        showStatements = new ArrayList<>();
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ArrayList<MethodTestData> getMethods() {
        return methods;
    }

    public void setMethods(ArrayList<MethodTestData> methods) {
        this.methods = methods;
    }

    public ArrayList<Boolean> getShowStatements() {
        return showStatements;
    }

    public void setShowStatements(ArrayList<Boolean> showStatements) {
        this.showStatements = showStatements;
    }
}
