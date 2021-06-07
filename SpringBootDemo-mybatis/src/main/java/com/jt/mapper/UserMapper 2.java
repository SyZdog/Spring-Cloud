package com.jt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.User;

//@Mapper//Mybatis为接口实例化对象创建的是代理对象，之后交给spring容器管理
public interface UserMapper extends BaseMapper<User>{
	//表示查询数据库的全部记录
	 List<User> findAll();
}
