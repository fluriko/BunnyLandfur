package mate.academy.database;

import mate.academy.exceptions.MysqlConnectionException;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnector {
    private static final Logger logger = Logger.getLogger(DatabaseConnector.class);
    private static final String userName = "root";
    private static final String password = "root";
    private static final String connectionUrl = "jdbc:mysql://localhost:3306/mysql?serverTimezone=Europe/Kiev";

    public static Connection connect() {
        Connection connection;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(connectionUrl, userName, password);
            logger.debug("connection received");
            return connection;
        } catch (Exception e) {
            logger.error("connection failed", e);
        }
        throw new MysqlConnectionException();
    }
}
