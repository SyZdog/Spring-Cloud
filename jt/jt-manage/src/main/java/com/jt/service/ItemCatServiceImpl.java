package com.jt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.jt.anno.CacheFind;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;

import redis.clients.jedis.Jedis;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private ItemCatMapper itemCatMapper;
	@Autowired
	private Jedis jedis;//让spring容器自动注入jedis对象

	@Override
	public String findItemCatName(Long itemCatId) {
		//获取商品信息
		ItemCat itemCat = itemCatMapper.selectById(itemCatId);
		return itemCat.getName();//获取商品名称并返回
	}
	/**
	 * 从数据库中查出来的结果类型是ItemCat--pojo
	 * 但是我们需要的是EasyUITree--vo
	 * 所以我们需要进行转换：把ItemCat对象转换成EasyUITree
	 */
	@Override
	@CacheFind
	public List<EasyUITree> findEasyUITreeByParentId(Long parentId) {
		//1.查询数据库的数据来动态的获取pojo数据
		List<ItemCat> itemCatList = getItemCatList(parentId);
		//2.将pojo对象转换成vo对象
		List<EasyUITree> treeList = new ArrayList<>(itemCatList.size());
		for (ItemCat itemCat : itemCatList) {
			String state = itemCat.getIsParent()?"closed":"open";
			EasyUITree easyUITree = new EasyUITree();
			easyUITree.setId(itemCat.getId()).setText(itemCat.getName()).setState(state);
			treeList.add(easyUITree);
		}
		return treeList;
	}
	private List<ItemCat> getItemCatList(Long parentId) {
		// TODO Auto-generated method stub
		QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("parent_id", parentId);
		return itemCatMapper.selectList(queryWrapper);
	}
	
	/**
	 * 业务分析：
	 * 商品分类的查询根据父级商品的分类，查询子级的商品分类信息
	 * SQL：select * from tb_item_cat where parent_id = 0;
	 * 根据SQL返回值List<itemCat>集合信息
	 * 页面展现时，需要将List<itemCat>转化为List<EasyUITree>对象
	 * 
	 * 缓存数据需求：
	 * 1.Redis保存的数据结构key:value结果。保证key唯一性：包名.类名.方法名::第一个参数
	 * 2.Java对象转化为string类型：Java对象~JSON~字符串
	 * 
	 * 缓存业务实现：
	 * 1.准备查询Redis中的key
	 * 2.根据key查询Redis缓存
	 * 3.没有数据：第一次到数据库中去查询，查询后将该数据先保存到Redis中
	 * 4.有数据：证明不是第一次查询，可以通过Redis缓存去直接获取数据，返回给用户
	 * 大大的节省了数据库查询时间
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EasyUITree> findItemCatListByCache(Long parentId) {
		Long startTime = System.currentTimeMillis();
		//1.准备动态变化的key
		String key = "com.jt.service.ItemCatServiceImpl.findItemCatListByCache::" + parentId;
		String json = jedis.get(key);
		List<EasyUITree> list = new ArrayList<>();
		//2.判断当前数据中是否为null
		if (ObjectUtils.isEmpty(json)) {
			//数据为null，查询数据库
			list = findEasyUITreeByParentId(parentId);//Java对象
			Long endTime = System.currentTimeMillis();
			System.out.println("查询数据库的时间：" + (endTime-startTime) + "毫秒");
			String jsonData = ObjectMapperUtil.toJson(list);
			jedis.set(key, jsonData);
		}else {
			//3.缓存中有数据，数据转化为对象
			list = ObjectMapperUtil.toObject(json, list.getClass());
			Long endTime = System.currentTimeMillis();
			System.out.println("查询缓存时间：" + (endTime-startTime) + "毫秒");
		}
		return list;
	}
	
}
