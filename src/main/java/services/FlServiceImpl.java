package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.util.messages.MessageBus;
import org.jetbrains.annotations.NotNull;

import org.apache.commons.lang3.SystemUtils;

import models.bean.FileLineScoreData;
import models.bean.ProcessResult;
import modules.ProjectModule;

public class FlServiceImpl implements FlService {
    static HashMap<String, ArrayList<FileLineScoreData>> testData = null;
    static boolean testDataCollected = false;
    static boolean fileEditorColoringEnabled = false;
    static boolean coloringTurnOn = true;
    static boolean viewResultTableDialogOpened = false;
    static boolean testDataCollecting = false;

    @Override
    public ProcessResult executeCommand(String command) {
        try {
            Runtime run = Runtime.getRuntime();
            Process proc = run.exec(command);

            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            ArrayList<String> lines = new ArrayList<>();
            while ((line = in.readLine()) != null) {
                lines.add(line);
                // Only for debug!
                //System.out.println(line);
            }
            proc.waitFor();
            in.close();
            return new ProcessResult(lines, proc.exitValue());
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ProcessResult executeTest(String pythonBinPath, String pyflPath, String projectPath) {
        String command = "";
        if(SystemUtils.IS_OS_WINDOWS) {
            command = "\"" + pythonBinPath + "\" " +
                    "\"" + pyflPath + "\" -d " +
                    "\"" + projectPath + "\"";
        }
        else if(SystemUtils.IS_OS_LINUX) {
            command = pythonBinPath.replaceAll(" ", "\\ ") + " " +
                    pyflPath.replaceAll(" ", "\\ ") + " -d " +
                    projectPath.replaceAll(" ", "\\ ");
        }
        // Only for debug!
        System.out.println(command);
        return executeCommand(command);
    }

    @Override
    public ProcessResult executeRequirementsInstall(String pythonBinPath, String pipBinPath, String requirementsPath, String projectPath) {
        String command = "";
        if(SystemUtils.IS_OS_WINDOWS) {
            command = "\"" + pythonBinPath + "\" " +
                    "\"" + pipBinPath + "\" " +
                    "install -r " +
                    "\"" + requirementsPath + "\"";
        }
        else if(SystemUtils.IS_OS_LINUX) {
            command = pythonBinPath.replaceAll(" ", "\\ ") + " " +
                    pipBinPath.replaceAll(" ", "\\ ") + " " +
                    "install -r " +
                    requirementsPath.replaceAll(" ", "\\ ");
        }
        // Only for debug!
        System.out.println(command);
        return executeCommand(command);
    }

    @Override
    public ProcessResult executeGetPipBinPath(String pythonBinPath, String checkPipBinPath) {
        String command = "";
        if(SystemUtils.IS_OS_WINDOWS) {
            command = "\"" + pythonBinPath + "\" " +
                    "\"" + checkPipBinPath + "\"";
        }
        else if(SystemUtils.IS_OS_LINUX) {
            command = pythonBinPath.replaceAll(" ", "\\ ") + " " +
                    checkPipBinPath.replaceAll(" ", "\\ ");
        }
        // Only for debug!
        System.out.println(command);
        return executeCommand(command);
    }

    @Override
    public HashMap<String, ArrayList<FileLineScoreData>> parseTestOutput(ArrayList<String> lines) {
        HashMap<String, ArrayList<FileLineScoreData>> testData = new HashMap<>();
        String dataMarker = "----------";
        boolean dataProcess = false;
        for(int i = 0; i < lines.size(); i++) {
            if(lines.get(i).equals(dataMarker) && dataProcess) {
                dataProcess = false;
            }
            if(dataProcess) {
                int fileIndex = lines.get(i).lastIndexOf(":");
                if (fileIndex != -1) {
                    String fileName = lines.get(i).substring(0, fileIndex);
                    String subLine = lines.get(i).substring(lines.get(i).lastIndexOf(":") + 1);
                    String[] splitLine = subLine.split(" ");
                    int lineNumber = Integer.parseInt(splitLine[0]);
                    double lineScore = round(Double.parseDouble(splitLine[1]), 2);

                    if(!testData.containsKey(fileName)) {
                        testData.put(fileName, new ArrayList<>());
                    }
                    testData.get(fileName).add(new FileLineScoreData(lineNumber, lineScore));
                }
            }
            if(lines.get(i).equals(dataMarker) && !dataProcess) {
                dataProcess = true;
            }
        }

        return testData;
    }

    @Override
    public HashMap<String, ArrayList<FileLineScoreData>> getTestData() {
        return FlServiceImpl.testData;
    }

    @Override
    public void setTestData(HashMap<String, ArrayList<FileLineScoreData>> testData) {
        FlServiceImpl.testData = testData;
    }

    @Override
    public void clearTestData() {
        if(FlServiceImpl.testData != null) {
            FlServiceImpl.testData.clear();
        }
    }

    @Override
    public void startFileEditorManagerListener(Project project) {
        MessageBus messageBus = project.getMessageBus();
        messageBus.connect().subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileEditorManagerListener() {
            /*@Override
            public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                System.out.println(file.getName() + " is opened!");
            }

            @Override
            public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                System.out.println(file.getName() + " is closed!");
            }*/

            @Override
            public void selectionChanged(@NotNull FileEditorManagerEvent e) {
                if(e.getManager() != null) {
                    Editor editor = e.getManager().getSelectedTextEditor();
                    if(editor != null) {
                        ColorService colorService = new ColorService();
                        colorService.setEditor(editor);
                        colorService.removeColorsByEditor();
                        if (testDataCollected && coloringTurnOn) {
                            String relativeFilePath = parseRelativeFilePath(e.getNewFile().getPath(), ProjectModule.getProjectPath());
                            colorService.setColorsByEditor(testData.get(relativeFilePath));
                        }
                    }
                }
            }
        });
    }

    @Override
    public String parseRelativeFilePath(String currentFilePath, String projectPath) {
        String relativeFilePath = currentFilePath.substring(projectPath.length() + 1);
        relativeFilePath = relativeFilePath.replace("\\", File.separator);
        relativeFilePath = relativeFilePath.replace("/", File.separator);
        return relativeFilePath;
    }

    @Override
    public boolean isTestDataCollected() {
        return testDataCollected;
    }

    @Override
    public boolean isFileEditorColoringEnabled() {
        return fileEditorColoringEnabled;
    }

    @Override
    public void setTestDataCollected(boolean status) {
        testDataCollected = status;
    }

    @Override
    public void setFileEditorColoringEnabled(boolean status) {
        fileEditorColoringEnabled = status;
    }

    @Override
    public boolean isColoringTurnOn() {
        return coloringTurnOn;
    }

    @Override
    public void setColoringTurnOn(boolean coloringTurnOn) {
        FlServiceImpl.coloringTurnOn = coloringTurnOn;
    }

    @Override
    public boolean isViewResultTableDialogOpened() {
        return viewResultTableDialogOpened;
    }

    @Override
    public void setViewResultTableDialogOpened(boolean viewResultTableDialogOpened) {
        FlServiceImpl.viewResultTableDialogOpened = viewResultTableDialogOpened;
    }

    @Override
    public boolean isTestDataCollecting() {
        return testDataCollecting;
    }

    @Override
    public void setTestDataCollecting(boolean testDataCollecting) {
        FlServiceImpl.testDataCollecting = testDataCollecting;
    }

    @Override
    public int getTestDataRowCount() {
        if(testData == null) {
            return 0;
        }

        int sum = 0;
        for(Map.Entry<String, ArrayList<FileLineScoreData>> entry : testData.entrySet()) {
            sum += testData.get(entry.getKey()).size();
        }
        return sum;
    }

    public String[][] prepareTestDataForTable() {
        String[][] data = new String[getTestDataRowCount()][3];
        int rowIndex = 0;
        for(Map.Entry<String, ArrayList<FileLineScoreData>> entry : testData.entrySet()) {
            for(int i = 0; i < entry.getValue().size(); i++) {
                data[rowIndex][0] = entry.getKey();
                data[rowIndex][1] = String.valueOf(entry.getValue().get(i).getLineNumber());
                data[rowIndex][2] = String.valueOf(entry.getValue().get(i).getLineScore());
                rowIndex++;
            }
        }

        int minindex = -1;
        String tmpFileName = "";
        String tmpLineNumber = "";
        String tmpLineScore = "";
        for(int i = 0; i < data.length - 1; i++){
            minindex = i;
            for(int j = i + 1 ;j < data.length; j++){
                if(Double.parseDouble(data[j][2]) > Double.parseDouble(data[minindex][2])){
                    minindex = j;
                }
            }

            tmpFileName = data[i][0];
            tmpLineNumber = data[i][1];
            tmpLineScore = data[i][2];

            data[i][0] = data[minindex][0];
            data[i][1] = data[minindex][1];
            data[i][2] = data[minindex][2];

            data[minindex][0] = tmpFileName;
            data[minindex][1] = tmpLineNumber;
            data[minindex][2] = tmpLineScore;
        }

        return data;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
