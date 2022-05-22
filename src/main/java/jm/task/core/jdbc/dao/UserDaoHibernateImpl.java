package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        Session session = Util.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        String sql = "CREATE TABLE IF NOT EXISTS users (Id int PRIMARY KEY AUTO_INCREMENT NOT NULL, " +
                "Name varchar(25),LastName varchar(25), Age int)";
        Query query = session.createSQLQuery(sql);
        query.executeUpdate();

        transaction.commit();

    }

    @Override
    public void dropUsersTable() {

        Session session = Util.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        String sql = "DROP TABLE IF EXISTS users";
        Query query = session.createSQLQuery(sql);
        query.executeUpdate();
        transaction.commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        Session session = Util.getSessionFactory().getCurrentSession();
        User user = new User(name, lastName, age);
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        System.out.printf("User с именем – %s добавлен в базу данных\n", name);
    }

    @Override
    public void removeUserById(long id) {

        Session session = Util.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, id);
        session.delete(user);
        transaction.commit();

    }

    @Override
    public List<User> getAllUsers() {

        Session session = Util.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<User> users = session.createQuery("from User").getResultList();
        transaction.commit();

        return users;
    }

    @Override
    public void cleanUsersTable() {

        Session session = Util.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("DELETE FROM User").executeUpdate();
        transaction.commit();

    }
}
