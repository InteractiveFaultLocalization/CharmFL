package models.bean;

import java.util.List;

public interface ITestData {

    String getName();

    void setName(String name);

    int getLine();

    void setLine(int line);

    double getTarantula();

    void setTarantula(double tarantula);

    double getOchiai();

    void setOchiai(double ochiai);

    double getWong2();

    void setWong2(double wong2);

    double getDstar();

    void setDstar(double dstar);

    int getRank();

    void setRank(int rank);

    boolean isFaulty();

    String getRelativePath();

    void setRelativePath(String path);


    int getSuperLine();

    void setSuperLine(int superLine);

    String getSuperName();

    void setSuperName(String superName);


    int getLevel();

    void setFaulty(boolean faulty);

    List<ITestData> getCloseContext();

    List<ITestData> getFarContext();


    List<ITestData> getOtherContext();

    List<ITestData> getElements();

}
