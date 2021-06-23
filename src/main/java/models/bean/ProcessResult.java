package models.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class ProcessResult implements Serializable {
    private ArrayList<String> output;
    private int exitCode;
    private static final long serialVersionUID = 5398505171721526287L;

    public ProcessResult() {
        output = new ArrayList<>();
        exitCode = 0;
    }

    public ProcessResult(ArrayList<String> output, int exitCode) {
        this.output = output;
        this.exitCode = exitCode;
    }

    public ArrayList<String> getOutput() {
        return output;
    }

    public void setOutput(ArrayList<String> output) {
        this.output = output;
    }

    public int getExitCode() {
        return exitCode;
    }

    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }
}
