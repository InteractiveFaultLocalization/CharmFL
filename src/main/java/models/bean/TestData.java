package models.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class TestData implements Serializable {
    private ArrayList<ClassTestData> classes;
    private ArrayList<Boolean> showMethods;
    private static final long serialVersionUID = 5399638378880334511L;

    public TestData() {
        classes = new ArrayList<>();
        showMethods = new ArrayList<>();
    }

    public ArrayList<ClassTestData> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<ClassTestData> classes) {
        this.classes = classes;
    }

    public ArrayList<Boolean> getShowMethods() {
        return showMethods;
    }

    public void setShowMethods(ArrayList<Boolean> showMethods) {
        this.showMethods = showMethods;
    }
}
