<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查看日志</title>
    <!--引入jqGrid的css文件-->
    <link rel="stylesheet" type="text/css" href="${path}/jqGrid-js/jqgrid/css/ui.jqgrid.css"/>

    <!--引入css文件-->
    <link rel="stylesheet" type="text/css" href="${path}/bootstrap/css/bootstrap.css"/>

    <style type="text/css">


    </style>
    <!--引入jQuery-->
    <script src="${path}/bootstrap/js/jquery-3.3.1.min.js" type="text/javascript"></script>
    <!--引入js文件-->
    <script src="${path}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>

    <!--引入jqGrid的js文件-->
    <script src="${path}/jqGrid-js/jqgrid/js/jquery.jqGrid.js" type="text/javascript"></script>
    <script src="${path}/jqGrid-js/jqgrid/js/i18n/grid.locale-cn.js" type="text/javascript"></script>
    <script src="${path}/jqGrid-js/ajaxfileupload.js" type="text/javascript"></script>
    <script>
        $(function () {
            pageInit();
        });

        function pageInit() {
            $("#table1").jqGrid(
                {
                    url: "${path}/log/logPage",
                    rowNum: 10,
                    rowList: [10, 20, 30],
                    mtype: "post",
                    styleUI: "Bootstrap",
                    autowidth: true,
                    height: "auto",
                    pager: 'userpage',
                    sortname: 'createDate',
                    viewrecords: true,
                    caption: "查看日志",
                    datatype: "json",
                    colNames: ['ID', '用户名', '时间', '操作', '是否成功'],
                    colModel: [
                        {name: 'id', align: "center"},
                        {name: 'adminUsername', align: "center"},
                        {
                            name: 'createDate',
                            align: "center"
                        },

                        {name: 'operate', align: "center"},
                        {name: 'status', align: "center"}
                    ]

                }
            )
            ;
            $("#table1").jqGrid('navGrid', '#userpage', {
                edit: false,
                add: false,
                del: false,
                search: false
            });

        }
    </script>
</head>
<body>
<div class="panel panel-default">
    <div class="panel-heading" style="background-color: #ffceb6">
        日志管理
    </div>
    <div class="panel-body">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#">查看日志</a></li>
        </ul>
        </br>

        <table id="table1">
        </table>
        <div id="userpage" style="height: 60px;margin: auto;overflow: hidden;padding-right: 20px"></div>
    </div>
</div>
</body>
</html>
