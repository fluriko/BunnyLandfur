<%--
  Created by IntelliJ IDEA.
  User: insania
  Date: 2019-05-02
  Time: 17:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Confirm code</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-light-blue">
<div class="w3-container w3-blue w3-opacity w3-right-align">
    <h9><c:out value="${user.login}"/>
    <button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/user/profile'">My profile</button>
    <button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/logout'">Log out</button>
    </h9>
</div>
<form method="post" class="w3-selection w3-light-blue w3-padding">
    <h2>Confirm your purchase:</h2>
    <h7><c:out value="${message}"/></h7>
    <label> Enter your disposable code:
        <input type="password" name="codeValue" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br /><br />
    </label>
    <input type="hidden" name="codeId" value="<c:out value="${codeId}"/>">
    <button type="submit" class="w3-btn w3-blue w3-round-large w3-margin-bottom">Submit</button>
</form>
    <button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/user/goods'">Back to goods page</button>
    <button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/main'">Back to main page</button>
</body>
</html>

