<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>GoEasy</title>
    <!--引入jQuery-->
    <script src="${path}/bootstrap/js/jquery-3.3.1.min.js" type="text/javascript"></script>

    <script src="${path}/GoEasy/goeasy-1.2.0.js"></script>
    <script>
        /*初始化GoEasy对象*/
        var goEasy = new GoEasy({
            host: 'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
            appkey: "BC-e037ab4e64d64ff6acb72bcf66a743ca", //替换为您的应用appkey
        });

        /*接收消息*/
        goEasy.subscribe({
            channel: "mychannel",//替换为您自己的channel
            onMessage: function (message) {  //获取的消息
                console.info("Channel:" + message.channel + " content:" + message.content);
            },
            onSuccess: function () {

            },
            onFailed: function (error) {
                console.log("Channel订阅失败, 错误编码：" + error.code + " 错误信息：" + error.content)
            }
        });

    </script>
</head>
<body>

</body>
</html>
