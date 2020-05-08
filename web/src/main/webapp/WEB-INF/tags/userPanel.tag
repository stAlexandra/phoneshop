<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@attribute name="pageTitle" type="java.lang.String" required="false" %>

<sec:authorize access="isAuthenticated()">
<div id="userpanel" class="card">
    <div class="row card-body">
        <div class="col-auto">
            <a class="btn btn-success" role="button" href="<c:url value="/my-profile"/>">
                <i class="fa fa-user-circle fa-2x" aria-hidden="true"></i>
            </a>
        </div>
        <div class="col-auto">
                <h5><sec:authentication property="name"/></h5>
            <span>Level: ${user.level}</span>&nbsp;<span>Discount: ${levelDiscount}%</span>
        </div>
    </div>
</div>
</sec:authorize>
