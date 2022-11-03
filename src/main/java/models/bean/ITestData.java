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

    int getRank();

    void setRank(int rank);

    boolean isFaulty();

    void setFaulty(boolean faulty);
    List<ITestData> getCloseContext();

    List<ITestData> getOtherContext();

}
