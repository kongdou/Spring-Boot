package fun.deepsky.springboot.shiro.config;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import fun.deepsky.springboot.shiro.entity.SysPermission;
import fun.deepsky.springboot.shiro.entity.SysRole;
import fun.deepsky.springboot.shiro.entity.UserInfo;
import fun.deepsky.springboot.shiro.service.UserInfoService;

public class MyShiroRealm extends AuthorizingRealm {

	@Resource
	private UserInfoService userInfoService;

	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		UserInfo userInfo = (UserInfo) principals.getPrimaryPrincipal();
		for(SysRole role:userInfo.getRoleList()) {
			 authorizationInfo.addRole(role.getRole());
			 for(SysPermission premission : role.getPermissions()) {
				 authorizationInfo.addStringPermission(premission.getPermission());
			 }
		}
		return authorizationInfo;
	}

	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 获得输入的账号
		String username = (String) token.getPrincipal();
		System.out.println(token.getCredentials());
		//
		UserInfo userInfo = userInfoService.findByUsername(username);
		if(userInfo == null) {
			return null;
		}
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userInfo,userInfo.getPassword(),ByteSource.Util.bytes(userInfo.getCredentialsSalt()),getName());
		return authenticationInfo;
	}

}
