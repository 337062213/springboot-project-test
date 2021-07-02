<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
 <html>
   <head>
     <title>展示</title>
   </head>  
   <body>
     <c:forEach var="me" items="${fileMap}">
         <c:url value="/servlet/downLoadServlet" var="downurl">
             <c:param name="filename" value="${me.key}"></c:param>
         </c:url>
         ${me.value}<a href="${downurl}">下载</a>
         <br/>
     </c:forEach>
   </body>
 </html>