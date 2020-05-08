<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Order" showMiniCart="${false}">
    <p></p>
    <h1>Order</h1>
    <a class="btn btn-outline-primary" href="<c:url value="/cart"/>" role="button">Back to cart</a>
    <p></p>
    <c:choose>
        <c:when test="${empty cart.items}">
            <p></p>
            <h3 class="text-center">Your order is empty!</h3>
            <p></p>
            <div class="row justify-content-center">
                <div class="col-auto">
                    <a class="btn btn-primary btn-lg" href="<c:url value="/productList"/>" role="button">
                        Back to shopping
                    </a>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <c:url var="placeOrderUrl" value="/order"/>
            <form:form method="post" action="${placeOrderUrl}" modelAttribute="orderData">
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
                    <c:forEach var="item" items="${cart.items}" varStatus="status">
                        <tr class="row">
                            <td class="col">${item.phone.brand}</td>
                            <td class="col">${item.phone.model}</td>
                            <td class="col">
                                <c:forEach var="color" items="${item.phone.colors}">
                                    ${color.code}
                                </c:forEach>
                            </td>
                            <td class="col">${item.phone.displaySizeInches}"</td>
                            <td class="col">${item.quantity}
                                <c:set value="${item.phone.id}" var="phoneId"/>
                                <form:hidden path="phoneIdToQuantity[${phoneId}]" value="${item.quantity}"/><br/>
                                <form:errors path="phoneIdToQuantity[${phoneId}]" cssClass="text-danger"/>
                            </td>
                            <td class="col">
                                <fmt:formatNumber value="${item.phone.price}" type="currency" currencySymbol="$"/>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <table class="table table-sm">
                    <tr class="row justify-content-end">
                        <td class="col-2"><b>Subtotal: </b></td>
                        <td class="col-2">
                            <fmt:formatNumber value="${cart.subtotalPrice}" type="currency" currencySymbol="$"/>
                        </td>
                    </tr>
                    <tr class="row justify-content-end">
                        <td class="col-2"><b>Delivery: </b></td>
                        <td class="col-2">
                            <spring:message code="delivery.price" var="deliveryPrice"/>
                            <fmt:formatNumber value="${deliveryPrice}" type="currency" currencySymbol="$"/>
                        </td>
                    </tr>
                    <tr class="row justify-content-end">
                        <td class="col-2 table-success"><b>Discount: </b></td>
                        <td class="col-2 table-success">
                            &minus;<fmt:formatNumber value="${cart.totalDiscount}" type="currency" currencySymbol="$"/>
                        </td>
                    </tr>
                    <tr class="row justify-content-end">
                        <td class="col-2 table-info"><b>TOTAL PRICE: </b></td>
                        <td class="col-2 table-info">
                            <fmt:formatNumber value="${cart.totalPrice + deliveryPrice}" type="currency"
                                              currencySymbol="$"/>
                        </td>
                    </tr>
                </table>
                <br/><br/>
                <div class="row">
                    <div class="col-6">
                        <table class="table">
                            <tr class="row">
                                <td class="col-4"><form:label path="firstName"><b>First name*:</b></form:label></td>
                                <td class="col-8">
                                    <form:input path="firstName" cssClass="form-control"/>
                                    <form:errors path="firstName" cssClass="text-danger"/>
                                </td>
                            </tr>
                            <tr class="row">
                                <td class="col-4"><form:label path="lastName"><b>Last name*:</b></form:label></td>
                                <td class="col-8">
                                    <form:input path="lastName" cssClass="form-control"/>
                                    <form:errors path="lastName" cssClass="text-danger"/>
                                </td>

                            </tr>
                            <tr class="row">
                                <td class="col-4"><form:label path="deliveryAddress"><b>Address*:</b></form:label></td>
                                <td class="col-8">
                                    <form:input path="deliveryAddress" cssClass="form-control"/>
                                    <form:errors path="deliveryAddress" cssClass="text-danger"/>
                                </td>
                            </tr>
                            <tr class="row">
                                <td class="col-4"><form:label path="contactPhoneNo"><b>Phone*:</b></form:label></td>
                                <td class="col-8">
                                    <form:input path="contactPhoneNo" cssClass="form-control"/>
                                    <form:errors path="contactPhoneNo" cssClass="text-danger"/>
                                </td>
                            </tr>
                            <tr class="row">
                                <td class="col-8 offset-4">
                                    <input type="submit" class="btn btn-success btn-lg btn-block" value="Submit"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </form:form>
        </c:otherwise>
    </c:choose>
</tags:master>