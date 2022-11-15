package models.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import modules.PluginModule;
import modules.ProjectModule;
import services.ProcessService;

import javax.swing.*;

/**
 * This represents the python module which contains python classes.
 */
public class TestData {
    private static TestData instance;
    private ArrayList<ClassTestData> classes;
    private HashMap<String, MethodTestData> edgeDataTransformer;
    private static ArrayList<String> edgeArrayList;


    private TestData() {
        classes = new ArrayList<>();
        edgeDataTransformer = new HashMap<>();
    }

    public static TestData getInstance() {
        if (instance == null) {
            instance = new TestData();
        }
        return instance;
    }

    public static TestData getInstance(String relativePath) {
        if (instance == null) {
            instance = new TestData();
        }
//        String command = "\"" + PluginModule.getPythonBinPath()  + "\""+ " \"" + PluginModule.getCallGraphEdges()+ "\"" +
//                " " +  "\""+ProjectModule.getProjectPath() + File.separator + "**/"+relativePath +"\"" +
//                " " + "\""+ ProjectModule.getProjectPath() + "\""+ " " + "-" + " " + "\"" + PluginModule.getPythonBinPath() + "\"";


//        String command = PluginModule.getPythonBinPath() + " " + PluginModule.getCallGraphEdges() +
//                " " + ProjectModule.getProjectPath() + File.separator + "*.py" +
//                " " + ProjectModule.getProjectPath() + " " + "\"-\"" + " " + PluginModule.getPythonBinPath();

        return instance;
    }

    public ArrayList<String> getEdgeArrayList() {
        return new ArrayList<>();
        //return edgeArrayList;
    }

    public HashMap<String, MethodTestData> makeEdgeTransformation() {
        getAllMethods().stream().forEach(m -> {
            String edgeName = m.getRelativePath().substring(0, m.getRelativePath().indexOf(".py")).replace(File.separator, "__");
            if (m.getSuperName().equals("<not_class>")) {
                edgeName += "__" + m.getName();
            } else {
                edgeName += "__" + m.getSuperName() + "__" + m.getName();
            }

            //System.out.println(edgeName);


            edgeDataTransformer.put(edgeName, (MethodTestData) m);
        });
        return edgeDataTransformer;
    }

    public ArrayList<ClassTestData> getClasses() {
        return classes;
    }

    public ArrayList<ITestData> getAllMethods() {
        ArrayList<ITestData> methods = new ArrayList<>();
        for (var classInstance : this.getClasses()) {
            methods.addAll(classInstance.getElements());
        }
        return methods;
    }

    public ArrayList<ITestData> getAllStatements() {
        ArrayList<ITestData> statements = new ArrayList<>();
        for (var classInstance : this.getClasses()) {
            for (var methodInstance : classInstance.getElements()) {
                statements.addAll(methodInstance.getElements());
            }
        }
        return statements;
    }


    public ITestData getElement(String relativePath, int line) {
        for (var classInstance : this.getClasses()) {
            if (classInstance.getRelativePath().equals(relativePath)) {
                if (classInstance.getLine() == line) {
                    return classInstance;
                } else {
                    for (var methodInstance : classInstance.getElements()) {
                        if (methodInstance.getLine() == line) {
                            return methodInstance;
                        } else {
                            for (var statementInstance : methodInstance.getElements()) {
                                if (statementInstance.getLine() == line) {
                                    return statementInstance;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
