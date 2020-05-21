<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<tags:master pageTitle="My Profile" hideUserPanel="${true}" showMiniCart="${false}">
    <div class="row justify-content-end">
        <div class="col-auto">
            <a class="btn btn-outline-success" href="<c:url value="/my-profile/orders"/>" role="button">
                My Orders
            </a>
        </div>
        <div class="col-auto">
            <tags:backToProductList/>
        </div>
    </div>
    <div id="userinfo" class="row">
        <div class="media col-6">
            <img src="https://i.pinimg.com/originals/13/a4/11/13a411076cdee39085cad97da215d9be.png" width="200"
                 height="200" class="align-self-start mr-3" alt="Profile photo">
            <div class="media-body mt-3 ml-4">
                <h1><sec:authentication property="name"/></h1>
                <p>
                    <span class="h3">Level ${user.level} Discount ${user.discountPercentage}%</span>
                </p>
                <div>${user.XP} / ${user.maxXP}
                    <progress value="${user.XP}" max="${user.maxXP}"></progress>
                </div>
            </div>
        </div>
        <div class="col-6">

        </div>
    </div>
    <br/><br/><br/>
    <table id="achievements" class="table">
        <tr class="row table-success">
            <td class="col">
                <h3><b>My Achievements</b></h3>
            </td>
        </tr>
        <c:forEach items="${user.achievements}" var="achievement">
            <tr class="row">
                <td class="col-2 offset-1">
                    <img width="100" height="100" class="align-self-end"
                         src="https://yandex.by/images/_crpd/adcs10749/d4d0513igU/uBDBJc1hnntyk1-42fcaNNI2awI-XOE4VLKSOzG2qy-v_22JdAO1nmN1bRUc1Igq_ESeTG2jzhKY35ueTIE8E-gR33fZbsMibsVW5IY">
                </td>
                <td class="col-8">
                    <h3>${achievement.name}</h3>
                    <p>${achievement.description}</p>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br/><br/><br/>
    <table id="treasures" class="table">
        <tbody>
        <tr class="row table-warning">
            <td class="col">
                <h3><b>My treasures</b></h3>
            </td>
        </tr>
        <tr class="row">
            <td class="col-6 offset-2">
                <img height="300" src="https://im0-tub-by.yandex.net/i?id=3adea41e3a02dae6cf6d3a0f49968dec&n=13&exp=1">
            </td>
            <td class="col-4 card">
                <p>This is Description This is Description This is DescriptionThis is DescriptionThis
                    is DescriptionThis
                    is DescriptionThis is DescriptionThis is DescriptionThis is DescriptionThis is DescriptionThis is
                    Description</p>
            </td>
        </tr>
        <tr class="row">
            <td class="col-8 custom-progressbar">
                <progress id="progressbar" value="80" max="100"></progress>
            </td>
        </tr>
        </tbody>
    </table>
</tags:master>