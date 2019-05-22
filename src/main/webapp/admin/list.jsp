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
    <style>
        table {
            width:100%;
        }
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }
        th, td {
            padding: 15px;
            text-align: left;
        }
        table#t01 tr:nth-child(even) {
            background-color: #eee;
        }
        table#t01 tr:nth-child(odd) {
            background-color: #fff;
        }
        table#t01 th {
            background-color: black;
            color: white;
        }
    </style>
</head>
<body >
<div class="w3-container  w3-opacity w3-right-align">
    <h4>Admin <c:out value="${user.login}"/>
        <button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/user/profile'">My profile</button>
        <button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/logout'">Log out</button>
    </h4>
</div>
<div class="w3-container w3-left-align">
    <h2><c:out value="${message}"/></h2>
    <h1>List of users</h1>
<table id="t01">
<tr>
    <th>ID</th>
    <th>Name</th>
    <th>Role</th>
    <th>Mail</th>
    <th>Edit</th>
    <th>Delete</th>
</tr>
    <c:forEach items="${users}" var="user">
    <tr>
        <td><c:out value="${user.id}"/></td>
        <td><c:out value="${user.login}"/></td>
        <td><c:out value="${user.role}"/></td>
        <td><c:out value="${user.mail}"/></td>
        <td><a href='/admin/edit?id=${user.id}'>edit</a></td>
        <td><a href='/admin/delete?id=${user.id}'>delete</a></td>
    </tr>
    </c:forEach>
</table>
</div><br />
<button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/admin/addUser'">Add new user</button><br /><br />
<button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/admin'">Back to admin</button><br /><br />
<button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/main'">Back to main</button><br /><br />
</body>
</html>
