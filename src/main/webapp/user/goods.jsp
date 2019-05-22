<%--
  Created by IntelliJ IDEA.
  User: insania
  Date: 2019-05-02
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>All goods</title>
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
<div class="w3-container w3-right-align">
<h7><c:out value="${cartInfo}"/> goods in your cart</h7><br />
    <button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/user/cart'"> Go to cart</button>
</div>
<div class="w3-container w3-left-align">
    <h3>Choose what you want to buy:</h3>
    <h7><c:out value="${message}"/></h7><br /><br />
    <table id="t01">
        <tr>
            <th>ID</th>
            <th>label</th>
            <th>description</th>
            <th>category</th>
            <th>price</th>
            <th>add to cart</th>
        </tr>
        <c:forEach items="${goods}" var="good">
            <tr>
                <td><c:out value="${good.id}"/></td>
                <td><c:out value="${good.label}"/></td>
                <td><c:out value="${good.description}"/></td>
                <td><c:out value="${good.category}"/></td>
                <td><c:out value="${good.price}"/></td>
                <td><a href='/user/addToCart?goodId=${good.id}'>ADD TO CART</a></td>
            </tr>
        </c:forEach>
    </table>
</div><br />
<button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/main'">Back to main</button>
</body>
</html>