package services;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class RankingService {
    private ArrayList<Data> dataList;
    private ArrayList<Double> originalScoreList;

    class Data implements Comparable {
        Double score;
        Double rank;

        public Data(Double score) {
            this.score = score;
            this.rank = 0.0;
        }

        @Override
        public int compareTo(@NotNull Object o) {
            Data other = (Data) o;
            return score.compareTo(other.score);
        }

        public boolean hasRank() {
            return this.rank != 0.0;
        }
    }

    public RankingService(ArrayList<Double> scoreList) {
        this.originalScoreList = scoreList;
        this.dataList = new ArrayList<>();
        for (Double score : scoreList) {
            this.dataList.add(new Data(score));
        }
        dataList.sort(Collections.reverseOrder());
    }

    /**
     * Ranks the list in ascending order and gives them the minimum rank when a tie happens.
     * @return
     */
    public ArrayList<Double> minRanking() {
        double repeatedRank = Double.MAX_VALUE;
        for (int i = 1; i < this.dataList.size(); i++) {
            Data currentData = this.dataList.get(i - 1);
            Data nextData = this.dataList.get(i);

            if (currentData.score.equals(nextData.score)) {
                if (repeatedRank > i) {
                    repeatedRank = i;
                }
                currentData.rank = repeatedRank;
                nextData.rank = repeatedRank;
            } else {
                repeatedRank = Double.MAX_VALUE;
                if (!currentData.hasRank()) {
                    currentData.rank = (double) i;
                }
            }
            if (i == this.dataList.size() - 1 && !nextData.hasRank()) {
                nextData.rank = (double) i + 1;
            }
        }
        return makeRankList();
    }

    /**
     * Ranks the list in ascending order and gives them the maximum rank when a tie happens.
     * @return
     */
    public ArrayList<Double> maxRanking() {
        double repeatedRank = 0.0;
        for (int i = 1; i < this.dataList.size(); i++) {
            Data currentData = this.dataList.get(i - 1);
            Data nextData = this.dataList.get(i);
            if (currentData.score.equals(nextData.score)) {
                if (repeatedRank == 0.0) {
                    repeatedRank = findMaxRank(i);
                }
                currentData.rank = repeatedRank;
                nextData.rank = repeatedRank;
            } else {
                repeatedRank = 0.0;
                if (!currentData.hasRank()) {
                    currentData.rank = (double) i;
                }
            }
            if (i == this.dataList.size() - 1 && !nextData.hasRank()) {
                nextData.rank = (double) i + 1;
            }
        }
        return makeRankList();
    }

    /**
     * Ranks the list in ascending order and gives them the average rank when a tie happens.
     * @return
     */
    public ArrayList<Double> averageRanking() {
        double repeatedRank = 0.0;
        for (int i = 1; i < this.dataList.size(); i++) {
            Data currentData = this.dataList.get(i - 1);
            Data nextData = this.dataList.get(i);
            if (currentData.score.equals(nextData.score)) {
                if (repeatedRank == 0.0) {
                    repeatedRank = findAvgRank(i);
                }
                currentData.rank = repeatedRank;
                nextData.rank = repeatedRank;
            } else {
                repeatedRank = 0.0;
                if (!currentData.hasRank()) {
                    currentData.rank = (double) i;
                }
            }
            if (i == this.dataList.size() - 1 && !nextData.hasRank()) {
                nextData.rank = (double) i + 1;
            }
        }
        return makeRankList();
    }

    private Double findMaxRank(int i) {
        int tempCounter = i - 1;
        Data tempCurrentData = this.dataList.get(i - 1);
        while (tempCurrentData.score.equals(this.dataList.get(i - 1).score)) {
            tempCounter++;
            if (tempCounter < this.dataList.size()) {
                tempCurrentData = this.dataList.get(tempCounter);
            } else {
                break;
            }
        }
        return (double) tempCounter;
    }

    private Double findAvgRank(int i) {
        int tempCounter = i - 1;
        int sum = 0;
        int repeatedRankNum = 0;
        Data tempCurrentData = this.dataList.get(i - 1);
        while (tempCurrentData.score.equals(this.dataList.get(i - 1).score)) {
            tempCounter++;
            sum = sum + (tempCounter);
            repeatedRankNum++;
            if (tempCounter < this.dataList.size()) {
                tempCurrentData = this.dataList.get(tempCounter);
            } else {
                break;
            }
        }
        return (double) sum / repeatedRankNum;
    }

    private ArrayList<Double> makeRankList() {
        ArrayList<Double> rankList = new ArrayList<>();
        for (Double score : this.originalScoreList) {
            for (Data data : this.dataList) {
                if (Objects.equals(data.score, score)) {
                    rankList.add(data.rank);
                    break;
                }
            }
        }
        return rankList;
    }
}
