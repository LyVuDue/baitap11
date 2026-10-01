package vn.ute.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vn.ute.dao.IProductDao_24162016;
import vn.ute.entity.Product_24162016;
import vn.ute.util.DBConnect_24162016;

public class ProductDaoImpl_24162016 implements IProductDao_24162016 {

    private static final String SELECT_BASE =
        "SELECT p.*, c.categoryName "
      + "FROM Product p LEFT JOIN Category c ON p.categoryId = c.categoryId ";

    private Integer getInteger(ResultSet rs, String col) throws SQLException {
        int v = rs.getInt(col);
        return rs.wasNull() ? null : v;
    }

    private Product_24162016 map(ResultSet rs) throws SQLException {
        Product_24162016 p = new Product_24162016();
        p.setProductId(rs.getInt("productId"));
        p.setProductName(rs.getString("productName"));

        long code = rs.getLong("productCode");
        p.setProductCode(rs.wasNull() ? null : code);

        p.setCategoryId(getInteger(rs, "categoryId"));
        p.setCategoryName(rs.getString("categoryName"));
        p.setDescription(rs.getString("description"));

        double price = rs.getDouble("price");
        p.setPrice(rs.wasNull() ? null : price);

        p.setAmount(getInteger(rs, "amount"));
        p.setStock(getInteger(rs, "stock"));
        p.setImages(rs.getString("images"));
        p.setWishlist(getInteger(rs, "wishlist"));
        p.setStatus(getInteger(rs, "status"));
        p.setCreateDate(rs.getDate("createDate"));
        p.setSellerId(getInteger(rs, "sellerId"));
        return p;
    }

    @Override
    public List<Product_24162016> findAll() {
        List<Product_24162016> list = new ArrayList<>();
        String sql = SELECT_BASE + "ORDER BY p.sellerId, p.productId";
        try (Connection con = DBConnect_24162016.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Product_24162016 findById(int productId) {
        String sql = SELECT_BASE + "WHERE p.productId = ?";
        try (Connection con = DBConnect_24162016.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}