<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="UploadServlet" method="post" enctype="multipart/form-data">
		学号：<input type="text" name="sno"/><br/>
		姓名：<input type="text" name="sname"/><br/>
		上传照片：<input type="file" name="spicture"/><br/>
		<input type="submit"/>
	</form>
	<br/>
	<a href="DownloadServlet?fileName=中国.jpg">中国</a>
	<a href="DownloadServlet?fileName=timg.jpg">timg</a>
</body>
</html>