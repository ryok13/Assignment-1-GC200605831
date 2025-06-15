package com.georgiancollege.assignment1gc200605831;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenreStats {
    private String genre;
    private double averageRevenue;
    private double maxRevenue;
    private double averageScore;

    public GenreStats(String genre, double averageRevenue, double maxRevenue, double averageScore) {
        this.genre = genre;
        this.averageRevenue = averageRevenue;
        this.maxRevenue = maxRevenue;
        this.averageScore = averageScore;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getAverageRevenue() {
        return averageRevenue;
    }

    public void setAverageRevenue(double averageRevenue) {
        this.averageRevenue = averageRevenue;
    }

    public double getMaxRevenue() {
        return maxRevenue;
    }

    public void setMaxRevenue(double maxRevenue) {
        this.maxRevenue = maxRevenue;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public static List<GenreStats> calculateFrom(List<Movie> movieList) {
        // ジャンル名ごとに対応する Movie のリストを格納するマップ
        Map<String, List<Movie>> genreMap = new HashMap<>();

        // まずジャンルごとに分類する
        for (Movie m : movieList) {
            String genre = m.getGenre();
            if (!genreMap.containsKey(genre)) {
                genreMap.put(genre, new ArrayList<>());
            }
            genreMap.get(genre).add(m);
        }

        // 結果を格納するリスト
        List<GenreStats> statsList = new ArrayList<>();

        // 各ジャンルに対して集計処理を行う
        for (String genre : genreMap.keySet()) {
            List<Movie> movies = genreMap.get(genre);

            double totalRevenue = 0;
            double maxRevenue = 0;
            double totalScore = 0;

            for (Movie m : movies) {
                double revenue = m.getRevenue();
                double score = m.getImdbScore();
                totalRevenue += revenue;
                totalScore += score;

                if (revenue > maxRevenue) {
                    maxRevenue = revenue;
                }
            }

            double avgRevenue = totalRevenue / movies.size();
            double avgScore = totalScore / movies.size();

            // 結果を GenreStats として追加
            GenreStats stats = new GenreStats(genre, avgRevenue, maxRevenue, avgScore);
            statsList.add(stats);
        }
        return statsList;
    }
}
