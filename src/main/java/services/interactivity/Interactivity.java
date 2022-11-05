package services.interactivity;

import models.bean.Formula;
import models.bean.ITestData;
import models.bean.context.FarContext;

public interface Interactivity {

    void recalculateEntityScore(ITestData statement, Double recalculationFactor, Formula formula);

    void recalculateCloseContextScores(ITestData codeElement, Double recalculationFactor, Formula formula);

    void recalculateFarContextScores(int line, FarContext farContext, Double recalculationFactor);

    void recalculateOtherElementScores(ITestData codeElement, Double recalculationFactor, Formula formula);
}
