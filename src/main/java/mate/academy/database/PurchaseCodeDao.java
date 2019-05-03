package mate.academy.database;

import mate.academy.model.Code;
import mate.academy.model.Good;
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

public class PurchaseCodeDao {
    private static final Logger logger = Logger.getLogger(PurchaseCodeDao.class);
    private static final UserDao userDao = new UserDao();
    private static final GoodDao goodDao = new GoodDao();
    private Connection connection = DatabaseConnector.connect();

    public int addCode(Code code) {
        if (!this.contains(code)) {
            String sql = "INSERT INTO ma.codes(value,user_id,good_id) VALUES(?,?,?);";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, code.getValue());
                preparedStatement.setInt(2, code.getUser().getId());
                preparedStatement.setLong(3, code.getGood().getId());
                preparedStatement.executeUpdate();
                logger.debug(sql);
                return this.getCode(code.getValue()).get().getId();
            } catch (SQLException e) {
                logger.error("adding failed for code: " + code.getValue(), e);
            }
        }
        return 0;
    }

    public int removeCode(Code code) {
        if (this.contains(code)) {
            Code codeToRemove = code;
            String sql = "DELETE FROM ma.codes WHERE id = ?;";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, code.getId());
                preparedStatement.executeUpdate();
                logger.debug(sql);
                return codeToRemove.getId();
            } catch (SQLException e) {
                logger.error("removing failed for code: " + codeToRemove, e);
            }
        }
        return 0;
    }

    public int removeCodeById(int id) {
            String sql = "DELETE FROM ma.codes WHERE id = ?;";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                logger.debug(sql);
                return id;
            } catch (SQLException e) {
                logger.error("removing failed for code: " + id, e);
            }
        return 0;
    }

    public int removeCodeForUser(User user) {
        String sql = "DELETE FROM ma.codes WHERE user_id = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();
            logger.debug(sql);
            return user.getId();
        } catch (SQLException e) {
            logger.error("removing code failed for user: " + user.getId(), e);
        }
        return 0;
    }

    private Optional<Code> getCode(String value) {
        String sql = "SELECT * FROM ma.codes WHERE value = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Code codeToGet = getFromResultSet(resultSet);
                logger.debug(sql);
                return Optional.of(codeToGet);
            }
        } catch (SQLException e) {
            logger.error("getting failed for code: " + value, e);
        }
        return Optional.empty();
    }

    public Optional<Code> getCode(int id) {
        String sql = "SELECT * FROM ma.codes WHERE id = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Code codeToGet = getFromResultSet(resultSet);
                logger.debug(sql);
                return Optional.of(codeToGet);
            }
        } catch (SQLException e) {
            logger.error("getting failed for code: " + id, e);
        }
        return Optional.empty();
    }


    public List<Code> getCodes() {
        String sql = "SELECT * FROM ma.codes";
        List<Code> codes = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Code code = getFromResultSet(resultSet);
                codes.add(code);
            }
            logger.debug(sql);
        } catch (SQLException e) {
            logger.error("getting all goods failed", e);
        }
        return codes;
    }

    private Code getFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String value = resultSet.getString("value");
        int user_id = resultSet.getInt("user_id");
        Long good_id = resultSet.getLong("good_id");
        User user = userDao.getUserById(user_id).get();
        Good good = goodDao.getGood(good_id).get();
        return new Code(id, value, user, good);
    }

    public boolean contains(Code code) {
        String sql = "SELECT * FROM ma.codes WHERE value = ? AND user_id = ? AND good_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, code.getValue());
            preparedStatement.setInt(2, code.getUser().getId());
            preparedStatement.setLong(3, code.getGood().getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.debug(sql);
            return resultSet.next();
        } catch (SQLException e) {
            logger.error("checking presence failed for code: " + code, e);
        }
        return false;
    }

    public void removeAll() {
        String sql = "DELETE FROM ma.codes WHERE 1=1;";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            logger.debug(sql);
        } catch (SQLException e) {
            logger.error("removing all codes failed", e);
        }
    }
}
