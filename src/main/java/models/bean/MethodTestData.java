package models.bean;

import java.util.ArrayList;
import java.util.List;
import models.bean.context.CloseContext;
import models.bean.context.FarContext;
import models.bean.context.OtherContext;

public class MethodTestData implements ITestData {

    private String name;
    private String className;
    private int classLine;
    private String relativePath;
    private int line;
    private double tarantula;
    private double ochiai;
    private double wong2;
    private double dstar;
    private int rank;
    private boolean faulty;
    private ArrayList<ITestData> statements;
    private CloseContext closeContext;
    private OtherContext otherContext;
    private FarContext farContext;




    public MethodTestData() {
        name = "";
        className = "";
        relativePath = "";
        line = 0;
        tarantula = 0;
        ochiai = 0;
        wong2 = 0;
        dstar = 0;
        rank = 0;
        faulty = false;
        statements = new ArrayList<>();
        closeContext = new CloseContext(this);
        otherContext = new OtherContext(this);
        farContext = new FarContext(this);
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    @Override
    public int getSuperLine() {
        return classLine;
    }

    @Override
    public void setSuperLine(int superLine) {
        classLine = superLine;
    }

    @Override
    public String getSuperName() {
        return className;
    }

    @Override
    public void setSuperName(String superName) {
        className = superName;
    }


    /**
     * This represents the name of the method in the python file.
     *
     * @return a string that contains the name of the method.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * This represents the starting line number of the method in the python file.
     *
     * @return the starting line number of the method (i.e. integer)
     */
    public int getLine() {
        return line;
    }

    /**
     * This sets the starting line number of the method in the python file
     *
     * @param line, an integer that represents the starting statement.
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
    public void setDstar(double wong2) {
        this.dstar = dstar;
    }


    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * Returns a boolean saying whether the class is faulty.
     *
     * @return true if the class is faulty
     */
    public boolean isFaulty() {
        return faulty;
    }

    @Override
    public int getLevel() {
        return 2;
    }

    /**
     * Sets the faulty parameter of the class object.
     */
    public void setFaulty(boolean faulty) {
        this.faulty = faulty;
    }

    public List<ITestData> getCloseContext() {
        return (List<ITestData>) closeContext.getCloseContext();
    }

    @Override
    public List<ITestData> getOtherContext() {
        return (List<ITestData>) otherContext.getOtherContext();
    }

    @Override
    public List<ITestData> getElements() {
        return statements;
    }

    public List<ITestData> getFarContext(){return farContext.getFarContextForMethodLevel();}

    /**
     * This provides the statemets that belong to this method.
     *
     * @return a list of statements
     */
//    public ArrayList<StatementTestData> getStatements() {
//        return statements;
//    }
}
