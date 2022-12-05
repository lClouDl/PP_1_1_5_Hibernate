package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    public static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    public static final String USERNAME = "root1";
    public static final String PASSWORD = "root";

    public Connection getConnection() {
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Во время соединения с базой данных произошла ошибка!");
        }
        return null;
    }

}
