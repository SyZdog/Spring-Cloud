package com.jt.vo;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ImageVO {
	private Integer error;	//检查图片上传是否有误 0正常  1失败
	private String url;		//图片虚拟路径
	private Integer width;	//宽度
	private Integer height;	//高度
	
	public static ImageVO fail() {	
		return new ImageVO(1, null, null, null);
	}
	public static ImageVO success(String url,Integer width,Integer height) {
		//业务：需要检查返回值是否为null吗？
		if(ObjectUtils.isEmpty(url) || width == null || width <= 0|| height == null || height <= 0) {
			//return new ImageVO(1,null,null,null);
			return ImageVO.fail();
		}
		return new ImageVO(0, url, width, height);
	}


}

