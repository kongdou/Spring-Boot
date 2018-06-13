package fun.deepsky.service.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import fun.deepsky.service.api.HelloApplication;

@EnableDiscoveryClient
@SpringBootApplication
public class HelloApplication {
	public static void main(String[] args) {
		new SpringApplication(HelloApplication.class).run(args);
	}
}
