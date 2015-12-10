<!DOCTYPE html>
<HTML lang="es-ES">
<html>
<head>
    <%@ include file="/WEB-INF/pages/shared/head.jsp" %>
</head>
<body scroll="no" ng-app="crystal" class="pace-done">
    <div id="wrapper">
        <%@ include file="/WEB-INF/pages/shared/menu.jsp" %>
    <%--<%@ include file="/WEB-INF/pages/shared/login.jsp" %>--%>
        <div id="page-wrapper" class="gray-bg">
            <div class="row border-bottom">
                <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                    <div class="navbar-header">
                        <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                    </div>
                    <ul class="nav navbar-top-links navbar-right">
                        <li>
                            <span class="m-r-sm text-muted welcome-message">Sistema de Control, Registro y Seguimiento de Auditor&iacute;as - <strong>Crystal</strong> </span>
                        </li>
                        <li>
                            <a href="@Url.Action("LogOff", "Account", new { area = "" })">
                            <i class="fa fa-sign-out"></i> Salir
                            </a>
                        </li>
                    </ul>

                </nav>
            </div>
            <sec:authorize access="isAnonymous()">
                <%--<%@ include file="/WEB-INF/pages/shared/index.jsp" %>--%>
            </sec:authorize>
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

