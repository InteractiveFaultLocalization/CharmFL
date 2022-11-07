package models.bean.context;


import java.util.List;
import models.bean.MethodTestData;
import models.bean.TestData;
import services.CallGraphEdgeData;

/**
 * This is the caller and called methods of the investigated method.
 * When we are at statement level, we agregate the levels accordingly.
 */
public class FarContextMethodLevel {
    MethodTestData method;
    TestData testData;
    CallGraphEdgeData callGraphEdgeData;

    public FarContextMethodLevel(MethodTestData method){
        this.method = method;
        this.testData = TestData.getInstance();
        this.callGraphEdgeData = new CallGraphEdgeData(method.getRelativePath(), method.getClassName(), method.getName());
    }

    public List<MethodTestData> getFarContext(){
        for(int i=0; i<this.callGraphEdgeData.getEdgeList().size(); i++){
            System.out.println(callGraphEdgeData.getCallerMethod(i) + " -> " + callGraphEdgeData.getCalledMethod(i));
        }
        return testData.getAllMethods();
    }
}
