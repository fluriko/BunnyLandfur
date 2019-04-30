package mate.academy.database;

import mate.academy.model.Role;
import mate.academy.model.Roles;
import mate.academy.model.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao {
    private static final Logger logger = Logger.getLogger(UserDao.class);

    public int addUser(User user) {
        if (!this.contains(user.getName())) {
            String insert = "INSERT INTO ma.users(login,password,role_id) VALUES(?,?,?);";
            Connection connection = DatabaseConnector.connect();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insert);
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setInt(3, user.getRole().getId());
                preparedStatement.executeUpdate();
                Optional<User> userPut = this.getUser(user.getName());
                if (userPut.isPresent()) {
                    return userPut.get().getId();
                }
            } catch (SQLException e) {
                logger.error("adding failed for user: " + user.getName(), e);
            }
        }
        return 0;
    }

    public int removeUser(String name) {
        if (this.contains(name)) {
            User userToRemove = this.getUser(name).get();
            if (userToRemove.getRole().getId() != 1) {
                int id = userToRemove.getId();
                String delete = "DELETE FROM ma.users WHERE login = ?;";
                Connection connection = DatabaseConnector.connect();
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(delete);
                    preparedStatement.setString(1, name);
                    preparedStatement.executeUpdate();
                    return id;
                } catch (SQLException e) {
                    logger.error("removing failed for user: " + name, e);
                }
            }
        }
        return 0;
    }

    public Optional<User> getUser(String name) {
        String select = "SELECT * FROM ma.users WHERE login = ?;";
        Connection connection = DatabaseConnector.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String password = resultSet.getString("password");
                Role role = new Role(resultSet.getInt("role_id"));
                User userToGet = new User(id, name, password, role);
                return Optional.of(userToGet);
            }
        } catch (SQLException e) {
            logger.error("getting failed for user: " + name, e);
        }
        return Optional.empty();
    }

    public int editUser(String name, String newPass) {
        if (this.contains(name)) {
            String insert = "UPDATE ma.users SET password = ? WHERE login = ?;";
            Connection connection = DatabaseConnector.connect();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insert);
                preparedStatement.setString(1, newPass);
                preparedStatement.setString(2, name);
                preparedStatement.executeUpdate();
                return this.getUser(name).get().getId();
            } catch (SQLException e) {
                logger.error("editing failed for user: " + name, e);
            }
        }
        return 0;
    }

    public boolean setRole(String name, int roleId) {
        if (this.contains(name) && roleId <= Roles.values().length) {
            String insert = "UPDATE ma.users SET role_id = ? WHERE login = ?;";
            Connection connection = DatabaseConnector.connect();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insert);
                preparedStatement.setInt(1, roleId);
                preparedStatement.setString(2, name);
                preparedStatement.executeUpdate();
                return true;
            } catch (SQLException e) {
                logger.error("setting role failed for user: " + name, e);
            }
        }
        return false;
    }

    public List<User> getUsers() {
        String select = "SELECT * FROM ma.users;";
        List<User> users = new ArrayList<>();
        Connection connection = DatabaseConnector.connect();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(select);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("login");
                String password = resultSet.getString("password");
                int roleId = resultSet.getInt("role_id");
                users.add(new User(id, name, password, new Role(roleId)));
            }
        } catch (SQLException e) {
            logger.error("getting all users failed", e);
        }
        return users;
    }

    public boolean contains(String name) {
        String select = "SELECT * FROM ma.users WHERE login = ?;";
        Connection connection = DatabaseConnector.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            logger.error("checking presence failed for user: " + name, e);
        }
        return false;
    }

    public void removeAll() {
        String delete = "DELETE FROM ma.users WHERE 1=1;";
        Connection connection = DatabaseConnector.connect();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(delete);
        } catch (SQLException e) {
            logger.error("removing all users failed", e);
        }
    }
}
