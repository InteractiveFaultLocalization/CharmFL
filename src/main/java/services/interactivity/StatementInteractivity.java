package services.interactivity;

import models.bean.Formula;
import models.bean.ITestData;

/**
 * TODO This should have been more than one class, but very good thing to make in the future
 */
public class StatementInteractivity implements Interactivity {


    /**
     * Sets the new score to given statement by the recalculationFactor.
     * @param statement
     * @param recalculationFactor
     * @param formula
     */
    private void setScore(ITestData statement, Double recalculationFactor, Formula formula){
        if (formula == Formula.TARANTULA) {
            Double oldScore = statement.getTarantula();
            statement.setTarantula(oldScore * recalculationFactor);
        } else if (formula == Formula.OCHIAI) {
            Double oldScore = statement.getOchiai();
            statement.setOchiai(oldScore * recalculationFactor);
        } else if (formula == Formula.DSTAR) {
            //Double oldScore = contextStatement.getDStar();
            //contextStatement.setDStar(oldScore * recalculationFactor);
        } else if (formula == Formula.WONG2) {
            Double oldScore = statement.getWong2();
            statement.setWong2(oldScore * recalculationFactor);
        }
    }

    @Override
    public void recalculateEntityScore(ITestData statement, Double recalculationFactor, Formula formula) {
        setScore(statement, recalculationFactor, formula);
    }

    @Override
    public void recalculateCloseContextScores(ITestData statement, Double recalculationFactor, Formula formula) {
        for (var contextStatement : statement.getCloseContext()) {
            System.out.println(contextStatement.getName());
            System.out.println(contextStatement.getLine());
            setScore(contextStatement, recalculationFactor, formula);
        }
    }

    @Override
    public void recalculateFarContextScores(ITestData element,  Double recalculationFactor, Formula formula) {
        for (var farContext : element.getFarContext()){
            setScore(farContext, recalculationFactor, formula);
        }
    }

    @Override
    public void recalculateOtherElementScores(ITestData statement, Double recalculationFactor, Formula formula) {
        for(var otherStatement :  statement.getOtherContext()) {
            setScore(otherStatement, recalculationFactor, formula);
        }
    }
}
