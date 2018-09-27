package com.hytx.jcxfd.controller;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.github.pagehelper.PageInfo;
import com.hytx.jcxfd.config.RabbitConfig;
import com.hytx.jcxfd.model.Attach;
import com.hytx.jcxfd.model.UserDomain;
import com.hytx.jcxfd.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
    private UserService userService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private final RabbitTemplate rabbitTemplate;
	
	@Autowired
    public  UserController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
	
	@GetMapping("/rabbitMq")
    public void defaultMessage() {
		UserDomain user = new UserDomain();
		user.setPassword("111");
		user.setPhone("531");
		user.setUserName("报存用户信息想你想你细信息！");
        this.rabbitTemplate.convertAndSend(RabbitConfig.DEFAULT_BOOK_QUEUE, user);
        this.rabbitTemplate.convertAndSend(RabbitConfig.MANUAL_BOOK_QUEUE, user);
    }
	
    @GetMapping("/add")
    public int addUser(){
    	System.out.println("保存用户！");
    	UserDomain u = new UserDomain();
    	u.setPassword("111");
    	u.setPhone("531");
    	u.setUserName("xiaowei");
        return userService.addUser(u);
    }

   @GetMapping("/all")
   public PageInfo<UserDomain> findAllUser(
            @RequestParam(name = "pageNum", required = false, defaultValue = "1")
                    int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "3")
                    int pageSize){
	   PageInfo<UserDomain> findAllUser = userService.findAllUser(pageNum,pageSize);
	   System.out.println(findAllUser);
        return findAllUser;
    
   }
    
    @GetMapping("selectAll")
    public List<Attach> SelectAll(){
    	
    	return this.restTemplate.getForObject("http://localhost:8888/attach/all", List.class);
    }
}
