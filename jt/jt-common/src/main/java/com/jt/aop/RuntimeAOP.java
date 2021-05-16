package com.jt.aop;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jt.vo.SysResult;


@RestControllerAdvice//全局异常的处理机制+Json返回

public class RuntimeAOP {
	//当前的aop所拦截的异常类型（运行时异常）
	@ExceptionHandler(RuntimeException.class)
	public SysResult runtime(Exception exception) {
		exception.printStackTrace();//输出异常信息
		return SysResult.fail();
	}
}
