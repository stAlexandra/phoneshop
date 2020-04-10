<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@attribute name="pageTitle" type="java.lang.String" required="false" %>

<div id="userpanel" class="card">
    <div class="row card-body">
        <div class="col-auto">
            <a class="btn btn-success" role="button" href="<c:url value="/my-profile"/>">
                <i class="fa fa-user-circle fa-2x" aria-hidden="true"></i>
            </a>
        </div>
        <div class="col-auto">
            <sec:authorize access="isAuthenticated()">
                <h5><sec:authentication property="name"/></h5>
            </sec:authorize>
            <span>Level: 10</span>&nbsp;<span>Discount: 10%</span>
        </div>
    </div>
</div>