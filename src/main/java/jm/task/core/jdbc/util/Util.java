package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private static SessionFactory sessionFactory;

    public static Connection getMySQLConnection() throws SQLException {
        String hostName = "@localhost";
        String dbName = "databasename";
        String userName = "root";
        String password = "springcourse";

        return getMySQLConnection(hostName, dbName, userName, password);
    }

    public static Connection getMySQLConnection(String hostName, String dbName, String userName,
                                                String password) throws SQLException {

        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

        return DriverManager.getConnection(connectionURL, userName, password);
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties properties = new Properties();
                properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                properties.put(Environment.URL, "jdbc:mysql://localhost:3306/databasename");
                properties.put(Environment.USER, "root");
                properties.put(Environment.PASS, "springcourse");
                properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                properties.put(Environment.SHOW_SQL, "true");

                configuration.setProperties(properties);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
