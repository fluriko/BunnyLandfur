package mate.academy.database;

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
    private static final CodeDao CODE_DAO = new CodeDao();

    public int addUser(User user) {
        if (!this.contains(user.getName())) {
            String sql = "INSERT INTO ma.users(login,password,role_id,mail) VALUES(?,?,?,?);";
            Connection connection = DatabaseConnector.connect();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setInt(3, user.getRole().getId());
                preparedStatement.setString(4, user.getMail());
                preparedStatement.executeUpdate();
                logger.debug(sql);
                return 1;
            } catch (SQLException e) {
                logger.error("adding failed for user: " + user.getName(), e);
            }
        }
        return 0;
    }

    public int removeUser(String login) {
        if (this.contains(login)) {
            User userToRemove = this.getUser(login).get();
            CODE_DAO.removeCodeForUser(userToRemove);
            if (userToRemove.getRole() != Roles.ADMIN) {
                int id = userToRemove.getId();
                String sql = "DELETE FROM `ma`.`users` WHERE (`login` = ?);";
                Connection connection = DatabaseConnector.connect();
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, login);
                    preparedStatement.executeUpdate();
                    logger.debug(sql);
                    return id;
                } catch (SQLException e) {
                    logger.error("removing failed for user: " + login, e);
                }
            }
        }
        return 0;
    }

    private User getFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        Roles role = Roles.valueOf(resultSet.getString("name"));
        String mail = resultSet.getString("mail");
        return new User(id, login, password, role, mail);
    }

    public Optional<User> getUser(String login) {
        String sql = "SELECT * FROM ma.users INNER JOIN ma.roles ON ma.users.role_id = ma.roles.id WHERE login = ?;";
        Connection connection = DatabaseConnector.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User userToGet = getFromResultSet(resultSet);
                logger.debug(sql);
                return Optional.of(userToGet);
            }
        } catch (SQLException e) {
            logger.error("getting failed for user: " + login, e);
        }
        return Optional.empty();
    }

    public Optional<User> getUserById(int id) {
        String sql = "SELECT * FROM ma.users INNER JOIN ma.roles ON ma.users.role_id = ma.roles.id WHERE ma.users.id = ?;";
        Connection connection = DatabaseConnector.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User userToGet = getFromResultSet(resultSet);
                logger.debug(sql);
                return Optional.of(userToGet);
            }
        } catch (SQLException e) {
            logger.error("getting failed for user: " + id, e);
        }
        return Optional.empty();
    }

    public int editPassword(String login, String newPass) {
        String sql = "UPDATE ma.users SET password = ? WHERE login = ?;";
        Connection connection = DatabaseConnector.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newPass);
            preparedStatement.setString(2, login);
            preparedStatement.executeUpdate();
            logger.debug(sql);
            return this.getUser(login).get().getId();
        } catch (SQLException e) {
            logger.error("editing password failed for user: " + login, e);
        }
        return 0;
    }

    public int editUser(int id, String newLog, String newPass, String newMail) {
        String sql = "UPDATE ma.users SET login = ?, password = ?, mail = ? WHERE id = ?;";
        Connection connection = DatabaseConnector.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newLog);
            preparedStatement.setString(2, newPass);
            preparedStatement.setString(3, newMail);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
            logger.debug(sql);
            return id;
        } catch (SQLException e) {
            logger.error("editing failed for user: " + id, e);
        }
        return 0;
    }

    public boolean setRole(String login, int roleId) {
        if (this.contains(login) && roleId <= Roles.values().length) {
            String sql = "UPDATE ma.users SET role_id = ? WHERE login = ?;";
            Connection connection = DatabaseConnector.connect();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, roleId);
                preparedStatement.setString(2, login);
                preparedStatement.executeUpdate();
                logger.debug(sql);
                return true;
            } catch (SQLException e) {
                logger.error("setting role failed for user: " + login, e);
            }
        }
        return false;
    }

    public List<User> getUsers() {
        String sql = "SELECT * FROM ma.users INNER JOIN ma.roles ON ma.users.role_id = ma.roles.id";
        List<User> users = new ArrayList<>();
        Connection connection = DatabaseConnector.connect();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = getFromResultSet(resultSet);
                users.add(user);
            }
            logger.debug(sql);
        } catch (SQLException e) {
            logger.error("getting all users failed", e);
        }
        return users;
    }

    public boolean contains(String login) {
        String sql = "SELECT * FROM ma.users WHERE login = ?;";
        Connection connection = DatabaseConnector.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.debug(sql);
            return resultSet.next();
        } catch (SQLException e) {
            logger.error("checking presence failed for user: " + login, e);
        }
        return false;
    }

    public boolean containsMail(String mail) {
        String sql = "SELECT * FROM ma.users WHERE mail = ?;";
        Connection connection = DatabaseConnector.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, mail);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.debug(sql);
            return resultSet.next();
        } catch (SQLException e) {
            logger.error("checking presence failed for mail: " + mail, e);
        }
        return false;
    }

    public void removeAll() {
        String sql = "DELETE FROM ma.users WHERE 1=1;";
        Connection connection = DatabaseConnector.connect();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            logger.debug(sql);
        } catch (SQLException e) {
            logger.error("removing all users failed", e);
        }
    }
}
