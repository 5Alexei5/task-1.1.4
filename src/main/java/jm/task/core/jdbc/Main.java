package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Aleksei", "Tereshin", (byte) 33);
        userService.saveUser("Ivan", "Ivanov", (byte) 35);
        userService.saveUser("Nicolai", "Sidorov", (byte) 18);
        userService.saveUser("Mariya", "Semenova", (byte) 25);
        List<User> users = userService.getAllUsers();
        System.out.println(users);
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
