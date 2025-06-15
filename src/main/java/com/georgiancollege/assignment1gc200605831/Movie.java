package com.georgiancollege.assignment1gc200605831;

public class Movie {
    private String title, genre, director;
    private int id, year;
    private double revenue, imdbScore;

    public Movie(int id, String title, String genre, int year, double revenue, String director, double imdbScore) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.revenue = revenue;
        this.director = director;
        this.imdbScore = imdbScore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getRevenue() {
        return revenue / 100;  // 表示時に100分の1で返す
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public double getImdbScore() {
        return imdbScore;
    }

    public void setImdbScore(double imdbScore) {
        this.imdbScore = imdbScore;
    }
}
