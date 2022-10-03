package models.bean;

import java.util.ArrayList;

public class ProcessResultData {
    private ArrayList<String> output;
    private int exitCode;

    public ProcessResultData(ArrayList<String> output, int exitCode) {
        this.output = output;
        this.exitCode = exitCode;
    }

    public ArrayList<String> getOutput() {
        return output;
    }

    public int getExitCode() {
        return exitCode;
    }
}
