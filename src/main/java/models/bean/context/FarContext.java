package models.bean.context;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import models.bean.ClassTestData;
import models.bean.ITestData;
import models.bean.MethodTestData;
import models.bean.TestData;
import org.jetbrains.annotations.NotNull;
import services.CallGraphEdgeData;

/**
 * This is the caller and called methods of the investigated method.
 * When we are at statement level, we agregate the levels accordingly.
 */
public class FarContext {
    ITestData element;
    TestData testData;
    CallGraphEdgeData callGraphEdgeData;

    public FarContext(ITestData element){
        this.element = element;
        this.testData = TestData.getInstance();
    }

    public List<ITestData> getFarContextForMethodLevel(){
        MethodTestData method = (MethodTestData) element;
        this.callGraphEdgeData = new CallGraphEdgeData(method.getRelativePath(), method.getSuperName(), element.getName(), testData) ;
        var edgeDataTransformer = testData.makeEdgeTransformation();
        List<ITestData> farContextMethods = new ArrayList<>();
        for(int i=0; i<this.callGraphEdgeData.getEdgeList().size(); i++){
            //System.out.println(callGraphEdgeData.getCallerMethod(i) + " -> " + callGraphEdgeData.getCalledMethod(i));
            String caller = callGraphEdgeData.getCallerMethod(i);
            String called = callGraphEdgeData.getCalledMethod(i);
            try {
                MethodTestData callerMethodObj = edgeDataTransformer.get(caller);
                MethodTestData calledMethodObj = edgeDataTransformer.get(called);
                //System.out.println(callerMethodObj.getName());
                //System.out.println(calledMethodObj.getName());
                if (callerMethodObj.getName().equals(method.getName())){
                    //System.out.println("----------------------------------------------------------------");
                    //String callerMethodName = caller.substring(caller.lastIndexOf("__"));
                    //System.out.println(callerMethodName);
                    for (ClassTestData classTestData: testData.getClasses()){
                        if (calledMethodObj.getSuperName().equals(classTestData.getName())){
                            farContextMethods.add(classTestData.getMethodByName(calledMethodObj.getName()));
                        }
                    }
                }
                if (calledMethodObj.getName().equals(method.getName())){
                    //System.out.println("----------------------------------------------------------------");
                    //String callerMethodName = caller.substring(caller.lastIndexOf("__"));
                    //System.out.println(callerMethodName);
                    for (ClassTestData classTestData: testData.getClasses()){
                        if (callerMethodObj.getSuperName().equals(classTestData.getName())){
                            farContextMethods.add(classTestData.getMethodByName(callerMethodObj.getName()));
                        }
                    }
                }
            }
            catch (NullPointerException e){
                continue;
            }

        }

        return farContextMethods;
    }

    public List<ITestData> getFarContextForStatementLevel(){
        MethodTestData method = (MethodTestData) testData.getElement(element.getRelativePath(), element.getSuperLine());
        this.callGraphEdgeData = new CallGraphEdgeData(method.getRelativePath(), method.getSuperName(), element.getName(), testData);
        var edgeDataTransformer = testData.makeEdgeTransformation();
        List<ITestData> farContextMethods = new ArrayList<>();
        List<ITestData> farContextStatements = new ArrayList<>();
        for(int i=0; i<this.callGraphEdgeData.getEdgeList().size(); i++){
            //System.out.println(callGraphEdgeData.getCallerMethod(i) + " -> " + callGraphEdgeData.getCalledMethod(i));
            String caller = callGraphEdgeData.getCallerMethod(i);
            String called = callGraphEdgeData.getCalledMethod(i);
            try {
                MethodTestData callerMethodObj = edgeDataTransformer.get(caller);
                MethodTestData calledMethodObj = edgeDataTransformer.get(called);
                //System.out.println(callerMethodObj.getName());
                //System.out.println(calledMethodObj.getName());
                if (callerMethodObj.getName().equals(method.getName())){
                    //System.out.println("----------------------------------------------------------------");
                    //String callerMethodName = caller.substring(caller.lastIndexOf("__"));
                    //System.out.println(callerMethodName);
                    for (ClassTestData classTestData: testData.getClasses()){
                        if (calledMethodObj.getSuperName().equals(classTestData.getName())){
                            farContextMethods.add(classTestData.getMethodByName(calledMethodObj.getName()));
                        }
                    }
                }
                if (calledMethodObj.getName().equals(method.getName())){
                    //System.out.println("----------------------------------------------------------------");
                    //String callerMethodName = caller.substring(caller.lastIndexOf("__"));
                    //System.out.println(callerMethodName);
                    for (ClassTestData classTestData: testData.getClasses()){
                        if (callerMethodObj.getSuperName().equals(classTestData.getName())){
                            farContextMethods.add(classTestData.getMethodByName(callerMethodObj.getName()));
                        }
                    }
                }
            }
            catch (NullPointerException e){
                continue;
            }

        }


        for (var farMethod : farContextMethods){
            farMethod.getElements().stream().forEach(s -> farContextStatements.add(s));
        }

        return farContextStatements;
    }
}
