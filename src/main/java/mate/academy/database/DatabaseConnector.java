package mate.academy.database;

import mate.academy.exceptions.MysqlConnectionException;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnector {
    private static final String userName = "root";
    private static final String password = "root";
    private static final String connectionUrl = "jdbc:mysql://localhost:3306/mysql?serverTimezone=Europe/Kiev";

    public static Connection connect() {
        Connection connection;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(connectionUrl, userName, password);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new MysqlConnectionException();
    }
}
