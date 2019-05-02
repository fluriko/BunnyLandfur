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
</head>
<body >
<div class="w3-container w3-left-align">
    <h2><c:out value="${message}"/></h2>
    <h1>Choose what you want to buy!</h1>
</div>
<div class="w3-container w3-left-align">
    <table border='2' width='80%'>
        <tr>
            <th>ID</th>
            <th>label</th>
            <th>description</th>
            <th>category</th>
            <th>price</th>
            <th>action</th>
        </tr>
        <c:forEach items="${goods}" var="good">
            <tr>
                <td><c:out value="${good.id}"/></td>
                <td><c:out value="${good.label}"/></td>
                <td><c:out value="${good.description}"/></td>
                <td><c:out value="${good.category}"/></td>
                <td><c:out value="${good.price}"/></td>
                <td><a href='buy?goodId=${good.id}'>BUY IT!</a></td>
            </tr>
        </c:forEach>
    </table>
</div><br />
<button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/'">Back to main</button>
</body>
</html>