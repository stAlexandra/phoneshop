<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product List">
    <form method="get">
        <div class="input-group">
            <input class="form-control col-3" name="query" value="${param.query}"/>
            <span class="input-group-btn ">
                <button class="btn btn-outline-secondary">Search</button>
            </span>
            <c:if test="${not empty param.sort}">
                <input class="form-control" type="hidden" name="sort" value="${param.sort}"/>
            </c:if>
            <c:if test="${not empty param.order}">
                <input class="form-control" type="hidden" name="order" value="${param.order}"/>
            </c:if>
        </div>
    </form>
    <br/>
    <table class="table table-sm table-striped table-bordered">
        <thead class="thead-light">
        <tr class="row">
            <th class="col">Image</th>
            <th class="col">Brand
                <a href="<c:url value="">
                    <c:if test="${not empty param.query}">
                        <c:param name="query" value="${param.query}"/>
                    </c:if>
                    <c:param name="sort" value="brand"/>
                    <c:param name="order" value="asc"/></c:url>">
                    <i class="fas fa-long-arrow-alt-up"></i>
                </a>
                <a href="<c:url value="">
                    <c:if test="${not empty param.query}">
                        <c:param name="query" value="${param.query}"/>
                    </c:if>
                    <c:param name="sort" value="brand"/>
                    <c:param name="order" value="desc"/></c:url>">
                    <i class="fas fa-long-arrow-alt-down"></i>
                </a>
            </th>
            <th class="col">Model
                <a href="<c:url value="">
                    <c:if test="${not empty param.query}">
                        <c:param name="query" value="${param.query}"/>
                    </c:if>
                    <c:param name="sort" value="model"/>
                    <c:param name="order" value="asc"/></c:url>">
                    <i class="fas fa-long-arrow-alt-up"></i>
                </a>
                <a href="<c:url value="">
                    <c:if test="${not empty param.query}">
                        <c:param name="query" value="${param.query}"/>
                    </c:if>
                    <c:param name="sort" value="model"/>
                    <c:param name="order" value="desc"/></c:url>">
                    <i class="fas fa-long-arrow-alt-down"></i>
                </a>
            </th>
            <th class="col">Color</th>
            <th class="col">Display size
                <a href="<c:url value="">
                    <c:if test="${not empty param.query}">
                        <c:param name="query" value="${param.query}"/>
                    </c:if>
                    <c:param name="sort" value="displaySizeInches"/>
                    <c:param name="order" value="asc"/></c:url>">
                    <i class="fas fa-long-arrow-alt-up"></i>
                </a>
                <a href="<c:url value="">
                    <c:if test="${not empty param.query}">
                        <c:param name="query" value="${param.query}"/>
                    </c:if>
                    <c:param name="sort" value="displaySizeInches"/>
                    <c:param name="order" value="desc"/></c:url>">
                    <i class="fas fa-long-arrow-alt-down"></i>
                </a>
            </th>
            <th class="col">Price
                <a href="<c:url value="">
                    <c:if test="${not empty param.query}">
                        <c:param name="query" value="${param.query}"/>
                    </c:if>
                    <c:param name="sort" value="price"/>
                    <c:param name="order" value="asc"/></c:url>">
                    <i class="fas fa-long-arrow-alt-up"></i>
                </a>
                <a href="<c:url value="">
                    <c:if test="${not empty param.query}">
                        <c:param name="query" value="${param.query}"/>
                    </c:if>
                    <c:param name="sort" value="price"/>
                    <c:param name="order" value="desc"/></c:url>">
                    <i class="fas fa-long-arrow-alt-down"></i>
                </a>
            </th>
            <th class="col">Quantity</th>
            <th class="col">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="phone" items="${phonePage.content}" varStatus="status">
            <tr class="row">
                <td class="col">
                    <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}"
                         class="img-thumbnail" alt="Phone preview" width="150" height="150">
                </td>
                <td class="col">${phone.brand}</td>
                <td class="col">${phone.model}</td>
                <td class="col">
                    <c:forEach var="color" items="${phone.colors}">
                        ${color.code}
                    </c:forEach>
                </td>
                <td class="col">${phone.displaySizeInches}"</td>
                <td class="col">$ ${phone.price}</td>
                <td class="col">
                    <input type="number" id="quantity${phone.id}" name="quantity" class="form-control">
                    <div id="quantityError${phone.id}" name="quantityError" class="text-danger"></div>
                </td>
                <td class="col">
                    <button id="addToCartButton" type="button" class="btn btn-outline-primary"
                            onclick="addToCart(${phone.id})">
                        Add to cart
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <nav aria-label="Product list pages">
        <ul class="pagination pagination-lg justify-content-end">
                <c:choose>
                    <c:when test="${phonePage.hasPrevious()}">
                        <li class="page-item"></c:when>
                    <c:otherwise>
                        <li class="page-item disabled">
                    </c:otherwise>
                </c:choose>
                <a class="page-link" href="<c:url value="">
                            <c:param name="page" value="1"/>
                            <c:if test="${not empty param.query}">
                                <c:param name="query" value="${param.query}"/>
                            </c:if>
                            <c:if test="${not empty param.sort}">
                                <c:param name="sort" value="${param.sort}"/>
                            </c:if>
                            <c:if test="${not empty param.order}">
                                <c:param name="order" value="${param.order}"/>
                            </c:if>
                        </c:url>">&larr;</a>
                </li>
                <c:choose>
                    <c:when test="${phonePage.hasPrevious()}">
                        <li class="page-item"></c:when>
                    <c:otherwise>
                        <li class="page-item disabled"></c:otherwise>
                    </c:choose>
                <a class="page-link" href="<c:url value="">
                            <c:param name="page" value="${phonePage.number}"/>
                            <c:if test="${not empty param.query}">
                                <c:param name="query" value="${param.query}"/>
                            </c:if>
                            <c:if test="${not empty param.sort}">
                                <c:param name="sort" value="${param.sort}"/>
                            </c:if>
                            <c:if test="${not empty param.order}">
                                <c:param name="order" value="${param.order}"/>
                            </c:if>
                        </c:url>">&laquo;</a>
                </li>

            <c:forEach var="i" begin="${beginPage}" end="${endPage}" varStatus="status">
                <c:choose>
                    <c:when test="${phonePage.number eq i-1}">
                        <li class="page-item active">
                    </c:when>
                    <c:otherwise>
                        <li class="page-item">
                    </c:otherwise>
                </c:choose>
                <a class="page-link" href="
                        <c:url value="">
                            <c:param name="page" value="${i}"/>
                            <c:if test="${not empty param.query}">
                                <c:param name="query" value="${param.query}"/>
                            </c:if>
                            <c:if test="${not empty param.sort}">
                                <c:param name="sort" value="${param.sort}"/>
                            </c:if>
                            <c:if test="${not empty param.order}">
                                <c:param name="order" value="${param.order}"/>
                            </c:if>
                        </c:url>
                    ">${i}</a>
                </li>
            </c:forEach>

            <c:choose>
                    <c:when test="${phonePage.hasNext()}">
                        <li class="page-item">
                    </c:when>
                    <c:otherwise>
                        <li class="page-item disabled">
                    </c:otherwise>
            </c:choose>
                    <a class="page-link" href="<c:url value="">
                            <c:param name="page" value="${phonePage.number + 2}"/>
                            <c:if test="${not empty param.query}">
                                <c:param name="query" value="${param.query}"/>
                            </c:if>
                            <c:if test="${not empty param.sort}">
                                <c:param name="sort" value="${param.sort}"/>
                            </c:if>
                            <c:if test="${not empty param.order}">
                                <c:param name="order" value="${param.order}"/>
                            </c:if>
                        </c:url>">&raquo;</a>
            </li>

            <c:choose>
                <c:when test="${phonePage.hasNext()}">
                    <li class="page-item">
                </c:when>
                <c:otherwise>
                    <li class="page-item disabled">
                </c:otherwise>
            </c:choose>
                <a class="page-link" href="<c:url value="">
                            <c:param name="page" value="${lastPage}"/>
                            <c:if test="${not empty param.query}">
                                <c:param name="query" value="${param.query}"/>
                            </c:if>
                            <c:if test="${not empty param.sort}">
                                <c:param name="sort" value="${param.sort}"/>
                            </c:if>
                            <c:if test="${not empty param.order}">
                                <c:param name="order" value="${param.order}"/>
                            </c:if>
                        </c:url>">&rarr;</a>
            </li>
        </ul>
    </nav>
</tags:master>