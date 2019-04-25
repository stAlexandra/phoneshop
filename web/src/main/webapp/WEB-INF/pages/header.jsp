<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="row justify-content-end">
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <div class="col-auto"><a href="<c:url value="/admin/orders"/>">Admin</a></div>
    </sec:authorize>
    <sec:authorize access="!isAuthenticated()">
        <%--<div class="col-auto"><a href="<c:url value="/login"/>">Login</a></div>--%>
        <div class="col-auto"><button class="btn" formaction="<c:url value="/login"/>">Login</button></div>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
        <div class="col-auto"><sec:authentication property="name"/></div>
        <c:url value="/logout" var="logoutUrl"/>
        <form:form action="${logoutUrl}" method="POST">
            <div class="col-auto"><input type="submit" class="btn" value="Logout"/></div>
        </form:form>
    </sec:authorize>
</div>
<div class="row">
    <div class="col-10">
        <h1 class="display-3">Phonify</h1>
    </div>
    <c:if test="${param.showMiniCart}">
        <jsp:include page="minicart.jsp"/>
    </c:if>
</div>