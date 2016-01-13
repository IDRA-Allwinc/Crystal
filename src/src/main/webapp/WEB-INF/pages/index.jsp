<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<HTML lang="es-ES">
<html>
<head>
    <%@ include file="/WEB-INF/pages/shared/head.jsp" %>
</head>
<body scroll="no" ng-app="crystal" class="pace-done">
<sec:authorize access="isAnonymous()">
    <%@ include file="/WEB-INF/pages/shared/login.jsp" %>
</sec:authorize>

<sec:authorize access="isAuthenticated()">
    <div id="wrapper">
        <div data-ng-controller="menuController as mn" data-ng-init="mn.menu=0; mn.subMenu=0;">
            <%@ include file="/WEB-INF/pages/shared/menu.jsp" %>
        </div>
        <div id="page-wrapper" class="gray-bg">
            <%@ include file="/WEB-INF/pages/shared/header-bar.jsp" %>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <%--<%@ include file="/WEB-INF/pages/shared/index.jsp" %>--%>
            </sec:authorize>
            <hr />
            <div id="dlgUpsert"></div>
            <div class="row">
                <div class="col-xs-12">
                    <%@ include file="/WEB-INF/pages/shared/sharedSvc.jsp" %>
                    <%@ include file="/WEB-INF/pages/shared/footer.jsp" %>
                </div>
            </div>
        </div>
    </div>
</sec:authorize>

</body>
</html>

