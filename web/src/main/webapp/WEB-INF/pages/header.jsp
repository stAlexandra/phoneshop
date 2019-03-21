<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <div class="col-10">
        <h1 class="display-3">Phonify</h1>
    </div>
    <div class="col-2">
        <div id="minicart" class="card">
            <div class="card-body">
                <h4>My cart</h4>
                <p>
                    <span id="numItems">${cartNumItems}</span> items <span id="totalPrice">${cartTotalPrice}</span>$
                </p>
                <a class="btn btn-primary align-self-end" role="button" href="<c:url value="/cart"/>">Go to cart</a>
            </div>
        </div>
    </div>
</div>