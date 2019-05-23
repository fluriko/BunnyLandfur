<%--
  Created by IntelliJ IDEA.
  User: insania
  Date: 2019-05-07
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Admin Add Good</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-light-blue">
<div class="w3-container w3-blue w3-opacity w3-right-align">
    <h9>Admin <c:out value="${user.login}"/>
    <button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/user/profile'">My profile</button>
    <button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/logout'">Log out</button>
    </h9>
</div>
<form method="post" class="w3-selection w3-light-blue w3-padding">
    <h3>Enter data for new good: </h3><br /><br />
    <h7><c:out value="${message}"/></h7>
    <label> label:
        <input type="text" name="label" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br /><br />
    </label>
    <label> description:
        <input type="text" name="description" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br /><br />
    </label>
    <label> category:
        <input type="text" name="category" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br /><br />
    </label>
    <label> price:
        <input type="number" step="0.01" min="0.1" name="price" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br /><br />
    </label>
    <button type="submit" class="w3-btn w3-blue w3-round-large w3-margin-bottom">Submit</button>
</form>
<button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/admin/goods'">Back to goods list</button>
<button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/admin'">Back to admin page</button>
<button class="w3-btn w3-round-large" onclick="location.href='/main'">Back to main</button>
</body>
</html>