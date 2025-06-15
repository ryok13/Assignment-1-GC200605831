package com.georgiancollege.assignment1gc200605831;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class TableController {

    @FXML
    private TableView<Movie> tableView;

    @FXML
    private Button backToGraph;

    @FXML
    public void initialize() {
        ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

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
                observableMovies.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // カラムの定義とバインド
        TableColumn<Movie, String> idCol = new TableColumn<>("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Movie, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Movie, String> genreCol = new TableColumn<>("Genre");
        genreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));

        TableColumn<Movie, Integer> yearCol = new TableColumn<>("Year");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<Movie, String> directorCol = new TableColumn<>("Director");
        directorCol.setCellValueFactory(new PropertyValueFactory<>("director"));

        TableColumn<Movie, Double> revenueCol = new TableColumn<>("Revenue");
        revenueCol.setCellValueFactory(new PropertyValueFactory<>("revenue"));

        TableColumn<Movie, Double> scoreCol = new TableColumn<>("IMDb Score");
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("imdbScore"));

        // テーブルにカラムを追加
        tableView.getColumns().addAll(idCol, titleCol, genreCol, yearCol, directorCol, revenueCol, scoreCol);
        tableView.setItems(observableMovies);

        // ボタンでグラフ画面に戻る処理
        backToGraph.setOnAction(e -> switchToGraphScene());
    }

    private void switchToGraphScene () {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("graph-view.fxml"));
            Scene graphScene = new Scene(loader.load());
            Stage stage = (Stage) tableView.getScene().getWindow();
            stage.setScene(graphScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
