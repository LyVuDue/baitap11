package vn.ute.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import vn.ute.dao.IUserDao_24162016;
import vn.ute.entity.User_24162016;
import vn.ute.util.DBConnect_24162016;

public class UserDaoImpl_24162016 implements IUserDao_24162016 {

    private User_24162016 map(ResultSet rs) throws SQLException {
        User_24162016 u = new User_24162016();
        u.setUserId(rs.getInt("userId"));
        u.setUsername(rs.getString("username"));
        u.setEmail(rs.getString("email"));
        u.setFullname(rs.getString("fullname"));
        u.setPassword(rs.getString("password"));
        u.setImages(rs.getString("images"));
        u.setPhone(rs.getString("phone"));
        u.setStatus(rs.getInt("status"));
        u.setCode(rs.getString("code"));

        int roleId = rs.getInt("roleId");
        u.setRoleId(rs.wasNull() ? null : roleId);

        int sellerId = rs.getInt("sellerid");
        u.setSellerId(rs.wasNull() ? null : sellerId);
        return u;
    }

    private User_24162016 findOne(String sql, String param) {
        try (Connection con = DBConnect_24162016.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, param);
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
    public User_24162016 findByUsername(String username) {
        return findOne("SELECT * FROM Users WHERE username = ?", username);
    }

    @Override
    public User_24162016 findByEmail(String email) {
        return findOne("SELECT * FROM Users WHERE email = ?", email);
    }

    @Override
    public void insert(User_24162016 u) {
        String sql = "INSERT INTO Users(username,email,fullname,password,images,phone,status,code,roleId,sellerid) "
                   + "VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (Connection con = DBConnect_24162016.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getFullname());
            ps.setString(4, u.getPassword());
            ps.setString(5, u.getImages());
            ps.setString(6, u.getPhone());
            ps.setInt(7, u.getStatus());
            ps.setString(8, u.getCode());
            ps.setObject(9, u.getRoleId());
            ps.setObject(10, u.getSellerId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void activate(String email) {
        String sql = "UPDATE Users SET status = 1, code = NULL WHERE email = ?";
        try (Connection con = DBConnect_24162016.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUnverified(String username, String email) {
        String sql = "DELETE FROM Users WHERE status = 0 AND (username = ? OR email = ?)";
        try (Connection con = DBConnect_24162016.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, email);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User_24162016> findPage(int offset, int limit) {
        List<User_24162016> list = new ArrayList<>();
        String sql = "SELECT * FROM Users ORDER BY userId DESC LIMIT ? OFFSET ?";
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
        String sql = "SELECT COUNT(*) FROM Users";
        try (Connection con = DBConnect_24162016.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User_24162016 findById(int userId) {
        String sql = "SELECT * FROM Users WHERE userId = ?";
        try (Connection con = DBConnect_24162016.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
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
    public void update(User_24162016 u) {
        String sql = "UPDATE Users SET username=?, email=?, fullname=?, password=?, images=?, "
                   + "phone=?, status=?, roleId=?, sellerid=? WHERE userId=?";
        try (Connection con = DBConnect_24162016.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getFullname());
            ps.setString(4, u.getPassword());
            ps.setString(5, u.getImages());
            ps.setString(6, u.getPhone());
            ps.setInt(7, u.getStatus());
            ps.setObject(8, u.getRoleId());
            ps.setObject(9, u.getSellerId());
            ps.setInt(10, u.getUserId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int userId) {
        String sql = "DELETE FROM Users WHERE userId = ?";
        try (Connection con = DBConnect_24162016.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1451) {
                return false;
            }
            throw new RuntimeException(e);
        }
    }

    private Map<Integer, String> findMap(String sql) {
        Map<Integer, String> map = new LinkedHashMap<>();
        try (Connection con = DBConnect_24162016.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    @Override
    public Map<Integer, String> findRoles() {
        return findMap("SELECT roleId, roleName FROM UserRoles ORDER BY roleId");
    }

    @Override
    public Map<Integer, String> findSellers() {
        return findMap("SELECT sellerId, sellername FROM Seller ORDER BY sellerId");
    }
}