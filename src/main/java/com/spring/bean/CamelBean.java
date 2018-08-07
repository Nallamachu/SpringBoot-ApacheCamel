package com.spring.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("camelBean")
public class CamelBean {

	/*Reading greet value from application.properties*/
	@Value("${greet}")
	private String greet;
	
	public String greet() {
		return greet;
	}
}
