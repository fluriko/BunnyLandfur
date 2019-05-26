<%--
  Created by IntelliJ IDEA.
  User: insania
  Date: 2019-05-26
  Time: 21:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>My orders</title>
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
<div class="w3-container w3-right-align">
    <h9><c:out value="${user.login}"/>
        <button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/user/profile'">My profile</button>
        <button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/logout'">Log out</button>
    </h9>
</div>
<div class="w3-container w3-left-align">
    <h2>Your paid orders:</h2>
<c:forEach items="${orders}" var="order">
    <h3>Order <c:out value="${order.id}"/></h3>
    <table>
        <tr>
            <th>GOOD ID</th>
            <th>GOOD LABEL</th>
            <th>GOOD QUANTITY</th>
        </tr>
        <c:forEach items="${order.goods}" var="good">
            <tr>
                <td><c:out value="${good.id}"/></td>
                <td><c:out value="${good.label}"/></td>
                <td><c:out value="${good.quantity}"/></td>
            </tr>
        </c:forEach>
    </table>
</c:forEach>
</div><br />
<button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/main'">Back to main</button>
</body>
</html>
