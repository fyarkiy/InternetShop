<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product List</title>
</head>
<body>
<h1>Hello Mate! Please, provide product details</h1>
<form method="post" action="${pageContext.request.contextPath}/productSetUp">
    Please provide product name: <input type="text" name="productName">
    Please provide product price: <input type="number" name="price">
    <br>
    <button type="submit">Create Product</button>
</form>
<br>
<a href="${pageContext.request.contextPath}/">Go to the main page</a>
</body>
</html>
