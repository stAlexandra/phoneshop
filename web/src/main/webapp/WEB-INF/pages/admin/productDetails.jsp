<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<tags:master pageTitle="Product Details">
    <a class="btn btn-outline-primary" href="<c:url value="/admin/products"/>" role="button">Back to product list</a>
    <p></p>
    <c:url value="/admin/products/${product.id}" var="editProductUrl"/>
    <form:form method="post" action="${editProductUrl}" modelAttribute="updatePhoneData">
    <div class="row justify-content-start">
        <div class="col-6">
            <h1>${product.model}</h1><br/>
            <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}"
                 class="img-fluid img-thumbnail" alt="Phone image">
            <p class="lead">
                    ${product.description}
            </p>
            <table>
                <thead class="thead-light">
                <tr class="row">
                    <th class="col">Currency</th>
                    <th class="col">Value</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach begin="${0}" end="${2}" varStatus="status">
                    <tr class="row">
                        <td class="col">
                            <c:set var="i" value="${status.index}"/>
                            <form:input path="priceList[${i}].currencyName" cssClass="form-control"/>
                        </td>
                        <td class="col">
                            <form:input path="priceList[${i}].value" cssClass="form-control"/>
                        </td>
                        <form:errors path="priceList[${i}]" cssClass="text-danger"
                                     element="div"/>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <input type="hidden" name="_method" value="PUT">
            <button class="btn btn-primary">Update</button>
        </div>
        <div class="col-1"></div>
        <div class="col-4">
            <h3>Display</h3>
            <table class="table table-sm">
                <tbody>
                <tr class="row">
                    <td class="col">Size</td>
                    <td class="col">${product.displaySizeInches}</td>
                </tr>
                <tr class="row">
                    <td class="col">Resolution</td>
                    <td class="col">${product.displayResolution}</td>
                </tr>
                <tr class="row">
                    <td class="col">Technology</td>
                    <td class="col">${product.displayTechnology}</td>
                </tr>
                <tr class="row">
                    <td class="col">Pixel density</td>
                    <td class="col">${product.pixelDensity}</td>
                </tr>
                </tbody>
            </table>
            <h3>Dimensions & weight</h3>
            <table class="table table-sm">
                <tbody>
                <tr class="row">
                    <td class="col">Length</td>
                    <td class="col">
                        <form:input path="lengthMm" cssClass="form-control"/>
                        <form:errors path="lengthMm"/>
                    </td>
                </tr>
                <tr class="row">
                    <td class="col">Width</td>
                    <td class="col">
                        <form:input path="widthMm" cssClass="form-control"/>
                        <form:errors path="widthMm"/>
                    </td>
                </tr>
                <tr class="row">
                    <td class="col">Height</td>
                    <td class="col">${product.heightMm}</td>
                </tr>
                <tr class="row">
                    <td class="col">Weight</td>
                    <td class="col">${product.weightGr}</td>
                </tr>
                </tbody>
            </table>
            <h3>Camera</h3>
            <table class="table table-sm">
                <tbody>
                <tr class="row">
                    <td class="col">Front</td>
                    <td class="col">${product.frontCameraMegapixels}</td>
                </tr>
                <tr class="row">
                    <td class="col">Back</td>
                    <td class="col">${product.backCameraMegapixels}</td>
                </tr>
                </tbody>
            </table>
            <h3>Battery</h3>
            <table class="table table-sm">
                <tbody>
                <tr class="row">
                    <td class="col">Talk time</td>
                    <td class="col">${product.talkTimeHours}</td>
                </tr>
                <tr class="row">
                    <td class="col">Stand by time</td>
                    <td class="col">${product.standByTimeHours}</td>
                </tr>
                <tr class="row">
                    <td class="col">Battery capacity</td>
                    <td class="col">${product.batteryCapacityMah}</td>
                </tr>
                </tbody>
            </table>
            <h3>Other</h3>
            <table class="table table-sm">
                <tbody>
                <tr class="row">
                    <td class="col">Colors</td>
                    <td class="col">
                        <c:forEach var="color" items="${product.colors}">
                            ${color.code}
                        </c:forEach>
                    </td>
                </tr>
                <tr class="row">
                    <td class="col">Device type</td>
                    <td class="col">${product.deviceType}</td>
                </tr>
                <tr class="row">
                    <td class="col">Bluetooth</td>
                    <td class="col">${product.bluetooth}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
        </form:form>
</tags:master>