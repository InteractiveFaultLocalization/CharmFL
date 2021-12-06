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
        // Only for debug!
        //System.out.println(command);
        return executeCommand(command);
        /*//MOCK
        ProcessResult processResult = new ProcessResult();
        processResult.setExitCode(0);
        ArrayList<String> lines = new ArrayList<>();
        lines.add("{\"files\": [{\"path\": \"example.py\", \"classes\": [{\"name\": \"\", \"line\": 0, \"methods\": [{\"name\": \"addToCart\", \"line\": 8, \"statements\": [{\"line\": \"8\", \"tar\": 0.4800000000000001, \"och\": 0.2773500981126146, \"wong2\": 0.0, \"faulty\": \"false\"}, {\"line\": \"34\", \"tar\": 0.4800000000000001, \"och\": 0.2773500981126146, \"wong2\": 0.0, \"faulty\": \"false\"}, {\"line\": \"15\", \"tar\": 0.4800000000000001, \"och\": 0.19611613513818404, \"wong2\": 0.0, \"faulty\": \"false\"}], \"tar\": 0.5806451612903226, \"och\": 0.5262348115842176, \"wong2\": 2.0, \"faulty\": \"false\"}, {\"name\": \"getProductCount\", \"line\": 34, \"statements\": [{\"line\": \"34\", \"tar\": 0.4800000000000001, \"och\": 0.2773500981126146, \"wong2\": 0.0, \"faulty\": \"false\"}, {\"line\": \"15\", \"tar\": 0.4800000000000001, \"och\": 0.19611613513818404, \"wong2\": 0.0, \"faulty\": \"false\"}], \"tar\": 0.4800000000000001, \"och\": 0.3922322702763681, \"wong2\": 0.0, \"faulty\": \"false\"}, {\"name\": \"removeFromCart\", \"line\": 15, \"statements\": [{\"line\": \"15\", \"tar\": 0.4800000000000001, \"och\": 0.19611613513818404, \"wong2\": 0.0, \"faulty\": \"false\"}], \"tar\": 0.4090909090909091, \"och\": 0.3144854510165755, \"wong2\": -1.0, \"faulty\": \"false\"}]}]}]}");
        processResult.setOutput(lines);
        return processResult;*/
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
        //System.out.println(command);
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
        //System.out.println(command);
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

    /*@Override
    public TestData parseTestOutput(ArrayList<String> lines) {
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
    }*/

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

    /*@Override
    public int getTestDataRowCount() {
        if(testData == null) {
            return 0;
        }

        int sum = 0;
        for(Map.Entry<String, ArrayList<FileLineScoreData>> entry : testData.entrySet()) {
            sum += testData.get(entry.getKey()).size();
        }
        return sum;
    }*/

    /*public String[][] prepareTestDataForTable() {
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
    }*/

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
