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
    <h9><c:out value="${user.login}"/>
        <button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/user/profile'">My profile</button>
    <button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/logout'">Log out</button>
</div>
<br /><br />
        <div class="w3-container w3-center">
            <h7><c:out value="${message}"/></h7>
            <h2><c:out value="${user.login}"/>, we are glad to see you in BunnyLand!</h2>
            <button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/user/goods'"><h1>Goods catalog</h1></button><br /><br />
            <button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/user/cart'"><h2>My cart</h2></button>
    <h2>Some cool paragraph</h2> <br />
    Some interesting text about bunnies, <br />
    And more...<br />
    And more...<br />
        <h2>Some special content for user</h2> <br />
        Secret information <br /><br /><br />
    </div>
</div>
</body>
</html>
