<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Orders">
    <p></p>
    <h3>Orders</h3>
    <table class="table">
        <thead class="thead-light">
        <tr class="row">
            <th class="col">Order number</th>
            <th class="col">Customer</th>
            <th class="col">Phone</th>
            <th class="col">Address</th>
            <th class="col">Date</th>
            <th class="col">Total price</th>
            <th class="col">Status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="order" items="${orderList}" varStatus="status">
            <tr class="row">
                <td class="col">
                    <a href="<c:url value="/admin/orders/${order.id}"/>">${order.id}</a>
                </td>
                <td class="col">${order.firstName} ${order.lastName}</td>
                <td class="col">${order.contactPhoneNo}</td>
                <td class="col">${order.deliveryAddress}"</td>
                <td class="col">
                    <fmt:parseDate value="${order.orderDate}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                    <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}" />
                </td>
                <td class="col">
                    <fmt:formatNumber value="${order.totalPrice}" type="currency" currencyCode="USD"/>
                </td>
                <td class="col">${order.status}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <p></p>
</tags:master>