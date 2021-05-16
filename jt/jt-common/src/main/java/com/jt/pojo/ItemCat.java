package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@TableName("tb_item_cat")
@Data
@Accessors(chain = true)
public class ItemCat extends BasePojo{
	private static final long serialVersionUID = 5006550716849464495L;
	@TableId(type=IdType.AUTO)	//主键自增
	private Long id;
	private Long parentId;	//父级ID
	private String name;
	private Integer status;
	private Integer sortOrder;	//排序号
	private Boolean isParent;	//是否为父级 true  false

}
