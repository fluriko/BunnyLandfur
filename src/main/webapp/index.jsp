<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>BunnyLand.fur</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-light-blue">
<div class="w3-container w3-blue w3-opacity w3-right-align">
    <h1>We are glad to see you in BunnyLand!</h1>
    <h2><c:out value="${message}"/></h2>
</div>
<c:if test = "${user == null}">
<div class="w3-container w3-center">
    <div class="w3-bar w3-padding-large w3-padding-24">
    <button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/registration'">Register</button><br /><br />
    <button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/login'">Log in</button><br /><br />
</div>
    </c:if>
    <div class="w3-container w3-center">
    <h2>Some cool paragraph</h2> <br />
    Some interesting text about bunnies, <br />
    And more...<br />
    And more...<br />
    </div>
<c:if test = "${user != null}">
    <div class="w3-container w3-center">
        <h2>Some special content for user</h2> <br />
        Secret information <br /><br /><br />
        <button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/logout'">Log out</button><br /><br />
    </div>
    </c:if>
</div>
<div class="w3-container w3-center">
    <button class="w3-btn w3-round-large" onclick="location.href='/goods'">ALL GOODS</button><br /><br />
</div>
</body>
</html>
