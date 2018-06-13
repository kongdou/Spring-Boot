package fun.deepsky.springboot.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class WebSocketApplication {


	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(WebSocketApplication.class);
		app.run(args);
	}
}
