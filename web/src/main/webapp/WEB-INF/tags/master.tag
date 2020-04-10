<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@attribute name="pageTitle" type="java.lang.String" required="false" %>
<%@attribute name="showMiniCart" type="java.lang.Boolean" required="false" %>
<%@attribute name="hideUserPanel" type="java.lang.Boolean" required="false" %>
<html>
<head>
    <title>${pageTitle}</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css"
          integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
    <sec:csrfMetaTags />
</head>
<body>
<div class="container">
    <jsp:include page="/WEB-INF/pages/header.jsp">
        <jsp:param name="showMiniCart" value="${showMiniCart}"/>
        <jsp:param name="hideUserPanel" value="${hideUserPanel}"/>
    </jsp:include>
    <jsp:doBody/>
    <jsp:include page="/WEB-INF/pages/footer.jsp"/>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e,xhr,options) {
        xhr.setRequestHeader(header, token);
    });
</script>
<script src="<c:url value="/resources/js/ajaxAddToCart.js"/>"></script>
</body>
</html>
