package models.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * This represents the python module which contains python classes.
 */
public class TestData {
    private static TestData instance;
    private ArrayList<ClassTestData> classes;



    private TestData() {
        classes = new ArrayList<>();
    }

    public static TestData getInstance(){
        if (instance == null){
            instance = new TestData();
        }
        return instance;
    }

    public ArrayList<ClassTestData> getClasses() {
        return classes;
    }

    public ArrayList<MethodTestData> getAllMethods(){
        ArrayList<MethodTestData> methods = new ArrayList<MethodTestData>();
        for (var classInstance : this.getClasses()) {
            methods.addAll(classInstance.getMethods());
        }
        return methods;
    }

    public ArrayList<StatementTestData> getAllStatements(){
        ArrayList<StatementTestData> statements = new ArrayList<StatementTestData>();
        for (var classInstance : this.getClasses()) {
            for (var methodInstance : classInstance.getMethods()) {
                statements.addAll(methodInstance.getStatements());
            }
        }
        return statements;
    }

    public ITestData getElement(String relativePath, int line){
        for (var classInstance : this.getClasses()) {
            if (classInstance.getRelativePath().equals(relativePath)) {
                if (classInstance.getLine() == line) {
                    return classInstance;
                } else {
                    for (var methodInstance : classInstance.getMethods()) {
                        if (methodInstance.getLine() == line) {
                            return methodInstance;
                        } else {
                            for (var statementInstance : methodInstance.getStatements()) {
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
