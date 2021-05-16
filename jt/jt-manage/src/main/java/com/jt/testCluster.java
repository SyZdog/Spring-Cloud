package com.jt;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class testCluster {
	/**
	 * 测试Redis集群入门案例
	 */
	@Test
	public void name() {
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.126.3",7000));
		nodes.add(new HostAndPort("192.168.126.3",7001));
		nodes.add(new HostAndPort("192.168.126.3",7002));
		nodes.add(new HostAndPort("192.168.126.3",7003));
		nodes.add(new HostAndPort("192.168.126.3",7004));
		nodes.add(new HostAndPort("192.168.126.3",7005));
		JedisCluster jedisCluster = new JedisCluster(nodes);
		jedisCluster.set("hello redisCluster", "测试Redis集群搭建成功");
		System.out.println(jedisCluster.get("hello redisCluster"));
	}
}
