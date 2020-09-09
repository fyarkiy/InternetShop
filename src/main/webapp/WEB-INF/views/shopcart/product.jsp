<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Products from Shopping Cart</title>
</head>
<body>
<h1> List of Selected Products from Shopping Cart </h1>
<h3> Customer </h3>
<table border="3">
    <tr>
        <th> Product Id</th>
        <th> Product Name</th>
        <th> Price</th>
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
        </tr>
    </c:forEach>
</table>
<br>
<a href="${pageContext.request.contextPath}/">Go to the main page</a>
</body>
</html>
