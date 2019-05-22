<%--
  Created by IntelliJ IDEA.
  User: insania
  Date: 2019-05-12
  Time: 16:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %><html><html>
<head>
    <title>Add user</title>
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
    <h3>Fill the form below to add new user: </h3>
    <h7><c:out value="${message}"/></h7>
    <label> Name:
        <input type="text" name="login" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br /><br />
    </label>
    <label> Password:
        <input type="password" name="password" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br /><br />
    </label>
    <label> Gmail:
        <input type="text" name="mail" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br /><br />
    </label>
    <label>Set role:</label><br /><br />
    <c:forEach items="${roles}" var="role">
        <input type="radio" name="role" value="<c:out value="${role.id}"/>" checked>   <c:out value="${role.name}"/><br /><br />
    </c:forEach>
    <button type="submit" class="w3-btn w3-blue w3-round-large w3-margin-bottom">Submit</button>
</form>
<button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/admin/list'">Back to users list</button>
<button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/admin'">Back to admin page</button>
    <button class="w3-btn w3-round-large" onclick="location.href='/main'">Back to main</button>
</body>
</html>