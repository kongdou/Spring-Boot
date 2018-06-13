package fun.zxj.springboot.mvc.first.web;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SseController {

	@RequestMapping(value="/push",produces={"text/event-stream"})
	public @ResponseBody String push(){
		Random random = new Random();
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "data:Test 1,2,3"+random.nextInt() +"\n\n";
	}
}
