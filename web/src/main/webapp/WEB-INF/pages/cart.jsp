<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="My Cart" showMiniCart="${false}">
    <form id="deleteForm" method="post">
        <input type="hidden" name="_method" value="DELETE"/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
    <tags:backToProductList/>
    <c:choose>
        <c:when test="${empty cart.items}">
            <h3 class="text-center">Your cart is empty!</h3>
        </c:when>
        <c:otherwise>
            <c:url value="/cart" var="cartUrl"/>
            <form:form method="post" action="${cartUrl}" modelAttribute="updateCartRequestData">
                <table class="table table-sm">
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
                    <c:forEach var="item" items="${cart.items}" varStatus="status">
                        <tr class="row">
                            <td class="col">
                                <a href="<c:url value="/productDetails/${item.product.id}"/>">
                                    <img src="${item.product.imageUrl}"
                                         class="img-thumbnail" alt="Product preview" width="150" height="150">
                                </a>
                            </td>
                            <td class="col">
                                <a href="<c:url value="/productDetails/${item.product.id}"/>">${item.product.name}</a>
                            </td>
                            <td class="col"><fmt:formatNumber value="${item.product.price}" type="currency"
                                                              currencySymbol="$"/></td>
                            <td class="col">
                                <c:set var="i" value="${status.index}"/>
                                <form:input path="cartItemDataList[${i}].quantity" cssClass="form-control"/>
                                <form:hidden path="cartItemDataList[${i}].id"/>
                                <form:errors path="cartItemDataList[${i}].quantity" cssClass="text-danger"
                                             element="div"/>
                            </td>
                            <td class="col">
                                <button id="deleteButton" form="deleteForm" class="btn btn-outline-secondary"
                                        formaction="<c:url value="/cart/${item.product.id}"/>">
                                    Delete
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr class="row justify-content-end">
                        <td class="col-2"><b>Subtotal: </b></td>
                        <td class="col-3">
                            <fmt:formatNumber value="${cart.subtotalPrice}" type="currency"
                                                               currencySymbol="$"/>
                        </td>
                    </tr>
                    <tr class="row  justify-content-end">
                        <td class="col-2 table-success"><b>Discount: </b></td>
                        <td class="col-3 table-success">
                            <fmt:formatNumber value="${cart.totalDiscount}" type="currency"
                                                               currencySymbol="$"/>
                        </td>
                    </tr>
                    <tr class="row justify-content-end">
                        <td class="col-2 table-info"><b>TOTAL PRICE: </b></td>
                        <td class="col-3 table-info">
                            <fmt:formatNumber value="${cart.totalPrice}" type="currency"
                                                                  currencySymbol="$"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <br/>
                <div class="row justify-content-end">
                    <div class="col-auto">
                        <input type="hidden" name="_method" value="PUT">
                        <button class="btn btn-info">Update</button>
                    </div>
                    <div class="col-auto">
                        <a class="btn btn-success" href="<c:url value="/order"/>" role="button">Order</a>
                    </div>
                </div>
            </form:form>
            <tags:couponsCard/>
        </c:otherwise>
    </c:choose>
</tags:master>