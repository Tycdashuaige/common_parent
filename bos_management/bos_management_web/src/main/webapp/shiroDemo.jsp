<%--
  Created by IntelliJ IDEA.
  User: tangyucong
  Date: 2018/8/7
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<shiro:authenticated>
   <h1>认证</h1> 
</shiro:authenticated>

<shiro:hasPermission name="area">
    area权限认证
</shiro:hasPermission>

<shiro:hasRole name="zs">
    admin角色
</shiro:hasRole>
</body>
</html>
