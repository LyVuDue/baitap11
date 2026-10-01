package vn.ute.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect_24162016 {

    private static final String URL =
        "jdbc:mysql://localhost:3306/online"
        + "?useSSL=false&allowPublicKeyRetrieval=true"
        + "&serverTimezone=Asia/Ho_Chi_Minh"
        + "&useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root";     
    private static final String PASS = "admin123";    

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void main(String[] args) {
        try (Connection c = getConnection()) {
            System.out.println("Ket noi thanh cong: " + c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}