<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product Details">
    <a class="btn btn-outline-primary" href="<c:url value="/productList"/>" role="button">Back to product list</a>
    <p></p>
    <div class="row justify-content-start">
        <div class="col-6">
            <h1>${phone.model}</h1><br/>
            <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}"
                 class="img-fluid img-thumbnail" alt="Phone image">
            <p class="lead">${phone.description}</p>
            <div class="card col-5">
                <div class="card-body">
                    <h3><strong>Price: </strong><span>${phone.price}$</span></h3>
                    <div class="input-group">
                        <input class="form-control" id="quantity${phone.id}"/>
                        <span class="input-group-btn"><button class="btn btn-primary" onclick="addToCart(${phone.id})">Add to cart</button></span>
                    </div>
                    <div id="quantityError${phone.id}" name="quantityError" class="text-danger"></div>
                </div>
            </div>
        </div>
        <div class="col-1"></div>
        <div class="col-4">
            <h3>Display</h3>
            <table class="table table-sm">
                <tbody>
                <tr class="row">
                    <td class="col">Size</td>
                    <td class="col">${phone.displaySizeInches}</td>
                </tr>
                <tr class="row">
                    <td class="col">Resolution</td>
                    <td class="col">${phone.displayResolution}</td>
                </tr>
                <tr class="row">
                    <td class="col">Technology</td>
                    <td class="col">${phone.displayTechnology}</td>
                </tr>
                <tr class="row">
                    <td class="col">Pixel density</td>
                    <td class="col">${phone.pixelDensity}</td>
                </tr>
                </tbody>
            </table>
            <h3>Dimensions & weight</h3>
            <table class="table table-sm">
                <tbody>
                <tr class="row">
                    <td class="col">Length</td>
                    <td class="col">${phone.lengthMm}</td>
                </tr>
                <tr class="row">
                    <td class="col">Width</td>
                    <td class="col">${phone.widthMm}</td>
                </tr>
                <tr class="row">
                    <td class="col">Height</td>
                    <td class="col">${phone.heightMm}</td>
                </tr>
                <tr class="row">
                    <td class="col">Weight</td>
                    <td class="col">${phone.weightGr}</td>
                </tr>
                </tbody>
            </table>
            <h3>Camera</h3>
            <table class="table table-sm">
                <tbody>
                <tr class="row">
                    <td class="col">Front</td>
                    <td class="col">${phone.frontCameraMegapixels}</td>
                </tr>
                <tr class="row">
                    <td class="col">Back</td>
                    <td class="col">${phone.backCameraMegapixels}</td>
                </tr>
                </tbody>
            </table>
            <h3>Battery</h3>
            <table class="table table-sm">
                <tbody>
                <tr class="row">
                    <td class="col">Talk time</td>
                    <td class="col">${phone.talkTimeHours}</td>
                </tr>
                <tr class="row">
                    <td class="col">Stand by time</td>
                    <td class="col">${phone.standByTimeHours}</td>
                </tr>
                <tr class="row">
                    <td class="col">Battery capacity</td>
                    <td class="col">${phone.batteryCapacityMah}</td>
                </tr>
                </tbody>
            </table>
            <h3>Other</h3>
            <table class="table table-sm">
                <tbody>
                <tr class="row">
                    <td class="col">Colors</td>
                    <td class="col">
                        <c:forEach var="color" items="${phone.colors}">
                            ${color.code}
                        </c:forEach>
                    </td>
                </tr>
                <tr class="row">
                    <td class="col">Device type</td>
                    <td class="col">${phone.deviceType}</td>
                </tr>
                <tr class="row">
                    <td class="col">Bluetooth</td>
                    <td class="col">${phone.bluetooth}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</tags:master>