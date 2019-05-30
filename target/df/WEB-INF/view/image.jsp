<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
	<title></title>
	<link rel="stylesheet" type="text/css" href="https://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/wangEditor.css">
	<link rel="stylesheet" type="text/css" href="css/base.css">
	<link rel="stylesheet" type="text/css" href="css/profile.css">
</head>
<body>
<%@ include file="header.jsp" %>


	<!-- 中间主体板块 -->
	<div class="main w clearfix">
		<c:forEach items="${imageList}" var="image">
			<img src="${image}">
		</c:forEach>

	</div><!-- 主体结束 -->

<%@ include file="footer.jsp" %>
<script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript" src="js/base.js"></script>
</body>
</html>













