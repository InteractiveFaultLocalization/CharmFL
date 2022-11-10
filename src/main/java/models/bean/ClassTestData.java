package models.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import models.bean.context.CloseContext;
import models.bean.context.OtherContext;

public class ClassTestData  implements ITestData{
    private String name;
    private int line;
    private double tarantula;
    private double ochiai;
    private double wong2;
    private double dstar;
    private int rank;
    private boolean faulty;
    private String relativePath;
    private ArrayList<ITestData> methods;
    private CloseContext closeContext;
    private OtherContext otherContext;

    public ClassTestData() {
        name = "";
        line = 0;
        tarantula = 0;
        ochiai = 0;
        wong2 = 0;
        dstar = 0;
        rank = 0;
        faulty = false;
        relativePath = "";
        methods = new ArrayList<>();
        closeContext = new CloseContext(this);
        otherContext = new OtherContext(this);
    }

    /**
     * This represents the name of the Class in the python file.
     * @return a string that contains the name of the class.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * This represents the starting line number of the class in the python file.
     * @return the starting line number of the class (i.e. integer)
     */
    public int getLine() {
        return line;
    }

    /**
     * This sets the starting line number of the class in the python file
     * @param line, an integer that represents the starting statement.
     */
    public void setLine(int line) {
        this.line = line;
    }

    /**
     * This provides the tarantula score of the class.
     * @return the score
     */
    public double getTarantula() {
        return tarantula;
    }

    /**
     * This method sets the tarantula score for the class object.
     * @param tarantula score, a double type number
     */
    public void setTarantula(double tarantula) {
        this.tarantula = tarantula;
    }

    /**
     * This provides the ochiai score of the class.
     * @return the score
     */
    public double getOchiai() {
        return ochiai;
    }

    /**
     * This method sets the ochicai score for the class object.
     * @param ochiai score, a double type number
     */
    public void setOchiai(double ochiai) {
        this.ochiai = ochiai;
    }

    /**
     * This provides the wong2 score of the class.
     * @return the score
     */
    public double getWong2() {
        return wong2;
    }



    /**
     * This method sets the wong2 score for the class object.
     * @param wong2 score, a double type number
     */
    public void setWong2(double wong2) {
        this.wong2 = wong2;
    }

    /**
     * This method sets the dstar score for the class object.
     * @param dstar score, a double type number
     */
    public void setDstar(double dstar) {
        this.dstar = dstar;
    }

    /**
     * This provides the dstar score of the class.
     * @return the score
     */
    public double getDstar() {
        return dstar;
    }



    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * Returns a boolean saying whether the class is faulty.
     * @return true if the class is faulty
     */
    public boolean isFaulty() {
        return faulty;
    }


    @Override
    public int getLevel() {
        return 1;
    }

    /**
     * Sets the faulty parameter of the class object.
     * @param faulty
     */
    public void setFaulty(boolean faulty) {
        this.faulty = faulty;
    }

    /**
     * This provides the relative path of the python class starting from the root.
     * @return a string of the relative path.
     */
    public String getRelativePath() {
        return relativePath;
    }

    /**
     * This sets the relative path of the python class
     * @param path, a relative path starting from the root.
     */
    public void setRelativePath(String path) {
        this.relativePath = path;
    }

    @Override
    public int getSuperLine() {
        return 0;
    }

    @Override
    public void setSuperLine(int superLine) {

    }

    @Override
    public String getSuperName() {
        return relativePath;
    }

    @Override
    public void setSuperName(String superName) {
        this.relativePath = superName;
    }

    public List<ITestData> getCloseContext(){
        return (List<ITestData>) closeContext.getCloseContext();
    }

    @Override
    public List<ITestData> getFarContext() {
        return null;
    }

    @Override
    public List<ITestData> getOtherContext() {
        return (List<ITestData>) otherContext.getOtherContext();
    }

    @Override
    public List<ITestData> getElements() {
        return methods;
    }

    /**
     * This provides the class's methods
     * @return a list of methods of a class.
     */
//    public ArrayList<MethodTestData> getMethods() {
//        return methods;
//    }

    public MethodTestData getMethodByName(String methodsName){
        return (MethodTestData) methods.stream().filter(m -> m.getName().equals(methodsName)).collect(Collectors.toList()).get(0);
    }

    public MethodTestData getMethodByLineNumber(int lineNumber){
        return (MethodTestData) methods.stream().filter(m -> m.getLine() == lineNumber).collect(Collectors.toList()).get(0);
    }
}
