<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<HTML lang="es-ES">
<html>
<head>
    <%@ include file="/WEB-INF/pages/shared/head.jsp" %>
    <link href="${pageContext.request.contextPath}/assets/content/ui-bootstrap-custom-1.1.0-csp.css" rel="stylesheet"
          type="text/css">
    <script src="${pageContext.request.contextPath}/assets/scripts/client-app/ctrl/audit/audit.ctrl.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/angular-bootstrap/ui-bootstrap-custom-1.1.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/angular-bootstrap/ui-bootstrap-custom-tpls-1.1.0.min.js"></script>

</head>
<body scroll="no" ng-app="crystal" class="pace-done">
<div id="wrapper">
    <div data-ng-controller="menuController as mn" data-ng-init="mn.menu=3;">
        <%@ include file="/WEB-INF/pages/shared/menu.jsp" %>
    </div>
    <div id="page-wrapper" class="gray-bg">
        <%@ include file="/WEB-INF/pages/shared/header-bar.jsp" %>
        <hr/>
        <div id="dlgUpsert"></div>
        <div class="row">
            <div class="col-xs-12">
                <%@ include file="/WEB-INF/pages/shared/sharedSvc.jsp" %>
                <%@ include file="/WEB-INF/pages/shared/footer.jsp" %>
            </div>
        </div>
    </div>
</div>
</body>
</html>

