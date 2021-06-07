package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
//如果对象的名称和表中的名称一致，则可以省略不写
@TableName//("user")//将对象和表进行关联
public class User {
	//编辑pojo都使用包装类型
	@TableId(type = IdType.AUTO)//标识id为主键且自增
	private Integer id;
	/**
	 * 关联映射
	 */
	//@TableField("name")
	private String name;
	//@TableField("age")
	private Integer age;
	//@TableField("sex")
	private String sex;
}
