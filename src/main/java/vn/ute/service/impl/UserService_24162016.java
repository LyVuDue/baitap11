package vn.ute.service.impl;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

import vn.ute.dao.IUserDao_24162016;
import vn.ute.dao.impl.UserDaoImpl_24162016;
import vn.ute.entity.User_24162016;
import vn.ute.service.IUserService_24162016;
import vn.ute.util.MailUtil_24162016;

public class UserService_24162016 implements IUserService_24162016 {

    private final IUserDao_24162016 dao = new UserDaoImpl_24162016();

    public User_24162016 login(String username, String password) {
        User_24162016 u = dao.findByUsername(username);
        if (u != null && u.getStatus() == 1
                && u.getPassword() != null && u.getPassword().equals(password)) {
            return u;
        }
        return null;
    }

    @Override
    public String register(User_24162016 u) {

        dao.deleteUnverified(u.getUsername(), u.getEmail());

        if (dao.findByUsername(u.getUsername()) != null) {
            return "Tên đăng nhập đã tồn tại";
        }
        if (dao.findByEmail(u.getEmail()) != null) {
            return "Email đã được sử dụng";
        }

        String otp = String.format("%06d", new SecureRandom().nextInt(1_000_000));

        try {
            MailUtil_24162016.send(u.getEmail(), "Mã OTP kích hoạt tài khoản",
                    "Xin chào " + u.getFullname() + ",\n\nMã OTP của bạn là: " + otp
                    + "\n\nVui lòng nhập mã này để kích hoạt tài khoản.");
        } catch (Exception e) {
            e.printStackTrace();
            return "Không gửi được email OTP, hãy kiểm tra email hoặc cấu hình mail";
        }

        u.setRoleId(2);   
        u.setStatus(0);   
        u.setCode(otp);
        dao.insert(u);
        return null;
    }

    @Override
    public boolean verifyOtp(String email, String otp) {
        User_24162016 u = dao.findByEmail(email);
        if (u != null && u.getStatus() == 0 && otp != null && otp.equals(u.getCode())) {
            dao.activate(email);
            return true;
        }
        return false;
    }

    @Override
    public List<User_24162016> findPage(int page, int size) {
        int offset = (Math.max(page, 1) - 1) * size;
        return dao.findPage(offset, size);
    }

    @Override
    public int count() {
        return dao.count();
    }

    @Override
    public int totalPages(int size) {
        int total = dao.count();
        return Math.max(1, (int) Math.ceil(total / (double) size));
    }

    @Override
    public User_24162016 findById(int userId) {
        return dao.findById(userId);
    }

    @Override
    public String adminSave(User_24162016 u) {
        boolean isNew = u.getUserId() == 0;

        if (isNew && (u.getPassword() == null || u.getPassword().isEmpty())) {
            return "Mật khẩu không được để trống";
        }

        User_24162016 byName = dao.findByUsername(u.getUsername());
        if (byName != null && byName.getUserId() != u.getUserId()) {
            return "Tên đăng nhập đã tồn tại";
        }
        User_24162016 byMail = dao.findByEmail(u.getEmail());
        if (byMail != null && byMail.getUserId() != u.getUserId()) {
            return "Email đã được sử dụng";
        }

        if (u.getRoleId() == null) {
            return "Vui lòng chọn vai trò";
        }
        if (u.getRoleId() == 3) {
            if (u.getSellerId() == null) {
                return "Vai trò Seller phải chọn cửa hàng";
            }
        } else {
            u.setSellerId(null); 
        }

        if (isNew) {
            dao.insert(u);
        } else {
            if (u.getPassword() == null || u.getPassword().isEmpty()) {
                User_24162016 old = dao.findById(u.getUserId());
                if (old == null) {
                    return "Không tìm thấy người dùng";
                }
                u.setPassword(old.getPassword()); 
            }
            dao.update(u);
        }
        return null;
    }

    @Override
    public boolean delete(int userId) {
        return dao.delete(userId);
    }

    @Override
    public Map<Integer, String> findRoles() {
        return dao.findRoles();
    }

    @Override
    public Map<Integer, String> findSellers() {
        return dao.findSellers();
    }
}