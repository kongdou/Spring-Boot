package fun.deepsky.springboot.atomikos.service;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.PageInfo;

import fun.deepsky.springboot.atomikos.domain.User;

public interface UserService {

	int addUser(User user);
	 
    PageInfo<User> findAllUser(int pageNum, int pageSize);
}
