package com.jt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//动态的为mapper的接口创建代理对象
@MapperScan("com.jt.mapper")
public class SpringBootDemoMybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoMybatisApplication.class, args);
	}

}
