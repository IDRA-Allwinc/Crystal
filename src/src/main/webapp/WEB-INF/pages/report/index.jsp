<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<HTML lang="es-ES">
<html>
<head>
    <%@ include file="/WEB-INF/pages/shared/headTb.jsp" %>
    <link href="${pageContext.request.contextPath}/assets/content/ui-bootstrap-custom-1.3.1-csp.css" rel="stylesheet"
          type="text/css">

    <script type="text/javascript">
    </script>

</head>
<body scroll="no" ng-app="crystal" class="pace-done">
<div id="wrapper">
    <div>
        <%@ include file="/WEB-INF/pages/shared/menu.jsp" %>
    </div>
    <div id="page-wrapper-a" class="gray-bg">
        <%@ include file="/WEB-INF/pages/shared/header-bar.jsp" %>

        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-xs-12">
                <h2>Reportes</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="">Observaciones por &oacute;rgano fiscalizador</a>
                    </li>
                </ol>
                <br/>

                <div class="alert alert-info alert-10">
                    <i class="fa fa-lightbulb-o fa-lg"></i> &nbsp En esta secci&oacute;n puede consultar el reporte de observaciones por &oacute;rgano fiscalizador.
                </div>
            </div>
        </div>

        <div class="wrapper wrapper-content animated fadeInRight" id="angJsjqGridId"
             data-ng-controller="modalDlgController as vm">
            <div class="row">
                <div class="col-xs-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <span class="label label-success pull-right">Reportes</span>

                            <h2 class="text-navy">
                                <i class="glyphicon glyphicon-stats"></i> &nbsp; Observaciones por &oacute;rgano fiscalizador
                            </h2>
                        </div>
                    </div>
                </div>
            </div>
            <div class="blocker" ng-show="vm.working">
                <div>
                    Procesando...<img src="${pageContext.request.contextPath}/assets/img/ajax_loader.gif" alt=""/>
                </div>
            </div>
        </div>

        <hr/>
        <div id="dlgUpsert"></div>
        <div class="row">
            <div class="col-xs-12">
                <%@ include file="/WEB-INF/pages/shared/sharedSvc.jsp" %>
            </div>
        </div>
    </div>
</div>
</body>
</html>

