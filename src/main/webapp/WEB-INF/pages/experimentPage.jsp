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

    <link rel="stylesheet" href="css/jquery.treeview.css" type="text/css"/>
    <link rel="stylesheet" href="css/screen.css" type="text/css"/>
    <script src="js/jquery.treeview.js" type="text/javascript"></script>


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

            <div id="fileTreeDiv">
                <ul id="treeview" class="filetree">
                    <li><span class="file">密码修改</span></li>
                    <li><span class="file">系统管理</span></li>
                    <li><span class="folder">行政部门</span>
                        <ul>
                            <li><span class="file">合同管理</span></li>
                            <li><span class="file">加班信息</span></li>
                            <li><span class="file">业绩报告</span></li>
                        </ul>
                    </li>
                </ul>
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
                        data-toggle="dropdown">
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
    $(function () {
        $(".file").click(function () {
            var src = $(this).attr("href");
            alert(src);

            $("#my-iframe").attr("src", src);
            return false;
        })
    })

    $(document).ready(function(){
        $("#treeview").treeview({
            toggle: function() {
                console.log("%s was toggled.", $(this).find(">span").text());
            }
        });
    });
</script>

</body>
</html>
