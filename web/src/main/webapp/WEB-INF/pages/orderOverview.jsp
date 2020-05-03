<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Order Overview">
    <p></p>
    <c:choose>
        <c:when test="${empty order.orderItems}">
            <h3 class="text-danger">Order not found!</h3>
        </c:when>
        <c:otherwise>
            <h3 class="text-success">Thank you for your order!</h3>
            <p></p>
            <h5><b>Order number: ${order.id}</b></h5>
            <p></p>
            <table class="table">
                <thead class="thead-light">
                <tr class="row">
                    <th class="col">Brand</th>
                    <th class="col">Model</th>
                    <th class="col">Color</th>
                    <th class="col">Display size</th>
                    <th class="col">Quantity</th>
                    <th class="col">Price</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${order.orderItems}" varStatus="status">
                    <tr class="row">
                        <td class="col">${item.phone.brand}</td>
                        <td class="col">${item.phone.model}</td>
                        <td class="col">
                            <c:forEach var="color" items="${item.phone.colors}">
                                ${color.code}
                            </c:forEach>
                        </td>
                        <td class="col">${item.phone.displaySizeInches}"</td>
                        <td class="col">${item.quantity}</td>
                        <td class="col">
                            <fmt:formatNumber value="${item.phone.price}" type="currency" currencySymbol="$"/>
                        </td>
                    </tr>
                </c:forEach>
                <tr class="row justify-content-end">
                    <td class="col-2 table-info"><b>Subtotal: </b></td>
                    <td class="col-2 table-info">
                        <fmt:formatNumber value="${order.subtotal}" type="currency" currencySymbol="$"/>
                    </td>
                </tr>
                <tr class="row justify-content-end">
                    <td class="col-2 table-info"><b>Delivery: </b></td>
                    <td class="col-2 table-info">
                        <fmt:formatNumber value="${order.deliveryPrice}" type="currency" currencySymbol="$"/>
                    </td>
                </tr>
                <tr class="row justify-content-end">
                    <td class="col-2 table-success"><b>TOTAL PRICE: </b></td>
                    <td class="col-2 table-success">
                        <fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="$"/>
                    </td>
                </tr>
                </tbody>
            </table>
            <table class="table">
                <tr class="row">
                    <td class="col-1"><b>First name:</b></td>
                    <td class="col-2">${order.firstName}</td>
                </tr>
                <tr class="row">
                    <td class="col-1"><b>Last name:</b></td>
                    <td class="col-2">${order.lastName}</td>

                </tr>
                <tr class="row">
                    <td class="col-1"><b>Address:</b></td>
                    <td class="col-2">${order.deliveryAddress}</td>
                </tr>
                <tr class="row">
                    <td class="col-1"><b>Phone:</b></td>
                    <td class="col-2">${order.contactPhoneNo}</td>
                </tr>
            </table>
            <br>
        </c:otherwise>
    </c:choose>
    <a class="btn btn-outline-primary btn-lg" href="<c:url value="/productList"/>" role="button">Back to shopping</a>
    <p></p>
</tags:master>