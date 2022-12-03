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
    public Connection connection;
    public Util() throws SQLException {
        Driver driver = new com.mysql.cj.jdbc.Driver();
        DriverManager.registerDriver(driver);
        this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

}
