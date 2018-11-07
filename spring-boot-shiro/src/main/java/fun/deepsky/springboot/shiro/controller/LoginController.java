package fun.deepsky.springboot.shiro.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

	@RequestMapping({"/","/index"})
	public String index() {
		return "/index";
	}
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request,Map<String,Object> map) throws Exception{
		System.out.println("login ... ");
		String exception = (String) request.getAttribute("shiroLoginFailure");
		System.out.println("Exception :"+exception);
		String msg = "";
		if(exception != null) {
			if(UnknownAccountException.class.getName().equals(exception)){
				System.out.println("UnknownAccountException --> 账号不存在");
				msg = "UnknownAccountException --> 账号不存在";
			}else if(IncorrectCredentialsException.class.getName().equals(exception)) {
				System.out.println("IncorrectCredentialsException --> 密码不正确");
				msg = "IncorrectCredentialsException --> 密码不正确";
			}else if("kaptchaValidateFailed".equals(exception)) {
				System.out.println("kaptchaValidateFailed --> 验证码错误");
				msg = "kaptchaValidateFailed --> 验证码错误";
			}else {
				msg = "else >> "+exception;
				System.out.println("else -- >" + exception);
			}
		}
		map.put("msg", msg);
		return "/login";
	}
	
	@RequestMapping("/403")
	public String unauthorizedRole() {
		System.out.println("------没有权限------");
		return "/403";
	}
	
}
