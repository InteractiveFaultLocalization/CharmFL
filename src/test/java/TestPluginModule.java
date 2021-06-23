import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import modules.PluginModule;

public class TestPluginModule {

    @Test
    @DisplayName("Test Parse pip binary path")
    public void testParsePipBinPath() {
        String input = "C:\\Programs\\Python\\python";
        String expected = "C:\\Programs\\Python\\Scripts\\pip";
        boolean result = PluginModule.parsePipBinPath(input);
        assertEquals(true, result);
        assertEquals(expected, PluginModule.getPipBinPath());

        input = "C:\\Programs\\Python\\Scripts\\python";
        expected = "C:\\Programs\\Python\\Scripts\\pip";
        result = PluginModule.parsePipBinPath(input);
        assertEquals(true, result);
        assertEquals(expected, PluginModule.getPipBinPath());

        input = "NotValidPathPython";
        result = PluginModule.parsePipBinPath(input);
        assertEquals(false, result);
    }
}
