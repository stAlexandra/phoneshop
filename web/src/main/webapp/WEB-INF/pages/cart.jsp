<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="My Cart">
    <form id="deleteForm" method="post">
        <input type="hidden" name="_method" value="DELETE"/>
    </form>
    <form:form method="post" action="${pageContext.servletContext.contextPath}/cart" modelAttribute="updateCartRequestData" commandName="cart">
        <p></p>
        <div class="row">
            <div class="col">
                <a class="btn btn-outline-primary" href="<c:url value="/productList"/>" role="button">
                    Back to product list
                </a>
            </div>
            <div class="col-auto">
                <input type="hidden" name="_method" value="PUT"/>
                <button class="btn btn-info pull-right">Update</button>
            </div>
        </div>
        <br/>
        <table class="table table-sm">
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
            <c:forEach var="item" items="${cart.items}" varStatus="status">
                <tr class="row">
                    <td class="col">
                        <a href="<c:url value="/productDetails/${item.phone.id}"/>">
                            <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${item.phone.imageUrl}"
                                 class="img-thumbnail" alt="Phone preview" width="150" height="150">
                        </a>
                    </td>
                    <td class="col">${item.phone.brand}</td>
                    <td class="col"><a href="<c:url value="/productDetails/${item.phone.id}"/>">${item.phone.model}</a>
                    </td>
                    <td class="col">
                        <c:forEach var="color" items="${item.phone.colors}">
                            ${color.code}
                        </c:forEach>
                    </td>
                    <td class="col">${item.phone.displaySizeInches}"</td>
                    <td class="col">$ ${item.phone.price}</td>
                    <td class="col">
                        <input type="text" name="cartItemDataList[${status.index}].quantity" value="${item.quantity}" class="form-control">
                        <input type="hidden" name="cartItemDataList[${status.index}].phoneId" value="${item.phone.id}"/>
                        <div id="cartItemDataList[${status.index}].quantity.errors" class="text-danger"></div>
                    </td>
                    <td class="col">
                            <button id="deleteButton" form="deleteForm" class="btn btn-outline-secondary"
                                    formaction="<c:url value="/cart/${item.phone.id}"/>">
                                Delete
                            </button>
                    </td>
                </tr>
            </c:forEach>
            <tr class="row table-info justify-content-end">
                <td class="col-3">
                    <b>TOTAL PRICE:</b> ${cart.totalPrice}$
                </td>
            </tr>
            </tbody>
        </table>
        <br/>
        <div class="row justify-content-end">
            <div class="col-auto">
                <button class="btn btn-info">Update</button>
            </div>
            <div class="col-auto">
                <a class="btn btn-success" href="<c:url value="/order"/>" role="button">Order</a>
            </div>
        </div>
    </form:form>

</tags:master>