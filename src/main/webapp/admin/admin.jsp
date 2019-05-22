<%--
  Created by IntelliJ IDEA.
  User: insania
  Date: 2019-05-06
  Time: 23:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Admin page</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-light-blue">
<div class="w3-container w3-blue w3-opacity w3-right-align">
    <h9>Admin <c:out value="${user.login}"/>
        <button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/user/profile'">My profile</button>
        <button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/logout'">Log out</button>
    </h9>
</div>
<div class="w3-container w3-center">
    <br /><br />
    <h3>Main admin page, chose action:</h3>
    <br /><br />
    <button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/admin/list'">List of users</button><br /><br />
<button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/admin/goods'">List of goods</button><br /><br />
<button class="w3-btn w3-round-large" onclick="location.href='/main'">Back to main</button>
</div>
</body>
</html>
