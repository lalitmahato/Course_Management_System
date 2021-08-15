package SQLConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    public static Connection getDatabaseConnection() throws SQLException {
        String databaseURL = "jdbc:mysql://" + SQLConfiguration.host + ":" + SQLConfiguration.port + "/" + SQLConfiguration.dbName+"?useTimezone=true&serverTimezone=UTC";
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        return DriverManager.getConnection(databaseURL,SQLConfiguration.username,SQLConfiguration.password);
    }
}
