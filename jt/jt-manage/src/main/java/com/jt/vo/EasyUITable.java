package com.jt.vo;

import java.util.List;


import com.jt.pojo.Item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class EasyUITable {
	//根据页面的JSON动态获取
	private Integer total;//记录总数
	private List<Item> rows;//记录信息
}
