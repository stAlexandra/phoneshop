<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product List">
    <table class="table table-sm table-striped">
        <thead class="thead-light">
        <tr class="row">
            <th class="col">Image</th>
            <th class="col">Brand</th>
            <th class="col">Model</th>
            <th class="col">Color</th>
            <th class="col">Display size</th>
            <th class="col">Price</th>
            <th class="col">Quantity</th>
            <th class="col">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="phone" items="${phonePage.content}">
            <tr class="row">
                <td class="col">
                    <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}"
                         class="img-thumbnail" alt="Phone preview" width="150" height="150">
                </td>
                <td class="col">${phone.brand}</td>
                <td class="col">${phone.model}</td>
                <td class="col">${phone.colors}</td>
                <td class="col">${phone.displaySizeInches}"</td>
                <td class="col">$ ${phone.price}</td>
                <td class="col"></td>
                <td class="col"></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <nav aria-label="Product list pages">
        <ul class="pagination pagination-lg justify-content-end">
            <c:if test="${phonePage.hasPrevious()}">
            <li class="page-item"></c:if>
                <c:if test="${not phonePage.hasPrevious()}">
            <li class="page-item disabled"></c:if>
                <a class="page-link" href="?page=${phonePage.number}">&laquo;</a>
            </li>

            <c:forEach var="i" begin="${beginPage}" end="${endPage}" varStatus="status">
                <c:if test="${phonePage.number eq i-1}">
                    <li class="page-item active">
                </c:if>
                <c:if test="${phonePage.number != i-1}">
                    <li class="page-item">
                </c:if>
                <a class="page-link" href="
                        <c:url value="">
                            <c:param name="page" value="${i}"/>
                        </c:url>
                    ">${i}</a>
                </li>
            </c:forEach>

            <c:if test="${phonePage.hasNext()}">
            <li class="page-item"></c:if>
                <c:if test="${not phonePage.hasNext()}">
            <li class="page-item disabled"></c:if>
                <a class="page-link" href="?page=${phonePage.number + 2}">&raquo;</a>
            </li>
        </ul>
    </nav>
</tags:master>