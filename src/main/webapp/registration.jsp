<%--
  Created by IntelliJ IDEA.
  User: insania
  Date: 2019-04-20
  Time: 16:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %><html><html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-light-blue">
<div class="w3-container w3-blue w3-opacity w3-left-align">
    <h2>Fill the form below to register:</h2>
</div>
<form method="post" class="w3-selection w3-light-blue w3-padding">
    <h7><c:out value="${instruction}"/></h7><br />
    <h7><c:out value="${violations}"/></h7><br /><br />
    <label> login:
    <input type="text" minlength="4" maxlength="16" name="login" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br /><br />
    </label>
    <label> password:
    <input type="password" minlength="6" maxlength="16" name="password" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br /><br />
    </label>
    <label> mail:
        <input type="email" name="mail" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br /><br />
    </label>
    <button type="submit" class="w3-btn w3-blue w3-round-large w3-margin-bottom">Submit</button>
</form>
    <button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/main'">Back to main</button>
    <button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/login'">Log in</button>
</body>
</html>
