<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="minicart" class="card">
    <div class="row card-body">
        <div class="col-8">
            <h5>My cart</h5>
            <span id="numItems">${cart.items.size()}</span> items
            <span id="totalPrice"><fmt:formatNumber value="${cart.totalPrice}" type="currency"
                                                    currencySymbol="$"/></span>
        </div>
        <div class="col-4">
            <a class="btn btn-primary" role="button" href="<c:url value="/cart"/>">
                <i class="fa fa-shopping-cart fa-2x" aria-hidden="true"></i>
            </a>
        </div>
    </div>
</div>