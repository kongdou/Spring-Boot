package fun.deepsky.springboot.atomikos.mapper.twodb;

import java.util.List;

import org.springframework.stereotype.Repository;

import fun.deepsky.springboot.atomikos.domain.Person;

@Repository
public interface PersonDao {

	int insert(Person record);
	
    List<Person> selectPersons();
}
