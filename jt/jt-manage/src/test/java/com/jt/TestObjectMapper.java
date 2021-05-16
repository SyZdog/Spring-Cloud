package com.jt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemDesc;
import com.jt.util.ObjectMapperUtil;

public class TestObjectMapper {
	/**
	 * 该API实现将Java对象转化为JSON字符串
	 * 1.实例化工具API对象
	 * 2.利用API方法实现数据转化
	 */
	private static final ObjectMapper MAPPER =new ObjectMapper();
	@Test
	public void testObjectJSON() throws JsonProcessingException {
		//Java对象
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(100L) 
				.setItemDesc("您好Redis！！！！")
				.setCreated(new Date())
				.setUpdated(itemDesc.getCreated());
		//Java对象转化为JSON
		//对象转化为JSON时，调用的是对象的get方法
		String json = MAPPER.writeValueAsString(itemDesc);
		System.out.println(json);
		
		//能否将JSON转化为Java对象,需要提供对象类型
		//json转化为Java对象时，调用对象的set方法
		ItemDesc itemDesc2 = MAPPER.readValue(json, ItemDesc.class);
		//toString调用时，获取对象方法代码中设置的属性，继承的不显示
		System.out.println(itemDesc2.toString());
	}
	//List转化为JSON
	@SuppressWarnings("unchecked")//压制警告
	@Test
	public void testListJSON() throws JsonProcessingException {
		//Java对象
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(100L) 
				.setItemDesc("您好Redis！！！！")
				.setCreated(new Date())
				.setUpdated(itemDesc.getCreated());
		ItemDesc itemDesc2 = new ItemDesc();
		itemDesc2.setItemId(100L) 
				.setItemDesc("您好Redis！！！！")
				.setCreated(new Date())
				.setUpdated(itemDesc.getCreated());
		//1.封装Java对象
		List<ItemDesc> list = new ArrayList<>();
		list.add(itemDesc);
		list.add(itemDesc2);
		//2.将对象(list集合)转化为JSON
		String json = MAPPER.writeValueAsString(list);
		System.out.println(json);
		//3.将JSON转化为List集合
		List<ItemDesc> itemList = MAPPER.readValue(json, list.getClass());
		System.out.println(itemList);
	}
	/**
	 * 3.测试objectMapper的工具方法
	 */
	@Test
	public void testObjectMapperUtil() {
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(100L) 
				.setItemDesc("您好Redis！！！！")
				.setCreated(new Date())
				.setUpdated(itemDesc.getCreated());
		String json = ObjectMapperUtil.toJson(itemDesc);
		System.out.println(json);
		
		ItemDesc itemDesc2 = ObjectMapperUtil.toObject(json, ItemDesc.class);
		System.out.println(itemDesc2);
	}
}

