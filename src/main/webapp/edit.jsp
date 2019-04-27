<%--
  Created by IntelliJ IDEA.
  User: insania
  Date: 2019-04-24
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit</title>
</head>
<body>
<h2>Enter new password for user: <%=request.getParameter("name")%> </h2>
<h2><c:out value="${error}"/></h2>
<form method="post" class="w3-selection w3-light-blue w3-padding">
<label> Password:
    <input type="password" name="password" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br /><br />
</label>
    <button type="submit" class="w3-btn w3-blue w3-round-large w3-margin-bottom">Submit</button>
</form>
</body>
</html>
