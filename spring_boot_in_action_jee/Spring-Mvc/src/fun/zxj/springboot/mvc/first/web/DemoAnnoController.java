package fun.zxj.springboot.mvc.first.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import fun.zxj.springboot.mvc.first.domain.DemoObj;

@Controller//控制器
@RequestMapping("/anno")//访问路径/anno
public class DemoAnnoController {

	//未标明路径，使用类级别路径/anno
	@RequestMapping(produces="text/plain;charset=UTF-8")
	public @ResponseBody String index(HttpServletRequest request){ //request作为请求参数
		return "url:"+request.getRequestURL()+"can access...";
	}
	
	@RequestMapping(value="/pathvar/{str}",produces="text/plain;charset=UTF-8")//接受路径参数
	public @ResponseBody String demoPathVar(@PathVariable String str,HttpServletRequest request){
		return "url:"+request.getRequestURL()+"can access ,str:"+str; 
	}
	
	//request获取请求参数:路径/anno/requestParam?id=1
	@RequestMapping(value="/requestParam",produces="text/plain;charset=UTF-8")
	public @ResponseBody String passRequestParam(long id,HttpServletRequest request){
		return "url:"+request.getRequestURL()+"can acccess,id :"+id;
	}
	
	//request参数对象参数:路径/anno/obj?id=1&name=deepsky
	@RequestMapping(value="/obj" ,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String passObj(DemoObj obj,HttpServletRequest request){
		return "url:"+request.getRequestURL()+" can access,obj.id:"+obj.getId()+" obj.name "+obj.getName();
		
	}
	//不同路径映射到相同的方法上
	@RequestMapping(value={"/name1","/name2"},produces="text/plain;charset=UTF-8")
	public @ResponseBody String remove(HttpServletRequest request){
		return "url:"+request.getRequestURL()+" can access";
	}

	
}
