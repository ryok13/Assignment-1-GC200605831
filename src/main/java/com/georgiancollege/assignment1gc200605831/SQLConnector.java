package com.georgiancollege.assignment1gc200605831;

import java.sql.*;

public class SQLConnector {
    private static final String URL = "jdbc:mysql://172.31.22.43:3306/Ryo200605831";
    private static final String USER = "Ryo200605831";
    private static final String PASSWORD = "rreFmVInJH";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
