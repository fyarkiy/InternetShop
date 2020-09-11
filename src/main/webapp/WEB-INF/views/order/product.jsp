<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Your order</title>
</head>
<body>
<table border="4">
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
<a href="${pageContext.request.contextPath}/user/order">Go to the list of your orders</a>
<br>
<a href="${pageContext.request.contextPath}/">Go to the main page</a>
</body>
</html>
