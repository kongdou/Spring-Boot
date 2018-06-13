package fun.zxj.springboot.mvc.first.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import fun.zxj.springboot.mvc.first.domain.DemoObj;

@Controller
public class AdviceController {

	@RequestMapping("/advice")
	public String getSomething(@ModelAttribute("msg") String msg,DemoObj obj){
		
		throw new IllegalArgumentException("参数错误,来自@ModelAttribute:"+msg);
		
	}
}
