package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<User> users;
        UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();
        userDao.createUsersTable();
        userDao.saveUser("Александр", "Иванов",30);
        userDao.saveUser("Сергей", "Петров", 29);
        userDao.saveUser("Олег", "Сидоров", 40);
        userDao.saveUser("Ольга", "Иванова", 28);
        users = userDao.getAllUsers();
        System.out.println(users);
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
