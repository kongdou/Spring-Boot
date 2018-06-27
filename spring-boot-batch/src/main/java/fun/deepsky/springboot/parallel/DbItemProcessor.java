package fun.deepsky.springboot.parallel;

import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;

import fun.deepsky.springboot.filetodb.domain.Person;

public class DbItemProcessor extends ValidatingItemProcessor<Vehicle> {

	@Override
	public Vehicle process(Vehicle vehicle) throws ValidationException {
		super.process(vehicle);
		System.out.println("处理数据(此处增加对数据的处理):"+vehicle.getVehicle_id());
		return vehicle;
	}
}
