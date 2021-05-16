package com.jt;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Sentinel;

import com.mchange.v2.sql.filter.SynchronizedFilterDataSource;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class TestRedis2 {
	/**
	 * redis哨兵测试
	 */
	public void testSentinel() {
		
		Set<String> set =  new HashSet<>();
		set.add("192.168.126.3::26379");
		JedisSentinelPool pool = new JedisSentinelPool("mymaster", set);
		Jedis jedis = pool.getResource();
		jedis.set("CGB2009","相亲");
		System.out.println(jedis);
		jedis.close();
	}
	
}
