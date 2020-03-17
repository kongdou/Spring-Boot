package fun.deepsky.mybatis.model;

import fun.deepsky.mybatis.tag.PkTag;

public class User {
	
    private Integer userId;

    private String password;
    
    private String userName;

    private String phone;

    @PkTag
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @PkTag
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }
    
    @PkTag
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    @PkTag
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }
}