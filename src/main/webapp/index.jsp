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
    <h1>Guest, we are glad to see you in BunnyLand!</h1>
    <h2>You can register of log in to have more actions</h2>
    <h7><c:out value="${message}"/></h7>
</div>
<br /><br />
<div class="w3-container w3-center">
    <div class="w3-bar w3-padding-large w3-padding-24">
    <button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/registration'"><h2>Register</h2></button>
        <button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/login'"><h2>Log in</h2></button>
</div>
        <div class="w3-container w3-center">
    <h2>Some cool paragraph</h2> <br />
    Some interesting text about bunnies, <br />
    And more...<br />
    And more...<br />
    </div>
</div>
</body>
</html>
