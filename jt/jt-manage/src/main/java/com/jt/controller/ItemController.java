package com.jt.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUITable;
import com.jt.vo.SysResult;


@RequestMapping("/item")
@RestController
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	/**
	 * http://localhost:8091/item/query?page=1&rows=20
	 * 请求参数：page页数（页码值），rows行数
	 */
	@GetMapping("/query")
	public EasyUITable findItemByPage(Integer page, Integer rows) {
		return itemService.findItemByPage(page,rows) ;
	}
	/**
	 * url:/item/save
	 *参数：表单中的数据
	 *返回值：SysResult
	 *保存商品信息
	 *注意事项：如果采用多个参数接收页面数据，则需要注意不要出现重名的属性
	 */
	@PostMapping("/save")
	public SysResult saveItem(Item item, ItemDesc itemDesc) {
		itemService.saveItem(item, itemDesc);
		return SysResult.success();
//		try {
//			itemService.saveItem(item);
//			return SysResult.success();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			return SysResult.fail();
//		}
		
	}
	/**
	 * 页面回显的调用流程
	 * 1.当修改按钮点击时，开始执行工具栏中的操作
	 * 2.动态的获取用户选中的数据，之后转化为字符串中间使用“,”进行分隔
	 * 3.之后根据ids判断是否没选，或者是否多选
	 * 4.根据id选择器，选中窗口div进行弹出框操作
	 * 5.当弹出框操作完成之后，div发起herf请求，动态获取编辑页面信息 href:'/page/item-edit
	 * 6.当窗口弹出后，开始动态的实现数据回显功能
	 * 7.同时发起Ajax请求，动态获取商品详情数据信息
	 * 
	 * url地址：/item/query/item/desc/1474392165
	 * 参数：1474392165
	 * 返回值结果：Sysresult对象
	 */
	@GetMapping("/query/item/desc/{itemId}")
	public SysResult findItemDescById(@PathVariable Long itemId) {
		//1.根据id查询商品详情信息
		ItemDesc itemDesc = itemService.findItemDescById(itemId);
		//2.itemDesc中的itemDesc属性是HTML的代码片段
		return SysResult.success(itemDesc);
	}
	/**
	 * 实现商品信息的修改
	 * URL：/item/update
	 * 参数：item数据和itemDesc数据
	 * 返回值：SysResult对象
	 */
	@PostMapping("/update")
	public SysResult updateItem(Item item, ItemDesc itemDesc) {
		itemService.updateItem(item, itemDesc);
		return SysResult.success();
	}
	/**
	 * 需求：商品删除操作
	 * url：/item/delete
	 * 请求参数：ids=xxxxx,xxxxx
	 * 返回值：SysResult对象 
	 */
	@PostMapping("/delete")
	public SysResult deleteItem(Long... ids) {
		itemService.deleteItems(ids);
		return SysResult.success();
		
	}
	/**
	 * 业务需求：完成商品的上架/下架
	 * URL:上架：/item/reshelf status=1
	 *     下架：/item/instock status=2
	 * 参数：ids
	 * 返回值结果：SysResult对象
	 */
	//上架
	@PostMapping("/reshelf")
	public SysResult reshelf(Long...ids) {
		int status = 1;//表示当前为上架状态
		itemService.updateStatus(ids,status);
		return SysResult.success();
	}
	//上架
	@PostMapping("/instock")
	public SysResult instock(Long...ids) {
		int status = 2;//表示当前为下架状态
		itemService.updateStatus(ids,status);
		return SysResult.success();
	}
}
