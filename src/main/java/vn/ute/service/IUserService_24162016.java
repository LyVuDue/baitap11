package vn.ute.service;

import java.util.List;
import java.util.Map;

import vn.ute.entity.User_24162016;

public interface IUserService_24162016 {
    User_24162016 login(String username, String password);

    String register(User_24162016 user);

    boolean verifyOtp(String email, String otp);

    List<User_24162016> findPage(int page, int size);

    int count();

    int totalPages(int size);

    User_24162016 findById(int userId);

    String adminSave(User_24162016 user);

    boolean delete(int userId);

    Map<Integer, String> findRoles();

    Map<Integer, String> findSellers();
}