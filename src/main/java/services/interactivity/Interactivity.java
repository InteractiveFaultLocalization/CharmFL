package services.interactivity;

import models.bean.Formula;
import models.bean.ITestData;

public interface Interactivity {

    void recalculateEntityScore(ITestData statement, Double recalculationFactor, Formula formula);

    void recalculateCloseContextScores(ITestData codeElement, Double recalculationFactor, Formula formula);

    void recalculateFarContextScores(ITestData statement,  Double recalculationFactor, Formula formula);

    void recalculateOtherElementScores(ITestData codeElement, Double recalculationFactor, Formula formula);
}
