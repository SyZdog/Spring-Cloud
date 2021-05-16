package com.jt.test;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

public class testHttpClient {
	/**
	 * 1.实例化httpClient对象
	 * 2.定义请求的路径  String 
	 * 3.定义请求的对象  get/post/put/delete
	 * 4.发起请求  获取响应对象
	 * 5.判断返回值的状态 200/504/404/500/400 请求参数类型不匹配/406 返回值类型异常
	 * 6.如果返回值正确, 动态获取响应数据.
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@Test
	public void testHttpClient() throws Exception {
		//1.实例化httpClient对象
		HttpClient httClient = HttpClients.createDefault();
		//2.定义请求资源路径
		String url = "http://www.baidu.com";
		//3.定义请求的对象，请求的方式：get/post/put/delete等
		HttpGet get = new HttpGet(url);
		//4.发起请求，并且返回响应结果
		HttpResponse response = httClient.execute(get);
		if (response.getStatusLine().getStatusCode() == 200) {
			System.out.println("请求正确！！！");
			HttpEntity entity = response.getEntity();
			//将entity中携带的信息转化为字符串
			String result = EntityUtils.toString(entity);
			System.out.println("result");
		}else {
			System.out.println("请求异常！！！！");
		}
	}
} 
