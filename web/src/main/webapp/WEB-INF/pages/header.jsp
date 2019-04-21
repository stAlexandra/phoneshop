<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row justify-content-end">
    <c:choose>
        <c:when test="${empty pageContext.request.remoteUser}">
            <div class="col-auto"><a href="<c:url value="/login"/>">Login</a></div>
        </c:when>
        <c:otherwise>
            <div class="col-auto">${pageContext.request.remoteUser}</div>
            <div class="col-auto"><a href="<c:url value="/logout"/>">Logout</a></div>
        </c:otherwise>
    </c:choose>
    <div class="col-auto"><a href="<c:url value="/admin/orders"/>">Admin</a></div>
</div>
<div class="row">
    <div class="col-10">
        <h1 class="display-3">Phonify</h1>
    </div>
    <c:if test="${param.showMiniCart}">
        <jsp:include page="minicart.jsp"/>
    </c:if>
</div>