package fun.deepsky.springboot.atomikos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import fun.deepsky.springboot.atomikos.domain.Person;
import fun.deepsky.springboot.atomikos.mapper.twodb.PersonDao;
import fun.deepsky.springboot.atomikos.service.PersonService;

@Service(value="personService")
public class PersonServiceImpl implements PersonService{

	@Autowired
	private PersonDao personDao;
	
	@Override
	public int addPerson(Person person) {
		return personDao.insert(person);
	}

	@Override
	public PageInfo<Person> findAllPerson(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Person> persons = personDao.selectPersons();
		PageInfo result = new PageInfo(persons);
		return result;
	}

}
