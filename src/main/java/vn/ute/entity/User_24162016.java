package vn.ute.entity;

import java.io.Serializable;

public class User_24162016 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int userId;
    private String username;
    private String email;
    private String fullname;
    private String password;
    private String images;
    private String phone;
    private int status;
    private String code;
    private Integer roleId;
    private Integer sellerId;

    public User_24162016() {
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public Integer getRoleId() { return roleId; }
    public void setRoleId(Integer roleId) { this.roleId = roleId; }

    public Integer getSellerId() { return sellerId; }
    public void setSellerId(Integer sellerId) { this.sellerId = sellerId; }
}