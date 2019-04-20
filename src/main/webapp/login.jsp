<%--
  Created by IntelliJ IDEA.
  User: insania
  Date: 2019-04-20
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log In</title>
</head>
<body>
<h1>Fill the form below to log in!</h1>
<form method="post">
    <label> Name:
        <input type="text" name="name"><br /><br />
    </label>
    <label> Password:
        <input type="password" name="password"><br /><br />
    </label>
    <button type="submit">Submit</button>
</form>
<div>
    <button onclick="location.href='/'">Back to main</button>
</div>
</body>
</html>
