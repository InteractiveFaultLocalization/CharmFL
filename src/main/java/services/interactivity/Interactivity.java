package services.interactivity;

import models.bean.Formula;
import models.bean.ITestData;
import models.bean.context.FarContextMethodLevel;

public interface Interactivity {

    void recalculateEntityScore(ITestData statement, Double recalculationFactor, Formula formula);

    void recalculateCloseContextScores(ITestData codeElement, Double recalculationFactor, Formula formula);

    void recalculateFarContextScores(int line, FarContextMethodLevel farContextMethodLevel, Double recalculationFactor);

    void recalculateOtherElementScores(ITestData codeElement, Double recalculationFactor, Formula formula);
}
