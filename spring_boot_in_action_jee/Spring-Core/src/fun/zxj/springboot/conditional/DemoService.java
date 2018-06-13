package fun.zxj.springboot.conditional;

import org.springframework.stereotype.Service;

@Service
public class DemoService {

	public void outputResult(){
		System.out.println("从组合注解配置获得bean");
	}
}
