package services.interactivity;

import java.util.List;
import models.bean.Formulas;
import models.bean.ITestData;
import models.bean.StatementTestData;
import models.bean.context.FarContext;

public class StatementInteractivity implements Interactivity{

    @Override
    public void recalculateEntityScore(ITestData statement, Double recalculationFactor, Formulas formula){
        if(formula == Formulas.TARANTULA){
            Double oldScore = statement.getTarantula();
            statement.setTarantula(oldScore * recalculationFactor);
        }
        else if(formula == Formulas.OCHIAI){
            Double oldScore = statement.getOchiai();
            statement.setOchiai(oldScore * recalculationFactor);
        }
        else if(formula == Formulas.DSTAR){
            //Double oldScore = contextStatement.getDStar();
            //contextStatement.setDStar(oldScore * recalculationFactor);
        }
        else if(formula == Formulas.WONG2){
            Double oldScore = statement.getWong2();
            statement.setWong2(oldScore * recalculationFactor);
        }
    }

    @Override
    public void recalculateCloseContextScores(ITestData statement, Double recalculationFactor, Formulas formula) {
        for(var contextStatement : statement.getCloseContext()){
            if(formula == Formulas.TARANTULA){
                Double oldScore = contextStatement.getTarantula();
                contextStatement.setTarantula(oldScore * recalculationFactor);
            }
            else if(formula == Formulas.OCHIAI){
                Double oldScore = contextStatement.getOchiai();
                contextStatement.setOchiai(oldScore * recalculationFactor);
            }
            else if(formula == Formulas.DSTAR){
                //Double oldScore = contextStatement.getDStar();
                //contextStatement.setDStar(oldScore * recalculationFactor);
            }
            else if(formula == Formulas.WONG2){
                Double oldScore = contextStatement.getWong2();
                contextStatement.setWong2(oldScore * recalculationFactor);
            }
        }
    }

    @Override
    public void recalculateFarContextScores(int line, FarContext farContext, Double recalculationFactor) {

    }
}
