package com.jt.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.vo.ImageVO;

@Service
@PropertySource("classpath:/properties/image.properties")
public class FileServiceImpl implements FileService{
	//4.设定本地存储的根目录
	@Value("${image.localPath}")
	private String localPath ;//= "/Users/zdzsmacbookpro/Desktop/Java/Image/";
	@Value("${image.urlPath}")
	private String urlPath;
	/**
	 * 1.图片的校验jpg|png|gif 
	 * 2.是否为恶意程序，例如：木马.exe.jpg 宽度：高度：
	 * 3.分目录存储。目的：控制目录中图片数量
	 * 4.保证图片不能重名
	 * 
	 * 解决方案：
	 * 1.根据if判断，判断后缀是否包含指定的名称？
	 * 2.将数据把它填充到图片对象中，之后利用工具API获取宽度和高度？
	 * 3.利用hash方式进行目录存储，利用时间进行目录存储？
	 * 4.随机数，UUID方式？
	 */
	@Override
	public ImageVO fileUpload(MultipartFile uploadFile) {
		//1.获取图片名称
		String fileName = uploadFile.getOriginalFilename();
		//1.1将数据转换成小写字母
		fileName = fileName.toLowerCase();
		//2.利用正则表达式校验是否为图片
		if (!fileName.matches(".+(.png|.jpeg|.jpg|.gif)$")) {
			return ImageVO.fail();//表示不是图片信息
		}
		//3.判断图片是否为恶意程序
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(uploadFile.getInputStream());
			int height = bufferedImage.getHeight();
			int width = bufferedImage.getWidth();
			if(height == 0 || width == 0) {
				return ImageVO.fail();
			}
		//5.实现分目录存储 data转化成字符串 /Users/zdzsmacbookpro/Desktop/Java/Image/yyyy/MM/dd/
			String datePath = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
			//拼接本地存储的路径
			String fileLocalPath = localPath + datePath;
			File fileDir = new File(fileLocalPath);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
		//6.为了防止文件重名，指定文件名称UUID
			String uuid = UUID.randomUUID().toString().replace("-","");
			String fileType = fileName.substring(fileName.lastIndexOf("."));
			String realFileName = uuid + fileType;
		//7.实现文件上传
			String realFilePath = fileLocalPath + realFileName;
			File realFile = new File(realFilePath);
			uploadFile.transferTo(realFile);
			//url是设定图片的虚拟地址 http://image.jt.com/yyyy/MM/dd/uuid.jpg
			//String url="https://img10.360buyimg.com/seckillcms/s250x250_jfs/t1/166849/38/18964/122601/607944afEe53bbfd1/018f7cd08b18080d.jpg";
			String url = urlPath + datePath + realFileName;
			System.out.println(url);
			return ImageVO.success(url, width, height);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ImageVO.fail();
		}
	} 
}
	