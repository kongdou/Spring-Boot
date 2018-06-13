package fun.deepsky.jpa;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
public class JpaConfiguration {
	public EntityManagerFactory entityManagerFactory() {
		return null;
	}
}
