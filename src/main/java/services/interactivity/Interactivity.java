package services.interactivity;

import java.util.List;
import models.bean.Formulas;
import models.bean.ITestData;
import models.bean.context.FarContext;

public interface Interactivity {

    void recalculateEntityScore(ITestData statement, Double recalculationFactor, Formulas formula);

    void recalculateCloseContextScores(ITestData codeElement, Double recalculationFactor, Formulas formula);

    void recalculateFarContextScores(int line, FarContext farContext, Double recalculationFactor);

    void recalculateOtherElementScores(ITestData codeElement, Double recalculationFactor, Formulas formula);
}
