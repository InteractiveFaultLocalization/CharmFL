package services.interactivity;

import models.bean.context.CloseContext;
import models.bean.context.FarContext;

public interface Interactivity {
    public void recalculateCloseContextScores(int line, CloseContext closeContext, Double recalculationFactor);
    public void recalculateFarContextScores(int line, FarContext farContext, Double recalculationFactor);
}
