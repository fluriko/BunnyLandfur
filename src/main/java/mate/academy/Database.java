package mate.academy;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {
    public static void addUser(User user) {
        String insert = String.format("INSERT INTO homework.users(name,password) VALUES('%s','%s');", user.getName(), user.getPassword());
        Connection connection = DatabaseConnector.connect();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(insert);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeUser(User user) {
        String delete = String.format("DELETE FROM homework.users WHERE name = '%s';", user.getName());
        Connection connection = DatabaseConnector.connect();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(delete);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static User getUser(User user) {
        String select = String.format("SELECT * FROM homework.users WHERE name = '%s';", user.getName());
        Connection connection = DatabaseConnector.connect();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            if (resultSet.next()) {
                return new User(resultSet.getString("name"), resultSet.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NoSuchUserException();
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
        String select = String.format("SELECT * FROM homework.users WHERE name = '%s';", user.getName());
        Connection connection = DatabaseConnector.connect();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
