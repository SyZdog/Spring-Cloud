package com.jt.util;
/**
 * 该工具方法，实现对象与json数据的转换
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {
	private static final ObjectMapper mapper = new ObjectMapper();
	/**
	 * 1.将对象转化成json
	 */
	public static String toJson(Object obj) {
		try {
			String json = mapper.writeValueAsString(obj);
			return json;//如果没有错，就返回JSON串
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	} 
	/**
	 * 2.将Json串转化为对象
	 * 需求：要求自动的转化数据类型，用户传什么类型，就实例化什么对象
	 */
	public static <T> T toObject(String json, Class<T> targetClass) {
		try {
			 T t = mapper.readValue(json, targetClass);
			 return t;
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		} 
}
