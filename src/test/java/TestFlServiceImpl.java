import static org.junit.jupiter.api.Assertions.assertEquals;

import models.bean.ProcessResult;
import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.HashMap;

import services.FlService;
import services.FlServiceImpl;
import models.bean.FileLineScoreData;

public class TestFlServiceImpl {
    private static FlService flService;

    @BeforeAll
    public static void beforeAll() {
        flService = new FlServiceImpl();
    }

    @Test
    @DisplayName("Test parse relative file path from full file path and full project path")
    public void testParseRelativeFilePath() {
        String currentFilePath = "C:\\Users\\User\\Desktop\\Project\\modules\\module.py";
        String projectPath = "C:\\Users\\User\\Desktop\\Project";
        String result = flService.parseRelativeFilePath(currentFilePath, projectPath);
        assertEquals("modules\\module.py", result);

        currentFilePath = "C:\\Users\\Username with space\\Desktop\\Project\\modules\\module.py";
        projectPath = "C:\\Users\\Username with space\\Desktop\\Project";
        result = flService.parseRelativeFilePath(currentFilePath, projectPath);
        assertEquals("modules\\module.py", result);

        currentFilePath = "/home/user/Project with space/modules/module.py";
        projectPath = "/home/user/Project with space";
        result = flService.parseRelativeFilePath(currentFilePath, projectPath);
        assertEquals("modules\\module.py", result);

        currentFilePath = "/home/user/Project/modules/module.py";
        projectPath = "/home/user/Project";
        result = flService.parseRelativeFilePath(currentFilePath, projectPath);
        assertEquals("modules\\module.py", result);
    }

    @Test
    @DisplayName("Test process execute and it's output")
    public void testExecuteCommand() {
        String command;
        ArrayList<String> expected = new ArrayList<String>();
        expected.add("Test output");
        expected.add("Second line");
        if(SystemUtils.IS_OS_LINUX) {
            command = "/bin/bash -c \"echo -e Test output\\\\\\nSecond line\"";
        }
        else if(SystemUtils.IS_OS_WINDOWS) {
            command = "cmd.exe /c \"echo Test output&&echo Second line\"";
        }
        else{
            return;
        }
        ProcessResult processResult = flService.executeCommand(command);
        assertEquals(expected.get(0), processResult.getOutput().get(0));
        assertEquals(expected.get(1), processResult.getOutput().get(1));
    }

    @Test
    @DisplayName("Test parse test output")
    public void testParseTestOutput() {
        ArrayList<String> input = new ArrayList<>();
        input.add("Pytest header");
        input.add("Other info");
        input.add("----------");
        input.add("modules/module.py:1 0.3");
        input.add("modules/module.py:3 0.5");
        input.add("modules/another_module.py:102 0.8");
        input.add("----------");

        HashMap<String, ArrayList<FileLineScoreData>> expected = new HashMap<>();
        expected.put("modules/module.py", new ArrayList<>());
        expected.get("modules/module.py").add(new FileLineScoreData(1, 0.3));
        expected.get("modules/module.py").add(new FileLineScoreData(3, 0.5));
        expected.put("modules/another_module.py", new ArrayList<>());
        expected.get("modules/another_module.py").add(new FileLineScoreData(102, 0.8));

        HashMap<String, ArrayList<FileLineScoreData>> result = flService.parseTestOutput(input);
        assertEquals(expected.get("modules/module.py").get(0).getLineNumber(), result.get("modules/module.py").get(0).getLineNumber());
        assertEquals(expected.get("modules/module.py").get(0).getLineScore(), result.get("modules/module.py").get(0).getLineScore());
        assertEquals(expected.get("modules/module.py").get(1).getLineNumber(), result.get("modules/module.py").get(1).getLineNumber());
        assertEquals(expected.get("modules/module.py").get(1).getLineScore(), result.get("modules/module.py").get(1).getLineScore());
        assertEquals(expected.get("modules/another_module.py").get(0).getLineNumber(), result.get("modules/another_module.py").get(0).getLineNumber());
        assertEquals(expected.get("modules/another_module.py").get(0).getLineScore(), result.get("modules/another_module.py").get(0).getLineScore());
    }
}
