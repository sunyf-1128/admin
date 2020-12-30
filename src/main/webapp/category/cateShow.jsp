<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>展示类别信息</title>
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
                    url: "${path}/cate/cateOnePage",
                    editurl: "${path}/cate/editOne",
                    datatype: "json",
                    rowNum: 8,
                    rowList: [8, 10, 20, 30],
                    pager: '#userpage',
                    styleUI: "Bootstrap",
                    sortname: 'id',
                    mtype: 'post',
                    autowidth: true,
                    height: 'auto',
                    viewrecords: true,
                    subGrid: true,
                    colNames: ['ID', '类别名', '级别', '上级类别ID'],
                    colModel: [
                        {name: 'id', width: 55, align: "center"},
                        {name: 'name', width: 100, align: "center", editable: true},
                        {name: 'levels', width: 80, align: "center"},
                        {name: 'parentId', width: 80, align: "center"},
                    ],
                    subGridRowExpanded: function (subgrid_id, row_id) {
                        var subgrid_table_id = subgrid_id + "_t";
                        var pager_id = "p_" + subgrid_table_id;
                        $("#" + subgrid_id).html(
                            "<table id='" + subgrid_table_id
                            + "' class='scroll'></table><div id='"
                            + pager_id + "' class='scroll' style='height: 40px'></div>");
                        $("#" + subgrid_table_id).jqGrid(
                            {
                                url: "${path}/cate/cateTwoPage?parentId=" + row_id,
                                editurl: "${path}/cate/editTwo?parentId=" + row_id,
                                datatype: "json",
                                colNames: ['ID', '类别名', '级别', '上级类别ID'],
                                colModel: [
                                    {name: 'id', width: 55, align: "center"},
                                    {name: 'name', width: 100, align: "center", editable: true},
                                    {name: 'levels', width: 80, align: "center"},
                                    {name: 'parentId', width: 80, align: "center"},
                                ],
                                rowNum: 8,
                                rowList: [8, 10, 20, 30],
                                pager: pager_id,
                                sortname: 'id',
                                styleUI: "Bootstrap",
                                sortname: 'id',
                                mtype: 'post',
                                autowidth: true,
                                height: 'auto'
                            });
                        $("#" + subgrid_table_id).jqGrid('navGrid',
                            "#" + pager_id, {
                                edit: true,
                                add: true,
                                del: true, edittext: "编辑", addtext: "添加", deltext: "删除"
                            }, {
                                closeAfterEdit: true,
                                afterSubmit: function (data) {
                                    //console.info(data);
                                    alert(data.responseText);
                                }
                            }, {
                                closeAfterAdd: true,
                                afterSubmit: function (data) {
                                    //console.info(data);
                                    alert(data.responseText);
                                }
                            }, {
                                closeAfterDel: true,
                                afterSubmit: function (data) {
                                    //console.info(data);

                                    alert(data.responseText);
                                }
                            });
                    }

                });
            $("#table1").jqGrid('navGrid', '#userpage', {
                    add: true, edit: true, del: true, addtext: "添加", edittext: "编辑", deltext: "删除"
                }, {
                    closeAfterAdd: true,
                    afterSubmit: function (data) {
                        //console.info(data);
                        alert(data.responseText);
                        $("#table1").trigger("reloadGrid");
                    }
                },
                {
                    closeAfterEdit: true,
                    afterSubmit: function (data) {
                        //console.info(data);
                        alert(data.responseText);
                        $("#table1").trigger("reloadGrid");
                    }
                }, {
                    closeAfterDel: true,
                    afterSubmit: function (data) {
                        //console.info(data);
                        alert(data.responseText);
                        $("#table1").trigger("reloadGrid");
                    }
                });
        }


    </script>
</head>
<body>
<div class="panel panel-default">
    <div class="panel-heading" style="background-color: #ffceb6">
        类别管理
    </div>
    <div class="panel-body">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#">类别管理</a></li>
        </ul>
        </br>

        <table id="table1">

        </table>
        <div id="userpage" style="height: 60px;margin: auto;overflow: hidden;padding-right: 20px"></div>
    </div>

</body>
</html>
