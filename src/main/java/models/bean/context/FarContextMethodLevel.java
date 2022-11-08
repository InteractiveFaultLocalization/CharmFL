package models.bean.context;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import models.bean.ClassTestData;
import models.bean.MethodTestData;
import models.bean.TestData;
import org.jetbrains.annotations.NotNull;
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
    }

    public List<MethodTestData> getFarContext(){
        this.callGraphEdgeData = new CallGraphEdgeData(method.getRelativePath(), method.getClassName(), method.getName());

        List<MethodTestData> farContextMethods = new ArrayList<>();
        for(int i=0; i<this.callGraphEdgeData.getEdgeList().size(); i++){
            System.out.println(callGraphEdgeData.getCallerMethod(i) + " -> " + callGraphEdgeData.getCalledMethod(i));
            String callerMethodName = callGraphEdgeData.getCallerMethod(i);
            String calledMethodName = callGraphEdgeData.getCalledMethod(i);
            if (callerMethodName.contains(method.getName())){
                System.out.println("----------------------------------------------------------------");
                for (ClassTestData classTestData: testData.getClasses()){
                    if (callerMethodName.contains(classTestData.getName())){
                        farContextMethods.add(classTestData.getMethodByName(method.getName()));
                    }
                }
            }
            if (calledMethodName.contains(method.getName())){
                System.out.println("----------------------------------------------------------------");
                for (ClassTestData classTestData: testData.getClasses()){
                    if (calledMethodName.contains(classTestData.getName())){
                        farContextMethods.add(classTestData.getMethodByName(method.getName()));
                    }
                }
            }

        }

        return farContextMethods;
    }
}
