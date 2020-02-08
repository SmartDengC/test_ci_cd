<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>登陆</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.1.4.min.js"></script>
</head>
<body>
<h2>登陆界面</h2>
<%--这里的/login提交地址 是默认的表单认证过滤器的提交地址--%>
<form action="${pageContext.request.contextPath}/login" method="post" id="logform">
    用户名<input type="text" name="username"/>
    密码<input type="password" name="password"/>
    记住我<input type="checkbox" name="remember-me" value="true">
    <input type="button" id="logBtn" value="登陆">
</form>

<%--如果使用异步登陆--%>
<script type="text/javascript">
    $(function () {
        $("#logBtn").click(function () {
            $.post("${pageContext.request.contextPath}/login", $("#logform").serialize(), function (data) {
                if (data.success) {
                    window.location.href = "${pageContext.request.contextPath}/index";
                } else {
                    alert("登陆失败"+data.errorMsg);
                }
            }, "json");
        });
    })
</script>
</body>
</html>