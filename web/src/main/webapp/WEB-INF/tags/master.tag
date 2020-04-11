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
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css"
          integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
<%--    <link rel="stylesheet" href="<c:url value="/resources/styles/progressbar.css"/>">--%>
<%--    <link rel="stylesheet" href="<c:url value="/resources/styles/progressbar-2.scss"/>">--%>
    <link rel="stylesheet" href="<c:url value="/resources/styles/progress.css"/>">

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
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e,xhr,options) {
        xhr.setRequestHeader(header, token);
    });
</script>
<script src="<c:url value="/resources/js/ajaxAddToCart.js"/>"></script>
<%--<script>--%>
<%--    $(document).ready(function () {--%>
<%--        let progressbar = $('#progressbar'),--%>
<%--            max = progressbar.attr('value'),--%>
<%--            time = (1000 / max) * 8,--%>
<%--            value = 0;--%>
<%--        let loading = function () {--%>
<%--            value += 1;--%>
<%--            addValue = progressbar.val(value);--%>
<%--            $('.progress-value').html(value + '%');--%>
<%--            if (value == max) {--%>
<%--                clearInterval(animate);--%>
<%--            }--%>
<%--        };--%>
<%--        let animate = setInterval(function () {--%>
<%--            loading();--%>
<%--        }, time);--%>
<%--    });--%>
<%--</script>--%>
</body>
</html>
