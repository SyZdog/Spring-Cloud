package com.jt.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.vo.ImageVO;

@RestController
public class FileController {
	@Autowired
	private FileService fileService;
	/**
	 * 实现文件上传之后，返回特定的数据。
	 * url地址：localhost:8091/file
	 * 用户提交的参数：fileImage
	 * 返回值要求：
	 * 注意事项：默认条件下文件上传不能超过1M
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@PostMapping("/file")
	public String file(MultipartFile fileImage) throws IllegalStateException, IOException {
		//1.确定文件的目录结构
		String path = "/Users/zdzsmacbookpro/Desktop/java/";
		//1.1判断图片路径是否正确
		File file = new File(path);
		if (!file.exists()) {//表示取非
			file.mkdirs();//创建多级的目录
		}
		//2.拼接文件的路径信息/Users/zdzsmacbookpro/Desktop/java/Flower.jpg
		String fileName = fileImage.getOriginalFilename();//获取图片真实名称
		String filePath = path + fileName;
		//3.按照指定的路径上传图片
		fileImage.transferTo(new File(filePath));
		return "文件上传执行成功！";
	}
	/**
	 * 图片上传的实现
	 * 1.URL地址：http://localhost:8091/pic/upload?dir=image
	 * 2.参数：uploadFile
	 * 3.返回值的结果：ImageVO对象
	 */
	@PostMapping("/pic/upload")
	public ImageVO fileUpload(MultipartFile uploadFile) {
		return fileService.fileUpload(uploadFile);
	}
}
