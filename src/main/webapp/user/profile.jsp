<%--
  Created by IntelliJ IDEA.
  User: insania
  Date: 2019-05-17
  Time: 15:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User Profile</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-light-blue">
<div class="w3-container w3-blue w3-opacity w3-right-align">

    <h2>You can change you info: <c:out value="${id}"/> </h2>
    <h2><c:out value="${message}"/> </h2>
</div>
<form method="post" class="w3-selection w3-light-blue w3-padding">
    <label> login:
        <input type="text" name="login" value = "<c:out value="${user.login}"/>" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br /><br />
    </label>
    <label> password:
        <input type="password" name="password" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br /><br />
    </label>
    <label> mail:
        <input type="text" name="mail" value = "<c:out value="${user.mail}"/>" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br /><br />
    </label>

    <button type="submit" class="w3-btn w3-blue w3-round-large w3-margin-bottom">Submit</button>
</form>
<button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/'">Back to main page</button><br /><br />
</body>
</html>
