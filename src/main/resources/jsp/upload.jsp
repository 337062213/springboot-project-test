<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>上传</title>
   </head>
  
   <body>
       <form action="${pageContext.request.contextPath}/servlet/uploadHandleServlet2" method ="post" enctype="multipart/form-data">
		           用户名<input type="text" name="username"><br/>
		          文件1<input type="file" name="file1"><br/>
		          文件2<input type="file" name="file2"><br/>
		   <input type="submit" value="提交">
       </form>
   </body>
</html>