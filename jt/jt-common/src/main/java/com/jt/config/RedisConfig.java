package com.jt.config;


import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;

@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {
	@Value("${redis.host}")
	private String node;//ip:port
	//手动封装Redis对象
	@Bean
	public Jedis jedis() {
		String[] nodeArray = node.split(":");
		String host =nodeArray[0];
		int port = Integer.parseInt(nodeArray[1]);
		return new Jedis(host,port);
	}
//	
//	@Value("${redis.sentinel}")
//	private String sentinel;
//	/**
//	 * 核心步骤
//	 * 1.动态创建Redis哨兵的池对象
//	 * 2.动态从池中获取jedis对象，实现数据的存取
//	 */
//	@Bean
//	public JedisSentinelPool jedisSentinelPool() {
//		Set<String> sentinels = new HashSet<>();
//		sentinels.add(sentinel);
//		return new JedisSentinelPool("mymaster", sentinels);
//		
//	}
//	/**
//	 *配置Redis集群
//	 */
//	@Value("${redis.cluster}")
//	private String nodes;//node,node,node,node
//	@Bean
//	public JedisCluster jedisCluster() {
//		Set<HostAndPort> setNodes = new HashSet<>();
//		String[] arrayNodes = nodes.split(",");//node=host:port
//		for (String Node : arrayNodes) {//host:port
//			String host = Node.split(":")[0];
//			int port = Integer.parseInt(Node.split(":")[1]);
//			HostAndPort hostAndPort = new HostAndPort(host, port);//封装
//			setNodes.add(hostAndPort);
//		}
//		return new JedisCluster(setNodes);
//	}
//	//如果@Bean注解中添加参数，该参数由Spring容器管理则可以动态赋值
//	@Bean
//	public Jedis jedis1(JedisSentinelPool jedisSentinelPool) {
//		return jedisSentinelPool.getResource();
//	}
}
