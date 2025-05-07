package models.bean;

import java.util.ArrayList;

public class TestResultData{
    String testname;
    ArrayList<String> list;


    public String getTestname() {
        return testname;
    }

    public void setTestname(String testname) {
        this.testname = testname;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }
}
