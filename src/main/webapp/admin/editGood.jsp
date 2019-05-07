<%--
  Created by IntelliJ IDEA.
  User: insania
  Date: 2019-05-07
  Time: 12:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Admin Edit Good</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-light-blue">
<div class="w3-container w3-blue w3-opacity w3-right-align">

    <h2>Enter new data for good: <c:out value="${id}"/> </h2>

</div>
<form method="post" class="w3-selection w3-light-blue w3-padding">
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
        <input type="text" name="price" class="w3-input w3-animate-input w3-border w3-round-large" style="width: 30%"><br /><br />
    </label>
    <button type="submit" class="w3-btn w3-blue w3-round-large w3-margin-bottom">Submit</button>
</form>
<button class="w3-btn w3-hover-blue w3-round-large" onclick="location.href='/admin'">Back to list</button><br /><br />
</body>
</html>

