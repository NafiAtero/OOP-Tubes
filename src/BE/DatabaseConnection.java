package BE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static String DB_URL = "jdbc:mysql://localhost:3306/resto_vision";
    private static String DB_USER = "root";
    private static String DB_PASS = "";
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public static void setDbUrl(String dbUrl) {
        DB_URL = "jdbc:mysql://" + dbUrl;
    }

    public static void setDbUser(String dbUser) {
        DB_USER = dbUser;
    }

    public static void setDbPass(String dbPass) {
        DB_PASS = dbPass;
    }
}
