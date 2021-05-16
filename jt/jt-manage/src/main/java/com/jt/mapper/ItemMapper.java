package com.jt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.Item;

public interface ItemMapper extends BaseMapper<Item>{
	@Select("SELECT  id,image,sell_point,price,created,num,title,barcode,updated,status,cid  FROM tb_item ORDER BY update DESC limit #{start},#{rows} ")
	List<Item> findItemByPage(Integer start, Integer rows);
	
}
