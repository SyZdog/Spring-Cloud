package com.jt;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.params.SetParams;
@SpringBootTest
public class TestRedis {
	@Autowired
	private Jedis jedis;
	/**
	 * redis链接的入门案例
	 * 需求：通过Java程序远程链接Redis
	 * 1.要求Linux系统关闭防火墙
	 * 2.Redis的配置文件中的ip绑定注释掉
	 * 3.Redis的配置文件的保护模式关闭
	 */
	@Test
	public void testStringSet(){
		String host = "192.168.126.3";
		int port = 6379;
		//创建Jedis对象
		Jedis jedis = new Jedis(host, port);
		jedis.set("redis","redis缓存实现");
		System.out.println(jedis.get("redis"));
	}

	/**
	 * 2.需求:如果当前key已经存在。则不允许赋值（覆盖）
	 */
	
/*	@BeforeEach
	public void init() {
		String host = "192.168.126.3";
		int port = 6379;
		//创建Jedis对象
		jedis = new Jedis(host, port);
	}*/
	
	//1.默认规则：如果采用set方式为Redis赋值，则之后的操作会覆盖之前的记录
	@Test
	public void testStringNX() {
		jedis.set("redis","redis缓存实现");
		//jedis.set("redis","您好Redis！！！");
		jedis.setnx("redis", "测试nx");
		System.out.println(jedis.get("redis"));
	}
	
	//3.需求：为数据添加超时时间
	@Test
	public void testStringEX() throws InterruptedException {
		jedis.set("a", "aaa");
		//如果程序执行到这一步的时候突然宕机了，那么后面的代码都不会执行，该数据则会永不超时
		jedis.expire("a", 10);
		Thread.sleep(3000);
		Long live = jedis.ttl("a");
		System.out.println("当前数据还能存活"+ live + "秒");
		jedis.persist("a");//撤销失效时间
		
		//能否将以上的操作合成一步完成
		jedis.setex("aa", 10, "您好Redis");//保证了操作的原子性
		Thread.sleep(3000);
		Long live1 = jedis.ttl("aa");
		System.out.println("当前数据还能存活"+ live1 + "秒");
		System.out.println(jedis.get("aa"));
	}
	/**
	 * 4.需求：要求set不允许覆盖之前的值，并添加超时时间
	 * @param params NX|XX, 
	 * 		NX -- Only set the key if it does not already exist. 
	 * 		XX -- Only set the key if it already exist. 
	 * 	    EX = seconds; 
	 * 		PX = milliseconds;
	 */
	@Test
	public void testStringSet2() {
		SetParams params = new SetParams();
		params.nx().ex(30);
		String result = jedis.set("aa","123456",params);
		System.out.println(result);
	}
	/**
	 * 利用hash保存对象
	 */
	@Test
	public void testHash() {
		jedis.hset("person","id","11");
		jedis.hset("person","name","xiaoming");
		//获取对象的所有属性和值
		Map<String, String> map = jedis.hgetAll("person");
		System.out.println(map);
	}
	/**
	 *测试Redis中的list集合
	 *抢红包/秒杀业务操作，可能会使用list集合信息
	 */
	@Test
	public void testList() {
		jedis.lpush("list", "1","2","3","4","5");
		//当作队列使用
		String value = jedis.rpop("list");
		System.out.println(value);
	}
	@Test
	public void testSet() {
		jedis.sadd("set", "1","2","3");
		Long num = jedis.scard("set");//获取集合数量
		System.out.println("获取元素数据："+ num);
		Set<String> sets = jedis.smembers("set");
		System.out.println(sets);
	}
	/**
	 * 测试Redis的事务控制
	 */
	@Test
	public void testTx() {
		Transaction transaction = jedis.multi();//事务的开启
		try {
			transaction.set("m","mm");
			transaction.set("n","nn");
			transaction.set("e","ee");
			//int a = 1/0;
			transaction.exec();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			transaction.discard();//如果出现异常就回滚
		}
	} 
}
