package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;

@RestController
@RequestMapping("/item/cat")
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	/**
	 * 根据id查询商品分类名称
	 *请求路径URL： /item/cat/queryItemName
	 *请求参数：data:{itemCatId:val}
	 *结果: 商品分类名称
	 */
	@GetMapping("/queryItemName")
	public String queryItemCatNameById(Long itemCatId) {
		return itemCatService.findItemCatName(itemCatId);
	}
	/**
	 * 实现商品分类列表页面的呈现
	 * URL：/item/cat/List
	 * 参数：parentId
	 * 返回值的结果：List<EasyUITree>
	 * 异步数控件：
	 * 	当点击父级商品节点时，会发起新的URL请求，并且参数为id=xxxx.根据父级查询子级信息
	 * 需求：如果必须接收该参数，但是又不想随意修改参数名称，则使用
	 * @RequestParam 注解
	 */
	@PostMapping("/list")
	public List<EasyUITree> findEasyUITreeByParentId(@RequestParam(value = "id",defaultValue = "0")Long parentId) {
		//只查询一级节点
		//Long parentId = 0L;
		return itemCatService.findEasyUITreeByParentId(parentId);
		//return itemCatService.findItemCatListByCache(parentId);
	}
}
