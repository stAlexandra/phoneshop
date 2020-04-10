<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<div class="row justify-content-end">
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <div class="col-auto"><a href="<c:url value="/admin/orders"/>">Admin</a></div>
    </sec:authorize>
    <sec:authorize access="!isAuthenticated()">
        <div class="col-auto"><a href="<c:url value="/login"/>" class="btn btn-outline-secondary">Login</a></div>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
        <div class="col-auto"><sec:authentication property="name"/></div>
        <c:url value="/logout" var="logoutUrl"/>
        <form:form action="${logoutUrl}" method="POST">
            <div class="col-auto"><input type="submit" class="btn btn-outline-secondary" value="Logout"/></div>
        </form:form>
    </sec:authorize>
</div>
<div class="row justify-content-between">
    <div class="col-5">
        <h2 class="display-3">Phonify</h2>
    </div>
    <div class="col-3">
        <c:if test="${param.showMiniCart}">
            <jsp:include page="minicart.jsp"/>
        </c:if>
    </div>
    <sec:authorize access="isAuthenticated()">
        <c:if test="${not param.hideUserPanel}">
            <div class="col-4">
                <tags:userPanel/>
            </div>
        </c:if>
    </sec:authorize>
</div>