<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>

<html>
<head>
    <base href="<%=basePath%>">
</head>

<body>
<h2>Hello World!</h2>
<a href="login">进入测试页面</a>
<p>登录用户名密码均为“admin”</p>
</body>
</html>
