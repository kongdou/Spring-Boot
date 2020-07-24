package fun.deepsky.mongodb.demo.entity;

import org.bson.types.ObjectId;

import lombok.Data;

@Data
public class Employee {

	private ObjectId id;
	private String name;
	private Integer age;
	
}
