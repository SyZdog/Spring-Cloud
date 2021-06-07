package com.jt.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PropertySource("classpath:/properties/person.properties")
public class PersonController {
	@Value("${person.id}")
	private Integer id;
	@Value("${person.name}")
	private String name;
	@RequestMapping("/getPerson")
	public String getPerson() {
		return id + ":" +name;
	}
}
