package fun.deepsky.specs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = CustomRepositoryFactoryBean.class)
public class DataApplication {

	@Autowired
	PersonRepository personRepository;
	
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(DataApplication.class); 
		app.run(args);
	}
}
