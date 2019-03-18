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

<div class="container">
    <div class="row">
        <%--左侧Jupyter编辑区--%>
        <div class="col-xs-9 ">
            <iframe src="http://localhost:8888/notebooks/Untitled.ipynb?token=5b392634a78cd41f1e7cbf96f1c392a48aedaf6e2f1b5c05"
                    id="my-iframe">
            </iframe>
        </div>

        <%--右侧工具栏--%>
        <div class="col-xs-3" id="right-tool">
            <%--搜索区和新建按钮--%>
            <div class="row">
                <div class="col-lg-8">
                    <div id="searchDiv">
                        <form action="#" method="post">
                            <div class="input-group">
                                <input name="keyword" class="form-control" value="${param.keyword}"
                                       placeholder="搜索所有文件" required="" style="width: 100%"/>
                                <span class="input-group-btn ">
                    <button class="btn btn-default" type="submit" style="padding: 9px;margin-bottom: 15px;"
                            title="支持模糊搜索">
                        <span class="glyphicon glyphicon-search"></span>
                    </button>
                </span>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="col-lg-4">
                    <div>
                        <button type="button" class="btn btn-warning">新建</button>
                    </div>
                </div>
            </div>


            <%--实验指导和文件管理区域--%>
            <div>
                <ul id="myTab" class="nav nav-tabs">
                    <li class="active">
                        <a href="#expGuide" data-toggle="tab">实验指导</a>
                    </li>
                    <li>
                        <a href="#fileManage" data-toggle="tab">文件管理</a>
                    </li>
                </ul>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="expGuide">
                        <p>实验步骤1</p>
                        <p>实验步骤2</p>
                        <p>实验步骤3</p>
                        <p>实验步骤4</p>
                    </div>
                    <div class="tab-pane fade" id="fileManage">
                        <p>文件树内容</p>
                        <p>文件树内容</p>
                        <p>文件树内容</p>
                        <p>文件树内容</p>
                    </div>
                </div>
            </div>

            <%--视频区域--%>

        </div>
    </div>
</div>

</body>
</html>
