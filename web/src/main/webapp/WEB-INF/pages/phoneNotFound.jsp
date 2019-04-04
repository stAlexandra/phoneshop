<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tags:master pageTitle="Phone Not Found">
    <p></p>
    <h3>Phone is not found!</h3>
    <a class="btn btn-primary" href="<c:url value="/productList"/>" role="button">Back to product list</a>
    <p></p>
</tags:master>
