<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 11.09.2020
  Time: 17:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List of Product for Admin</title>
</head>
<body>
<table border="12">
    <tr>
        <th> Product Id</th>
        <th> Product Name</th>
        <th> Price</th>
        <th> Action</th>
    </tr>

<c:forEach var = "product" items ="${products}">
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
            <a href="${pageContext.request.contextPath}/product/delete?id=${product.id}">Delete Product</a>
        </td>


</c:forEach>
</table>
<br>
<a href="${pageContext.request.contextPath}/product/create">Add new product to DB</a>
<br>
<br>
<a href="${pageContext.request.contextPath}/">Go to the main page</a>
</body>
</html>
