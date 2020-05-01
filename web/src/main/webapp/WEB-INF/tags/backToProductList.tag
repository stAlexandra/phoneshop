<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="pageTitle" type="java.lang.String" required="false" %>
<%@attribute name="showMiniCart" type="java.lang.Boolean" required="false" %>
<p></p>
<div class="row">
    <div class="col">
        <a class="btn btn-outline-primary" href="<c:url value="/productList"/>" role="button">
            Back to product list
        </a>
    </div>
</div>
<br/>