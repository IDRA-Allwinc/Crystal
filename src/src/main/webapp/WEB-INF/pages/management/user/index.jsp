<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<HTML lang="es-ES">
<html>
<head>
    <%@ include file="/WEB-INF/pages/shared/head.jsp" %>
</head>
<body scroll="no" ng-app="crystal" class="pace-done">
    <div id="wrapper">
        <div data-ng-controller="menuController as mn" data-ng-init="mn.menu=1; mn.subMenu=1;">
            <%@ include file="/WEB-INF/pages/shared/menu.jsp" %>
        </div>
        <div id="page-wrapper" class="gray-bg">
            <%@ include file="/WEB-INF/pages/shared/header-bar.jsp" %>
            Prueba de index de usuario
            <hr />
            <div id="dlgUpsert"></div>
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2">
                    <%@ include file="/WEB-INF/pages/shared/sharedSvc.jsp" %>
                    <%@ include file="/WEB-INF/pages/shared/footer.jsp" %>
                </div>
            </div>
        </div>
    </div>
</body>
</html>

