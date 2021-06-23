package services;

import java.util.ArrayList;
import java.util.HashMap;

import com.intellij.openapi.project.Project;
import models.bean.FileLineScoreData;
import models.bean.ProcessResult;

public interface FlService {
    static HashMap<String, ArrayList<FileLineScoreData>> testData = null;
    static boolean testDataCollected = false;
    static boolean fileEditorColoringEnabled = false;
    static boolean coloringTurnOn = true;

    ProcessResult executeCommand(String command);
    ProcessResult executeTest(String pythonBinPath, String pyflPath, String projectPath);
    ProcessResult executeRequirementsInstall(String pythonBinPath, String pipBinPath, String requirementsPath, String projectPath);
    ProcessResult executeGetPipBinPath(String pythonBinPath, String checkPipBinPath);
    HashMap<String, ArrayList<FileLineScoreData>> parseTestOutput(ArrayList<String> lines);

    HashMap<String, ArrayList<FileLineScoreData>> getTestData();
    void setTestData(HashMap<String, ArrayList<FileLineScoreData>> testData);
    void clearTestData();
    void startFileEditorManagerListener(Project project);
    String parseRelativeFilePath(String currentFilePath, String projectPath);
    boolean isTestDataCollected();
    boolean isFileEditorColoringEnabled();
    boolean isColoringTurnOn();
    void setTestDataCollected(boolean status);
    void setFileEditorColoringEnabled(boolean status);
    void setColoringTurnOn(boolean status);
    boolean isViewResultTableDialogOpened();
    void setViewResultTableDialogOpened(boolean viewResultTableDialogOpened);
    boolean isTestDataCollecting();
    void setTestDataCollecting(boolean testDataCollecting);
    int getTestDataRowCount();
    String[][] prepareTestDataForTable();
}
