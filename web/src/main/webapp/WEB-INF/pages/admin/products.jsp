<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Products">
<table class="table table-sm">
    <thead class="thead-light">
    <tr class="row">
        <th class="col">Image</th>
        <th class="col">Name</th>
        <th class="col">Model</th>
        <th class="col">Color</th>
        <th class="col">Display size</th>
        <th class="col">Price</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${productsPage.content}" varStatus="status">
    <tr class="row">
        <td class="col">
            <a href="<c:url value="/admin/products/${item.id}"/>">
                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${item.product.imageUrl}"
                     class="img-thumbnail" alt="Phone preview" width="150" height="150">
            </a>
        </td>
        <td class="col"></td>
        <td class="col">
            <a href="<c:url value="/admin/products/${item.id}"/>">${item.model}</a>
        </td>
        <td class="col">
            <c:forEach var="color" items="${item.colors}">
                ${color.code}
            </c:forEach>
        </td>
        <td class="col">${item.displaySizeInches}"</td>
        <td class="col"><fmt:formatNumber value="${item.price}" type="currency"
                                          currencySymbol="$"/></td>
    </tr>
    </c:forEach>
</tags:master>
