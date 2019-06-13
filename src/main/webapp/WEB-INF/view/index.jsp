﻿<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
	<title>首页</title>
	<link rel="stylesheet" type="text/css" href="https://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/base.css">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
</head>
<body>
<%@ include file="header.jsp" %>
<div class="container">
	<div class="main w clearfix">
		<div class="main-left col-xs-12 col-md-8">
			<div class="share">
				<div class="share-left"><span></span>&nbsp;每日笑话</div>
				<div class="share-right">
					<a href="toPublish.do"><span class="glyphicon glyphicon-pencil"></span>&nbsp;我要发布</a>
				</div>
			</div>
			<div class="post">
				<div class="post-wrap">
					<div class="post-choice">
						<a href="#" class="post-choice-current">最近</a>
						<a href="#">最热</a>
						<a href="#" class="post-choice-last">精华</a>
					</div>

					<ul class="post-list">
						<c:forEach items="${pageBean.list}" var="post">
                            <li class="clearfix">
                                <div class="post-image">
                                    <a href="toProfile.do?uid=${post.user.uid}"><img src="${post.poster}"></a>
									<span class="post-username"><a href="toProfile.do?uid=${post.user.uid}">${post.user.username}</a></span>
                                </div>
                                <div class="post-content">
                                    <div class="post-title"><a href="toPost.do?pid=${post.pid}">${post.title}</a></div>
                                    <div class="post-other">
                                        <div class="post-other-left">

                                            <span>&nbsp;最后回复&nbsp;</span>
                                            <span class="post-reply-time">${post.replyTime}</span>
                                        </div>
                                        <div class="post-other-right">
                                            <span class="post-reply-count">回复 ${post.replyCount}</span>&nbsp;
                                            <span class="post-like-count">赞 ${post.likeCount}</span>&nbsp;
                                            <span class="post-scan-count">浏览 ${post.scanCount}</span>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </c:forEach>

					</ul>

                    <%--分页导航--%>
                    <nav class="col-md-10 col-md-offset-2">
                        <ul class="pagination pagination-sm" style="display: block;">
                            <%--&lt;%&ndash;首页&ndash;%&gt;--%>
                            <%--<li><a href="listPostByTime.do?pageNum=1">首页</a></li>--%>
                            <%--上一页--%>
                            <c:choose>
                                <c:when test="${pageBean.pageNum!=1 }">
                                    <li style="float: left"><a href="listPostByTime.do?pageNum=${pageBean.pageNum-1 }"><span>&laquo;上一页</span></a></li>
                                </c:when>
                            </c:choose>
                            <%--下一页--%>
                            <c:choose>
                                <c:when test="${pageBean.pageNum!=pageBean.pages }">
                                    <li style="float: right"><a href="listPostByTime.do?pageNum=${pageBean.pageNum+1 }"><span>下一页&raquo;</span></a></li>
                                </c:when>
                            </c:choose>
                        </ul>
                    </nav>

				</div>
			</div>
		</div>
		<div class="main-right hidden-xs col-md-4">
			
			<div class="hot-user">
				<div class="clearfix"><div class="hot-user-title"><span></span>&nbsp;近期活跃用户</div></div>
				<ul class="hot-user-list">
					<c:forEach items="${hotUserList}" var="user">
						<li class="clearfix">
							<a href="toProfile.do?uid=${user.uid}" class="hot-user-image"><img src="${user.headUrl}"></a>
							<a href="toProfile.do?uid=${user.uid}" class="hot-user-name">${user.username}</a>
						</li>
					</c:forEach>
				</ul>
			</div>

			<div class="hot-user">
				<div class="clearfix"><div class="hot-user-title"><span></span>&nbsp;近期加入用户</div></div>
				<ul class="hot-user-list">
					<c:forEach items="${userList}" var="user">
						<li class="clearfix">
							<a href="toProfile.do?uid=${user.uid}" class="hot-user-image"><img src="${user.headUrl}"></a>
							<a href="toProfile.do?uid=${user.uid}" class="hot-user-name">${user.username}</a>
						</li>
					</c:forEach>
				</ul>
			</div>

		</div>
	</div>
</div>
<%@ include file="footer.jsp" %>
<script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript" src="js/base.js"></script>
<script type="text/javascript">
    $(function(){
        var pageNum = ${pageBean.pageNum};
        $(".pageNum").each(function(){
            if($(this).text()==pageNum){
                $(this).addClass("active");
            }
        });
    });

</script>
</body>
</html>
