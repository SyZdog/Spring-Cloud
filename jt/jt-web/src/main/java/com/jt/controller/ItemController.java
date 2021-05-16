package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.jt.pojo.Item;
import com.jt.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	/**
	 * 请求:http://www.jt.com/items/562379.html
	 * 请求分析: 通过jt-web服务器 访问后台商品信息,其中562379表示商品的ID号
	 *		  根据商品id号,之后查询后台数据库,获取商品信息之后进行数据的页面展现
	 * 参数接收: 需要动态的接收url中的参数 利用restFul风格实现数据的动态获取
	 *页面展现数据要求:
	 *		<h1>${item.title}</h1>
			<strong>${item.sellPoint}</strong>
			需要查询后台数据之后.将数据保存到域中,之后利用el表达式完成数据的动态取值.
	 */
	@GetMapping("/{itemId}")
	public String findItemById(@PathVariable Long itemId) {
		Item item = itemService.findItemById(itemId);
		return "item";
		
	}
}
