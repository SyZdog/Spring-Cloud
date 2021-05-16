package com.jt.aop;

import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.jt.anno.CacheFind;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.Jedis;
@Aspect//标识切面
@Component//将对象交给容器处理
public class RedisAOP {
	@Autowired(required = false)
	private Jedis jedis;
	/**
	 * 业务描述：
	 * 利用@CacheFind注解，实现Redis缓存操作
	 * 1.动态生成key 包名.类名.方法名::第一个参数
	 * 技巧：
	 * 利用该配置，可以直接为参数添加指定的注解对象，目的是为了将来获取注解的属性方便
	 */
	@Around("@annotation(cacheFind)")
	public Object around(ProceedingJoinPoint joinPoint, CacheFind cacheFind) {
		//1.动态生成key
		String key = getKey(joinPoint, cacheFind);
		//2.利用Redis查询数据
		String json = jedis.get(key);
		
		Object returnObejct = null;
		//3.判断Redis中的数据是否有值
		if(ObjectUtils.isEmpty(json)) {
			//如果JSON为null，则表示第一次查询，应该查询数据库
			try {
				returnObejct = joinPoint.proceed();//让目标方法执行，查询数据库
				//将数据获取之后，保存到Redis中。将数据转化为JSON
				String objJSON = ObjectMapperUtil.toJson(returnObejct);
				if (cacheFind.seconds() > 0) {
					//表示赋值操作，有超时时间
					jedis.setex(key, cacheFind.seconds(), objJSON);
				}else {
					jedis.set(key, objJSON);
				}
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			System.out.println("AOP查询数据库！！！！");
		}else {
			//redis中不为空，可以直接返回数据
			Class<?> targetClass = getClass(joinPoint);
			returnObejct = ObjectMapperUtil.toObject(json,targetClass);
			System.out.println("AOP查询缓存！！！！");
		}
			
		return returnObejct;
		
	}
	//动态获取返回值类型
	private Class<?> getClass(ProceedingJoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		return methodSignature.getReturnType();
	}
	
	/**
	 * 如果用户有key，则使用自己的key
	 * 如果没有，则动态生成key
	 * @param joinPoint
	 * @return
	 */
	public String getKey(ProceedingJoinPoint joinPoint, CacheFind cacheFind) {
		if(!ObjectUtils.isEmpty(cacheFind.key())) {
			//表示用户自己有key
			return cacheFind.key();
		}
		//1.获取对象的类路径 包名.类名
		String classname = joinPoint.getSignature().getDeclaringTypeName();
		//2.获取方法名
		String methodName = joinPoint.getSignature().getName();
		//3.动态获取第一个参数
		String firstArg = joinPoint.getArgs()[0].toString();
		return classname + "." + methodName + "::" + firstArg;
	}
	/**
	 * 切入点的写法：
	 * 1.bean(itemCatServiceImpl)//针对单个对象
	 * 2.within(包名.类名/*)//可以设定多个对象
	 * 3.execution(返回值的类型 包名.类名.方法名(参数列表))
	 * "execution(* com.jt.service..*.*(..))"
	 * 4.@annotation(注解的类型)
	 */
//	//bean(bean对象的ID)
//	//该切入点只对当前类生效
//	@Pointcut("execution(* com.jt.service..*.*(..))")
//	public void pointCut() {}
//	@Before("pointCut()")//定义前置通知
//	public void before() {
//		System.out.println("您好我是，前置通知！！！");
//	}
//	@AfterReturning("pointCut()")
//	public void afterReturning() {
//		System.out.println("您好我是，后置通知！！！");
//	}
//	
//	//当服务器发生异常时调用
//	@AfterThrowing(pointcut = "pointCut()", throwing = "throwable")
//	public void afterThrow(Throwable throwable) {
//		System.out.println(throwable.getMessage());
//		System.out.println("我是异常通知");
//	}
//	
//	//最终通知:无论什么时候都会执行的方法。
//	@After("pointCut()")
//	@Order(1)
//	public void after() {
//		System.out.println("我是最终通知！！！");
//	}
//	
//	/**
//	 * 环绕通知是五大通知类型中最为重要的配置
//	 * joinPoint，环绕通知，参数放在首位
//	 */
//	@Around("pointCut()")
//	public Object around(ProceedingJoinPoint joinPoint) {
//		try {
//			System.out.println("我是环绕通知一");
//			Object obj = joinPoint.proceed();//让目标方法执行
//			System.out.println("我是环绕通知二");
//			return obj;
//		} catch (Throwable e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw new RuntimeException();
//		}	
//}

}

