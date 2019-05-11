package mate.academy.database.good;

import mate.academy.database.DatabaseConnector;
import mate.academy.model.Good;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GoodDao {
    private static final Logger logger = Logger.getLogger(GoodDao.class);
    private Connection connection = DatabaseConnector.connect();

    public Long addGood(Good good) {
        String sql = "INSERT INTO ma.goods(label,description,category,price) VALUES(?,?,?,?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, good.getLabel());
            preparedStatement.setString(2, good.getDescription());
            preparedStatement.setString(3, good.getCategory());
            preparedStatement.setDouble(4, good.getPrice());
            preparedStatement.executeUpdate();
            logger.debug(sql);
            return this.getGood(good.getLabel()).get().getId();
        } catch (SQLException e) {
            logger.error("adding failed for good: " + good.getLabel(), e);
        }
        return 0L;
    }

    public Long removeGood(Long id) {
        if (this.contains(id)) {
            Good goodToRemove = this.getGood(id).get();
            String sql = "DELETE FROM ma.goods WHERE id = ?;";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
                logger.debug(sql);
                return id;
            } catch (SQLException e) {
                logger.error("removing failed for good: " + id, e);
            }
        }
        return 0L;
    }

    private Good getFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String label = resultSet.getString("label");
        String description = resultSet.getString("description");
        String category = resultSet.getString("category");
        double price = resultSet.getDouble("price");
        return new Good(id, label, description, category, price);
    }

    public Optional<Good> getGood(Long id) {
        String sql = "SELECT * FROM ma.goods WHERE id = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Good goodToGet = getFromResultSet(resultSet);
                logger.debug(sql);
                return Optional.of(goodToGet);
            }
        } catch (SQLException e) {
            logger.error("getting failed for good: " + id, e);
        }
        return Optional.empty();
    }

    private Optional<Good> getGood(String label) {
        String sql = "SELECT * FROM ma.goods WHERE label = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, label);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Good goodToGet = getFromResultSet(resultSet);
                logger.debug(sql);
                return Optional.of(goodToGet);
            }
        } catch (SQLException e) {
            logger.error("getting failed for good: " + label, e);
        }
        return Optional.empty();
    }

    public Long editGood(Long id, String label, String description, String category, double price) {
        if (this.contains(id)) {
            String sql = "UPDATE ma.goods SET label = ?, description = ?, category = ?, price = ? WHERE id = ?;";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, label);
                preparedStatement.setString(2, description);
                preparedStatement.setString(3, category);
                preparedStatement.setDouble(4, price);
                preparedStatement.setLong(5, id);
                preparedStatement.executeUpdate();
                logger.debug(sql);
                return id;
            } catch (SQLException e) {
                logger.error("editing failed for good: " + id, e);
            }
        }
        return 0L;
    }

    public List<Good> getGoods() {
        String sql = "SELECT * FROM ma.goods";
        List<Good> goods = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Good good = getFromResultSet(resultSet);
                goods.add(good);
            }
            logger.debug(sql);
        } catch (SQLException e) {
            logger.error("getting all goods failed", e);
        }
        return goods;
    }

    public boolean contains(Long id) {
        String sql = "SELECT * FROM ma.goods WHERE id = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.debug(sql);
            return resultSet.next();
        } catch (SQLException e) {
            logger.error("checking presence failed for good: " + id, e);
        }
        return false;
    }

    public void removeAll() {
        String sql = "DELETE FROM ma.goods WHERE 1=1;";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            logger.debug(sql);
        } catch (SQLException e) {
            logger.error("removing all goods failed", e);
        }
    }
}
