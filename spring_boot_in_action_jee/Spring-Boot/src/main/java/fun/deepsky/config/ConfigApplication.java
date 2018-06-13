package fun.deepsky.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ConfigApplication {

	@Autowired
	private ProjectSetting ps;
	
	@RequestMapping("/index")
	public String index(){
		return "Project name："+ps.getName()+"  year:"+ps.getYear()+"  persons:"+ps.getPersons();
	}
	
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ConfigApplication.class);
		app.setShowBanner(false);
		app.run(args);
	}
	
}
