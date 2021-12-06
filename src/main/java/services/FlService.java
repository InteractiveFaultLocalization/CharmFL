package services;

import java.util.ArrayList;

import com.intellij.openapi.project.Project;

import models.bean.ProcessResult;
import models.bean.TestData;

public interface FlService {
    ProcessResult executeCommand(String command);
    ProcessResult executeTest(String pythonBinPath, String pyflPath, String projectPath);
    ProcessResult executeRequirementsInstall(String pythonBinPath, String pipBinPath, String requirementsPath, String projectPath);
    ProcessResult executeGetPipBinPath(String pythonBinPath, String checkPipBinPath);
    ArrayList<String> readTextFile(String fileName);
    TestData parseTestDataJSON(ArrayList<String> lines);

    TestData getTestData();
    void setTestData(TestData testData);
    void clearTestData();
    void startFileEditorManagerListener(Project project);
    String parseRelativeFilePath(String currentFilePath, String projectPath);
    boolean isTestDataCollected();
    boolean isFileEditorColoringEnabled();
    void setTestDataCollected(boolean status);
    void setFileEditorColoringEnabled(boolean status);
    boolean isViewResultTableDialogOpened();
    void setViewResultTableDialogOpened(boolean viewResultTableDialogOpened);
    boolean isTestDataCollecting();
    void setTestDataCollecting(boolean testDataCollecting);
}
