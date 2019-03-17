<%--
  Created by IntelliJ IDEA.
  User: QiuYukang
  Date: 2019/3/17
  Time: 10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>

<html lang="zh-CN">
<head>
    <base href="<%=basePath%>">
    <link href="css/bootstrap/3.3.6/bootstrap.css" rel="stylesheet"/>
    <link href="css/navigator.css" rel="stylesheet">

    <script href="js/jquery/2.0.0/jquery.min.js"></script>

    <style>
        .login {
            color: white;
            height: 38px;
            background-color: #2b669a;
        }
        #rem{
            width: 15px;
        }
        #login{
            margin-top: 10%;
        }
        #login_text{
            margin-bottom: 15px;
        }
        .label-danger{
            margin-left: 90px;
        }
    </style>

    <title>登录</title>
</head>
<body>
    <jsp:include page="navigator.jsp"/>

    <div class="container" id="login" style="width: 1200px">
        <h2 class="text-center" id="login_text">用户登录</h2>
        <div class="row">
            <form id="loginForm" class="form-horizontal col-md-offset-4 col-md-4" action="loginCheck" method="post">
                <div class="form-group">
                    <label for="inputUsername" class="col-sm-2 control-label">账户</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="inputUsername" placeholder="请输入用户名" name="name">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword" class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" id="inputPassword" placeholder="请输入密码" name="password">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <div class="checkbox" style="width: 170px">
                            <label>
                                <input type="checkbox" id="rem">记住密码
                            </label>
                        </div>
                    </div>
                </div>

                <c:if test="${!empty error}">
                    <div class="form-group">
                        <span class="label label-danger">${error}</span>
                    </div>
                </c:if>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default login " style="width: 100%">登录</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
