package services;

import com.intellij.openapi.roots.ProjectRootManager;
import models.bean.ProcessResultData;
import modules.PluginModule;
import modules.ProjectModule;

import java.io.File;
import java.util.*;

public class CallGraphEdgeData {
    private ArrayList<Edge> edges = new ArrayList<>();
    private final String command;
    private String filterName;

    class Edge {
        String callerMethod;
        String calledMethod;

        public Edge(String callerMethod, String calledMethod) {
            this.callerMethod = callerMethod;
            this.calledMethod = calledMethod;
        }
    }

    /**
     * This calls the edge extractor python script
     * @param fileName
     */
    public CallGraphEdgeData(String fileName) {
        String pythonBinPath = ProjectRootManager.getInstance(ProjectModule.getProject()).getProjectSdk().getHomePath();
        PluginModule.setPythonBinPath(pythonBinPath);
        this.command = PluginModule.getPythonBinPath() + " " + PluginModule.getCallGraphEdges() +
                " " + ProjectModule.getProjectPath() + File.separator + "**/*.py" + " " + ProjectModule.getProjectPath();
        this.filterName = fileName.substring(0, fileName.indexOf(".py")).replace("\\", "__");
        getEdges();
    }

    /**
     * Gets [(callerMethod, calledMethod), ...] string list from stdOut and removes "[" and "]" brackets
     */
    private void getEdges() {

        ProcessResultData processResultData = ProcessService.executeCommand(command);
        ArrayList<String> collectedEdges = processResultData.getOutput();
        for (String edge : collectedEdges) {
            makeEdgesArray(edge.substring(edge.indexOf("[")+1, edge.indexOf("]")));
        }
    }

    /**
     * @param edgeDataList string containing information about the call-graph edges in the following format:
     *                     (callerMethod, calledMethod), ...
     *                     First, the function takes the parameter string and extracts the caller method and the
     *                     called method of each edge, by creating a list from the data between "(" and ")" brackets.
     *                     These will be added to an ArrayList as callerMethod and calledMethod.
     */

    private void makeEdgesArray(String edgeDataList) {
        String[] vertexList;
        String callerMethod;
        String calledMethod;
        if (edgeDataList.length() > 0) {
            vertexList = edgeDataList.substring(edgeDataList.indexOf("(") + 1, edgeDataList.indexOf(")"))
                    .split(", ");
            callerMethod = vertexList[0].substring(1, vertexList[0].length() - 1);
            calledMethod = vertexList[1].substring(1, vertexList[1].length() - 1);
            if (!callerMethod.contains("test") && (callerMethod.equals(filterName) ||
                    callerMethod.contains(filterName + "__") || calledMethod.contains(filterName + "__"))) {
                this.edges.add(new Edge(callerMethod, calledMethod));
            }
            edgeDataList = edgeDataList.substring(edgeDataList.indexOf(")") + 1);
            makeEdgesArray(edgeDataList);
        }
    }

    public ArrayList<Edge> getEdgeList() {
        return this.edges;
    }

    public String getCallerMethod(int index) {
        return this.edges.get(index).callerMethod;
    }

    public String getCalledMethod(int index) {
        return this.edges.get(index).calledMethod;
    }
}
