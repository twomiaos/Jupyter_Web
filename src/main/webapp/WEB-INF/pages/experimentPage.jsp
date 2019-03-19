<%--
  Created by IntelliJ IDEA.
  User: QiuYukang
  Date: 2019/3/17
  Time: 19:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <script src="js/jquery/2.0.0/jquery.min.js"></script>
    <link href="css/bootstrap/3.3.6/bootstrap.css" rel="stylesheet"/>
    <link href="css/navigator.css" rel="stylesheet">
    <link href="css/experimentPage.css" rel="stylesheet">
    <script src="js/bootstrap/3.3.6/bootstrap.js"></script>


    <title>实训中心</title>
</head>
<body>

<jsp:include page="navigator.jsp"/>

<div class="container" id="pageContain">
    <div class="row">
        <%--左侧文件管理区--%>
        <div class="col-lg-2 area">
            <div class="input-group" id="searchDiv">
                <input type="text" class="form-control" placeholder="搜索所有文件" aria-describedby="basic-addon2">
            </div>

            <div class="titleDiv">
                文件管理
            </div>

            <div id="folderDiv">

            </div>
            <div id="fileDiv">

            </div>

        </div>

        <%--中间Jupyter编辑区--%>
        <div class="col-xs-8 area">
            <iframe src=" " id="my-iframe">
            </iframe>
        </div>

        <%--右侧实验指导+工具栏区--%>
        <div class="col-xs-2 area">

            <div id="toolDiv">
                <label>工具栏</label>
                <button type="button" class="btn btn-default dropdown-toggle"
                        data-toggle="dropdown" id="toolBtn">
                    <span class="caret"></span>

                </button>
                <ul class="dropdown-menu" role="menu">
                    <li>
                        <a href="#">60:00</a>
                    </li>
                    <li>
                        <a href="#">延时</a>
                    </li>
                    <li role="presentation">
                        <a href="#">保存实验</a>
                    </li>

                    <li role="presentation">
                        <a href="#">重置实验</a>
                    </li>
                    <li role="presentation">
                        <a href="#">导出实验</a>
                    </li>
                    <li role="presentation">
                        <a href="#">提交实验</a>
                    </li>
                    <li role="presentation">
                        <a href="#">退出实验</a>
                    </li>
                </ul>
            </div>

            <div class="titleDiv" style="margin-top: 60px;">
                实验指导
            </div>

            <div id="expGuideDiv">
                <div class="list-group">
                    <button type="button" class="list-group-item active">实验步骤1</button>
                    <button type="button" class="list-group-item">实验步骤2</button>
                    <button type="button" class="list-group-item">实验步骤3</button>
                    <button type="button" class="list-group-item">实验步骤4</button>
                    <button type="button" class="list-group-item">实验步骤5</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    // 点击文件链接，替换iframe标签的src
    $('body').on('click', '.fileName', function () {
        var src = $(this).attr("href");

        $("#my-iframe").attr("src", src);
        return false;
    });

    // 点击文件夹链接，发送Ajax请求
    $('body').on('click', '.folderName', function () {
        var basePath = "http://localhost:8888/";
        var notebookDir = "notebooks/";
        var username = "admin";
        var fileApi = "api/contents/";
        var type = "directory";
        var token = "42f3396e95168791084f33b3d667c307da09fc868c1db3a4";
        var page = $(this).attr('href');

        $.get(
            page,
            {"type": type, "token": token},
            function (result) {
                var json = eval(result);
                var files = json.content;

                // 清空div中的内容
                $("#fileDiv").empty();
                $("#folderDiv").empty();

                // 添加返回上一级按钮
                var temp = basePath + fileApi + username;
                var lastHtml =
                    "<div class=\"folder\">" +
                    "<span class=\"glyphicon glyphicon-folder-close\"></span>" +
                    "<button type=\"button\" class='btn btn-link folderName' href=" + temp + ">返回上一级...</button>" +
                    "</div>";
                $("#folderDiv").append(lastHtml);

                for (var i = 0; i < files.length; i++) {
                    if (files[i].type == "directory") {
                        var folderHref = basePath + fileApi + files[i].path;
                        var html =
                            "<div class=\"folder\">" +
                            "<span class=\"glyphicon glyphicon-folder-close\"></span>" +
                            "<button type=\"button\" class='btn btn-link folderName' href=" + folderHref + ">" + files[i].name + "</button>" +
                            "</div>";
                        $("#folderDiv").append(html);
                    } else {
                        var fileHref = basePath + notebookDir + files[i].path;
                        var html =
                            "<div class=\"file\">" +
                            "<a class='fileName' href=" + fileHref + ">" + files[i].name + "</a>" +
                            "</div>";
                        $("#fileDiv").append(html);
                    }

                }
            }
        );
    });

    // 文档加载后，发送Ajax请求文件列表
    $(document).ready(function () {
        var basePath = "http://localhost:8888/";
        var notebookDir = "notebooks/";
        var username = "admin";
        var fileApi = "api/contents/";
        var page = fileApi + username;
        var type = "directory";
        var token = "42f3396e95168791084f33b3d667c307da09fc868c1db3a4";

        $.get(
            basePath + fileApi + username,
            {"type": type, "token": token},
            function (result) {
                var json = eval(result);
                var files = json.content;

                // 清空div中的内容
                $("#fileDiv").empty();
                $("#folderDiv").empty();

                for (var i = 0; i < files.length; i++) {
                    if (files[i].type == "directory") {
                        var folderHref = basePath + fileApi + files[i].path;
                        var html =
                            "<div class=\"folder\">" +
                                "<span class=\"glyphicon glyphicon-folder-close\"></span>" +
                                "<button type=\"button\" class='btn btn-link folderName' href=" + folderHref + ">" + files[i].name + "</btn>" +
                            "</div>";
                        $("#folderDiv").append(html);
                    } else {
                        var fileHref = basePath + notebookDir + files[i].path;
                        var html =
                            "<div class=\"file\">" +
                            "<a class='fileName' href=" + fileHref + ">" + files[i].name + "</a>" +
                            "</div>";
                        $("#fileDiv").append(html);
                    }

                }
            }
        );
    });
</script>

</body>
</html>
