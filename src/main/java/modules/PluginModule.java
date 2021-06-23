package modules;

import java.io.File;

import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.openapi.extensions.PluginId;

public class PluginModule {
    private static final String PLUGIN_ID = "hu.szte.raymonddrakon.charmfl";
    private static final String pyflBin = "main.py";
    private static final String checkPipBin = "check_pip.py";
    private static final String pipBin = "pip";
    private static final String requirementsFileName = "requirements.txt";
    private static PluginId pluginId = null;
    private static String pluginPath = "";
    private static String pyflBinPath = "";
    private static String checkPipBinPath = "";
    private static final String pythonScriptsFolderName = "Scripts";
    private static String pythonBinPath;
    private static String pipBinPath;

    public static void refreshPluginId() {
        pluginId = PluginId.getId(PLUGIN_ID);
    }

    public static void refreshPluginPath() {
        pluginPath = PluginManagerCore.getPlugin(pluginId).getPluginPath().toString();
    }

    public static void refreshPluginPythonBinPath() {
        pyflBinPath = pluginPath + File.separator + pyflBin;
        checkPipBinPath = pluginPath + File.separator + checkPipBin;
    }

    public static String getPluginId() {
        return PLUGIN_ID;
    }

    public static String getPyflBin() {
        return pyflBin;
    }

    public static String getCheckPipBin() {
        return checkPipBin;
    }

    public static String getPipBin() {
        return pipBin;
    }

    public static String getPluginPath() {
        return pluginPath;
    }

    public static String getPyflBinPath() {
        return pyflBinPath;
    }

    public static String getCheckPipBinPath() {
        return checkPipBinPath;
    }

    public static String getPythonBinPath() {
        return pythonBinPath;
    }

    public static String getPipBinPath() {
        return pipBinPath;
    }

    public static String getRequirementsFileName() {
        return requirementsFileName;
    }

    public static String getRequirementsFilePath() {
        return pluginPath + File.separator + requirementsFileName;
    }

    public static String getPythonScriptsFolderName() {
        return pythonScriptsFolderName;
    }

    public static void setPythonBinPath(String pythonBinPath) {
        PluginModule.pythonBinPath = pythonBinPath;
    }

    /*public static boolean parsePipBinPath(String pythonBinPath) {
        int projectSdkPathIndex = pythonBinPath.lastIndexOf("\\");
        if(projectSdkPathIndex == -1) {
            projectSdkPathIndex = pythonBinPath.lastIndexOf("/");
        }
        if(projectSdkPathIndex == -1) {
            return false;
        }

        String projectSdkPath = pythonBinPath.substring(0, projectSdkPathIndex + 1);
        String pipBinPath = "";
        if(projectSdkPath.endsWith(PluginModule.getPythonScriptsFolderName() + File.separator)) {
            pipBinPath = projectSdkPath + PluginModule.getPipBin();
        }
        else {
            pipBinPath = projectSdkPath + PluginModule.getPythonScriptsFolderName() + File.separator + PluginModule.getPipBin();
        }
        setPipBinPath(pipBinPath);
        return true;
    }*/

    public static void setPipBinPath(String pipBinPath) {
        PluginModule.pipBinPath = pipBinPath;
    }
}
