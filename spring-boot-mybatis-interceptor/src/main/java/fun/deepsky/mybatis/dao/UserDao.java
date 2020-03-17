package fun.deepsky.mybatis.dao;



import fun.deepsky.mybatis.model.User;

import java.util.List;

public interface UserDao {


    int insert(User record);



    List<User> selectUsers();
    
    
    void deleteUser(User user);
}