package fun.deepsky.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class HelloServiceApplication {

	@Autowired
	private HelloService helloService;
	
	@RequestMapping("/helloservice")
	public String index(){
		return helloService.sayHello();
	}
	
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(HelloServiceApplication.class);
		app.setShowBanner(false);
		app.run(args);
	}
}
