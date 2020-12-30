<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>展示所有管理员</title>
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
            $("#table1").jqGrid({
                url: "${path}/admin/adminPage",
                editurl: "${path}/admin/edit",
                datatype: "json",
                rowNum: 2,
                rowList: [2, 10, 20, 30],
                pager: '#userpage',
                sortname: 'id',
                mtype: "post",
                styleUI: "Bootstrap",
                autowidth: true,
                height: "auto",
                viewrecords: true,
                colNames: ['ID', '用户名', '密码', '盐', '身份', '账号状态'],
                colModel: [
                    {name: 'id', align: "center"},
                    {name: 'username', align: "center", editable: true},
                    {name: 'password', align: "center", editable: true},
                    {name: 'salt', align: "center",},
                    {
                        name: 'content', align: "center", formatter: function (cellvalue, options, rowObject) {
                            if (cellvalue === "commen") {
                                return "<button class='btn btn-info'>普通管理员</button>";
                            } else {
                                return "<button class='btn btn-success'>超级管理员</button>";
                            }
                        }
                    },

                    {
                        name: 'status',
                        index: 'note',
                        align: "center",
                        formatter: function (cellvalue, options, rowObject) {
                            // console.info(rowObject);
                            if (cellvalue == "normal") {
                                //console.info("<button class='btn btn-success' onclick='updateUserStatus(" + rowObject.id + "," + rowObject.status + ")'>冻结</button>");
                                return "<button class='btn btn-success' onclick='updateUserStatus(\"" + rowObject.id + "\",\"" + rowObject.status + "\")'>冻结</button>";
                            } else {
                                return "<button class='btn btn-danger' onclick='updateUserStatus(\"" + rowObject.id + "\",\"" + rowObject.status + "\")'>解除冻结</button>";
                            }

                        }
                    }
                ]
            });
            $("#table1").jqGrid('navGrid', '#userpage', {
                    edit: true,
                    add: true,
                    del: true,
                    addtext: "添加",
                    edittext: "修改",
                    deltext: "删除"
                },
                {
                    closeAfterEdit: true
                },
                {
                    closeAfterAdd: true

                }, {});
        }

        //updateUserStatus
        function updateUserStatus(id, status) {
            //console.info(id);
            if (status == "normal") {
                $.post("${path}/admin/updateAdminStatus", {id: id, status: "lock"}, function () {
                    $("#table1").trigger("reloadGrid");
                }, "JSON")
            } else {
                $.post("${path}/admin/updateAdminStatus", {id: id, status: "normal"}, function () {
                    $("#table1").trigger("reloadGrid");
                }, "JSON")
            }
        }
    </script>
</head>
<body>
<div class="panel panel-default">
    <div class="panel-heading" style="background-color: #ffceb6">
        管理员账户信息
    </div>
    <div class="panel-body">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#">用户管理</a></li>
        </ul>
        </br>
        <div>
            <button class="btn btn-primary">导出用户信息</button>
            <button class="btn btn-info">导入用户</button>
            <button class="btn btn-warning">测试按钮</button>
        </div>
        </br>
        <table id="table1">

        </table>
        <div id="userpage" style="height: 60px;margin: auto;overflow: hidden;padding-right: 20px"></div>
    </div>
</div>

</body>
</html>
