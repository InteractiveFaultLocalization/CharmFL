package modules;

import java.io.File;

import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.openapi.extensions.PluginId;

public class PluginModule {
    private static final String PLUGIN_NAME = "CharmFL";
    private static final String PLUGIN_ID = "hu.szte.raymonddrakon.charmfl";
    private static final String pyflBin = "main.py";
    private static final String checkPipBin = "check_pip.py";
    private static final String pipBin = "pip";
    private static final String requirementsFileName = "requirements.txt";
    private static final String resultsJSONFileName = "results.json";
    private static PluginId pluginId = null;
    private static String pluginPath = "";
    private static String pyflBinPath = "";
    private static String checkPipBinPath = "";
    private static final String pythonScriptsFolderName = "Scripts";
    private static String pythonBinPath;
    private static String pipBinPath;

    private static boolean tarantulaSelected = true;
    private static boolean ochiaiSelected = false;
    private static boolean dStarSelected = false;
    private static boolean wongIISelected = false;

    private static boolean maximumSelected = true;
    private static boolean minimumSelected = false;
    private static boolean averageSelected = false;

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

    public static String getPluginName() {
        return PLUGIN_NAME;
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

    public static final String getResultsJSONFileName() {
        return resultsJSONFileName;
    }

    public static String getPythonScriptsFolderName() {
        return pythonScriptsFolderName;
    }

    public static void setPythonBinPath(String pythonBinPath) {
        PluginModule.pythonBinPath = pythonBinPath;
    }

    public static boolean isTarantulaSelected() {
        return tarantulaSelected;
    }

    public static void setTarantulaSelected(boolean tarantulaSelected) {
        PluginModule.tarantulaSelected = tarantulaSelected;
    }

    public static boolean isOchiaiSelected() {
        return ochiaiSelected;
    }

    public static void setOchiaiSelected(boolean ochiaiSelected) {
        PluginModule.ochiaiSelected = ochiaiSelected;
    }

    public static boolean isDStarSelected() {
        return dStarSelected;
    }

    public static void setDStarSelected(boolean dStarSelected) {
        PluginModule.dStarSelected = dStarSelected;
    }

    public static boolean isWongIISelected() {
        return wongIISelected;
    }

    public static void setWongIISelected(boolean wongIISelected) {
        PluginModule.wongIISelected = wongIISelected;
    }

    public static boolean isMaximumSelected() {
        return maximumSelected;
    }

    public static void setMaximumSelected(boolean maximumSelected) {
        PluginModule.maximumSelected = maximumSelected;
    }

    public static boolean isMinimumSelected() {
        return minimumSelected;
    }

    public static void setMinimumSelected(boolean minimumSelected) {
        PluginModule.minimumSelected = minimumSelected;
    }

    public static boolean isAverageSelected() {
        return averageSelected;
    }

    public static void setAverageSelected(boolean averageSelected) {
        PluginModule.averageSelected = averageSelected;
    }

    public static void setPipBinPath(String pipBinPath) {
        PluginModule.pipBinPath = pipBinPath;
    }
}
