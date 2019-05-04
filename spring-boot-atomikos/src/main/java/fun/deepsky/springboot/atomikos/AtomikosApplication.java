package fun.deepsky.springboot.atomikos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import fun.deepsky.springboot.atomikos.config.OneDbProperties;
import fun.deepsky.springboot.atomikos.config.TwoDbProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = { OneDbProperties.class, TwoDbProperties.class })
public class AtomikosApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtomikosApplication.class, args);
	}
}
