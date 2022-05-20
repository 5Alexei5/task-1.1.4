package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    static private Connection connection;

    static {
        try {
            connection = Util.getMySQLConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {

            String sql = "CREATE TABLE Users(Id int AUTO_INCREMENT NOT NULL PRIMARY KEY, Name varchar(25), " +
                    "LastName varchar(25), Age int)";

            statement.execute(sql);

        }catch (SQLException e) {
            //ignore
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {

            String sql = "DROP TABLE Users";
            statement.execute(sql);

        } catch (SQLException s) {
            //ignore
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement("INSERT INTO Users VALUES (?, ?, ?, ?);")) {

            preparedStatement.setNull(1, Types.INTEGER);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, lastName);
            preparedStatement.setInt(4,age);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.printf("User с именем – %s добавлен в базу данных\n", name);
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement("DELETE FROM Users WHERE Id = ?")) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {

            statement.execute("DELETE FROM Users");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
