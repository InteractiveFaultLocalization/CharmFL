package services.interactivity;

import models.bean.Formulas;
import models.bean.ITestData;
import models.bean.context.FarContext;

public class StatementInteractivity implements Interactivity {


    /**
     * Sets the new score to given statement by the recalculationFactor.
     * @param statement
     * @param recalculationFactor
     * @param formula
     */
    private void setScore(ITestData statement, Double recalculationFactor, Formulas formula){
        if (formula == Formulas.TARANTULA) {
            Double oldScore = statement.getTarantula();
            statement.setTarantula(oldScore * recalculationFactor);
        } else if (formula == Formulas.OCHIAI) {
            Double oldScore = statement.getOchiai();
            statement.setOchiai(oldScore * recalculationFactor);
        } else if (formula == Formulas.DSTAR) {
            //Double oldScore = contextStatement.getDStar();
            //contextStatement.setDStar(oldScore * recalculationFactor);
        } else if (formula == Formulas.WONG2) {
            Double oldScore = statement.getWong2();
            statement.setWong2(oldScore * recalculationFactor);
        }
    }

    @Override
    public void recalculateEntityScore(ITestData statement, Double recalculationFactor, Formulas formula) {
        setScore(statement, recalculationFactor, formula);
    }

    @Override
    public void recalculateCloseContextScores(ITestData statement, Double recalculationFactor, Formulas formula) {
        for (var contextStatement : statement.getCloseContext()) {
            setScore(contextStatement, recalculationFactor, formula);
        }
    }

    @Override
    public void recalculateFarContextScores(int line, FarContext farContext, Double recalculationFactor) {

    }

    @Override
    public void recalculateOtherElementScores(ITestData statement, Double recalculationFactor, Formulas formula) {
        for(var otherStatement :  statement.getOtherContext()) {
            setScore(otherStatement, recalculationFactor, formula);
        }
    }
}
