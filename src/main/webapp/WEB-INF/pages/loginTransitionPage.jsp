<%--
  Created by IntelliJ IDEA.
  User: QiuYukang
  Date: 2019/3/20
  Time: 23:29
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
    <script src="js/bootstrap/3.3.6/bootstrap.js"></script>


    <title>启动实验</title>
</head>
<body>

<jsp:include page="navigator.jsp"/>

<div style="margin-top: 40px;">
    <p style="text-align: center">
        <img src="img/start.gif">
    </p>
</div>

<div class="progress" style="width: 30%; margin:0 auto">
    <div class="progress-bar" style="width: 0%;">
    </div>
</div>

<div style="text-align: center;margin-top: 20px;color: #898888">
    正在启动实验, 请稍等...
</div>

<script>
    function sleep(n) {
        var start = new Date().getTime();
        while (true) if (new Date().getTime() - start > n) break;
    }

    $(document).ready(function () {
        // var percent = 0;
        // for(var i = 0; i<10; i++){
        //     percent = percent + 10;
        //     sleep(500);
        //     $(".progress-bar").css("width", percent + "%");
        // }
        setTimeout("step1()", 500);
        setTimeout("step2()", 500);
        setTimeout("step3()", 500);
        setTimeout("step4()", 500);
    });
    
    function step1() {
        $(".progress-bar").css("width", "25%");
    }
    function step2() {
        $(".progress-bar").css("width", "50%");
    }
    function step3() {
        $(".progress-bar").css("width", "75%");
    }
    function step4() {
        $(".progress-bar").css("width", "100%");
    }
</script>

</body>
</html>
