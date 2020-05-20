<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<tags:master pageTitle="Order Details">
    <p></p>
    <div class="row justify-content-between">
        <h4 class="col-2">Order number: <b>${order.id}</b></h4>
        <h4 class="col-2">Order status: <b>${order.status}</b></h4>
    </div>
    <p></p>
    <table class="table">
        <thead class="thead-light">
        <tr class="row">
            <th class="col">Image</th>
            <th class="col">Name</th>
            <th class="col">Price</th>
            <th class="col">Quantity</th>
            <th class="col">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${order.orderItems}" varStatus="status">
            <tr class="row">
                <td class="col">
                    <a href="<c:url value="/productDetails/${item.product.id}"/>">
                        <img src="${item.product.imageUrl}"
                             class="img-thumbnail" alt="Product preview" width="150" height="150">
                    </a>
                </td>
                <td class="col">${item.product.name}</td>
                <td class="col">${item.product.price}</td>
                <td class="col">${item.quantity}</td>
                <td class="col">
                    <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="$"/>
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
    <spring:eval var="deliveredStatus" expression="T(com.es.core.model.order.OrderStatus).DELIVERED"/>
    <spring:eval var="rejectedStatus" expression="T(com.es.core.model.order.OrderStatus).REJECTED"/>
    <form:form>
        <input type="hidden" name="_method" value="PUT">
        <a href="<c:url value="/admin/orders"/>" role="button" class="btn btn-outline-primary btn-lg">Back</a>
        <button class="btn btn-success btn-lg" name="orderStatus" value="${deliveredStatus}">Delivered</button>
        <button class="btn btn-danger btn-lg" name="orderStatus" value="${rejectedStatus}">Rejected</button>
    </form:form>
</tags:master>