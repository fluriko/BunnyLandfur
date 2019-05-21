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
</head>
<body >
<div class="w3-container w3-left-align">
    <h4><c:out value="${user.login}"/>
        <button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/user/profile'">My profile</button>
        <button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/logout'">Log out</button>
    </h4>
    <h2><c:out value="${message}"/></h2>
    <h1>Your cart: </h1>
</div>
<div class="w3-container w3-left-align">
    <table border='2' width='80%'>
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
    <table border='2' width='80%'>
        <tr>
            <th>Total Price</th>
            <th><c:out value="${total}"/></th>
        </tr>
    </table>
</div><br />
<button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/user/buy'">Buy all in cart</button><br /><br />
<button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/user/cleanCart'">Clean cart</button><br /><br />
<button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/user/goods'">Back to goods</button><br /><br />
<button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/main'">Back to main page</button><br /><br />
</body>
</html>