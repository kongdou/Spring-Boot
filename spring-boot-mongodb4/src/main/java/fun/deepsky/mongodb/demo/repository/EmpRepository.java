package fun.deepsky.mongodb.demo.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

import fun.deepsky.mongodb.demo.entity.Employee;

public interface EmpRepository extends MongoRepository<Employee, Long> {
	
	Employee findByName(String name);

	List<Employee> findByAgeGreaterThan(int age);
}
