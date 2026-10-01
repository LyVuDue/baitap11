package vn.ute.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import vn.ute.dao.ICategoryDao_24162016;
import vn.ute.entity.Category_24162016;
import vn.ute.util.DBConnect_24162016;

public class CategoryDaoImpl_24162016 implements ICategoryDao_24162016 {

    private Category_24162016 map(ResultSet rs) throws SQLException {
        Category_24162016 c = new Category_24162016();
        c.setCategoryId(rs.getInt("categoryId"));
        c.setCategoryName(rs.getString("categoryName"));
        c.setImages(rs.getString("images"));
        int st = rs.getInt("status");
        c.setStatus(rs.wasNull() ? null : st);
        return c;
    }

    @Override
    public List<Category_24162016> findPage(int offset, int limit) {
        List<Category_24162016> list = new ArrayList<>();
        String sql = "SELECT * FROM Category ORDER BY categoryId DESC LIMIT ? OFFSET ?";
        try (Connection con = DBConnect_24162016.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public int count() {
        String sql = "SELECT COUNT(*) FROM Category";
        try (Connection con = DBConnect_24162016.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Category_24162016 findById(int categoryId) {
        String sql = "SELECT * FROM Category WHERE categoryId = ?";
        try (Connection con = DBConnect_24162016.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
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

    @Override
    public void insert(Category_24162016 c) {
        String sql = "INSERT INTO Category(categoryName, images, status) VALUES(?,?,?)";
        try (Connection con = DBConnect_24162016.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getCategoryName());
            ps.setString(2, c.getImages());
            ps.setObject(3, c.getStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Category_24162016 c) {
        String sql = "UPDATE Category SET categoryName = ?, images = ?, status = ? WHERE categoryId = ?";
        try (Connection con = DBConnect_24162016.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getCategoryName());
            ps.setString(2, c.getImages());
            ps.setObject(3, c.getStatus());
            ps.setInt(4, c.getCategoryId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int categoryId) {
        String sql = "DELETE FROM Category WHERE categoryId = ?";
        try (Connection con = DBConnect_24162016.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException || e.getErrorCode() == 1451) {
                return false;
            }
            throw new RuntimeException(e);
        }
    }
}