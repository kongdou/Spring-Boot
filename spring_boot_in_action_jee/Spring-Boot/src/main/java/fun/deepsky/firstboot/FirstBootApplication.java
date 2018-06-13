package fun.deepsky.firstboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class FirstBootApplication 
{
	@Value("${name}")
	private String name;
	
	@Value("${age}")
	private int age;
	
	@RequestMapping("/")
	public String greeting(){
		return "hello first boot";
	}
	
	@RequestMapping("/message")
	public String getMessage(){
		return "My Name is :"+name +" and age:"+age;
	}
    @SuppressWarnings("deprecation")
	public static void main( String[] args )
    {
    	
        SpringApplication app = new SpringApplication(FirstBootApplication.class);
        app.setShowBanner(false);//不显示Banner
        app.run(args);
        
    	//new SpringApplicationBuilder(FirstBootApplication.class).showBanner(false).run(args);
    }
}
