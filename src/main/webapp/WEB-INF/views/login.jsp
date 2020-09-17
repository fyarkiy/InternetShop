<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Login</title>
</head>
<body>
<h1>Login page</h1>
<br>
<h4 style="color: red">${errorMsg}</h4>
<br>
<form action="${pageContext.request.contextPath}/login" method="post">
    Please provide your login: <input type="text" name="login">
    <br>
    <br>
    Please provide your password: <input type="password" name="pwd">
    <br>
    <br>
    <button type="submit">Login</button>
</form>
<br>
<a href="${pageContext.request.contextPath}/registration">Go to the Registration page</a>
<br>
<a href="${pageContext.request.contextPath}/product/all">Show All products</a>
<br>
<a href="${pageContext.request.contextPath}/">Go to the main page</a>
</body>
</html>
