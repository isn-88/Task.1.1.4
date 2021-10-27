package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class  Util {
    private final String dbURL = "jdbc:mysql://localhost:3306/intellij";
    private final String dbUsername = "root";
    private final String dbPassword = "pass";

    private Connection connection;
    private SessionFactory sessionFactory;

    public Util () {
    }

    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            Properties properties = new Properties();
            properties.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
            properties.setProperty(Environment.HBM2DDL_AUTO,"update");
            properties.setProperty(Environment.DRIVER, "com.mysql.jdbc.Driver");
            properties.setProperty(Environment.USER, dbUsername);
            properties.setProperty(Environment.PASS, dbPassword);
            properties.setProperty(Environment.URL, dbURL);
            configuration.setProperties(properties);
            configuration.addAnnotatedClass(User.class);
            sessionFactory = configuration.buildSessionFactory();
        }
        return sessionFactory;
    }








}


