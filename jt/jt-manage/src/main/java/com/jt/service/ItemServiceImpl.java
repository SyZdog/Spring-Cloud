package com.jt.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUITable;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;
	/**
	 * 两张表，同时入库
	 * 问题：itemDesc的入库需要依赖于item主键信息，item的主键入库之后才有值
	 * 思路1：先item对象完成入库，之后根据item的属性查询数据库获取itemId（效率低）
	 * 思路2：能否在insert之后，将数据库的信息自动的封装到item对象中
	 *解决思路：利用MP实现入库操作，之后动态回显item的信息
	 */
	@Override
	@Transactional//事务控制
	public void saveItem(Item item,ItemDesc itemDesc) {
		//商品表
		//item对象主键自增，当数据入库之后查询数据库中的id的最大值+1实现入库操作
		item.setStatus(1).setCreated(new Date())
			.setUpdated(item.getCreated());
		//MP:当数据入库之后,会自动的回显主键信息.
		itemMapper.insert(item);
		
		//商品描述表
		//问题: item是主键自增.只有入库之后才有主键.但是对象中没有主键信息.
		itemDesc.setItemId(item.getId())//获取itemId的值保证数据一致
				.setCreated(item.getCreated())
				.setUpdated(item.getUpdated());
		itemDescMapper.insert(itemDesc);
	}
	
	/**
	 * 利用MP的方式实现分页查询
	 */
	@Override
	public EasyUITable findItemByPage(Integer page, Integer rows) {
		//1.定义IPage定义分页的相关数据
		Page<Item> iPage =  new Page<>();
		iPage.setSize(rows);
		iPage.setCurrent(page);
		QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc("updated");
		IPage<Item> itemPage = itemMapper.selectPage(iPage, queryWrapper);
		//2.获取分页数据
		//查询总页数
		int total = (int) itemPage.getTotal();
		//查询分页信息
		List<Item> itemList = itemPage.getRecords();
		return new EasyUITable(total, itemList);//total用来封装记录总数，rows用来封装分页信息
	}
/**
 * 进行分页查询
 * total：表示记录总数
 * rows：查询到的记录的信息
 */
//	@Override
//	public EasyUITable findItemByPage(Integer page, Integer rows) {
//		//1.动态的获取记录总数total
//		int total = itemMapper.selectCount(null);
//		//2.动态获取数据库的分页信息
//		int start = (page-1) * rows;
//		List<Item> rows = itemMapper.findItemByPage(start,rows);
//		return new EasyUITable(total,rows);
//	}
	
	//实现批量删除
	@Override
	@Transactional//保持执行事务一致
	public void deleteItems(Long[] ids) {
		//将可变参数的数组-->集合List
		List<Long> idList = Arrays.asList(ids);
		//删除商品信息
		itemMapper.deleteBatchIds(idList);
		//删除商品详情信息
		itemDescMapper.deleteBatchIds(idList);
	}
	/**
	 * 实现商品上架和下架
	 */
	@Override
	public void updateStatus(Long[] ids, int status) {
		// TODO Auto-generated method stub
		Item item = new Item();
		item.setStatus(status).setUpdated(new Date());
		QueryWrapper<Item> updateWrapper = new QueryWrapper<>();
		updateWrapper.in("id", Arrays.asList(ids));
		itemMapper.update(item,updateWrapper);
	}

	@Override
	public ItemDesc findItemDescById(Long itemId) {
		// TODO Auto-generated method stub
		return itemDescMapper.selectById(itemId);
	}

	@Override
	@Transactional//保证事务的一致性
	public void updateItem(Item item, ItemDesc itemDesc) {
		Date date = new Date();
		item.setUpdated(date);
		itemMapper.updateById(item);
		//同时更新商品详情信息
		itemDesc.setItemId(item.getId())
				.setUpdated(date);
		itemDescMapper.updateById(itemDesc);
	}
}
