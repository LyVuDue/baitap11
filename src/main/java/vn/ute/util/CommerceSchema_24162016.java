package vn.ute.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class CommerceSchema_24162016 {
    private CommerceSchema_24162016() {}

    public static void migrate() throws SQLException {
        try (Connection c = DBConnect_24162016.getConnection();
                Statement s = c.createStatement()) {
            s.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Cart (cartId VARCHAR(50) PRIMARY KEY, userId INT,"
                        + " buyDate DATETIME, status INT) ENGINE=InnoDB");
            s.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS CartItem (cartItemId VARCHAR(50) PRIMARY KEY,"
                        + " quantity INT, unitPrice DOUBLE, productId INT, cartId VARCHAR(50))"
                        + " ENGINE=InnoDB");
            addColumn(c, "Cart", "recipientName", "VARCHAR(100) NULL");
            addColumn(c, "Cart", "phone", "VARCHAR(20) NULL");
            addColumn(c, "Cart", "address", "VARCHAR(500) NULL");
            addColumn(c, "Cart", "note", "VARCHAR(500) NULL");
            addColumn(c, "Cart", "paymentMethod", "VARCHAR(20) NOT NULL DEFAULT 'COD'");
            addColumn(c, "Cart", "totalAmount", "DECIMAL(18,2) NULL");
            addColumn(c, "CartItem", "productName", "VARCHAR(200) NULL");
            addColumn(c, "CartItem", "images", "VARCHAR(500) NULL");
        }
    }

    private static void addColumn(Connection c, String table, String column, String definition)
            throws SQLException {
        String sql =
                "SELECT 1 FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND"
                    + " LOWER(TABLE_NAME)=LOWER(?) AND LOWER(COLUMN_NAME)=LOWER(?)";
        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, table);
            p.setString(2, column);
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) return;
            }
        }
        try (Statement s = c.createStatement()) {
            s.executeUpdate(
                    "ALTER TABLE `" + table + "` ADD COLUMN `" + column + "` " + definition);
        }
    }

    public static void main(String[] args) throws SQLException {
        migrate();
        System.out.println("Đã chuẩn bị bảng Cart và CartItem cho đặt hàng COD.");
    }
}
