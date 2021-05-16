<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传的入门案例</title>
</head>
<body>
	<h1>实现文件长传</h1>
	<!--1.enctype="开启多媒体标签"  2.设定请求方式post 3.一般都会采用form表单提交方式-->
	<form action="http://localhost:8091/file" method="post" 
	enctype="multipart/form-data">
		<input name="fileImage" type="file" />
		<input type="submit" value="提交"/>
	</form>
</body>
</html>