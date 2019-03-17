<%--
  Created by IntelliJ IDEA.
  User: QiuYukang
  Date: 2019/3/17
  Time: 19:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>

<html>
<head>
    <base href="<%=basePath%>">
    <link href="css/bootstrap/3.3.6/bootstrap.css" rel="stylesheet"/>
    <link href="css/navigator.css" rel="stylesheet">

    <script href="js/jquery/2.0.0/jquery.min.js"></script>

    <style>
        .course-list{
            margin-top: 15px;
        }
    </style>

    <title>实训中心</title>
</head>
<body>
    <jsp:include page="navigator.jsp"/>

    <div class="container">
        <div class="row">
            <div class="page-header">
                <h1 style="text-align: center">请选择实验课程</h1>
            </div>
        </div>
        <div class="row">
            <div class="panel panel-info" style="margin-top: 20px;">
                <div class="panel-heading">Python实验</div>
                <div class="panel-body">
                    <div class="row course-list">
                        <div class="col-xs-4 "><a href="experiment?id=1">实验1</a></div>
                        <div class="col-xs-4 ">实验2</div>
                        <div class="col-xs-4 ">实验3</div>
                    </div>
                    <div class="row course-list">
                        <div class="col-xs-4 ">实验4</div>
                        <div class="col-xs-4 ">实验5</div>
                        <div class="col-xs-4 ">实验6</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="panel panel-info" style="margin-top: 20px;">
                <div class="panel-heading">XXX实验</div>
                <div class="panel-body">
                    <div class="row course-list">
                        <div class="col-xs-4 ">实验1</div>
                        <div class="col-xs-4 ">实验2</div>
                        <div class="col-xs-4 ">实验3</div>
                    </div>
                    <div class="row course-list">
                        <div class="col-xs-4 ">实验4</div>
                        <div class="col-xs-4 ">实验5</div>
                        <div class="col-xs-4 ">实验6</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
