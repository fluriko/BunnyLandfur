package mate.academy.database;

import mate.academy.exceptions.NoSuchUserException;
import mate.academy.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Database {
    public static void addUser(User user) {
        if (!Database.contains(user)) {
            String insert = "INSERT INTO homework.users(name,password) VALUES(?,?);";
            Connection connection = DatabaseConnector.connect();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insert);
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void removeUser(User user) {
        if (Database.contains(user)) {
            String delete = "DELETE FROM homework.users WHERE name = ?;";
            Connection connection = DatabaseConnector.connect();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(delete);
                preparedStatement.setString(1, user.getName());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static User getUser(User user) {
        String select = "SELECT * FROM homework.users WHERE name = ?;";
        Connection connection = DatabaseConnector.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setString(1, user.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(resultSet.getString("name"), resultSet.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NoSuchUserException();
    }

    public static void editUser(User user, String newPass) {
        if (Database.contains(user)) {
            String insert = "UPDATE homework.users SET password = ? WHERE name = ?;";
            Connection connection = DatabaseConnector.connect();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insert);
                preparedStatement.setString(1, newPass);
                preparedStatement.setString(2, user.getName());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<User> getUsers() {
        String select = "SELECT * FROM homework.users;";
        List<User> users = new ArrayList<>();
        Connection connection = DatabaseConnector.connect();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            while (resultSet.next()) {
                users.add(new User(resultSet.getString("name"), resultSet.getString("password")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static boolean contains(User user) {
        String select = "SELECT * FROM homework.users WHERE name = ?;";
        Connection connection = DatabaseConnector.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setString(1, user.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void removeAll() {
        String delete = "DELETE FROM homework.users WHERE 1=1;";
        Connection connection = DatabaseConnector.connect();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(delete);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
