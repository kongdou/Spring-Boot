package fun.deepsky.service;

import org.springframework.web.bind.annotation.RestController;

import fun.deepsky.model.User;
import fun.deepsky.service.api.HelloService;

@RestController
public class RefactorHelloController implements HelloService{

	@Override
	public String hello(String name) {
		// TODO Auto-generated method stub
		return "name:"+name;
	}

	@Override
	public User hello(String name, Integer age) {
		// TODO Auto-generated method stub
		return new User(name,age);
	}

	@Override
	public String hello(User user) {
		// TODO Auto-generated method stub
		return "Refactor Test Hello "+user.getName()+", "+user.getAge();
	}

}
