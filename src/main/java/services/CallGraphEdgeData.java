package services;

import com.intellij.openapi.roots.ProjectRootManager;
import modules.PluginModule;
import modules.ProjectModule;
import java.io.File;


public class CallGraphEdgeData {

    /**
     * This calls the call-graph highlighter python script
     * @param relativePath, methodName
     */
    public static void createHighlightedCallGraph(String relativePath, String methodName) {
        String relativePart = relativePath.substring(0, relativePath.indexOf(".py")).replace("\\", "__");
        String nodeName = relativePart + "__" + methodName;
        String pythonBinPath = ProjectRootManager.getInstance(ProjectModule.getProject()).getProjectSdk().getHomePath();
        PluginModule.setPythonBinPath(pythonBinPath);
        String command = PluginModule.getPythonBinPath() + " " + PluginModule.getCallGraphEdges() +
                " " + ProjectModule.getProjectPath() + File.separator + "**/*.py" +
                " " + ProjectModule.getProjectPath() + " " + nodeName;
        ProcessService.executeCommand(command);
    }
}
