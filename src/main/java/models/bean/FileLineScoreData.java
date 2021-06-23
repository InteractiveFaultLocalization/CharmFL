package models.bean;

import java.io.Serializable;

public class FileLineScoreData implements Serializable {
    private int lineNumber;
    private double lineScore;
    private static final long serialVersionUID = -105300095200714010L;

    public FileLineScoreData() {
        lineNumber = 0;
        lineScore = 0;
    }

    public FileLineScoreData(int lineNumber, double lineScore){
        this.lineNumber = lineNumber;
        this.lineScore = lineScore;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public double getLineScore() {
        return lineScore;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void setLineScore(double lineScore) {
        this.lineScore = lineScore;
    }
}
