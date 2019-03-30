<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: QiuYukang
  Date: 2019/3/17
  Time: 15:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>


<div id = topbar>
    <div class="container">
        <ol class="nav nav-pill left">
            <li><a href="#">大数据综合实训平台</a></li>
        </ol>
        <ol class="nav nav-pill right">
            <li><a href="#">实训中心</a></li>
            <li><a href="#">帮助中心</a></li>
            <li><a href="http://www.peixun.net/">拓展学习</a></li>
            <li><a href="http://www.pinggu.com/">答疑平台</a></li>
            <c:if test="${!empty sessionScope.username}">
                <li><a href="#">${sessionScope.username}</a></li>
            </c:if>
            <c:if test="${empty sessionScope.username}">
                <li><a href="login">登录</a></li>
            </c:if>
        </ol>
    </div>
</div>

