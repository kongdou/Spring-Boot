package fun.zxj.springboot.prepost;

public class BeanWayService {

	public void init(){
		System.out.println("Bean-init-Method");
	}
	
	public BeanWayService(){
		super();
		System.out.println("构造方法");
	}
	
	public void destory(){
		System.out.println("Bean-destroy-Method");
	}
}
