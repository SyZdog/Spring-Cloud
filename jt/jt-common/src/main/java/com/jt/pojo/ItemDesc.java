package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@TableName("tb_item_desc")//对象与表一一映射
@Data //toString 当前方法，只会添加自己的属性
@Accessors(chain = true)//链式加载
@NoArgsConstructor
@AllArgsConstructor
public class ItemDesc extends BasePojo{
	private static final long serialVersionUID = 6985804367717661008L;
	@TableId//主键
	private Long itemId;//主键信息
	private String itemDesc;//商品详情
}
