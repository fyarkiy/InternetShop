<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All YOUR orders</title>
</head>
<body>
<table border="3">
    <tr>
        <th> User id</th>
        <th> Order id</th>
        <th> Order Details</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td><c:out value="${order.userId}"/>
            </td>
            <td><c:out value="${order.orderId}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/order/product?orderId=${order.orderId}">Details</a>
            </td>
        </tr>
    </c:forEach>
</table>
<br>
<a href="${pageContext.request.contextPath}/">Go to the main page</a>
</body>
</html>
