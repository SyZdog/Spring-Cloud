package com.jt.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;

@SpringBootTest//springboot测试类注解，该测试类启动，主启动类执行
public class TestMybatis {
	@Autowired//从容器再动态注入对象
	private UserMapper userMapper;
	/**
	 * 需求：能否动态的获取spring容器中管理的mapper接口对象
	 */
	@Test
	public void testSelect() {
		List<User> userList = userMapper.findAll();
		System.out.println(userList);
	}
	/**
	 * 用户不需要编辑sql语句，就可以查询数据
	 * mybatis-plus负责将mapper的接口转换成SQL语句
	 */
	@Test
	public void testSelectList() {
		List<User> userList = userMapper.selectList(null);
		System.out.println(userList);
	}
	
	/**
	 * 条件：可以一次性根据多个ID查询数据
	 */
	@Test
	public void testSelect01() {
		List<Integer> idList = new ArrayList<>();
		idList.add(1);
		idList.add(2);
		idList.add(3);
		idList.add(4);
		List<User> userList = userMapper.selectBatchIds(idList);
		System.out.println(userList);
	}
	
	/**
	 * 根据指定字段，查询数据key，字段=value的值
	 */
	@Test
	public void testSelect02() {
		Map<String, Object> columnMap = new HashMap<>();
		columnMap.put("name", "金角大王");
		columnMap.put("age", "3000");
		List<User> userMap = userMapper.selectByMap(columnMap);
		System.out.println(userMap);
	}
	
	/**
	 * 1.name属性中包含"精"的数据,并且为女性
	 * sql: name like %精% and sex="女"
	 * > gt,= eq,< lt
	 * >=ge,<=le
	 */
	@Test
	public void testSelect03() {
		//queryWrapper:条件构造器，作用是拼接where条件，多条件时，默认使用and连接
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.like("name", "精").eq("sex", "女");
		List<User> userList = userMapper.selectList(queryWrapper);
		System.out.println(userList);
	}
	
	/**
	 * 	条件:查询年龄在18-35之间的女性用户
	 *  sql: SELECT * FROM USER WHERE age BETWEEN 18 AND 35 AND sex ="女";
	 */
	@Test
	public void testSelect04() {
		//queryWrapper:条件构造器，作用是拼接where条件，多条件时，默认使用and连接
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.between("age", 18, 35).eq("sex", "女");
		List<User> userList = userMapper.selectList(queryWrapper);
		System.out.println(userList);
	}
	
	/**
	 * 条件:查询年龄大于100岁的,并且按照年龄降序排列,如果年龄相同按照Id降序排列.
	 * Sql: SELECT * FROM USER WHERE age >100 ORDER BY age DESC,id desc;
	 */
	@Test
	public void testSelect05() {
		//queryWrapper:条件构造器，作用是拼接where条件，多条件时，默认使用and连接
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.gt("age", 100).orderByDesc("age", "id");//age相同，用id降序排列
		List<User> userList = userMapper.selectList(queryWrapper);
		System.out.println(userList);
	}
	
	/**
	 * 条件: 查询名称以"乔"结尾的,并且性别为女,并且age小于30岁.按照年龄降序排列.
	 * sql: SELECT * FROM USER WHERE NAME LIKE "%乔" AND sex ="女" AND age <30 ORDER BY age DESC;
	 */
	@Test
	public void testSelect06() {
		//queryWrapper:条件构造器，作用是拼接where条件，多条件时，默认使用and连接
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.likeLeft("name", "乔").eq("sex", "女").lt("age", 30).orderByDesc("age");
		List<User> userList = userMapper.selectList(queryWrapper);
		System.out.println(userList);
	}
	/**
	 * 条件：查询age < 100岁的用户,并且性别与name="孙尚香"的性别相同的的用户数据。
	 * sql:select * from user where age < 100 and sex in (select sex from user where name="孙尚香";
	 */
	@Test
	public void testSelect07() {
		//queryWrapper:条件构造器，作用是拼接where条件，多条件时，默认使用and连接
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.lt("age", 100).inSql("sex", "select sex from user where name='孙尚香'");
		List<User> userList = userMapper.selectList(queryWrapper);
		System.out.println(userList);
	}
	/**
	 * 查询(年龄<100 或者 性别为女 )并且姓名为王姓。
	 * SQL：select * from user where (age < 100 or sex = "女"）AND NAME like "王%";
	 */
	@Test
	public void testSelect08() {
		//queryWrapper:条件构造器，作用是拼接where条件，多条件时，默认使用and连接
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.nested(wq-> wq.lt("age", 100).or().eq("sex", "女")).likeRight("name", "王");
		List<User> userList = userMapper.selectList(queryWrapper);
		System.out.println(userList);
	}
	/**
	 * 条件:查询id为3，4，5的数据
	 * select id from where id in (3,4,5);
	 */
	@Test
	public void testSelect09() {
		//queryWrapper:条件构造器，作用是拼接where条件，多条件时，默认使用and连接
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		//queryWrapper.in("id", 3,4,5);
		List<Integer> idList = new ArrayList<>();
		idList.add(3);
		idList.add(4);
		idList.add(5);
		queryWrapper.in("id", idList);
		List<User> userList = userMapper.selectList(queryWrapper);
		System.out.println(userList);
	}
	/**
	 * 需求说明:有时查询可能不需要查询全部字段,会挑选其中几个进行查询.
		条件:查询age为18岁的用户的名称和id.
		Sql:select id,name from user;
	 */
	@Test
	public void testSelect10() {
		//queryWrapper:条件构造器，作用是拼接where条件，多条件时，默认使用and连接
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("id","name").eq("age", 18);
		List<User> userList = userMapper.selectList(queryWrapper);
		System.out.println(userList);
	}
	/**
	 * 条件:以name和sex不为null的数据当做where条件.
		Sql: select * from user where name ="xx" and sex="xx";
		condition:true 当条件为true时拼接where条件
		false则不拼接
	 */
	@Test
	public void testSelect11() {
		String name = "";
		String sex = "男";
		//queryWrapper:条件构造器，作用是拼接where条件，多条件时，默认使用and连接
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(ObjectUtils.isNotEmpty(name), "name", name).eq(ObjectUtils.isNotEmpty(sex), "sex", sex);
		List<User> userList = userMapper.selectList(queryWrapper);
		System.out.println(userList);
	}
	/**
	 * 条件:根据对象中不为null的数据充当where条件.
		SQL: select * from user where name ="xx" and sex="xx";
	 */
	@Test
	public void testSelect12() {
		User user = new User();
		user.setAge(3000);
		//queryWrapper:条件构造器，作用是拼接where条件，多条件时，默认使用and连接
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		List<User> userList = userMapper.selectList(queryWrapper);
		System.out.println(userList);
	}
	/**
	 * 条件:使用allEq方法查询数据.
	 *  SQL: select * from user where name ="xx" and age="xx";
	 */
	@Test
	public void testSelect13() {
		Map<String, Object> params = new HashMap<>();
		params.put("name", "金角大王");
		params.put("age", null);
		//queryWrapper:条件构造器，作用是拼接where条件，多条件时，默认使用and连接
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		//queryWrapper.allEq(params);//不会忽略null值
		queryWrapper.allEq(params,true);//false时会自动将为null的数据去除.
		List<User> userList = userMapper.selectList(queryWrapper);
		System.out.println(userList);
	}
	/**
	 * 业务说明:有时查询几个字段时,不需要使用对象接收,否则对象中的其他属性会出现null的现象.
		条件:使用selectMap查询数据.要求返回name和age列数据.
		SQL: select name,age from user where name ="xx";
	 */
	@Test
	public void testSelect14() {
		//queryWrapper:条件构造器，作用是拼接where条件，多条件时，默认使用and连接
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("name","age").eq("name", "王昭君");
		List<Map<String, Object>> list = userMapper.selectMaps(queryWrapper);
		System.out.println(list);
	}
	/**
	 * 条件:根据性别 分组,查询每组中最大年龄和最小年龄并且展现其信息
		Sql: SELECT MAX(age)max_age,MIN(age) min_age,AVG(age) avg_age,sex FROM USER GROUP BY sex HAVING (sex IN ("男","女"));
	 */
	@Test
	public void testSelect15() {
		//queryWrapper:条件构造器，作用是拼接where条件，多条件时，默认使用and连接
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("MAX(age)max_age","MIN(age)min_age","AVG(age)avg_age","sex").groupBy("sex").having("sex in ({0},{1})","男","女");
		List<Map<String, Object>> list = userMapper.selectMaps(queryWrapper);
		System.out.println(list);
	}
	/**
	 * 条件: 只返回一列数据时 使用objs.
		Sql: SELECT name,sex FROM user WHERE sex = ?;
	 */
	@Test
	public void testSelect16() {
		//queryWrapper:条件构造器，作用是拼接where条件，多条件时，默认使用and连接
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("name","sex").groupBy("sex").having("sex in ({0},{1})","男","女");
		List<Map<String, Object>> list = userMapper.selectMaps(queryWrapper);
		System.out.println(list);
	}
	/**
	 * 条件: 查询年龄=18岁的记录数.
	 * Sql: SELECT COUNT(*) FROM USER WHERE age = 18;
	 */
	@Test
	public void testSelect17() {
		//queryWrapper:条件构造器，作用是拼接where条件，多条件时，默认使用and连接
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("age","18");
		Integer count = userMapper.selectCount(queryWrapper);
		System.out.println(count);
	} 
	/**
	 * 说明:查询name为金角大王的一个数据.
	 * Sql: select * from user where name = "金角大王";
	   使用selectOne方法时，必须保证该记录只有一条可以使用，否则会报错
	 */
	@Test
	public void testSelect18() {
		//queryWrapper:条件构造器，作用是拼接where条件，多条件时，默认使用and连接
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("name","金角大王");
		User user = userMapper.selectOne(queryWrapper);
		System.out.println(user);
	} 
	/**
	 * 说明:采用对象的形式,实现用户信息入库.
	 * 入库user信息
	 */
	@Test
	public void insertuser() {
		User user = new User();
		user.setName("小法").setAge(20).setSex("男");
		int rows = userMapper.insert(user);
		System.out.println(rows);
	} 
	/**
	 * 说明:根据主键修改数据库记录.
	 * 根据id更新主键信息
	 * 作用: 根据主键修改数据库记录.
	 */
	@Test
	public void updateUserById() {
		User user = new User();
		user.setId(226).setName("火人").setSex("男").setAge(2000);
		int rows = userMapper.updateById(user);
		System.out.println(rows);
	} 
	/**
	 * 说明:根据条件修改数据库数据.其中不为null的元素充当set的条件.
	 * 根据条件修改数据.  将名称为张三的用户信息.修改为鳄鱼 年龄3000 性别男
	 * 作用: 根据主键修改数据库记录.
	 */
	@Test
	public void updateUser() {
		//更改数据
		User user = new User();
		user.setName("鳄鱼").setSex("男").setAge(3000);
		//寻找张三的信息
		UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("name", "张三");
		//更新数据
		int rows = userMapper.update(user, updateWrapper);
		System.out.println(rows);
	} 
	/**
	 * 删除Id=1的用户记录
	 */
	@Test
	public void deleteUser() {
		int rows = userMapper.deleteById(1);
		System.out.println(rows);
	}
	/**
	 * 条件:批量删除数据  id为 171,175,224
	 */
	@Test
	public void deleteUsers() {
		List<Integer> idList = new ArrayList<>();
		idList.add(171);
		idList.add(175);
		idList.add(224);
		int rows = userMapper.deleteBatchIds(idList);
		System.out.println(rows);
	}
	/**
	 *  条件:删除年龄>9000的数据用户数据.
	 */
	@Test
	public void deleteUsersByWrapper() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.gt("age", 9000);
		int rows = userMapper.delete(queryWrapper);
		System.out.println(rows);
	}
	/**
	 * 条件:根据Map删除用户信息
	 */
	@Test
	public void deleteByMap() {
		Map<String, Object> columnMap = new HashMap<>();
		columnMap.put("name", "甄姬");
		columnMap.put("age", 30);
		userMapper.deleteByMap(columnMap);
	}
	
}

