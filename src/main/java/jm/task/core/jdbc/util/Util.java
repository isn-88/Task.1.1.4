package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class  Util {
    private final String dbURL = "jdbc:mysql://localhost:3306/intellij";
    private final String dbUsername = "root";
    private final String dbPassword = "pass";

    private Connection connection;

    public Util () {
        try {
            connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
