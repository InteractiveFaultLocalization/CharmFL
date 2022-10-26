package services.interactivity;

import java.util.List;
import models.bean.Formulas;
import models.bean.ITestData;
import models.bean.context.FarContext;

public interface Interactivity {
    public void recalculateCloseContextScores(ITestData codeElement, Double recalculationFactor, Formulas formula);
    public void recalculateFarContextScores(int line, FarContext farContext, Double recalculationFactor);
}
