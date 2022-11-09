package services;

import com.intellij.openapi.roots.ProjectRootManager;
import java.util.HashMap;
import models.bean.ProcessResultData;
import models.bean.TestData;
import modules.PluginModule;
import modules.ProjectModule;
import java.io.File;
import java.util.ArrayList;


public class CallGraphEdgeData {
    private ArrayList<Edge> edges = new ArrayList<>();
    private final String command;
    private String filterName;
    private String methodName;
    private String className;
    private static final String SEPARATOR = "__";

    class Edge {
        String callerMethod;
        String calledMethod;

        public Edge(String callerMethod, String calledMethod) {
            this.callerMethod = callerMethod;
            this.calledMethod = calledMethod;
        }
    }

    /**
     * This calls the call-graph highlighter python script
     * @param relativePath, methodName
     */

    public CallGraphEdgeData(String relativePath, String className, String methodName, TestData testData) {
        this.className = className;
        this.methodName = methodName;
        String pythonBinPath = ProjectRootManager.getInstance(ProjectModule.getProject()).getProjectSdk().getHomePath();
        PluginModule.setPythonBinPath(pythonBinPath);


        String relativePart = relativePath.substring(0, relativePath.indexOf(".py")).replace("\\", SEPARATOR);
        String nodeName = relativePart + SEPARATOR + methodName;
        this.filterName = relativePart;






        this.command = PluginModule.getPythonBinPath() + " " + PluginModule.getCallGraphEdges() +
                " " + ProjectModule.getProjectPath() + File.separator + "**/"+relativePath +
                " " + ProjectModule.getProjectPath() + " " + nodeName + " " + PluginModule.getPythonBinPath();
        //System.out.println(this.command);
        getEdges(testData);

    }

    /**
     * Gets [(callerMethod, calledMethod), ...] string list from stdOut and removes "[" and "]" brackets
     */
    private void getEdges(TestData testData) {

        //ProcessResultData processResultData = ProcessService.executeCommand(command);
        //ArrayList<String> collectedEdges = processResultData.getOutput();
        ArrayList<String> collectedEdges = testData.getEdgeArrayList();
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
            callerMethod = vertexList[0].substring(1, vertexList[0].length() - 1); //oh god help me through this...
            calledMethod = vertexList[1].substring(1, vertexList[1].length() - 1);
            if (!callerMethod.contains("test") &&
                    (callerMethod.equals(filterName) ||
                            callerMethod.contains(filterName + SEPARATOR) ||
                            calledMethod.contains(filterName + SEPARATOR))) {
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
        //System.out.println(this.edges.get(index).calledMethod);
        return this.edges.get(index).calledMethod;

    }
}
