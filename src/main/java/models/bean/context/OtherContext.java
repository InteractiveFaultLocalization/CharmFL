package models.bean.context;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import models.bean.ITestData;
import models.bean.StatementTestData;
import models.bean.TestData;

public class OtherContext {

    ITestData elementData;
    TestData testData;

    public OtherContext(ITestData elementData) {
        this.elementData = elementData;
        this.testData = TestData.getInstance();
    }


    public List<? extends ITestData> getOtherContext() {
        List<Integer> excludeLines = new ArrayList<>();

        excludeLines.add(elementData.getLine());
        elementData.getCloseContext().stream().forEach(e -> excludeLines.add(e.getLine()));

        for (var classInstance : testData.getClasses()) {
            if (classInstance.getLine() == elementData.getLine()) {
                return testData.getClasses().stream()
                        .filter(element -> !excludeLines.contains(element.getLine()))
                        .collect(Collectors.toList());
            } else {
                for (var methodInstance : classInstance.getElements()) {
                    if (methodInstance.getLine() == elementData.getLine()) {
                        return testData.getAllMethods().stream()
                                .filter(element -> !excludeLines.contains(element.getLine()))
                                .collect(Collectors.toList());
                    } else {
                        for (var statementInstance : methodInstance.getElements()) {
                            if (statementInstance.getLine() == elementData.getLine()) {
                                return testData.getAllStatements().stream()
                                        .filter(element -> !excludeLines.contains(element.getLine()))
                                        .collect(Collectors.toList());

                            }
                        }
                    }
                }
            }
        }
        return null;
    }

}
