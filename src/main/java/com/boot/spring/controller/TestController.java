package com.boot.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {
	
	@GetMapping(path="/test")
	public String greet() {
		return "Congratulations... Your SpringBoot application is running successfully!";
	}

}
