package models.bean;

import java.util.ArrayList;

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
}
