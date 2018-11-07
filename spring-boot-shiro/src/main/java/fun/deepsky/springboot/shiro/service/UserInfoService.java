package fun.deepsky.springboot.shiro.service;

import fun.deepsky.springboot.shiro.entity.UserInfo;

public interface UserInfoService {

	 public UserInfo findByUsername(String username); 
}
