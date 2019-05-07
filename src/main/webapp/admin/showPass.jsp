<%--
  Created by IntelliJ IDEA.
  User: insania
  Date: 2019-05-07
  Time: 15:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User password hash</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-light-blue">
<div class="w3-container w3-blue w3-opacity w3-right-align">
    <h1>User password hash: </h1><br /><br />
    <c:out value="${hashPass}"/><br /><br />
</div>
<div class="w3-container w3-center">
    <button class="w3-btn w3-round-large" onclick="location.href='/admin/list'">Back list</button><br /><br />
    <button class="w3-btn w3-round-large" onclick="location.href='/'">Back to main page</button><br /><br />
</div>
</body>
</html>