package BE;

import java.sql.*;

public class JDBC {
    private static final String URL = "jdbc:mysql://localhost:3306/resto_vision";
    private static final String USER = "root";
    private static final String PASS = "";
    public static Connection conn;
    public static PreparedStatement stmt;
    public static ResultSet rs;

    public static void connect() {
        try {
            conn = DriverManager.getConnection(URL,USER,PASS);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void disconnect() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void update(String sql) {
        try {
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("UPDATE ERROR");
            System.out.println("sql: " + sql);
            System.out.println(e.getMessage());
        }
    }

    public static void query(String sql) {
        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("QUERY ERROR");
            System.out.println("sql: " + sql);
            System.out.println(e.getMessage());
        }
    }
}
