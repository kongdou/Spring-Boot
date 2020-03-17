package fun.deepsky.mybatis.controller;

import com.github.pagehelper.PageHelper;

import fun.deepsky.mybatis.model.User;
import fun.deepsky.mybatis.model.UtiOperateHistory;
import fun.deepsky.mybatis.service.user.UserService;
import fun.deepsky.mybatis.service.user.UtiOperateHistoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UtiOperateHistoryService utiOperateHistoryService;
    
    @ResponseBody
    @PostMapping("/add")
    public int addUser(User user){
        return userService.addUser(user);
    }

    @ResponseBody
    @GetMapping("/all")
    public Object findAllUser(
            @RequestParam(name = "pageNum", required = false, defaultValue = "1")
                    int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                    int pageSize){
        return userService.findAllUser(pageNum,pageSize);
    }
    @ResponseBody
    @DeleteMapping("/delete")
    public Object deleteUser(
            @RequestParam(name = "userName") String userName){
    		User user =new User();
    		user.setUserName("aaaa");
    		user.setPassword("bbbb");
    		user.setPhone("cccc");
    		user.setUserId(555);
    		userService.deleteUser(user);
        return "success";
    }
    @ResponseBody
    @GetMapping("/allh")
    public Object findAllHistories(
            @RequestParam(name = "pageNum", required = false, defaultValue = "1")
                    int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                    int pageSize){
        return utiOperateHistoryService.findAllUtiOperateHistories(pageNum, pageSize);
    }
    
    @ResponseBody
    @PostMapping("/addh")
    public Object deleteH(){
    	UtiOperateHistory h =new UtiOperateHistory();
    		h.setOperateType(3);
    		h.setEntity("test");
    		h.setEntityKey1("key1");
    		h.setEntityKey2("key2");
    		h.setEntityKey3("key3");
    		h.setEntityKey4("key4");
    		h.setEntityKey5("key5");
    		h.setEntityKey6("key6");
    		h.setEntityKey7("key7");
    		h.setEntityKey8("key8");
    		h.setFlag("flag");
    		utiOperateHistoryService.addUtiOperateHistory(h);
        return "";
    }
}
