package fun.deepsky.mongodb.demo;


import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import fun.deepsky.mongodb.demo.entity.Employee;
import fun.deepsky.mongodb.demo.repository.EmpRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongodbDemoApplicationTests {

	@Autowired
	private EmpRepository empRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Test
	@Before
	public void deleteAll() {
		empRepository.deleteAll();
	}
	
	@Test
	public void testSave() {
		
		Employee employee = new Employee();
		employee.setName("deepsky");
		employee.setAge(32);
		empRepository.save(employee);
	}
	
	@Test
	public void query(){
		//Employee employee = empRepository.findByName("deepsky");
		//System.out.println(employee.getName()+":"+employee.getAge());
		Query query = new Query(Criteria.where("name").is("deepsky"));
		List<Employee> employees = mongoTemplate.find(query, Employee.class);
		for(Employee e: employees) {
			System.out.println(e.getName()+":"+e.getAge());
		}
		
	}
}
