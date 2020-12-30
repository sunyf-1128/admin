<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>展示所有视频</title>


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
                    url: "${path}/video/videoPage",
                    // editurl: ctx + "/RowEditing",
                    datatype: "json",
                    rowNum: 3,
                    rowList: [3, 10, 20, 30],
                    pager: '#userpage',
                    sortname: 'id',
                    viewrecords: true,
                    height: "auto",
                    mtype: "post",
                    autowidth: true,
                    styleUI: "Bootstrap",
                    colNames: ['ID', '名称', '视频', '上传时间', '描述',
                        '所属类别', '类别ID', '用户ID'],
                    colModel: [
                        {name: 'id', editable: false, editoptions: {readonly: true, size: 10}, align: "center",},
                        {name: 'title', editable: false, editoptions: {size: 10}, align: "center",},
                        {
                            name: 'address',
                            editable: false,
                            edittype: "file",
                            editoptions: {size: 25},
                            align: "center",
                            formatter: function (cellvalue, options, rowObject) {
                                return "<video src='" + cellvalue + "' style='width: 200px;height: 100px' controls='controls' poster='" + rowObject.cover + "'>";
                            }
                        },
                        {name: 'createDate', align: "center", editable: false, editoptions: {size: 10}},
                        {name: 'intro', align: "center", editable: false, editoptions: {size: 10}},
                        {name: 'cateName', align: "center", editable: false, editoptions: {size: 10}},
                        {name: 'categoryId', align: 'center', editable: false, editoptions: {size: 10}},
                        {name: 'userId', align: 'center', editable: false, editoptions: {value: "FE:FedEx;TN:TNT"}},
                    ]
                });
            $("#table1").jqGrid('navGrid', '#userpage',
                {edit: false, add: false, del: false, search: false, edittext: "编辑", addtext: "添加", deltext: "删除"}, //options
                {closeAfterEdit: false, reloadAfterSubmit: false}, // edit options
                {closeAfterAdd: false, reloadAfterSubmit: false}, // add options
                {closeAfterDel: false, reloadAfterSubmit: false}, // del options
                {}// search options
            );
        }


        <!--模态框开始-->
        <!--添加视频 开始-->
        <!--获取所有一级类别-->
        function getCategoryOne() {
            $("#categoryOne").empty();
            $.ajax({
                url: "${path}/cate/queryAllOne",
                dataType: "json",
                type: "post",
                success: function (data) {
                    var content = "";
                    $.each(data, function (i) {
                        content += "<option value='" + data[i].id + "' id='" + data[i].id + "' title='" + data[i].name + "'>" + data[i].name + "</option>";
                    });
                    $("#categoryOne").append(content);
                }
            });
        };

        <!--获取所选定一级类别下的二级类别-->
        function getTwoCate(parentId) {
            console.info("一级分类的id：" + parentId);
            $("#categoryTwo").empty();
            $.ajax({
                url: "${path}/cate/cateTwoForParentId",
                dataType: "json",
                data: {"parentId": parentId},
                type: "post",
                success: function (data) {
                    var content = "";
                    $.each(data, function (i) {
                        content += "<option value='" + data[i].id + "' id='" + data[i].id + "'  title='" + data[i].name + "'>" + data[i].name + "</option>";
                    });
                    $("#categoryTwo").append(content);
                }
            });
        };

        <!--模态框上传视频-->
        function uploadVideo() {
            $.ajaxFileUpload({
                url: "${path}/video/addVideo",
                //携带参数给后台
                data: {"title": $("#title").val(), "intro": $("#intro").val(), "categoryId": $("#categoryTwo").val()},
                secureuri: false,
                fileElementId: "fileVideo",
                type: "post",
                dataType: "json",
                success: function (data) {
                    //获取并处理返回的数据
                    var obj = $.parseJSON(data.responseText.replace(/<.*?>/ig, ""));
                    console.info(obj);
                    if (obj.status == 200) {
                        //1s后关闭模态框
                        setTimeout(function () {
                            $('#myModal').modal('hide');
                            $(".modal-backdrop").remove();//必须设置否则页面会有阴影，处于无法选择的状态
                            $("body").removeClass('modal-open');//必须设置否则页面会有阴影，处于无法选择的状态
                        }, 1);
                        $("#message").attr("style", "color:green");
                        $("#message").text(obj.message);
                        $("#table1").trigger("reloadGrid")
                    }
                },
                error: function (data) {
                    var obj = $.parseJSON(data.responseText.replace(/<.*?>/ig, ""));
                    console.info(obj);
                    if (obj.status == 400) {
                        //1s后关闭模态框
                        setTimeout(function () {
                            $('#myModal').modal('hide');
                            $(".modal-backdrop").remove();
                            $("body").removeClass('modal-open');
                        }, 1);
                        $("#message").attr("style", "color:red");
                        $("#message").text(obj.message);
                        $("#table1").trigger("reloadGrid")
                        setTimeout(function () {
                            location.href = "${path}/login/login.jsp";
                        }, 5000);
                    }
                }
            });
        }


        <!--添加视频 结束-->

        <!--修改视频开始-->

        <!--获取所有一级类别-->
        function getCategoryOne1() {
            var rowId = $("#table1").jqGrid("getGridParam", "selrow");
            if (rowId == null) {
                alert("请选择您要修改视频所在行");
                //1s后关闭模态框
                setTimeout(function () {
                    $('#myModal1').modal('hide');
                    $(".modal-backdrop").remove();//必须设置否则页面会有阴影，处于无法选择的状态
                    $("body").removeClass('modal-open');//必须设置否则页面会有阴影，处于无法选择的状态
                }, 1);
            } else {
                // alert(rowId);
                $.ajax({
                    url: "${path}/video/queryOneVideo",
                    data: {"id": rowId},
                    type: "post",
                    dataType: "json",
                    success: function (data1) {
                        $("#videoId").val(data1.id);
                        $("#title1").val(data1.title);
                        $("#intro1").val(data1.intro);
                        $("#categoryOne1").empty();
                        $.ajax({
                            url: "${path}/cate/queryAllOne",
                            dataType: "json",
                            type: "post",
                            success: function (data) {
                                var content = "";
                                $.each(data, function (i) {
                                    content += "<option value='" + data[i].id + "' id='" + data[i].id + "' title='" + data[i].name + "'>" + data[i].name + "</option>";
                                });
                                $("#categoryOne1").append(content);
                            }
                        });
                    }
                });
            }
        };

        <!--获取所选定一级类别下的二级类别-->
        function getTwoCate1(parentId) {
            console.info("一级分类的id：" + parentId);
            $("#categoryTwo1").empty();
            $.ajax({
                url: "${path}/cate/cateTwoForParentId",
                dataType: "json",
                data: {"parentId": parentId},
                type: "post",
                success: function (data) {
                    var content = "";
                    $.each(data, function (i) {
                        content += "<option value='" + data[i].id + "' id='" + data[i].id + "'  title='" + data[i].name + "'>" + data[i].name + "</option>";
                    });
                    $("#categoryTwo1").append(content);
                }
            });
        };

        <!--模态框上传视频-->
        function uploadVideo1() {
            $.ajaxFileUpload({
                url: "${path}/video/updateVideo",
                //携带参数给后台
                data: {
                    "id": $("#videoId").val(),
                    "title": $("#title1").val(),
                    "intro": $("#intro1").val(),
                    "categoryId": $("#categoryTwo1").val()
                },
                secureuri: false,
                fileElementId: "fileVideo1",
                type: "post",
                dataType: "json",
                success: function (data) {
                    //获取并处理返回的数据
                    var obj = $.parseJSON(data.responseText.replace(/<.*?>/ig, ""));
                    //console.info(obj.status === "200");
                    if (obj.status) {
                        //1s后关闭模态框
                        setTimeout(function () {
                            $('#myModal1').modal('hide');
                            $(".modal-backdrop").remove();//必须设置否则页面会有阴影，处于无法选择的状态
                            $("body").removeClass('modal-open');//必须设置否则页面会有阴影，处于无法选择的状态
                        }, 1);
                        $("#message").attr("style", "color:green");
                        $("#message").text(obj.message);
                        $("#table1").trigger("reloadGrid")
                    }

                },
                error: function (data) {
                    var obj = $.parseJSON(data.responseText.replace(/<.*?>/ig, ""));
                    //console.info(obj.status === "400");
                    if (obj.status) {
                        //1s后关闭模态框
                        setTimeout(function () {
                            $('#myModal1').modal('hide');
                            $(".modal-backdrop").remove();//必须设置否则页面会有阴影，处于无法选择的状态
                            $("body").removeClass('modal-open');//必须设置否则页面会有阴影，处于无法选择的状态
                        }, 1);
                        $("#message").attr("style", "color:red");
                        $("#message").text(obj.message);
                        $("#table1").trigger("reloadGrid")
                    }
                }
            });
        }

        <!--修改视频结束-->
        <!--模态框结束-->
        <!--删除视频开始-->
        function deleteVideo() {
            var rowId = $("#table1").jqGrid("getGridParam", "selrow");
            if (rowId == null) {
                alert("请选择您要删除的视频所在行");
            } else {
                // alert(rowId);
                $.ajax({
                    url: "${path}/video/deleteVideo",
                    data: {"id": rowId},
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        console.info(data.responseText);
                        $("#message").attr("style", "color:red");
                        $("#message").text(data.responseText);
                        $("#table1").trigger("reloadGrid");
                    }
                })
            }
        }

        <!--删除视频结束-->
    </script>
</head>
<body>
<!--面板-->
<div class="panel panel-default">
    <div class="panel-heading" style="background-color: #ffceb6">
        视频信息
    </div>
    <div class="panel-body">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#">视频信息</a></li>
        </ul>
        </br>


        <div class="container">
            <!--模态框： 添加视频   开始-->
            <button class="btn btn-primary" data-toggle="modal" data-target="#myModal" onclick="getCategoryOne()">添加视频
            </button>
            <!--  定义模态框触发器，此处为按钮触发  -->
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                 aria-hidden="true">
                <!--  定义模态框，过渡效果为淡入，id为myModal,tabindex=-1可以禁用使用tab切换，aria-labelledby用于引用模态框的标题，aria-hidden=true保持模态框在触发前窗口不可见  -->
                <div class="modal-dialog">
                    <!--  显示模态框对话框模型（若不写下一个div则没有颜色）  -->
                    <div class="modal-content">
                        <!--  显示模态框白色背景，所有内容都写在这个div里面  -->

                        <div class="btn-info modal-header">
                            <!--  模态框标题  -->
                            <button type="button" class="close" data-dismiss="modal" id="close">&times;</button>
                            <!--  关闭按钮  -->
                            <h4>您好，欢迎使用添加视频功能</h4>
                            <!--  标题内容  -->
                        </div>
                        <div class="modal-body">
                            <!--  模态框内容，我在此处添加一个表单 -->
                            <form class="form-horizontal" role="form">
                                <!--视频标题-->
                                <div class="form-group">
                                    <label for="title" class="col-sm-2 control-label">视频标题</label>
                                    <div class="col-sm-9">
                                        <input type="text" id="title" name="title" class="form-control well"
                                               placeholder="请输入视频标题...." required="required"/>
                                    </div>
                                </div>
                                <!--视频简介-->
                                <div class="form-group">
                                    <label for="intro" class="col-sm-2 control-label">视频简介</label>
                                    <div class="col-sm-9">
                                        <input type="text" id="intro" name="intro" class="form-control well"
                                               placeholder="请输入视频简介...." required="required"/>
                                    </div>
                                </div>
                                <!--视频简介-->
                                <div class="form-group">
                                    <label for="fileVideo" class="col-sm-2 control-label">选择视频</label>
                                    <div class="col-sm-9">
                                        <input type="file" id="fileVideo" name="fileVideo" class="form-control"
                                               required="required"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="categoryOne" class="col-sm-2 control-label">所属分类</label>
                                    <div class="col-sm-9">
                                        <select id="categoryOne" name="cateName" class="form-control"
                                                onchange="getTwoCate(this.value)">
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="categoryTwo" class="col-sm-2 control-label">CategoryId</label>
                                    <div class="col-sm-9">
                                        <select id="categoryTwo" name="categoryId" class="form-control">
                                        </select>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <!--  模态框底部样式，一般是提交或者确定按钮 -->
                            <button type="button" class="btn btn-info" onclick="uploadVideo()">确定</button>
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        </div>
                    </div><!-- /.modal-content -->
                </div>
            </div> <!-- /.modal -->
            <!--模态框： 添加视频   结束-->


            &nbsp;&nbsp;
            <!--模态框： 修改视频   开始-->
            <button class="btn btn-info" data-toggle="modal" data-target="#myModal1" onclick="getCategoryOne1()">修改视频
            </button>
            <!--  定义模态框触发器，此处为按钮触发  -->
            <form method="post" action="${path}/video/addVideo" class="form-horizontal" role="form" id="myForm1"
                  enctype="multipart/form-data" onsubmit="return">
                <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                     aria-hidden="true">
                    <!--  定义模态框，过渡效果为淡入，id为myModal,tabindex=-1可以禁用使用tab切换，aria-labelledby用于引用模态框的标题，aria-hidden=true保持模态框在触发前窗口不可见  -->
                    <div class="modal-dialog">
                        <!--  显示模态框对话框模型（若不写下一个div则没有颜色）  -->
                        <div class="modal-content">
                            <!--  显示模态框白色背景，所有内容都写在这个div里面  -->
                            <div class="btn-info modal-header">
                                <!--  模态框标题  -->
                                <button type="button" class="close" data-dismiss="modal" id="close1">&times;</button>
                                <!--  关闭按钮  -->
                                <h4>您好，欢迎使用修改视频信息功能</h4>
                                <!--  标题内容  -->
                            </div>

                            <div class="modal-body">
                                <!--  模态框内容，我在此处添加一个表单,此表单没用不起作用，完全就是为了样式而存在 -->
                                <form class="form-horizontal" role="form">
                                    <!--视频ID-->
                                    <div class="form-group">
                                        <label for="title1" class="col-sm-2 control-label">视频标题</label>
                                        <div class="col-sm-9">
                                            <input type="text" id="videoId" name="id" class="form-control well"
                                                   readonly="readonly"/>
                                        </div>
                                    </div>
                                    <!--视频标题-->
                                    <div class="form-group">
                                        <label for="title1" class="col-sm-2 control-label">视频标题</label>
                                        <div class="col-sm-9">
                                            <input type="text" id="title1" name="title" class="form-control well"
                                                   placeholder="请输入视频标题...." required="required"/>
                                        </div>
                                    </div>
                                    <!--视频简介-->
                                    <div class="form-group">
                                        <label for="intro1" class="col-sm-2 control-label">视频简介</label>
                                        <div class="col-sm-9">
                                            <input type="text" id="intro1" name="intro" class="form-control well"
                                                   placeholder="请输入视频简介...." required="required"/>
                                        </div>
                                    </div>
                                    <!--视频简介-->
                                    <div class="form-group">
                                        <label for="fileVideo1" class="col-sm-2 control-label">选择视频</label>
                                        <div class="col-sm-9">
                                            <input type="file" id="fileVideo1" name="fileVideo1" class="form-control"
                                                   required="required"/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="categoryOne1" class="col-sm-2 control-label">所属分类</label>
                                        <div class="col-sm-9">
                                            <select id="categoryOne1" name="cateName" class="form-control"
                                                    onchange="getTwoCate1(this.value)">
                                            </select>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="categoryTwo1" class="col-sm-2 control-label">CategoryId</label>
                                        <div class="col-sm-9">
                                            <select id="categoryTwo1" name="categoryId" class="form-control">
                                            </select>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <!--  模态框底部样式，一般是提交或者确定按钮 -->
                                <button type="button" class="btn btn-info" onclick="uploadVideo1()">确定</button>
                                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            </div>
                        </div><!-- /.modal-content -->
                    </div>
                </div> <!-- /.modal -->
                <!--删除视频  开始-->
                <div style="float:top;margin-top: -34px;margin-left:195px">
                    <button class="btn btn-danger" data-toggle="modal" data-target="#myModa3" onclick="deleteVideo()">
                        删除视频
                    </button>
                </div>
                <!--删除视频  结束-->
            </form>
            <!--模态框： 修改视频   结束-->


        </div>

        </br>
        <div>
            <span id="message"></span>
        </div>
        <table id="table1">

        </table>
        <div id="userpage" style="height: 60px;margin: auto;overflow: hidden;padding-right: 20px"></div>
    </div>
</div>

</body>
</html>
