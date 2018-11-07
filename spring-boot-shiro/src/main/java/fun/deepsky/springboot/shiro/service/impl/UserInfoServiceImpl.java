package fun.deepsky.springboot.shiro.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import fun.deepsky.springboot.shiro.dao.UserInfoDao;
import fun.deepsky.springboot.shiro.entity.UserInfo;
import fun.deepsky.springboot.shiro.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService{

    @Resource
    private UserInfoDao userInfoDao;
    
	@Override
	public UserInfo findByUsername(String username) {
		UserInfo userInfo = userInfoDao.getByUsername(username);
		return userInfo;
	}

}
