<<%@ page language="java" contentType="text/html; charset=UTF-8"
          pageEncoding="UTF-8" %>
<%
    pageContext.setAttribute("path", request.getContextPath());
%>
<!DOCTYPE HTML>
<html>
<head>
    <title>首页</title>
    <style type="text/css">
        a {
            text-decoration: none;
            color: black;
            font-size: 18px;
        }
        h3 {
            width: 180px;
            height: 38px;
            margin: 100px auto;
            text-align: center;
            line-height: 38px;
            background: deepskyblue;
            border-radius: 4px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    基于SSM框架的管理系统：简单实现增、删、改、查。
                </h1>
            </div>
        </div>
    </div>
</div>
<br><br>
<h3>
    <a href="${path}/paper/allPaper">点击进入管理页面</a>
</h3>
<form action="/FreshmanInfomationAnalysSystem/dataImport/read" method="post" class="form-horizontal" role="form" enctype="multipart/form-data">
    <div class="col-md-12">
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right">文件域：</label>
            <div class="col-sm-9">
                <input type="file"  name="dbfs" multiple="multiple"/>
            </div>
            <br>
            年：<input type="text" name="year", value="2019">
            省<input type="text" name="province", value="22">

            <input type="submit" value="点击上传">
        </div>
    </div>
</form>

</body>
</html>