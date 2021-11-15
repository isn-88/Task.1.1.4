package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.PreparedStatement;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final Util sessionFactory;

    public UserDaoHibernateImpl() {
        sessionFactory = new Util();
    }


    @Override
    public void createUsersTable() {
        Session session = sessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        String sql = "CREATE TABLE IF NOT EXISTS users " +
                "(id INTEGER not NULL AUTO_INCREMENT, " +
                " name VARCHAR(50), " +
                " lastname VARCHAR (50), " +
                " age INTEGER not NULL, " +
                " PRIMARY KEY (id))";
        session.createSQLQuery(sql).executeUpdate();
        tx.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
        tx.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, int age) {
        Session session = sessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        User user = new User(name, lastName, age);
        session.save(user);
        System.out.println("User с именем - " + name + " добавлен в базу данных");
        tx.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("delete User where id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
        tx.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<User> users  = session.createQuery("from User").getResultList();
        tx.commit();
        session.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.createQuery("delete User").executeUpdate();
        tx.commit();
        session.close();
    }
}
