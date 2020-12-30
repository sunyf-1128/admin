<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
    <!--引入jQuery-->
    <script src="${path}/bootstrap/js/jquery-3.3.1.min.js" type="text/javascript"></script>
    <script src="${path}/eCharts/echarts.min.js"></script>
    <script src="${path}/GoEasy/goeasy-1.2.0.js"></script>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main1" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    var myChart = echarts.init(document.getElementById('main1'));

    var goEasy = new GoEasy({
        host: 'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
        appkey: "BC-e037ab4e64d64ff6acb72bcf66a743ca", //替换为您的应用appkey
    });

    goEasy.subscribe({
        channel: "mychannel",//替换为您自己的channel
        onMessage: function (message) {  //获取的消息
            console.info(message);

        },

    });
    $.post("${path}/user/queryCountForMonth", {"year": "2020"}, function (data) {

        /*接收消息*/

        console.info(data.month.length);
        var month = [];
        var count = [];
        for (let i = 0; i < data.month.length; i++) {
            console.info(data.month);
            month.push(data.month[i]);
            count.push(data.count[i]);
        }
        //console.info(month.length);
        myChart.setOption({
            title: {
                text: '2020年月度注册量表'
            },
            tooltip: {},
            legend: {
                data: ['注册量']
            },
            xAxis: {
                data: month
            },
            yAxis: {},
            series: [{
                name: '注册量',
                type: 'bar',
                data: count
            }]
        });


    });


</script>

</body>
</html>
