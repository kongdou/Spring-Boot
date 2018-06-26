package fun.deepsky.springboot.dbtodb;

import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;

import fun.deepsky.springboot.filetodb.domain.Person;

public class DbItemProcessor extends ValidatingItemProcessor<Person> {

	@Override
	public Person process(Person item) throws ValidationException {
		super.process(item);
		System.out.println("处理数据(此处增加对数据的处理):"+item.getName());
		if (item.getNation().equals("汉族")) {
			item.setNation("01");
		} else if (item.getNation().equals("回族")) {
			item.setNation("02");
		} else {
			item.setNation("99");
		}
		item.setAge(item.getAge()+1);
		return item;
	}
}
