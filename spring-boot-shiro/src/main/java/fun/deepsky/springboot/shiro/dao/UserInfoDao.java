package fun.deepsky.springboot.shiro.dao;

import org.springframework.data.repository.CrudRepository;

import fun.deepsky.springboot.shiro.entity.UserInfo;

public interface UserInfoDao extends CrudRepository<UserInfo, String>{

	public UserInfo getByUserName(String username);
}
