package fun.zxj.springboot.mvc.first.web;
import java.util.List;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fun.zxj.springboot.mvc.first.domain.DemoObj;

@RestController
@RequestMapping("/rest") //声明为控制器，并且返回时不需要@ResponseBody
public class DemoRestController {

	@RequestMapping(value="/getJson",produces={"application/json;charset=UTF-8"})
	public DemoObj getJson(DemoObj obj){
		return new DemoObj(obj.getId()+1,obj.getName()+"yy");
	}
	
	@RequestMapping(value="/getXml",produces={"application/xml;charset=UTF-8"})
	public DemoObj getXml(DemoObj obj){
		return new DemoObj(obj.getId()+1,obj.getName()+"yy");
	}
	

}
