package models.bean;

import java.util.List;
import models.bean.context.CloseContext;
import models.bean.context.OtherContext;

public class StatementTestData implements ITestData {

    private String className;
    private String methodName;
    private int line;
    private double tarantula;
    private double ochiai;
    private double wong2;
    private double dstar;
    private int rank;
    private boolean faulty;
    private CloseContext closeContext;
    private OtherContext otherContext;

    public StatementTestData() {
        className = "";
        methodName = "";
        line = 0;
        tarantula = 0;
        ochiai = 0;
        wong2 = 0;
        dstar = 0;
        rank = 0;
        faulty = false;
        closeContext = new CloseContext(this);
        otherContext = new OtherContext(this);
    }

    /**
     * This gives you the name of the class that the statement belongs to
     *
     * @return the name of the class
     */
    public String getClassName() {
        return className;
    }

    /**
     * Set the class name of the statement object
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * This gives you the name of the method that the statement belongs to
     *
     * @return the name of the method
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Set the method name of the statement object
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {
    }

    /**
     * Get the line number of the file where the statement is.
     *
     * @return the statement number
     */
    public int getLine() {
        return line;
    }

    /**
     * Sets the statement number
     *
     * @param line, an integer
     */
    public void setLine(int line) {
        this.line = line;
    }

    /**
     * This provides the tarantula score of the class.
     *
     * @return the score
     */
    public double getTarantula() {
        return tarantula;
    }

    /**
     * This method sets the tarantula score for the class object.
     *
     * @param tarantula score, a double type number
     */
    public void setTarantula(double tarantula) {
        this.tarantula = tarantula;
    }

    /**
     * This provides the ochiai score of the class.
     *
     * @return the score
     */
    public double getOchiai() {
        return ochiai;
    }

    /**
     * This method sets the ochicai score for the class object.
     *
     * @param ochiai score, a double type number
     */
    public void setOchiai(double ochiai) {
        this.ochiai = ochiai;
    }

    /**
     * This provides the wong2 score of the class.
     *
     * @return the score
     */
    public double getWong2() {
        return wong2;
    }

    /**
     * This method sets the wong2 score for the class object.
     *
     * @param wong2 score, a double type number
     */
    public void setWong2(double wong2) {
        this.wong2 = wong2;
    }

    /**
     * This provides the wong2 score of the class.
     *
     * @return the score
     */
    public double getDstar() {
        return dstar;
    }

    /**
     * This method sets the wong2 score for the class object.
     *
     * @param wong2 score, a double type number
     */
    public void setDstar(double dstar) {
        this.dstar = dstar;
    }


    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * Gets the info whether the statement is faulty or not
     *
     * @return true if statement is faulty
     */
    public boolean isFaulty() {
        return faulty;
    }

    public List<ITestData> getCloseContext() {
        return (List<ITestData>) closeContext.getCloseContext();
    }

    @Override
    public List<ITestData> getOtherContext() {
        return (List<ITestData>) otherContext.getOtherContext();
    }

    public void setFaulty(boolean faulty) {
        this.faulty = faulty;
    }
}
