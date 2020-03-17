package fun.deepsky.mybatis.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import fun.deepsky.mybatis.dao.UserDao;
import fun.deepsky.mybatis.model.User;
import fun.deepsky.mybatis.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public int addUser(User user) {

        return userDao.insert(user);
    }

    @Override
    public PageInfo<User> findAllUser(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> userDomains = userDao.selectUsers();
        PageInfo<User> result = new PageInfo<User>(userDomains);
        return result;
    }

	@Override
	public void deleteUser(User user) {
		userDao.deleteUser(user);
	}
}
