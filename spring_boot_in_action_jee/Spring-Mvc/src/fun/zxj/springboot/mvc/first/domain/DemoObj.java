package fun.zxj.springboot.mvc.first.domain;


public class DemoObj {

	private long id;
	private String name;
	
	public DemoObj(){
		super();
	}
	public DemoObj(long id,String name){
		super();
		this.id = id;
		this.name = name;
	}

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
