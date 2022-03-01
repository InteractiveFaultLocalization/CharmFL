package services;

import java.io.*;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.util.messages.MessageBus;
import models.bean.*;
import org.jetbrains.annotations.NotNull;

import org.apache.commons.lang3.SystemUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import modules.ProjectModule;

public class FlServiceImpl implements FlService {
    private static TestData testData = null;
    private static boolean testDataCollected = false;
    private static boolean fileEditorColoringEnabled = false;
    private static boolean viewResultTableDialogOpened = false;
    private static boolean testDataCollecting = false;

    @Override
    public ProcessResult executeCommand(String command) {
        try {
            Runtime run = Runtime.getRuntime();
            Process proc = run.exec(command);

            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            ArrayList<String> lines = new ArrayList<>();
            while((line = in.readLine()) != null) {
                lines.add(line);
                // Only for debug!
                //System.out.println(line);
            }
            proc.waitFor();
            in.close();
            return new ProcessResult(lines, proc.exitValue());
        }
        catch(IOException | InterruptedException e) {
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
        return executeCommand(command);
    }

    @Override
    public ArrayList<String> readTextFile(String fileName) {
        ArrayList<String> lines = new ArrayList<>();

        File file = new File(fileName);
        try {
            if(file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    @Override
    public TestData parseTestDataJSON(ArrayList<String> lines)  {
        TestData testData = new TestData();
        String json = String.join(" ", lines);
        JSONObject jsonObject = new JSONObject(json);

        JSONObject fileObject, classObject, methodObject, statementObject;
        String name;
        int line;
        double tarantula, ochiai, wong2;
        int rank;
        boolean faulty;
        String path;
        ClassTestData classTestData;
        MethodTestData methodTestData;
        StatementTestData statementTestData;
        JSONArray filesArray, classesArray, methodsArray, statementsArray;

        filesArray = jsonObject.getJSONArray("files");
        for(int i = 0; i < filesArray.length(); i++) {
            fileObject = filesArray.getJSONObject(i);
            classesArray = fileObject.getJSONArray("classes");
            path = fileObject.getString("path");

            for(int j = 0; j < classesArray.length(); j++) {
                classObject = classesArray.getJSONObject(j);
                name = classObject.getString("name");
                if(name.equals("")) {
                    name = "<not_class>";
                }
                line = classObject.getInt("line");
                if(classObject.has("tar")) {
                    tarantula = classObject.getDouble("tar");
                }
                else {
                    tarantula = 0;
                }
                if(classObject.has("och")) {
                    ochiai = classObject.getDouble("och");
                }
                else {
                    ochiai = 0;;
                }
                if(classObject.has("wong2")) {
                    wong2 = classObject.getDouble("wong2");
                }
                else {
                    wong2 = 0;
                }
                if(classObject.has("rank")) {
                    rank = classObject.getInt("rank");
                }
                else {
                    rank = 0;
                }
                if(classObject.has("faulty")) {
                    faulty = classObject.getBoolean("faulty");
                }
                else {
                    faulty = false;
                }

                classTestData = new ClassTestData();
                classTestData.setName(name);
                classTestData.setLine(line);
                classTestData.setTarantula(tarantula);
                classTestData.setOchiai(ochiai);
                classTestData.setWong2(wong2);
                classTestData.setRank(rank);
                classTestData.setFaulty(faulty);
                classTestData.setPath(path);

                methodsArray = classObject.getJSONArray("methods");
                for(int k = 0; k < methodsArray.length(); k++) {
                    methodObject = methodsArray.getJSONObject(k);
                    name = methodObject.getString("name");
                    if(name.equals("")) {
                        name = "<not_method>";
                    }
                    line = methodObject.getInt("line");
                    if(methodObject.has("tar")) {
                        tarantula = methodObject.getDouble("tar");
                    }
                    else {
                        tarantula = 0;
                    }
                    if(methodObject.has("och")) {
                        ochiai = methodObject.getDouble("och");
                    }
                    else {
                        ochiai = 0;;
                    }
                    if(methodObject.has("wong2")) {
                        wong2 = methodObject.getDouble("wong2");
                    }
                    else {
                        wong2 = 0;
                    }
                    if(methodObject.has("rank")) {
                        rank = methodObject.getInt("rank");
                    }
                    else {
                        rank = 0;
                    }
                    if(methodObject.has("faulty")) {
                        faulty = methodObject.getBoolean("faulty");
                    }
                    else {
                        faulty = false;
                    }

                    methodTestData = new MethodTestData();
                    methodTestData.setName(name);
                    methodTestData.setLine(line);
                    methodTestData.setTarantula(tarantula);
                    methodTestData.setOchiai(ochiai);
                    methodTestData.setWong2(wong2);
                    methodTestData.setRank(rank);
                    methodTestData.setFaulty(faulty);

                    statementsArray = methodObject.getJSONArray("statements");
                    for(int l = 0; l < statementsArray.length(); l++) {
                        statementObject = statementsArray.getJSONObject(l);
                        line = statementObject.getInt("line");
                        tarantula = statementObject.getDouble("tar");
                        ochiai = statementObject.getDouble("och");
                        wong2 = statementObject.getDouble("wong2");
                        if(statementObject.has("rank")) {
                            rank = statementObject.getInt("rank");
                        }
                        else {
                            rank = 0;
                        }
                        faulty = statementObject.getBoolean("faulty");

                        statementTestData = new StatementTestData();
                        statementTestData.setLine(line);
                        statementTestData.setTarantula(tarantula);
                        statementTestData.setOchiai(ochiai);
                        statementTestData.setWong2(wong2);
                        statementTestData.setRank(rank);
                        statementTestData.setFaulty(faulty);

                        methodTestData.getStatements().add(statementTestData);
                    }

                    classTestData.getMethods().add(methodTestData);
                    classTestData.getShowStatements().add(false);
                }

                testData.getClasses().add(classTestData);
                testData.getShowMethods().add(false);
            }
        }

        return testData;
    }

    @Override
    public TestData getTestData() {
        return FlServiceImpl.testData;
    }

    @Override
    public void setTestData(TestData testData) {
        FlServiceImpl.testData = testData;
    }

    @Override
    public void clearTestData() {
        if(FlServiceImpl.testData != null) {
            FlServiceImpl.testData.getClasses().clear();
            FlServiceImpl.testData.getShowMethods().clear();
        }
    }

    @Override
    public void startFileEditorManagerListener(Project project) {
        MessageBus messageBus = project.getMessageBus();
        messageBus.connect().subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileEditorManagerListener() {

            @Override
            public void selectionChanged(@NotNull FileEditorManagerEvent e) {
                if(e.getManager() != null) {
                    Editor editor = e.getManager().getSelectedTextEditor();
                    if(editor != null) {
                        ColorService colorService = new ColorService();
                        colorService.setEditor(editor);
                        colorService.removeColorsByEditor();
                        if(testDataCollected) {
                            String relativeFilePath = parseRelativeFilePath(e.getNewFile().getPath(), ProjectModule.getProjectPath());
                            colorService.setColorsByEditor(testData, relativeFilePath);
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

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
