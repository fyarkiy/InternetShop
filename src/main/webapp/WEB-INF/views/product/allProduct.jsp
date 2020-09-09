<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Product</title>
</head>
<body>
<h1>List of products</h1>
<table border="2">
    <tr>
        <th> Product Id</th>
        <th> Product Name</th>
        <th> Price</th>
        <th> Action</th>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>
                <c:out value="${product.id}"/>
            </td>
            <td>
                <c:out value="${product.productName}"/>
            </td>
            <td>
                <c:out value="${product.price}"/>
            </td>
            <td>
                <form method="get" action="${pageContext.request.contextPath}/shopcart/addProduct">
                    <input type="hidden" name="productId" value="${product.id}">
                    <button type="submit">I want this!</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<br>
<a href="${pageContext.request.contextPath}/">Go to the main page</a>
</body>
</html>
