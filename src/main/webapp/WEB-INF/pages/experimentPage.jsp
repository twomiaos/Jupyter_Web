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
    <%--<base href="<%=basePath%>">--%>
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
        <div class="col-lg-2 area" id="left">
            <div class="input-group" id="searchDiv">
                <input type="text" class="form-control" placeholder="搜索所有文件" aria-describedby="basic-addon2">
            </div>

            <div class="titleDiv">
                文件管理
            </div>

            <div id="folderDiv">
                服务器未响应，请尝试<a href="javascript:void(0)" onclick="refresh()">刷新</a>
            </div>
            <div id="fileDiv">

            </div>

        </div>

        <%--中间Jupyter编辑区--%>
        <div class="col-xs-8 area" id="center">
            <iframe src=" " id="my-iframe">
            </iframe>
        </div>

        <%--右侧实验指导+工具栏区--%>
        <div class="col-xs-2 area" id="right">

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
                        <a href="javascript:void(0)" id="save-notebook">保存实验</a>
                    </li>

                    <li role="presentation">
                        <a href="#">重置实验</a>
                    </li>
                    <li role="presentation">
                        <a href="javascript:void(0)" id="export-notebook">导出实验</a>
                    </li>
                    <li role="presentation">
                        <a href="#">提交实验</a>
                    </li>
                    <li role="presentation">
                        <a href="javascript:void(0)" id="start-kernel">启动实验</a>
                    </li>
                    <li role="presentation">
                        <a href="javascript:void(0)" id="shutdown-kernel">退出实验</a>
                    </li>
                </ul>
            </div>

            <div class="titleDiv" style="margin-top: 60px;">
                实验指导
            </div>

            <div id="expGuideDiv">
                <div id="step-menu"></div>
            </div>

            <div id="videoAndTestDiv">
                <div id="videoDiv" style="margin-top: 30px">
                    <video src="#" controls="controls" style="width: 98%;">
                        您的浏览器不支持 video 标签
                    </video>
                </div>

                <div id="submitDiv">
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#testingModal">
                        实验完成 开始测试
                    </button>
                </div>
            </div>

        </div>
    </div>
</div>

<%--点击完成实验 开始测试按钮的弹出框--%>
<div class="modal fade" id="testingModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button data-dismiss="modal" class="close" type="button"><span aria-hidden="true">×</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">测试题 | 实验名称</h4>
            </div>

            <div class="progress" style="width: 80%;margin: 0 auto;margin-top: 10px;">
                <div class="progress-bar" style="width: 20%;">
                </div>
            </div>

            <div class="modal-body" style="text-align: center">
                <p>题目内容</p>
                <div class="row">
                    <div class="col-lg-2">上一题</div>
                    <div class="col-lg-8">
                        <form role="form" style="text-align: left;">
                            <div class="checkbox">
                                <label><input type="checkbox" value="">选项A</label>
                            </div>
                            <div class="checkbox">
                                <label><input type="checkbox" value="">选项B</label>
                            </div>
                            <div class="checkbox">
                                <label><input type="checkbox" value="">选项C</label>
                            </div>
                            <div class="checkbox">
                                <label><input type="checkbox" value="">选项D</label>
                            </div>
                        </form>
                    </div>
                    <div class="col-lg-2">下一题</div>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary" type="button" style="display: none">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>


<script>
    // 点击文件链接，替换iframe标签的src
    $('body').on('click', '.fileName', function () {
        var src = $(this).attr("href") + "?token=" + "${sessionScope.token}";
        // alert(src);
        $("#my-iframe").attr("src", src);
        return false;
    });

    // 文档加载后，发送Ajax请求文件列表
    $(document).ready(function () {
        var basePath = "${sessionScope.basepath}";
        var notebookDir = "notebooks/";
        var username = "${sessionScope.username}";
        var fileApi = "api/contents/";
        var page = fileApi + username;
        var type = "directory";
        var token = "${sessionScope.token}";

        getFileListFromJson(basePath, fileApi, type, token, notebookDir);
    });

    function refresh(){
        var basePath = "${sessionScope.basepath}";
        var notebookDir = "notebooks/";
        var username = "${sessionScope.username}";
        var fileApi = "api/contents/";
        var page = fileApi + username;
        var type = "directory";
        var token = "${sessionScope.token}";

        getFileListFromJson(basePath, fileApi, type, token, notebookDir);
    }

    // 点击文件夹链接，发送Ajax请求
    $('body').on('click', '.folderName', function () {
        var basePath = "${sessionScope.basepath}";
        var notebookDir = "notebooks/";
        var username = "${sessionScope.username}";
        var fileApi = "api/contents/";
        var type = "directory";
        var token = "${sessionScope.token}";
        var page = $(this).attr('href');

        $.ajax({
            type: "get",
            url: page,
            data: {"type": type, "token": token},
            cache: false,
            success: function (result) {
                var json = eval(result);
                var files = json.content;

                // 清空div中的内容
                $("#fileDiv").empty();
                $("#folderDiv").empty();

                // 添加返回上一级按钮
                var temp = basePath + fileApi;
                var lastHtml =
                    "<div class=\"folder\">" +
                    "<span class=\"glyphicon glyphicon-folder-close\"></span>" +
                    "<button type=\"button\" id=\"lastFolder\" class='btn btn-link folderName' href=" + temp + ">" + "</button>" +
                    "</div>";
                $("#folderDiv").append(lastHtml);
                $("#lastFolder").text("返回上一级...");

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
        });
    });

    // 获取文件根目录
    function getFileListFromJson(basePath, fileApi, type, token, notebookDir) {
        $.ajax({
            type: "get",
            url:basePath + fileApi,
            data: {"type": type, "token": token},
            cache: false,
            success:function (result) {
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
            },
            error: function (xhr,state,errorThrown) {
                var html = "服务器未响应！请尝试<a href=javascript:void(0) onclick= refresh()>刷新</a>";
                $("#folderDiv").empty();
                $("#folderDiv").append(html);
            }
        });
    }
</script>

<script type="text/javascript">
    var frameID = 'my-iframe';
    // Add click event that can post massage to iframe.
    var actions_list = ["save-notebook", "shutdown-kernel", "export-notebook", "start-kernel"];
    $(function (argument) {
        for (var i = 0; i < actions_list.length; i++) {
            var n_action = actions_list[i];
            $("#" + n_action).click(function () {
                this_id = $(this).attr("id")
                var notebook = document.getElementById(frameID);
                var data = {"actions": this_id, "msg": ""};
                // if(data.actions == "scroll-heading") {
                //     data.msg = $('#scroll-heading').text();
                // } else {
                //     data.msg = "";
                // }
                console.log(data);
                notebook.contentWindow.postMessage(data, "*");
            });
        }
    });
    // Generate Table of Content in jupyter notebook
    window.addEventListener('message', function (event) {
        // Will print message continuously ???
        // if(event.origin !== 'http://192.168.3.80') return;
        act = event.data.actions;
        msg = event.data.msg;
        // For test
        console.log("The response is: " + act + " " + msg);
        // Switch to the event
        if (act == 'notebook-toc') {
            var menu_obj = $('#step-menu');
            // replace html
            menu_obj.html(msg);
        } else if (act == 'notebook-toc-evt') {
            // add click events for link ?????
            var links = $('#step-menu a');
            for (var i = 0; i < links.length; i++) {
                // console.log($(links[i]).text());
                $(links[i]).click(function () {
                    var notebook = document.getElementById(frameID);
                    var data = {"actions": "scroll-heading", "msg": $(this).text()};
                    console.log(data)
                    notebook.contentWindow.postMessage(data, "*");
                });
            }
        }
        else {
            console.log("Unrecognized command!");
        }
    }, false);
</script>

</body>
</html>
