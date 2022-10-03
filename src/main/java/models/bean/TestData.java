package models.bean;

import java.util.ArrayList;

/**
 * This represents the python module which contains python classes.
 */
public class TestData {
    private ArrayList<ClassTestData> classes;

    public TestData() {
        classes = new ArrayList<>();
    }

    public ArrayList<ClassTestData> getClasses() {
        return classes;
    }
}
