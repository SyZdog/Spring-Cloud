package com.jt.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController//@Controller+@ResponseBody
@ConfigurationProperties(prefix = "jdbc")//批量赋值
public class JDBCController {
	@Value("${jdbc.username}")
	private String username;
	@Value("${jdbc.password}")
	//提供get和set方法
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@RequestMapping("/getMsg")
	public String getMsg() {
		return username+":"+password;
		
	}
}
