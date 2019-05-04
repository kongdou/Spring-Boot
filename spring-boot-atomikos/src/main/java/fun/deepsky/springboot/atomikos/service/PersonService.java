package fun.deepsky.springboot.atomikos.service;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.PageInfo;

import fun.deepsky.springboot.atomikos.domain.Person;

public interface PersonService {

	int addPerson(Person user);
	 
    PageInfo<Person> findAllPerson(int pageNum, int pageSize);
}
