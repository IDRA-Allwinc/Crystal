<!DOCTYPE html>
<HTML lang="es-ES">
<html>
<head>
    <%@ include file="/WEB-INF/pages/shared/head.jsp" %>
    <%--<script src="${pageContext.request.contextPath}/assets/scripts/app/shared/mainIndexCtrl.js"></script>--%>
</head>
<body scroll="no" ng-app="crystal" class="pace-done skin-1">
    <div id="wrapper">
    <%--<%@ include file="/WEB-INF/pages/shared/menu.jsp" %>--%>
    <%--<%@ include file="/WEB-INF/pages/shared/login.jsp" %>--%>
    <div>
        <sec:authorize access="isAnonymous()">
            <%--<%@ include file="/WEB-INF/pages/shared/index.jsp" %>--%>
        </sec:authorize>
    </div>
</div>
<div class="row">
    <div class="col-xs-8 col-xs-offset-2">
        <%@ include file="/WEB-INF/pages/shared/sharedSvc.jsp" %>
        <%@ include file="/WEB-INF/pages/shared/footer.jsp" %>
    </div>
</div>
</body>
</html>

