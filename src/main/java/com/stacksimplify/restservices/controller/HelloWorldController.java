package com.stacksimplify.restservices.controller;


import com.stacksimplify.restservices.dto.UserDetails;
import com.stacksimplify.restservices.service.IUserService;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

//Controller
@RestController
public class HelloWorldController {

	private final IUserService userService;

	HelloWorldController(IUserService userService){
		this.userService=userService;
	}
	//@RequestMapping(method = RequestMethod.GET, path = "/helloworld")
	@GetMapping("/helloworld1")
	public String helloWorld() {
		return "Hello World1";
	}
	
	@GetMapping("/helloworld-bean")
	public UserDetails helloWorldBean() {
		return userService.returnNewUserDetails();
	}
	
}
