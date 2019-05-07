package mate.academy.database;

import mate.academy.model.Roles;
import mate.academy.model.User;
import mate.academy.util.HashUtil;
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
    private static final PurchaseCodeDao PURCHASE_CODE_DAO = new PurchaseCodeDao();
    private Connection connection = DatabaseConnector.connect();

    public int addUser(User user) {
        if (!this.contains(user.getName())) {
            String sql = "INSERT INTO ma.users(login,password,role_id,mail, salt) VALUES(?,?,?,?,?);";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, user.getName());
                String hashPass = HashUtil.getSha512SecurePassword(user.getPassword(), user.getSalt());
                preparedStatement.setString(2, hashPass);
                preparedStatement.setInt(3, user.getRole().getId());
                preparedStatement.setString(4, user.getMail());
                preparedStatement.setString(5, user.getSalt());
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
            PURCHASE_CODE_DAO.removeCodeForUser(userToRemove);
            if (userToRemove.getRole() != Roles.ADMIN) {
                int id = userToRemove.getId();
                String sql = "DELETE FROM `ma`.`users` WHERE (`login` = ?);";
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

    public int removeUserById(int id) {
        User userToRemove = this.getUserById(id).get();
        PURCHASE_CODE_DAO.removeCodeForUser(userToRemove);
        if (userToRemove.getRole() != Roles.ADMIN) {
            String sql = "DELETE FROM `ma`.`users` WHERE (`id` = ?);";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                logger.debug(sql);
                return id;
            } catch (SQLException e) {
                logger.error("removing failed for user: " + id, e);
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
        String salt = resultSet.getString("salt");
        return new User(id, login, password, role, mail, salt);
    }

    public Optional<User> getUser(String login) {
        String sql = "SELECT * FROM ma.users INNER JOIN ma.roles ON ma.users.role_id = ma.roles.id WHERE login = ?;";
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

    public int editUser(int id, String newLog, String newPass, String newMail) {
        String sql = "UPDATE ma.users SET login = ?, password = ?, mail = ?, salt = ? WHERE id = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newLog);
            String newSalt = HashUtil.getRandomSalt();
            String hashPass = HashUtil.getSha512SecurePassword(newPass, newSalt);
            preparedStatement.setString(2, hashPass);
            preparedStatement.setString(3, newMail);
            preparedStatement.setString(4, newSalt);
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
            logger.debug(sql);
            return id;
        } catch (SQLException e) {
            logger.error("editing failed for user: " + id, e);
        }
        return 0;
    }

    public boolean setRole(int id, int roleId) {
        String sql = "UPDATE ma.users SET role_id = ? WHERE id = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, roleId);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            logger.debug(sql);
            return true;
        } catch (SQLException e) {
            logger.error("setting role failed for user: " + id, e);
        }
        return false;
    }

    public List<User> getUsers() {
        String sql = "SELECT * FROM ma.users INNER JOIN ma.roles ON ma.users.role_id = ma.roles.id";
        List<User> users = new ArrayList<>();
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

    public boolean contains(int id) {
        String sql = "SELECT * FROM ma.users WHERE id = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.debug(sql);
            return resultSet.next();
        } catch (SQLException e) {
            logger.error("checking presence failed for user: " + id, e);
        }
        return false;
    }

    public boolean contains(String login) {
        String sql = "SELECT * FROM ma.users WHERE login = ?;";
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
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            logger.debug(sql);
        } catch (SQLException e) {
            logger.error("removing all users failed", e);
        }
    }
}
