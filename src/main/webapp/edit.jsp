<%@ page import="java.io.PrintWriter" %><%--
  Created by IntelliJ IDEA.
  User: insania
  Date: 2019-04-24
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit</title>
</head>
<body>
<%
    PrintWriter writer = response.getWriter();
String name = request.getParameter("name");
    writer.println("Edit user:  " + name + "!");
    request.setAttribute("name", name);
%>
</body>
<h2>Enter new password: </h2>
<form method="post" class="w3-selection w3-light-blue w3-padding">
<label> Password:
    <input type="password" name="password" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br /><br />
</label>
    <button type="submit" class="w3-btn w3-blue w3-round-large w3-margin-bottom">Submit</button>
</form>
</html>
