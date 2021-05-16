package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
	/**
	 * 能否实现通用页面的跳转
	 * 思路：
	 * 1.页面请求的数据地址/page/item-list和返回值的地址相同
	 * 2.如果可以动态的获取item-list数据信息，则可以直接返回数据
	 * 实现方案：可以利用restful风格去实现操作
	 * 语法：1.参数使用/分割
	 * 2.参数使用{}包裹
	 * 3.需要在方法中添加参数，并且使用@PathVariable注解实现数据的转化
	 * @param moduleName
	 * @return
	 */
	@RequestMapping("/page/{moduleName}")
	public String module(@PathVariable String moduleName) {
		return moduleName;
	}
	/**
	 * restful风格2
	 * 需求：利用restful风格去简化用户的URL请求
	 * 		能否利用一个URL请求就可以分别实现CRUD操作
	 * 请求说明：
	 * 1.新增http://localhost:8091/user/saveUser?id&name=tomcat
	 * 2.删除http://localhost:8091/user/deleteUser?id=1
	 * 3.修改http://localhost:8091/user/updateUser?id=1
	 * 4.查询http://localhost:8091/user/selectUser?pageCurrent=1
	 * 思路：
	 * 	url请求：http://localhost:8091/user
	 * 请求方式：get（执行查询操作），
	 * 		   post（执行提交操作），put（执行修改操作），
	 * 			Delete（执行删除操作）
	 * 简化说明：简化了请求方式
	 */
	//@RequestMapping(value = "/user",method = RequestMethod.GET)
//	@GetMapping("/user")
//	public User getUserById(int id) {
//		return user;
//	}
//	//@RequestMapping(value = "/user",method = RequestMethod.DELETE)
//	@DeleteMapping("/user")
//	public int deleteById(int id) {
//		return rows;
//	}
}
