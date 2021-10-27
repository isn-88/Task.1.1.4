package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Util connect;

    public UserDaoJDBCImpl() {
        connect = new Util();
    }

    public void createUsersTable() {
        Statement statement = null;
        try {
            statement = connect.getConnection().createStatement();
            String SQL = "CREATE TABLE IF NOT EXISTS users " +
                    "(id INTEGER not NULL AUTO_INCREMENT, " +
                    " name VARCHAR(50), " +
                    " lastname VARCHAR (50), " +
                    " age INTEGER not NULL, " +
                    " PRIMARY KEY (id))";
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        Statement statement = null;
        try {
            statement = connect.getConnection().createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, int age) {
        PreparedStatement ps = null;
        try {
            ps = connect.getConnection().prepareStatement("INSERT INTO `users` (name, lastname, age) VALUE (?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setInt(3, age);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("User с именем - " + name + " добавлен в базу данных");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        PreparedStatement ps = null;
        try {
            ps = connect.getConnection().prepareStatement("DELETE FROM  users WHERE id = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connect.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastname");
                byte age = resultSet.getByte("age");
                User user = new User(name, lastName, age);
                user.setId(id);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    public void cleanUsersTable() {
        Statement statement = null;
        try {
            statement = connect.getConnection().createStatement();
            statement.executeUpdate("TRUNCATE TABLE users");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
