<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>Hello Mate! Please provide your user details</h1>
<h4 style="color: darkred">${message}</h4>

<form method="post" action="${pageContext.request.contextPath}/registration">
    Please provide your name: <input type="text" name="userName">
    Please provide your login: <input type="text" name="login">
    <br>
    <br>
    Please provide your password: <input type="password" name="pwd">
    Please repeat your password: <input type="password" name="pwd-repeat">
    <button type="submit">Register</button>
</form>
<br>
<a href="${pageContext.request.contextPath}/">Go to the main page</a>
</body>
</html>
