<%--
  Created by IntelliJ IDEA.
  User: insania
  Date: 2019-05-19
  Time: 18:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Cart</title>
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
    <h3>Your cart: </h3>
    <h7><c:out value="${message}"/></h7>
    <table id="t01">
        <tr>
            <th>Good</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Change Quantity</th>
            <th>Remove</th>
        </tr>
        <c:forEach items="${goodsInCart}" var="good">
            <tr>
                <td><c:out value="${good.label}"/></td>
                <td><c:out value="${good.price}"/></td>
                <td><c:out value="${good.quantity}"/></td>
                <td><a href='/user/changeQuantity?goodId=${good.id}'>Change Quantity</a></td>
                <td><a href='/user/removeGood?goodId=${good.id}'>Remove</a></td>
            </tr>
        </c:forEach>
    </table><br /><br />
    <table>
        <tr>
            <td>Total Price</td>
            <td><c:out value="${total}"/></td>
            <td><button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/user/buy'">Buy all in cart</button>
            </td>
            <td><button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/user/cleanCart'">Clean cart</button>
            </td>
        </tr>
    </table>
</div><br />
<button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/user/goods'">Back to goods</button>
<button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/main'">Back to main page</button>
</body>
</html>