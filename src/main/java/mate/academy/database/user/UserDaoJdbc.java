//package mate.academy.database.user;
//
//import mate.academy.database.DatabaseConnector;
//import mate.academy.database.role.RoleDao;
//import mate.academy.database.role.RoleDaoHib;
//import mate.academy.model.Role;
//import mate.academy.model.User;
//import org.apache.log4j.Logger;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//public class UserDaoJdbc implements UserDao {
//    private static final Logger logger = Logger.getLogger(UserDaoJdbc.class);
//    private Connection connection = DatabaseConnector.connect();
//    private static final RoleDao ROLE_DAO = new RoleDaoHib();
//
//    @Override
//    public int addUser(User user) {
//        String sql = "INSERT INTO ma.users(login,password,role_id,mail, salt) VALUES(?,?,?,?,?);";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, user.getLogin());
//            preparedStatement.setString(2, user.getPassword());
//            preparedStatement.setInt(3, user.getRole().getId());
//            preparedStatement.setString(4, user.getMail());
//            preparedStatement.setString(5, user.getSalt());
//            preparedStatement.executeUpdate();
//            logger.debug(sql);
//            return 1;
//        } catch (SQLException e) {
//            logger.error("adding failed for user: " + user.getLogin(), e);
//        }
//        return 0;
//    }
//
//    @Override
//    public int removeUser(User user) {
//        if (!user.getRole().equals(ROLE_DAO.getRole(1))) {
//            String sql = "DELETE FROM `ma`.`users` WHERE (`id` = ?);";
//            try {
//                PreparedStatement preparedStatement = connection.prepareStatement(sql);
//                preparedStatement.setInt(1, user.getId());
//                preparedStatement.executeUpdate();
//                logger.debug(sql);
//                return user.getId();
//            } catch (SQLException e) {
//                logger.error("removing failed for user: " + user.getId(), e);
//            }
//        }
//        return 0;
//    }
//
//    private User getFromResultSet(ResultSet resultSet) throws SQLException {
//        int id = resultSet.getInt("id");
//        String login = resultSet.getString("login");
//        String password = resultSet.getString("password");
//        int roleId = resultSet.getInt("role_id");
//        Role role = ROLE_DAO.getRole(roleId).get();
//        String mail = resultSet.getString("mail");
//        String salt = resultSet.getString("salt");
//        return new User(id, login, password, role, mail, salt);
//    }
//
//    @Override
//    public Optional<User> getUserByLogin(String login) {
//        String sql = "SELECT * FROM ma.users INNER JOIN ma.roles ON ma.users.role_id = ma.roles.id WHERE login = ?;";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, login);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                User userToGet = getFromResultSet(resultSet);
//                logger.debug(sql);
//                return Optional.of(userToGet);
//            }
//        } catch (SQLException e) {
//            logger.error("getting failed for user: " + login, e);
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<User> getUserById(int id) {
//        String sql = "SELECT * FROM ma.users INNER JOIN ma.roles ON ma.users.role_id = ma.roles.id WHERE ma.users.id = ?;";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                User userToGet = getFromResultSet(resultSet);
//                logger.debug(sql);
//                return Optional.of(userToGet);
//            }
//        } catch (SQLException e) {
//            logger.error("getting failed for user: " + id, e);
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<User> getUserByMail(String mail) {
//        String sql = "SELECT * FROM ma.users INNER JOIN ma.roles ON ma.users.role_id = ma.roles.id WHERE mail = ?;";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, mail);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                User userToGet = getFromResultSet(resultSet);
//                logger.debug(sql);
//                return Optional.of(userToGet);
//            }
//        } catch (SQLException e) {
//            logger.error("getting failed for mail: " + mail, e);
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public int editUser(User user) {
//        String sql = "UPDATE ma.users SET login = ?, password = ?, role_id = ?, mail = ?, salt = ? WHERE id = ?;";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, user.getLogin());
//            preparedStatement.setString(2, user.getPassword());
//            preparedStatement.setInt(3, user.getRole().getId());
//            preparedStatement.setString(4, user.getMail());
//            preparedStatement.setString(5, user.getSalt());
//            preparedStatement.setInt(6, user.getId());
//            preparedStatement.executeUpdate();
//            logger.debug(sql);
//            return user.getId();
//        } catch (SQLException e) {
//            logger.error("editing failed for user: " + user.getId(), e);
//        }
//        return 0;
//    }
//
//    @Override
//    public List<User> getUsers() {
//        String sql = "SELECT * FROM ma.users INNER JOIN ma.roles ON ma.users.role_id = ma.roles.id";
//        List<User> users = new ArrayList<>();
//        try {
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(sql);
//            while (resultSet.next()) {
//                User user = getFromResultSet(resultSet);
//                users.add(user);
//            }
//            logger.debug(sql);
//        } catch (SQLException e) {
//            logger.error("getting all users failed", e);
//        }
//        return users;
//    }
//
//    public void removeAll() {
//        String sql = "DELETE FROM ma.users WHERE 1=1;";
//        try {
//            Statement statement = connection.createStatement();
//            statement.executeUpdate(sql);
//            logger.debug(sql);
//        } catch (SQLException e) {
//            logger.error("removing all users failed", e);
//        }
//    }
//}
