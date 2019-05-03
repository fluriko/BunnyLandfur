<%--
  Created by IntelliJ IDEA.
  User: insania
  Date: 2019-04-27
  Time: 18:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>List of users</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body >
<div class="w3-container w3-left-align">
    <h2><c:out value="${message}"/></h2>
    <h1>List of users</h1>
</div>
<div class="w3-container w3-left-align">
<table border='2' width='80%'>
<tr>
    <th>ID</th>
    <th>Name</th>
    <th>Password</th>
    <th>Role</th>
    <th>Mail</th>
    <th>Edit</th>
    <th>Delete</th>
</tr>
    <c:forEach items="${users}" var="user">
    <tr>
        <td><c:out value="${user.id}"/></td>
        <td><c:out value="${user.name}"/></td>
        <td><c:out value="${user.password}"/></td>
        <td><c:out value="${user.role}"/></td>
        <td><c:out value="${user.mail}"/></td>
        <td><a href='/edit?id=${user.id}'>edit all</a></td>
        <td><a href='/delete?name=${user.name}'>delete</a></td>
    </tr>
    </c:forEach>
</table>
</div><br />
<button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/registration'">Add new user</button><br /><br />
<button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/'">Back to main</button>
</body>
</html>
