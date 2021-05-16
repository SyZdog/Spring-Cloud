package com.jt.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MsgController {
	@Value("${server.port}")
	private Integer port;
	/**
	 * 编辑方法，动态返回当前服务器端口号
	 * @return
	 */
	@GetMapping("/getMsg")
	public String getMsg() {
		return "您当前访问的端口号是：" + port;
	}
}
