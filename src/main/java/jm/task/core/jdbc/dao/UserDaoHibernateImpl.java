package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Util util;
    public SessionFactory sessionFactory;
    public UserDaoHibernateImpl() {
        this.util = new Util();
        this.sessionFactory = util.getSessionFactory();
    }


    @Override
    public void createUsersTable() {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45), lastName VARCHAR(45), age TINYINT)").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Ошибка при создании таблицы в базе данных.");
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Ошибка при удалении таблицы из базы данных.");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Ошибка при добавлении User'а в базу данных");
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Ошибка при удалении User'а по id.");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            allUsers = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Ошибка при изъятии всех User'ов из таблицы в список.");
        }
        return allUsers;
    }

    @Override
    public void cleanUsersTable() {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("Ошибка при очистке таблицы.");
        }
    }

    @Override
    public void close() {
        try {
            sessionFactory.close();
        } catch (HibernateException e) {
            System.err.println("Ошибка при закрытии соединения с базой данных.");
        }
    }
}
