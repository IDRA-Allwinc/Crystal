<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<HTML lang="es-ES">
<html>
<head>
    <%@ include file="/WEB-INF/pages/shared/headTb.jsp" %>
    <link href="${pageContext.request.contextPath}/assets/content/ui-bootstrap-custom-1.1.0-csp.css" rel="stylesheet" type="text/css">

    <script src="${pageContext.request.contextPath}/assets/scripts/angular-bootstrap/ui-bootstrap-custom-1.1.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/angular-bootstrap/ui-bootstrap-custom-tpls-1.1.0.min.js"></script>

    <link href="${pageContext.request.contextPath}/assets/content/upload/jquery.fileupload.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/assets/content/ui-bootstrap-custom-1.1.0-csp.css" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath}/assets/scripts/upload/vendor/jquery.ui.widget.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/upload/jquery.iframe-transport.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/upload/jquery.fileupload.js"></script>

    <script src="${pageContext.request.contextPath}/assets/scripts/client-app/ctrl/audit/audit.ctrl.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/client-app/ctrl/audit/recommendation/recommendation.ctrl.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/client-app/ctrl/audit/letter/letter.ctrl.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/client-app/ctrl/audit/request/request.ctrl.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/client-app/ctrl/audit/request/requestViewDocs.ctrl.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/client-app/ctrl/audit/letter/letterViewDocs.ctrl.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/client-app/ctrl/audit/comment/comment.ctrl.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/client-app/ctrl/audit/comment/commentViewDocs.ctrl.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/client-app/ctrl/audit/recommendation/recommendation.ctrl.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/client-app/ctrl/audit/recommendation/recommendationViewDocs.ctrl.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/client-app/ctrl/audit/observation/observation.ctrl.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/client-app/ctrl/audit/observation/observationViewDocs.ctrl.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/client-app/ctrl/audit/responsibility/responsibility.ctrl.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/client-app/ctrl/audit/responsibility/responsibilityViewDocs.ctrl.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/client-app/ctrl/audit/request/extensionRequest.ctrl.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/client-app/ctrl/audit/event/event.ctrl.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/client-app/ctrl/audit/event/eventViewDocs.ctrl.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/client-app/ctrl/audit/comment/extensionComment.ctrl.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/client-app/ctrl/audit/recommendation/extensionRecommendation.ctrl.js"></script>

</head>
<body scroll="no" ng-app="crystal" class="pace-done" ng-cloak>
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
        <div class="row">

            <div class="col-xs-12 tabscontainer">
                <div class="col-xs-2 optiontab">
                    <ul class="nav nav-tabs tabs-left">
                        <li class="active"><a href="#auditData" data-toggle="tab"><span class="label-icon pull-left"><i
                                class="fa fa-bookmark-o i-big"></i></span>Datos de auditor&iacute;a</a></li>
                        <li><a href="#letters" data-toggle="tab"><span class="label-icon pull-left"><i
                                class="fa fa-files-o i-big"></i></span>Oficios</a></li>
                        <li><a href="#comments" data-toggle="tab"><span class="label-icon pull-left"><i
                                class="fa fa-eye i-big"></i></span>Observaciones</a></li>
                        <li><a href="#recommendations" data-toggle="tab"><span class="label-icon pull-left"><i
                                class="fa fa-hand-o-right i-big"></i></span>Recomendaciones</a></li>
                        <li><a href="#observations" data-toggle="tab"><span class="label-icon pull-left"><i
                                class="fa fa-list-ul i-big"></i></span>Pliego de observaciones</a></li>
                        <li><a href="#responsibilities" data-toggle="tab"><span class="label-icon pull-left"><i
                                class="fa fa-money i-big"></i></span>Promociones de responsabilidad</a></li>
                        <li><a href="#events" data-toggle="tab"><span class="label-icon pull-left"><i
                                class="fa fa-clock-o i-big"></i></span>Eventos</a></li>
                        <li><a href="#wornkin" data-toggle="tab"><span class="label-icon pull-left"><i
                                class="fa fa-comments-o i-big"></i></span>Notificaciones</a></li>
                    </ul>
                </div>
                <div class="col-xs-10 tabcontent">
                    <div class="tab-content">
                        <div class="tab-pane active animated fadeInDown" id="auditData">
                            <%@ include file="/WEB-INF/pages/audit/partial-audit.jsp" %>
                        </div>

                        <div class="tab-pane animated fadeInDown" id="letters">
                            <%@ include file="/WEB-INF/pages/audit/letter/index.jsp"%>
                        </div>

                        <div class="tab-pane animated fadeInDown" id="comments">
                            <%@ include file="/WEB-INF/pages/audit/comment/index.jsp"%>
                        </div>

                        <div class="tab-pane animated fadeInDown" id="recommendations">
                            <%@ include file="/WEB-INF/pages/audit/recommendation/index.jsp"%>
                        </div>

                        <div class="tab-pane animated fadeInDown" id="observations">
                            <%@ include file="/WEB-INF/pages/audit/observation/index.jsp"%>
                        </div>

                        <div class="tab-pane animated fadeInDown" id="responsibilities">
                            <%@ include file="/WEB-INF/pages/audit/responsibility/index.jsp"%>
                        </div>

                        <div class="tab-pane animated fadeInDown" id="events">
                            <%@ include file="/WEB-INF/pages/audit/event/index.jsp"%>
                        </div>

                        <div class="tab-pane animated fadeInDown" id="wornkin">
                            <%@ include file="/WEB-INF/pages/working.jsp" %>
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

