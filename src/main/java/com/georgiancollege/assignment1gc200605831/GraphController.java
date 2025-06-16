package com.georgiancollege.assignment1gc200605831;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GraphController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private RadioButton maxRadio, revenueRadio, scoreRadio;

    @FXML
    private Button viewTableButton;

    private ToggleGroup toggleGroup = new ToggleGroup();

    // 映画データ（本来はDBやファイルから読み込み）
    private List<Movie> movieList;


    @FXML
    public void initialize() {
        // Load movie data from MySQL
        movieList = fetchMoviesFromDB();

        // Set up radio buttons
        revenueRadio.setToggleGroup(toggleGroup);
        maxRadio.setToggleGroup(toggleGroup);
        scoreRadio.setToggleGroup(toggleGroup);
        revenueRadio.setSelected(true); // Default selection

        updateChart();

        // Update chart on radio button selection change
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> updateChart());

        // Set up navigation to table view
        viewTableButton.setOnAction(event -> switchToTableScene());
    }

    // Populate the bar chart with selected metric
    private void updateChart() {
        barChart.getData().clear();

        List<GenreStats> stats = GenreStats.calculateFrom(movieList);
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        if (revenueRadio.isSelected()) {
            series.setName("Average Revenue");
            for (GenreStats g : stats) {
                series.getData().add(new XYChart.Data<>(g.getGenre(), g.getAverageRevenue()));
            }
        } else if (maxRadio.isSelected()) {
            series.setName("Max Revenue");
            for (GenreStats g : stats) {
                series.getData().add(new XYChart.Data<>(g.getGenre(), g.getMaxRevenue()));
            }
        } else if (scoreRadio.isSelected()) {
            series.setName("Average IMDb Score");
            barChart.setCategoryGap(7); // Distance between bars
            for (GenreStats g : stats) {
                series.getData().add(new XYChart.Data<>(g.getGenre(), g.getAverageScore()));
            }
        }

        barChart.getData().add(series);
    }

    // Switch to the table view scene
    private void switchToTableScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("table-view.fxml"));
            Scene tableScene = new Scene(loader.load());
            Stage stage = (Stage) barChart.getScene().getWindow();
            stage.setScene(tableScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Retrieve movie data from MySQL database
    private List<Movie> fetchMoviesFromDB() {
        List<Movie> movies = new ArrayList<>();

        try (Connection conn = SQLConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM movies")) {

            while (rs.next()) {
                Movie m = new Movie(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getInt("year"),
                        rs.getDouble("revenue"),
                        rs.getString("director"),
                        rs.getDouble("imdb_score")
                );
                movies.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }
}
