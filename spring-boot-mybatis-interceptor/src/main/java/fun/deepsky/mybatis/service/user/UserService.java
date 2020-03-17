package fun.deepsky.mybatis.service.user;

import com.github.pagehelper.PageInfo;

import fun.deepsky.mybatis.model.User;

import java.util.List;


public interface UserService {

    int addUser(User user);

    PageInfo<User> findAllUser(int pageNum, int pageSize);
    
    void deleteUser(User user);
}
