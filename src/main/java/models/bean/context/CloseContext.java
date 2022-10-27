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
                return testData.getClasses();
            } else {
                for (var methodInstance : classInstance.getMethods()) {
                    if (methodInstance.getLine() == elementData.getLine()) {
                        return classInstance.getMethods();
                    } else {
                        for (var statementInstance : methodInstance.getStatements()) {
                            if (statementInstance.getLine() == elementData.getLine()) {
                                return methodInstance.getStatements();
                            }
                        }
                    }
                }
            }
        }
        return null;
    }


}
