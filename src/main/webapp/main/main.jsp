<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8"/>
    <!--在手机网站，都需要加上视口约束！！！-->
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no"/>
    <!--以最新的内核渲染页面-->
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>应学视频App后台管理系统</title>
    <!--引入css文件-->
    <link rel="stylesheet" type="text/css" href="${path}/bootstrap/css/bootstrap.css"/>

    <style type="text/css">


    </style>
    <!--引入jQuery-->
    <script src="${path}/bootstrap/js/jquery-3.3.1.min.js" type="text/javascript"></script>
    <!--引入js文件-->
    <script src="${path}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script>
        $(function () {
            $('#myCarousel').on('slide.bs.carousel', function () {
                // alert("当调用 slide 实例方法时立即触发该事件。");
            });
        });

        function exit() {
            $.ajax({
                url: "${path}/admin/exit",
                dataType: "json",
                type: "post",
                success: function (data) {

                    location.href = "${path}/login/login.jsp";

                }
            });
        }

        //href="javascript:$('#main').load('${path}/user/userShow.jsp')"
    </script>

</head>
<body>
<div class="container">
    <!--顶部导航-->
    <nav class="navbar navbar-default" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <span class="navbar-brand">应学视频App后台管理系统</span>
            </div>
            <ul class="nav navbar-nav navbar-right">
                <!--user is or not login-->

                <c:if test="${sessionScope.userLogin.username != null}">
                    <li><a><span class="glyphicon glyphicon-user"></span> 欢迎：${sessionScope.userLogin.username}</a></li>
                    <li>
                        <button class="glyphicon glyphicon-log-out" onclick="exit()">退出</button>
                    </li>
                </c:if>
                <!--admin is or not login-->
                <c:if test="${sessionScope.adminLogin == null}">
                    <li><a href="${path}/login/login.jsp"><span class="glyphicon glyphicon-log-in"></span> 登录</a></li>
                </c:if>
                <c:if test="${sessionScope.adminLogin != null}">
                    <li><a><span class="glyphicon glyphicon-user"></span>
                        欢迎：<strong>${sessionScope.adminLogin.username}</strong></a>
                    </li>
                    <li><a onclick="exit()"><span class="glyphicon glyphicon-log-out"></span> 退出</a></li>
                </c:if>
            </ul>
        </div>
    </nav>

    <div class="row">
        <div class="col-sm-2">
            <div class="panel-group" id="acc">
                <div class="panel panel-danger">
                    <div class="panel-heading">
                        <a href="#tg1" class="panel-title" data-toggle="collapse" data-parent="#acc">
                            <center><span class="glyphicon glyphicon-user">用户管理</span></center>
                        </a>
                    </div>
                    <div class="panel-collapse collapse" id="tg1">
                        <div class="panel-body">
                            <button class=" btn"
                                    style="width:100px;height:30px;background-color: #ffa608;margin-left: 16px;margin-bottom:
                            15px">
                                <a href="javascript:$('#main').load('${path}/user/userShow.jsp')">用户展示</a>
                            </button>


                            <button class="btn"
                                    style="width:100px;height:30px;background-color: #ffa608;margin-left: 16px;margin-bottom: 15px">
                                <center><span class="glyphicon glyphicon-hdd"><a
                                        href="javascript:$('#main').load('${path}/user/userEcharts.jsp')">用户统计</a></span>
                                </center>
                            </button>


                            <a href="#">
                                <button class="btn"
                                        style="width:100px;height:30px;background-color: #ffa608;margin-left: 16px">
                                    <center><span class="glyphicon glyphicon-th">用户分布</span></center>
                                </button>
                            </a>


                        </div>
                    </div>
                </div>
                <hr color="" width="173px"/>
                <div class="panel panel-success">
                    <div class="panel-heading">
                        <a href="#tg2" class="panel-title" data-toggle="collapse" data-parent="#acc">
                            <center><span class="glyphicon glyphicon-list">分类管理</span></center>
                        </a>
                    </div>
                    <div class="panel-collapse collapse" id="tg2">
                        <div class="panel-body">
                            <button class="btn"
                                    style="width:100px;height:30px;margin-left: 16px;margin-bottom: 15px;background-color: #4aaf51">
                                <span class="glyphicon glyphicon-list"><a
                                        href="javascript:$('#main').load('${path}/category/cateShow.jsp')">分类展示</a></span>
                            </button>
                        </div>
                    </div>
                </div>
                <hr color="" width="173px"/>
                <div class="panel panel-success">
                    <div class="panel-heading" style="background-color: #f7e1b5">
                        <a href="#tg3" class="panel-title" data-toggle="collapse" data-parent="#acc">
                            <center><span class="glyphicon glyphicon-film">视频管理</span></center>
                        </a>
                    </div>
                    <div class="panel-collapse collapse" id="tg3">
                        <div class="panel-body">

                            <button class="btn"
                                    style="width:100px;height:30px;margin-left: 16px;margin-bottom: 15px;background-color: #adadad">
                                <center><span class="glyphicon glyphicon-film"><a
                                        href="javascript:$('#main').load('${path}/video/videoShow.jsp')">视频信息</a></span>
                                </center>
                            </button>

                        </div>
                    </div>
                </div>
                <hr color="" width="173px"/>
                <c:if test="${sessionScope.adminLogin.content=='super'}">
                    <div class="panel panel-success">
                        <div class="panel-heading" style="background-color: #79c9ec">
                            <a href="#tg4" class="panel-title" data-toggle="collapse" data-parent="#acc">
                                <center><span class="glyphicon glyphicon-calendar">日志管理</span></center>
                            </a>
                        </div>
                        <div class="panel-collapse collapse" id="tg4">
                            <div class="panel-body">


                                <button class="btn"
                                        style="width:100px;height:30px;margin-left: 16px;margin-bottom: 15px;background-color: #b2dba1">
                                    <center><span class="glyphicon glyphicon-eye-open"><a
                                            href="javascript:$('#main').load('${path}/log/logShow.jsp')">查看日志</a></span>
                                    </center>
                                </button>


                            </div>
                        </div>
                    </div>
                    <hr color="" width="173px"/>
                </c:if>

                <div class="panel panel-success">
                    <div class="panel-heading" style="background-color: #79c9ec">
                        <a href="#tg5" class="panel-title" data-toggle="collapse" data-parent="#acc">
                            <center><span class="glyphicon glyphicon-envelope">反馈管理</span></center>
                        </a>
                    </div>
                    <div class="panel-collapse collapse" id="tg5">
                        <div class="panel-body">

                            <a href="#">
                                <button class="btn"
                                        style="width:100px;height:30px;margin-left: 16px;margin-bottom: 15px;background-color: #b2dba1">
                                    <center><span class="glyphicon glyphicon-comment">查看反馈</span></center>
                                </button>
                            </a>

                        </div>
                    </div>
                </div>
                <hr color="" width="173px"/>
                <c:if test="${sessionScope.adminLogin.content=='super'}">
                    <div class="panel panel-danger">
                        <div class="panel-heading">
                            <a href="#tg6" class="panel-title" data-toggle="collapse" data-parent="#acc">
                                <span class="glyphicon glyphicon-user">管理员账户管理</span>
                            </a>
                        </div>
                        <div class="panel-collapse collapse" id="tg6">
                            <div class="panel-body">

                                <button class="btn"
                                        style="width:100px;height:30px;background-color: #ffa608;margin-left: 16px;margin-bottom: 15px">
                                        <span class="glyphicon glyphicon-th-list"><a
                                                href="javascript:$('#main').load('${path}/main/adminShow.jsp')">管理账户</a></span></center>
                                </button>

                            </div>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
        <div class="col-md-10" id="main">


            <div class="jumbotron">
                <span style="font-size: 44px;color: #5e5e5e">欢迎来到应学视频App后台管理系统</span>
            </div>

            <!--轮播图-->
            <div id=" myCarousel" class="carousel slide" style="width: 947px;height: 270px">
                <!-- 轮播（Carousel）指标 -->
                <ol class="carousel-indicators">
                    <li data-target="#myCarousel" data-slide-to="0"
                        class="active"></li>
                    <li data-target="#myCarousel" data-slide-to="1"></li>
                    <li data-target="#myCarousel" data-slide-to="2"></li>
                    <li data-target="#myCarousel" data-slide-to="3"></li>
                </ol>
                <!-- 轮播（Carousel）项目 -->
                <div class="carousel-inner">
                    <div class="item active">
                        <img src="${path}/bootstrap/img/pic1.jpg" style="width: 947px;height: 270px" alt="">
                    </div>
                    <div class="item">
                        <img src="${path}/bootstrap/img/pic2.jpg" style="width: 947px;height: 270px" alt="">
                    </div>
                    <div class="item">
                        <img src="${path}/bootstrap/img/pic3.jpg" style="width: 947px;height: 270px" alt="">
                    </div>
                    <div class="item">
                        <img src="${path}/bootstrap/img/pic4.jpg" style="width: 947px;height:270px" alt="">
                    </div>
                </div>
                <!-- 轮播（Carousel）导航 -->
                <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>
        </div>
    </div>
    <div class="panel panel-default" style="margin-top:120px;height: 19px">
        <!-- 脚部 -->
        <div class="panel-footer">
            <span style="font-size: 13px;">
                <center>@百知教育 sunyf@163.com</center>
            </span>
        </div>
    </div>

</body>
</html>