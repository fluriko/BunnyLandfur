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
    <h1>Confirm your purchase</h1>
    <h2><c:out value="${message}"/></h2>
</div>
<form method="post" class="w3-selection w3-light-blue w3-padding">
    <label> Enter your disposable code:
        <input type="password" name="codeValue" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br /><br />
    </label>

    <button type="submit" class="w3-btn w3-blue w3-round-large w3-margin-bottom">Submit</button>
</form>
<div class="w3-container w3-blue w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='/'">Back to main page</button>
    <button class="w3-btn w3-round-large" onclick="location.href='/goods'">Back to goods page</button>
</div>
</body>
</html>

