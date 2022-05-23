package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            try {
                transaction = session.beginTransaction();
                String sql = "CREATE TABLE IF NOT EXISTS users (Id int PRIMARY KEY AUTO_INCREMENT NOT NULL, " +
                        "Name varchar(25),LastName varchar(25), Age int)";
                Query query = session.createSQLQuery(sql);
                query.executeUpdate();
                transaction.commit();
            } catch (RuntimeException e) {
                e.printStackTrace();
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            try {
                transaction = session.beginTransaction();
                String sql = "DROP TABLE IF EXISTS users";
                Query query = session.createSQLQuery(sql);
                query.executeUpdate();
                transaction.commit();
            } catch (RuntimeException e) {
                e.printStackTrace();
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            try {
                User user = new User(name, lastName, age);
                transaction = session.beginTransaction();
                session.save(user);
                transaction.commit();
                System.out.printf("User с именем – %s добавлен в базу данных\n", name);
            } catch (RuntimeException e) {
                e.printStackTrace();
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            try {
                transaction = session.beginTransaction();
                User user = session.get(User.class, id);
                session.delete(user);
                transaction.commit();
            } catch (RuntimeException e) {
                e.printStackTrace();
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> users = null;
        try (Session session = sessionFactory.openSession()){
            try {
                transaction = session.beginTransaction();
                users = session.createQuery("from User").getResultList();
                transaction.commit();
            } catch (RuntimeException e) {
                e.printStackTrace();
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            try {
                transaction = session.beginTransaction();
                session.createQuery("DELETE FROM User").executeUpdate();
                transaction.commit();
            } catch (RuntimeException e) {
                e.printStackTrace();
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
