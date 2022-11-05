package modules;

import java.io.File;

import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.openapi.extensions.PluginId;
import models.bean.Formula;

public class PluginModule {
    private static final String PLUGIN_NAME = "CharmFL";
    private static final String PLUGIN_ID = "hu.szte.sed.charmfl";
    private static final String PYFL_BIN = "main.py";
    private static final String CHECK_PIP_BIN = "check_pip.py";
    private static final String PIP_BIN = "pip";
    private static final String REQUIREMENTS_FILE_NAME = "requirements.txt";
    private static final String RESULTS_JSON_FILE_NAME = "results.json";
    private static PluginId pluginId = null;
    private static String pluginPath = "";
    private static String pyflBinPath = "";
    private static String checkPipBinPath = "";
    private static final String PYTHON_SCRIPTS_FOLDER_NAME = "Scripts";

    private static final String CALL_GRAPH_SCRIPT_NAME = "call_graph_maker.py";
    private static final String CALL_ADD_HIGHLIGHT_TO_GRAPH_SCRIPT_NAME = "call_graph_highlight.py";

    private static final String CALL_GRAPH_EDGES = "call_graphs/call_graph_edges.py";
    private static String pythonBinPath;
    private static String pipBinPath;

    private static boolean tarantulaSelected = true;
    private static boolean ochiaiSelected = false;
    private static boolean dStarSelected = false;
    private static boolean wongIISelected = false;

    private static boolean maximumSelected = true;
    private static boolean minimumSelected = false;
    private static boolean averageSelected = false;

    /**
     * Sets this object's plugin id to the built-in plugin id.
     */
    public static void refreshPluginId() {
        pluginId = PluginId.getId(PLUGIN_ID);
    }

    /**
     * Sets the path of the installed charmfl plugin
     */
    public static void refreshPluginPath() {
        pluginPath = PluginManagerCore.getPlugin(pluginId).getPluginPath().toString();
    }

    /**
     * Sets the path of the installed charmfl's plugin main.py (backend) and check_pip.py (checking requirements)
     */
    public static void refreshPluginPythonBinPath() {
        pyflBinPath = pluginPath + File.separator + PYFL_BIN;
        checkPipBinPath = pluginPath + File.separator + CHECK_PIP_BIN;
    }

    /**
     * Gets the installed charmfl's plugin name
     * @return the plugin name
     */
    public static String getPluginName() {
        return PLUGIN_NAME;
    }

    /**
     * Gets the installed charmfl's plugin id
     * @return the plugin id
     */
    public static String getPluginId() {
        return PLUGIN_ID;
    }

    /**
     * Gets the call graph generator scripts path
     * @return pathToInstalledCharmFL/call_graphs/call_graph_maker.py
     */
    public static String getCallGraphScriptName() {
        return pluginPath + File.separator + CALL_GRAPH_SCRIPT_NAME;
    }

    /**
     * Gets the call graph edge retriever scripts path
     * @return pathToInstalledCharmFL/call_graphs/call_graph_edges.py
     */
    public static String getCallGraphEdges() {
        return pluginPath + File.separator + CALL_GRAPH_EDGES;
    }

    /**
     * Gets the call graph highlighter scripts path
     * @return pathToInstalledCharmFL/call_graphs/call_graph_highlight.py
     */
    public static String callAddHighlightToGraphScriptName() {
        return pluginPath + File.separator + CALL_ADD_HIGHLIGHT_TO_GRAPH_SCRIPT_NAME;
    }

    /**
     * Gets the path of the installed charmfl's plugin main.py (backend)
     * @return the path to main.py
     */
    public static String getPyflBinPath() {
        return pyflBinPath;
    }

    /**
     * Gets the path of the installed charmfl's plugin check_pip.py (checking requirements)
     * @return the path to main.py
     */
    public static String getCheckPipBinPath() {
        return checkPipBinPath;
    }


    public static String getPythonBinPath() {
        return pythonBinPath;
    }

    /**
     * Returns the path of pip script
     * @return path
     */
    public static String getPipBinPath() {
        return pipBinPath;
    }

    /**
     * Returns the requirements.txt
     * @return path and filename
     */
    public static String getRequirementsFilePath() {
        return pluginPath + File.separator + REQUIREMENTS_FILE_NAME;
    }

    /**
     * Returns the results json's name
     * @return name
     */
    public static final String getResultsJsonFileName() {
        return RESULTS_JSON_FILE_NAME;
    }

    public static void setPythonBinPath(String pythonBinPath) {
        PluginModule.pythonBinPath = pythonBinPath;
    }

    /**
     * Returns whether Tarantula score calculation is the selected approach
     * @return true if tarantula is selected
     */
    public static boolean isTarantulaSelected() {
        return tarantulaSelected;
    }

    public static void setTarantulaSelected(boolean tarantulaSelected) {
        PluginModule.tarantulaSelected = tarantulaSelected;
    }

    /**
     * Returns whether Ochiai score calculation is the selected approach
     * @return true if ochiai is selected
     */
    public static boolean isOchiaiSelected() {
        return ochiaiSelected;
    }

    public static void setOchiaiSelected(boolean ochiaiSelected) {
        PluginModule.ochiaiSelected = ochiaiSelected;
    }

    /**
     * Returns whether Dstar score calculation is the selected approach
     * @return true if dstar is selected
     */
    public static boolean isDStarSelected() {
        return dStarSelected;
    }

    public static void setDStarSelected(boolean dStarSelected) {
        PluginModule.dStarSelected = dStarSelected;
    }

    /**
     * Returns whether Wong2 score calculation is the selected approach
     * @return true if tarantula is selected
     */
    public static boolean isWongIISelected() {
        return wongIISelected;
    }

    public static void setWongIISelected(boolean wongIISelected) {
        PluginModule.wongIISelected = wongIISelected;
    }

    /**
     * Ranking approach
     * @return true if maximum ranking is selected
     */
    public static boolean isMaximumSelected() {
        return maximumSelected;
    }

    public static void setMaximumSelected(boolean maximumSelected) {
        PluginModule.maximumSelected = maximumSelected;
    }

    /**
     * Ranking approach
     * @return true if minimum ranking is selected
     */
    public static boolean isMinimumSelected() {
        return minimumSelected;
    }

    public static void setMinimumSelected(boolean minimumSelected) {
        PluginModule.minimumSelected = minimumSelected;
    }

    /**
     * Ranking approach
     * @return true if average ranking is selected
     */
    public static boolean isAverageSelected() {
        return averageSelected;
    }

    public static void setAverageSelected(boolean averageSelected) {
        PluginModule.averageSelected = averageSelected;
    }

    public static void setPipBinPath(String pipBinPath) {
        PluginModule.pipBinPath = pipBinPath;
    }

    /**
     * This function returns the actually selected formula enum value, by default it's Tarantula.
     * Please note that since we currently using only Ochiai and Tarantula
     * thus this function must be updated if other formulas will be used!
     * @return the actually chosen formula
     */
    public static Formula getSelectedFormula(){
        if(isOchiaiSelected()) return Formula.OCHIAI;
        return Formula.TARANTULA;
    }
}
