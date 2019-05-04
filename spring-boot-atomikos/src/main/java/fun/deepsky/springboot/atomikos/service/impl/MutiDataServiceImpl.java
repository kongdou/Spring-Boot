package fun.deepsky.springboot.atomikos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fun.deepsky.springboot.atomikos.domain.Person;
import fun.deepsky.springboot.atomikos.domain.User;
import fun.deepsky.springboot.atomikos.service.MutiDataService;
import fun.deepsky.springboot.atomikos.service.PersonService;
import fun.deepsky.springboot.atomikos.service.UserService;

@Service(value="muliDataService")
public class MutiDataServiceImpl implements MutiDataService{

	@Autowired
	private PersonService personService;

	@Autowired
	private UserService userService;
	
	
	@Override
	@Transactional(value="transactionManager")
	public void insert() {
		User user = new User();
		user.setUserName("deepsky");
		user.setPassword("1234567890");
		user.setPhone("15810987192");
		userService.addUser(user);
		
		Person person = new Person();
		person.setUserName("kongdou");
		person.setUserAge(30);
		personService.addPerson(person);
	}

	
}
