<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All orders in our Store</title>
</head>
<body>
<table border="7">
    <tr>
        <th> User id</th>
        <th> Order id</th>
        <th> Display Order</th>
        <th> Action</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td><c:out value="${order.userId}"/>
            </td>
            <td><c:out value="${order.orderId}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/order/product?orderId=${order.orderId}">Order Details</a>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/order/delete?id=${order.orderId}">Delete Order</a>
            </td>
        </tr>
    </c:forEach>
</table>
<br>
<a href="${pageContext.request.contextPath}/">Go to the main page</a>
</body>
</html>
