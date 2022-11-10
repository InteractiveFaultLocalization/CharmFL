package models.bean.context;


import java.util.List;
import java.util.stream.Collectors;
import models.bean.ITestData;
import models.bean.TestData;

public class CloseContext {

    ITestData elementData;
    TestData testData;

    public CloseContext(ITestData elementData) {
        this.elementData = elementData;
        this.testData = TestData.getInstance();
    }

    public List<? extends ITestData> getCloseContext() {
        for (var classInstance : testData.getClasses()) {
            if (classInstance.getLine() == elementData.getLine()) {
                return testData.getClasses().stream().filter(element -> element.getLine() != classInstance.getLine()).collect(Collectors.toList());
            } else {
                for (var methodInstance : classInstance.getElements()) {
                    if (methodInstance.getLine() == elementData.getLine()) {
                        return classInstance.getElements().stream().filter(element -> element.getLine() != methodInstance.getLine()).collect(Collectors.toList());
                    } else {
                        for (var statementInstance : methodInstance.getElements()) {
                            if (statementInstance.getLine() == elementData.getLine()) {
                                return methodInstance.getElements().stream().filter(element -> element.getLine() != statementInstance.getLine()).collect(Collectors.toList());
                            }
                        }
                    }
                }
            }
        }
        return null;
    }


}
