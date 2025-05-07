package models.bean;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewTestData {
//cc.json-t reprezentálja
    private static ViewTestData instance;

    private HashMap<String, ArrayList<String>> tests;

    public static ViewTestData getInstance() {
        if (instance == null) {
            instance = new ViewTestData();
        }
        return instance;
    }

    public static ViewTestData getInstance(String relativePath) {
        if (instance == null) {
            instance = new ViewTestData();
        }
        return instance;
    }

    public HashMap<String, ArrayList<String>> getTests() {
        return tests;
    }

    public void setTests(HashMap<String, ArrayList<String>> tests) {
        this.tests = tests;
    }
}
