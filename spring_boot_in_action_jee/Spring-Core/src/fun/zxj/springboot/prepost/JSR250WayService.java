package fun.zxj.springboot.prepost;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class JSR250WayService {

	@PostConstruct
	public void init(){
		System.out.println("JSR250-init-Method");
	}
	
	
	public JSR250WayService(){
		super();
		System.out.println("JSR构造方法");
	}

	@PreDestroy
	public void destory(){
		System.out.println("JSR250-destory-Method");
	}
	
}
