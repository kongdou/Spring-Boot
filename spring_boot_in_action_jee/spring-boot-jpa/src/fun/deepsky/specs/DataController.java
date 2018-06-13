package fun.deepsky.specs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fun.deepsky.specs.PersonRepository;

@RestController
public class DataController {

	@Autowired
	PersonRepository personRepository;
	
	@RequestMapping("/save")
	public Person save(String name, Integer age, String address) {
		Person p = personRepository.save(new Person(null, name, age, address));
		return p;
	}

	@RequestMapping("/q1")
	public List<Person> q1(String address) {
		List<Person> people = personRepository.findByAddress(address);
		return people;
	}
	
	@RequestMapping("/q2")
	public Person q2(String name,String address){
		Person person = personRepository.findByNameAndAddress(name, address);
		return person;
	}
	
	@RequestMapping("/q3")
	public Person q3(String name,String address){
		Person p = personRepository.withNameAndAddressQuery(name, address);
		return p;
	}
	
	@RequestMapping("/q4")
	public Person q4(String name,String address){
		Person p = personRepository.withNameAndAddressNameQuery(name, address);
		return p;
	}
	
	@RequestMapping("/sort")
	public List<Person> sort(){
		List<Person> people = personRepository.findAll(new Sort(Direction.ASC,"age"));
		return people;
	}
	
	@RequestMapping("/page")
	public Page<Person> page(){
		Page<Person> pagePerson = personRepository.findAll(new PageRequest(1,2));
		return pagePerson;
	}
	
	@RequestMapping("/auto")
	public Page<Person> auto(Person person){
		Page<Person> pagePerson = personRepository.findByAuto(person,new PageRequest(0,10));
		return pagePerson;
	}
}
