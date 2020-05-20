<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<tags:master pageTitle="Product Details" showMiniCart="${true}">
    <a class="btn btn-outline-primary" href="<c:url value="/productList"/>" role="button">Back to product list</a>
    <p></p>
    <div class="row justify-content-start">
        <div class="col-3">
            <img src="${book.imageUrl}" class="img-fluid img-thumbnail" alt="book image" width="200" height="400">
        </div>
        <div class="col-5">
            <h2>${book.name}</h2><br/>
            <div class="card">
                <div class="card-body">
                    <h3>
                        <strong>Price: </strong>
                        <span><fmt:formatNumber value="${book.price}" type="currency" currencySymbol="$"/></span>
                    </h3>
                    <div class="input-group">
                        <input class="form-control" id="quantity${book.id}" value="1"/>
                        <div id="quantityError${book.id}" name="quantityError" class="text-danger"></div>
                        <span class="input-group-btn">
                                <button class="btn btn-primary" onclick="addToCart(${book.id})">Add to cart</button>
                            </span>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-4">
            <table class="table table-lg">
                <tbody>
                <tr class="row">
                    <td class="col">Genre</td>
                    <td class="col">${book.genre}</td>
                </tr>
                <tr class="row">
                    <td class="col">Publisher</td>
                    <td class="col">${book.publisher}</td>
                </tr>
                <tr class="row">
                    <td class="col">Pages</td>
                    <td class="col">${book.pageCount}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <p><h1></h1></p>
    <div class="row">
        <div class="col-8">
            <p>${book.description}</p>
        </div>
    </div>
</tags:master>