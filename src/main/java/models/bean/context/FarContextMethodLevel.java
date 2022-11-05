package models.bean.context;


import java.util.List;
import models.bean.MethodTestData;
import models.bean.TestData;

/**
 * This is the caller and called methods of the investigated method.
 * When we are at statement level, we agregate the levels accordingly.
 */
public class FarContextMethodLevel {
    MethodTestData method;
    TestData testData;

    public FarContextMethodLevel(MethodTestData method){
        this.method = method;
        this.testData = TestData.getInstance();
    }

    public List<MethodTestData> getFarContext(){
        return null;
    }
}
