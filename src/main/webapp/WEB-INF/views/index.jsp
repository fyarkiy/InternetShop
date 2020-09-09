<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Hello Mate!</h1>
<br>
<a href="${pageContext.request.contextPath}/registration">Add user to DB</a>
<br>
<a href="${pageContext.request.contextPath}/user/all">Show All users</a>
<br>
<a href="${pageContext.request.contextPath}/product/setUp">Add product to DB</a>
<br>
<a href="${pageContext.request.contextPath}/product/all">Show All products</a>
<br>
<a href="${pageContext.request.contextPath}/shopcart/product">Show products from Shopping Cart</a>
<br>
<a href="${pageContext.request.contextPath}/injectData">Add mock data</a>
</body>
</html>
