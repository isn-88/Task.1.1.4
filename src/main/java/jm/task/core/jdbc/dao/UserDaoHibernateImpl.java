package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final Util sessionFactory;

    public UserDaoHibernateImpl() {
        sessionFactory = new Util();
    }


    @Override
    public void createUsersTable() {
        Transaction tx = null;
        try (Session session = sessionFactory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS users " +
                    "(id INTEGER not NULL AUTO_INCREMENT, " +
                    " name VARCHAR(50), " +
                    " lastname VARCHAR (50), " +
                    " age INTEGER not NULL, " +
                    " PRIMARY KEY (id))";
            session.createSQLQuery(sql).executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction tx = null;
        try (Session session = sessionFactory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            tx.commit();
            session.close();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, int age) {
        Transaction tx = null;
        try (Session session = sessionFactory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            System.out.println("User с именем - " + name + " добавлен в базу данных");
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tx = null;
        try (Session session = sessionFactory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Query<User> query = session.createQuery("delete User where id=:id", User.class);
            query.setParameter("id", id);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        Transaction tx = null;
        try (Session session = sessionFactory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            users = session.createQuery("from User", User.class).getResultList();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction tx = null;
        try (Session session = sessionFactory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        e.printStackTrace();
        }
    }
}
