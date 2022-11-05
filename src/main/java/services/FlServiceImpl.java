package services;

import java.io.*;
import java.util.ArrayList;

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

import static services.ProcessService.executeCommand;

public class FlServiceImpl {
    private static TestData testData = null;
    private static boolean testDataCollected = false;
    private static boolean fileEditorColoringEnabled = false;
    private static boolean viewResultTableDialogOpened = false;
    private static boolean testDataCollecting = false;

    /**
     * This calls the subprocess of executing the call graph
     * @param callGraphScriptName
     * @param projectPath
     * @param mainFileName
     * @param pythonBinPath
     * @return
     */
    public ProcessResultData executeCallGraph(String callGraphScriptName, String projectPath, Object mainFileName, String pythonBinPath) {
        String command = "";
        if (SystemUtils.IS_OS_WINDOWS) {
            command = "\"" + pythonBinPath + "\" " + "\"" + callGraphScriptName + "\" " + "\"" + projectPath + "\" " + "\"" + mainFileName + "\" " + " -cg";
    }

        return executeCommand(command);
    }

    /**
     * This calls the subprocess of executing the call graph highlight
     * @param callGraphScriptName
     * @param projectPath
     * @param methodName
     * @param pythonBinPath
     * @return
     */
    public ProcessResultData executeAddHighlightToCallGraph(String callGraphScriptName, String projectPath, Object methodName, String pythonBinPath) {
        String command = "";
        if (SystemUtils.IS_OS_WINDOWS) {
            command = "\"" + pythonBinPath + "\" " + "\"" + callGraphScriptName + "\" " + "\"" + projectPath + "\" " + methodName + " -cg";
        }

        return executeCommand(command);
    }

    /**
     * This calls the subprocess which executed the main.py
     * @param pythonBinPath
     * @param pyflPath
     * @param projectPath
     * @return
     */
    public ProcessResultData executeTest(String pythonBinPath, String pyflPath, String projectPath) {
        String command = "";
        if (SystemUtils.IS_OS_WINDOWS) {
            command = "\"" + pythonBinPath + "\" " +
                    "\"" + pyflPath + "\" -d " +
                    "\"" + projectPath + "\"" +
                    " -fl";
        } else if (SystemUtils.IS_OS_LINUX) {
            command = pythonBinPath.replaceAll(" ", "\\ ") + " " +
                    pyflPath.replaceAll(" ", "\\ ") + " -d " +
                    projectPath.replaceAll(" ", "\\ ") + " -fl";
        }
        return executeCommand(command);
    }

    /**
     * This checks whether the packages are installed
     * @param pythonBinPath
     * @param pipBinPath
     * @param requirementsPath
     * @param projectPath
     * @return
     */
    public ProcessResultData executeRequirementsInstall(String pythonBinPath, String pipBinPath, String requirementsPath, String projectPath) {
        String command = "";
        if (SystemUtils.IS_OS_WINDOWS) {
            command = "\"" + pythonBinPath + "\" " +
                    "\"" + pipBinPath + "\" " +
                    "install -r " +
                    "\"" + requirementsPath + "\"";
        } else if (SystemUtils.IS_OS_LINUX) {
            command = pythonBinPath.replaceAll(" ", "\\ ") + " " +
                    pipBinPath.replaceAll(" ", "\\ ") + " " +
                    "install -r " +
                    requirementsPath.replaceAll(" ", "\\ ");
        }
        return executeCommand(command);
    }

    /**
     * This executes the subprocess which gets the pip path
     * @param pythonBinPath
     * @param checkPipBinPath
     * @return
     */
    public ProcessResultData executeGetPipBinPath(String pythonBinPath, String checkPipBinPath) {
        String command = "";
        if (SystemUtils.IS_OS_WINDOWS) {
            command = "\"" + pythonBinPath + "\" " +
                    "\"" + checkPipBinPath + "\"";
        } else if (SystemUtils.IS_OS_LINUX) {
            command = pythonBinPath.replaceAll(" ", "\\ ") + " " +
                    checkPipBinPath.replaceAll(" ", "\\ ");
        }
        return executeCommand(command);
    }

    /**
     * This reads the file (e.g. results.json)
     * @param fileName
     * @return
     */
    public ArrayList<String> readTextFile(String fileName) {
        ArrayList<String> lines = new ArrayList<>();

        File file = new File(fileName);
        try {
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    //TODO: composite design pattern
    /**
     * This parses the results.json to Test Data
     * Gets the classes methods and statements and make the connections.
     * @param lines the lines of the document that will be parsed
     * @return
     */
    public TestData parseTestDataJSON(ArrayList<String> lines) {
        TestData testData = TestData.getInstance();
        String json = String.join(" ", lines);
        JSONObject jsonObject = new JSONObject(json);

        JSONObject fileObject, classObject, methodObject, statementObject;
        String name;
        int line;
        double tarantula, ochiai, wong2, dstar;
        int rank;
        boolean faulty;
        String relativePath;
        ClassTestData classTestData;
        MethodTestData methodTestData;
        StatementTestData statementTestData;
        JSONArray filesArray, classesArray, methodsArray, statementsArray;

        filesArray = jsonObject.getJSONArray("files");
        for (int i = 0; i < filesArray.length(); i++) {
            fileObject = filesArray.getJSONObject(i);
            classesArray = fileObject.getJSONArray("classes");
            relativePath = fileObject.getString("relativePath");

            for (int j = 0; j < classesArray.length(); j++) {
                classObject = classesArray.getJSONObject(j);
                name = classObject.getString("name");
                if (name.equals("")) {
                    name = "<not_class>";
                }
                line = classObject.getInt("line");
                if (classObject.has("tar")) {
                    tarantula = classObject.getDouble("tar");
                } else {
                    tarantula = 0;
                }
                if (classObject.has("och")) {
                    ochiai = classObject.getDouble("och");
                } else {
                    ochiai = 0;
                }
                if (classObject.has("wong2")) {
                    wong2 = classObject.getDouble("wong2");
                }
                else {
                    wong2 = 0;
                }
                if (classObject.has("dstar")) {
                    dstar = classObject.getDouble("dstar");
                }
                else {
                    dstar = 0;
                }
                if (classObject.has("rank")) {
                    rank = classObject.getInt("rank");
                } else {
                    rank = 0;
                }
                if (classObject.has("faulty")) {
                    faulty = classObject.getBoolean("faulty");
                } else {
                    faulty = false;
                }

                classTestData = new ClassTestData();
                classTestData.setName(name);
                classTestData.setLine(line);
                classTestData.setTarantula(tarantula);
                classTestData.setOchiai(ochiai);
                classTestData.setWong2(wong2);
                classTestData.setDstar(dstar);
                classTestData.setRank(rank);
                classTestData.setFaulty(faulty);
                classTestData.setRelativePath(relativePath);

                methodsArray = classObject.getJSONArray("methods");
                for (int k = 0; k < methodsArray.length(); k++) {
                    methodObject = methodsArray.getJSONObject(k);
                    name = methodObject.getString("name");
                    if (name.equals("")) {
                        name = "<not_method>";
                    }
                    line = methodObject.getInt("line");
                    if (methodObject.has("tar")) {
                        tarantula = methodObject.getDouble("tar");
                    } else {
                        tarantula = 0;
                    }
                    if (methodObject.has("och")) {
                        ochiai = methodObject.getDouble("och");
                    } else {
                        ochiai = 0;
                    }
                    if (methodObject.has("wong2")) {
                        wong2 = methodObject.getDouble("wong2");
                    } else {
                        wong2 = 0;
                    }
                    if (methodObject.has("dstar")) {
                        dstar = methodObject.getDouble("dstar");
                    } else {
                        dstar = 0;
                    }
                    if (methodObject.has("rank")) {
                        rank = methodObject.getInt("rank");
                    } else {
                        rank = 0;
                    }
                    if (methodObject.has("faulty")) {
                        faulty = methodObject.getBoolean("faulty");
                    } else {
                        faulty = false;
                    }

                    methodTestData = new MethodTestData();
                    methodTestData.setName(name);
                    methodTestData.setLine(line);
                    methodTestData.setTarantula(tarantula);
                    methodTestData.setOchiai(ochiai);
                    methodTestData.setWong2(wong2);
                    methodTestData.setDstar(dstar);
                    methodTestData.setRank(rank);
                    methodTestData.setFaulty(faulty);

                    statementsArray = methodObject.getJSONArray("statements");
                    for (int l = 0; l < statementsArray.length(); l++) {
                        statementObject = statementsArray.getJSONObject(l);
                        line = statementObject.getInt("line");
                        tarantula = statementObject.getDouble("tar");
                        ochiai = statementObject.getDouble("och");
                        wong2 = statementObject.getDouble("wong2");
                        dstar = statementObject.getDouble("dstar");
                        if (statementObject.has("rank")) {
                            rank = statementObject.getInt("rank");
                        } else {
                            rank = 0;
                        }
                        faulty = statementObject.getBoolean("faulty");

                        statementTestData = new StatementTestData();
                        statementTestData.setClassName(classTestData.getName());
                        statementTestData.setMethodName(methodTestData.getName());
                        statementTestData.setLine(line);
                        statementTestData.setTarantula(tarantula);
                        statementTestData.setOchiai(ochiai);
                        statementTestData.setWong2(wong2);
                        statementTestData.setDstar(dstar);
                        statementTestData.setRank(rank);
                        statementTestData.setFaulty(faulty);

                        methodTestData.getStatements().add(statementTestData);
                    }

                    classTestData.getMethods().add(methodTestData);
                }

                testData.getClasses().add(classTestData);
            }
        }

        return testData;
    }

    public TestData getTestData() {
        return FlServiceImpl.testData;
    }

    public void setTestData(TestData testData) {
        FlServiceImpl.testData = testData;
    }

    public void clearTestData() {
        if (FlServiceImpl.testData != null) {
            FlServiceImpl.testData.getClasses().clear();
        }
    }

    /**
     * Sets the file to be editable and colorable.
     * @param project
     */
    public void startFileEditorManagerListener(Project project) {
        MessageBus messageBus = project.getMessageBus();
        messageBus.connect().subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileEditorManagerListener() {

            @Override
            public void selectionChanged(@NotNull FileEditorManagerEvent e) {
                if (e.getManager() != null) {
                    Editor editor = e.getManager().getSelectedTextEditor();
                    if (editor != null) {
                        ColorService colorService = new ColorService();
                        colorService.setEditor(editor);
                        colorService.removeColorsByEditor();
                        if (testDataCollected) {
                            String relativeFilePath = parseRelativeFilePath(e.getNewFile().getPath(), ProjectModule.getProjectPath());
                            colorService.setColorsByEditor(testData, relativeFilePath);
                        }
                    }
                }
            }
        });
    }

    /**
     * This will replace the separator charachters to the separator charachters used in the current system
     * @param currentFilePath
     * @param projectPath
     * @return
     */
    public String parseRelativeFilePath(String currentFilePath, String projectPath) {
        String relativeFilePath = currentFilePath.substring(projectPath.length() + 1);
        relativeFilePath = relativeFilePath.replace("\\", File.separator);
        relativeFilePath = relativeFilePath.replace("/", File.separator);
        return relativeFilePath;
    }

    public boolean isTestDataCollected() {
        return testDataCollected;
    }

    public boolean isFileEditorColoringEnabled() {
        return fileEditorColoringEnabled;
    }

    public void setTestDataCollected(boolean status) {
        testDataCollected = status;
    }

    public void setFileEditorColoringEnabled(boolean status) {
        fileEditorColoringEnabled = status;
    }

    public boolean isViewResultTableDialogOpened() {
        return viewResultTableDialogOpened;
    }

    public void setViewResultTableDialogOpened(boolean viewResultTableDialogOpened) {
        FlServiceImpl.viewResultTableDialogOpened = viewResultTableDialogOpened;
    }

    public boolean isTestDataCollecting() {
        return testDataCollecting;
    }

    public void setTestDataCollecting(boolean testDataCollecting) {
        FlServiceImpl.testDataCollecting = testDataCollecting;
    }
}
