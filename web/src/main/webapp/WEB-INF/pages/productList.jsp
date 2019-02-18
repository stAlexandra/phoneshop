<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product List">
    <p>
        Found
            <c:out value="${phones.size()}"/> phones.
    <table class="table table-sm table-striped">
        <thead class="thead-light">
        <tr>
            <th>Image</th>
            <th>Brand</th>
            <th>Model</th>
            <th>Color</th>
            <th>Display size</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="phone" items="${phones}">
            <tr>
                <td>
                    <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}"
                         class="img-thumbnail">
                </td>
                <td>${phone.brand}</td>
                <td>${phone.model}</td>
                <td>${phone.colors}</td>
                <td>${phone.displaySizeInches}"</td>
                <td>$ ${phone.price}</td>
                <td></td>
                <td></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </p>
</tags:master>