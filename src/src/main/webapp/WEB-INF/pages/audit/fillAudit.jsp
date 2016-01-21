<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<HTML lang="es-ES">
<html>
<head>
    <%@ include file="/WEB-INF/pages/shared/headTb.jsp" %>
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

        <div class="row wrapper border-bottom white-bg">
            <div class="col-xs-12">
                <div class="ibox-title">
                    <span class="label pull-right label-primary pull-right">Auditor&iacute;as</span>

                    <h2 class="text-navy">
                        <i class="fa fa-university"></i> &nbsp; Llenado de auditor&iacute;as
                    </h2>
                    <hr style="height:1px;border:none;color:#333;background-color:#333;">
                </div>
            </div>
        </div>
        <div>
            <div class="fh-breadcrumb">
                <div class="fh-column">
                    <div class="full-height-scroll">
                        <ul class="list-group elements-list">
                            <li class="list-group-item tab-size-medium active">
                                <a data-toggle="tab" href="#tab-1">
                                    <h5>
                                        <strong>
                                                    <span class="label pull-right label-primary"><i
                                                            class="fa fa-qrcode i-medium"></i></span>
                                            * Datos de auditor&iacute;a
                                        </strong>
                                    </h5>
                                </a>
                            </li>
                            <li class="list-group-item tab-size-medium">
                                <a data-toggle="tab" href="#tab-2" class="tab-select-medium">
                                    <h5>
                                        <strong>
                                                    <span class="label pull-right label-primary"><i
                                                            class="fa fa-file i-medium"></i></span>
                                            * Oficios
                                        </strong>
                                    </h5>
                                </a>
                            </li>
                            <li class="list-group-item tab-size-medium">
                                <a data-toggle="tab" href="#tab-2">
                                    <h5>
                                        <strong>
                                                    <span class="label pull-right label-primary"><i
                                                            class="fa fa-eye i-medium"></i></span>
                                            * Observaciones
                                        </strong>
                                    </h5>
                                </a>
                            </li>

                            <li class="list-group-item tab-size-medium">
                                <a data-toggle="tab" href="#tab-2">
                                    <h5>
                                        <strong>
                                                    <span class="label pull-right label-primary"><i
                                                            class="fa fa-thumbs-up i-medium"></i></span>
                                            * Recomendaciones
                                        </strong>
                                    </h5>
                                </a>
                            </li>
                            <li class="list-group-item tab-size-medium">
                                <a data-toggle="tab" href="#tab-2">
                                    <h5>
                                        <strong>
                                                    <span class="label pull-right label-primary"><i
                                                            class="fa fa-list-ul i-medium"></i></span>
                                            * Pliego de las observaciones
                                        </strong>
                                    </h5>
                                </a>
                            </li>

                            <li class="list-group-item tab-size-medium">
                                <a data-toggle="tab" href="#tab-2">
                                    <h5>
                                        <strong>
                                                    <span class="label pull-right label-primary"><i
                                                            class="fa fa-sitemap i-medium"></i></span>
                                            * Promoci&oacute;n de responsabilidad
                                        </strong>
                                    </h5>
                                </a>
                            </li>
                            <li class="list-group-item tab-size-medium">
                                <a data-toggle="tab" href="#tab-2">
                                    <h5>
                                        <strong>
                                                    <span class="label pull-right label-primary"><i
                                                            class="fa fa-th-large i-medium"></i></span>
                                            * Eventos
                                        </strong>
                                    </h5>
                                </a>
                            </li>
                            <li class="list-group-item tab-size-medium">
                                <a data-toggle="tab" href="#tab-2">
                                    <h5>
                                        <strong>
                                                    <span class="label pull-right label-primary"><i
                                                            class="fa fa-volume-up i-medium"></i></span>
                                            * Notificaciones
                                        </strong>
                                    </h5>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="full-height">
                    <div class="full-height-scroll white-bg border-left">
                        <div class="element-detail-box" style="margin-left: 10px !important;">
                            <div class="tab-content">
                                <div id="tab-1" class="tab-pane active">
                                    <%@ include file="/WEB-INF/pages/audit/partial-audit.jsp" %>
                                </div>
                                <div id="tab-2" class="tab-pane">
                                    En construcci&oacute;n
                                </div>
                            </div>
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
                <%@ include file="/WEB-INF/pages/shared/footer.jsp" %>
            </div>
        </div>
    </div>
</div>
</body>
</html>

