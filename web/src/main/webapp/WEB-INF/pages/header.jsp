<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <div class="col-10">
        <h1 class="display-3">Phonify</h1>
    </div>
    <c:if test="${param.showMiniCart}">
        <jsp:include page="minicart.jsp"/>
    </c:if>
</div>