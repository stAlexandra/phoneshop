<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="col-2">
    <div id="minicart" class="card">
        <div class="card-body">
            <h4>My cart</h4>
            <p>
                <span id="numItems">${cart.items.size()}</span> items
                <span id="totalPrice"><fmt:formatNumber value="${cart.totalPrice}" type="currency" currencyCode="USD"/></span>
            </p>
            <a class="btn btn-primary align-self-end" role="button" href="<c:url value="/cart"/>">Go to cart</a>
        </div>
    </div>
</div>