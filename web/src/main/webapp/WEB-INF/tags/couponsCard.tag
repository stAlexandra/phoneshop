<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="isAuthenticated()">
    <div class="col-6">
        <div class="card" style="width: 18rem;">
            <div class="card-body">
                <h5 class="card-title">Have a coupon?</h5>
                <p class="card-text">
                    <input class="form-control" id="couponCodeInput" name="couponCode"
                           placeholder="Enter your code here"/>
                </p>
                <input id="activateCouponBtn" onclick="activateCartCoupon()" type="submit" class="btn btn-primary"
                       value="Redeem Code"/>
                <div class="text-success">${couponActivatedMsg}</div>
                <div class="text-danger">${couponErrorMsg}</div>
            </div>
        </div>
    </div>
</sec:authorize>
<script src="<c:url value="/resources/js/ajaxActivateCoupon.js"/>"></script>
