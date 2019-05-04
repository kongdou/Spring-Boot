package fun.deepsky.springboot.atomikos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import fun.deepsky.springboot.atomikos.domain.User;
import fun.deepsky.springboot.atomikos.mapper.onedb.UserDao;
import fun.deepsky.springboot.atomikos.service.UserService;

@Service(value="userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public int addUser(User user) {
		return userDao.insert(user);
	}

	@Override
	public PageInfo<User> findAllUser(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<User> users = userDao.selectUsers();
		PageInfo result = new PageInfo(users);
		return result;
	}

}
