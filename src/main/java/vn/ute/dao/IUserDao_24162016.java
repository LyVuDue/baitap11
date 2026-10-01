package vn.ute.dao;

import java.util.List;
import java.util.Map;

import vn.ute.entity.User_24162016;

public interface IUserDao_24162016 {

    User_24162016 findByUsername(String username);

    User_24162016 findByEmail(String email);

    void insert(User_24162016 user);

    void activate(String email);

    void deleteUnverified(String username, String email);

    List<User_24162016> findPage(int offset, int limit);

    int count();

    User_24162016 findById(int userId);

    void update(User_24162016 user);

    boolean delete(int userId);

    Map<Integer, String> findRoles();

    Map<Integer, String> findSellers();
}