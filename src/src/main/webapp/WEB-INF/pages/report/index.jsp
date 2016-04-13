<%@ page import="com.crystal.model.shared.Constants" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<HTML lang="es-ES">
<html>
<head>

    <script src="${pageContext.request.contextPath}/assets/scripts/plugins/jsPDF/jspdf.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/plugins/jsPDF/standard_fonts_metrics.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/plugins/jsPDF/jspdf.plugin.autotable.js"></script>

    <%@ include file="/WEB-INF/pages/shared/headTb.jsp" %>
    <link href="${pageContext.request.contextPath}/assets/content/ui-bootstrap-custom-1.3.1-csp.css" rel="stylesheet"
          type="text/css">
    <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.5.3/angular-animate.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/client-app/ctrl/report/report.ctrl.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/angular-bootstrap/ui-bootstrap-custom-1.3.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/angular-bootstrap/ui-bootstrap-custom-tpls-1.3.1.min.js"></script>

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
                    <i class="fa fa-lightbulb-o fa-lg"></i> &nbsp En esta secci&oacute;n puede consultar el reporte de
                    observaciones por &oacute;rgano fiscalizador.
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
                                <i class="glyphicon glyphicon-stats"></i> &nbsp; Observaciones por &oacute;rgano
                                fiscalizador
                            </h2>
                        </div>
                        <div class="ibox-content" data-ng-controller="reportController as vmr" data-ng-init='vmr.lstSupervisorEntities = ${(lstSupervisorEntities == null ? "[]" : lstSupervisorEntities)};'>
                            <div class="row">
                                <div class="col-xs-2 col-xs-offset-5 text-center">
                                    <h4>Elije un órgano fiscalizador</h4>
                                    <input type="text"
                                           ng-model="vmr.entity"
                                           uib-typeahead="entity as entity.name for entity in vmr.lstSupervisorEntities | filter:$viewValue | limitTo:8"
                                           class="form-control"
                                           typeahead-min-length="0"
                                           typeahead-on-select='vmr.selectSupervisory($item, "<c:url value='/report/getReportDataBySupervisor.json' />");'>
                                </div>
                            </div>

                            <br/>
                            <br/>

                            <div class="row" data-ng-show="vmr.selectedSupervisory !== undefined">
                                <div class="col-xs-4 col-xs-offset-4 text-center">
                                    <h4>Órgano fiscalizador: {{vmr.selectedSupervisory.name}}</h4>
                                    <button ng-click="vmr.exportToPDF()">Exportar a PDF</button>
                                </div>
                            </div>
                            <br/>
                            <div class="row" data-ng-show="vmr.selectedSupervisory !== undefined">
                                <div class="col-xs-10 col-xs-offset-1">
                                    <table id="supervisorTable" class="table table-responsive tableReportP">
                                        <col style="width:20%">
                                        <col style="width:10%">
                                        <col style="width:10%">
                                        <col style="width:10%">
                                        <col style="width:10%">
                                        <col style="width:10%">
                                        <col style="width:10%">
                                        <col style="width:10%">
                                        <col style="width:10%">
                                        <thead>
                                        <%--<tr>--%>
                                            <%--<th class="text-center" colspan="9"> Sector Economía <br/> Situación de acciones emitidas por la ASF</th>--%>
                                        <%--</tr>--%>
                                        <tr class="tableReportHeader">
                                            <th class="text-center" style="vertical-align: middle;" rowspan="2">Cuenta Pública</th>
                                            <th class="text-center" style="vertical-align: middle;" rowspan="2">No.<br/>Auditorias</th>
                                            <th class="text-center" style="border-bottom: none !important;" colspan="2">Acciones</th>
                                            <th class="text-center" style="border-bottom: none !important;" colspan="4">Pendientes</th>
                                            <th class="text-center" style="vertical-align: middle;" rowspan="2">Avance<br/>(A/E)%</th>
                                        </tr>
                                        <tr class="tableReportHeader">
                                            <th class="text-center" style="border-top: none !important;">Emitidas (E)</th>
                                            <th class="text-center" style="border-top: none !important;">Atendidas (A)</th>
                                            <th class="text-center" style="border-top: none !important;">R</th>
                                            <th class="text-center" style="border-top: none !important;">PO</th>
                                            <th class="text-center" style="border-top: none !important;">PRAS</th>
                                            <th class="text-center" style="border-top: none !important;">Total</th>
                                        </tr>
                                        </thead>
                                        <tbody class="tableReportC1">
                                        <tr class="animate-repeat" ng-repeat-start="row in vmr.supervisoryData">
                                            <td class="text-center">
                                                <a ng-click='vmr.selectYear(row.id, "<c:url value='/report/getReportDataBySupervisorYear.json' />");'>
                                                    <div style="height:100%;width:100%">
                                                        {{ row.id }}
                                                    </div>
                                                </a>
                                            </td>
                                            <td class="text-center">{{ row.auditNumber }}</td>
                                            <td class="text-center">{{ row.emitted }}</td>
                                            <td class="text-center">{{ row.attended }}</td>
                                            <td class="text-center">{{ row.recommendations }}</td>
                                            <td class="text-center">{{ row.observations }}</td>
                                            <td class="text-center">{{ row.responsibilities }}</td>
                                            <td class="text-center">{{ row.notAttended }}</td>
                                            <td class="text-center">{{ row.progress }}</td>
                                        </tr>
                                        <tr class="animate-repeat" ng-repeat-end ng-if="row.id === vmr.selectedYear">
                                            <td colspan="9" style="padding: 0px;">
                                                <table class="table table-hover" style="margin-bottom: auto">
                                                    <col style="width:20%">
                                                    <col style="width:10%">
                                                    <col style="width:10%">
                                                    <col style="width:10%">
                                                    <col style="width:10%">
                                                    <col style="width:10%">
                                                    <col style="width:10%">
                                                    <col style="width:10%">
                                                    <col style="width:10%">
                                                    <tbody class="tableReportC2">
                                                    <tr ng-repeat-start="row in vmr.yearData">
                                                        <td class="text-center">
                                                            <a ng-click='vmr.selectEntityType(row.aux, "<c:url value='/report/getReportDataBySupervisorYearEntityType.json' />");'>
                                                                <div style="height:100%;width:100%">
                                                                    {{row.id}}
                                                                </div>
                                                            </a>
                                                        </td>
                                                        <td class="text-center">{{ row.auditNumber }}</td>
                                                        <td class="text-center">{{ row.emitted }}</td>
                                                        <td class="text-center">{{ row.attended }}</td>
                                                        <td class="text-center">{{ row.recommendations }}</td>
                                                        <td class="text-center">{{ row.observations }}</td>
                                                        <td class="text-center">{{ row.responsibilities }}</td>
                                                        <td class="text-center">{{ row.notAttended }}</td>
                                                        <td class="text-center">{{ row.progress }}</td>
                                                    </tr>
                                                    <tr ng-repeat-end ng-if="row.aux === vmr.selectedEntityType">
                                                        <td colspan="9" style="padding: 0px;">
                                                            <table class="table"
                                                                   style="margin-bottom: auto">
                                                                <col style="width:20%">
                                                                <col style="width:10%">
                                                                <col style="width:10%">
                                                                <col style="width:10%">
                                                                <col style="width:10%">
                                                                <col style="width:10%">
                                                                <col style="width:10%">
                                                                <col style="width:10%">
                                                                <col style="width:10%">
                                                                <tbody class="tableReportC3">
                                                                <tr ng-repeat-start="row in vmr.entityTypeData">
                                                                    <td class="text-center">
                                                                        <a ng-click='vmr.selectEntity(row.aux, "<c:url value='/report/getReportDataBySupervisorYearEntity.json' />");'>
                                                                            <div style="height:100%;width:100%">
                                                                                {{ row.id }}
                                                                            </div>
                                                                        </a>
                                                                    </td>
                                                                    <td class="text-center">{{ row.auditNumber }}</td>
                                                                    <td class="text-center">{{ row.emitted }}</td>
                                                                    <td class="text-center">{{ row.attended }}</td>
                                                                    <td class="text-center">{{ row.recommendations }}</td>
                                                                    <td class="text-center">{{ row.observations }}</td>
                                                                    <td class="text-center">{{ row.responsibilities }}
                                                                    </td>
                                                                    <td class="text-center">{{ row.notAttended }}</td>
                                                                    <td class="text-center">{{ row.progress }}</td>
                                                                </tr>
                                                                <tr ng-repeat-end ng-if="row.aux === vmr.selectedEntity">
                                                                    <td colspan="9" style="padding: 0px;">
                                                                        <table class="table"
                                                                               style="margin-bottom: auto">
                                                                            <col style="width:20%">
                                                                            <col style="width:10%">
                                                                            <col style="width:10%">
                                                                            <col style="width:10%">
                                                                            <col style="width:10%">
                                                                            <col style="width:10%">
                                                                            <col style="width:10%">
                                                                            <col style="width:10%">
                                                                            <col style="width:10%">
                                                                            <tbody class="tableReportC4">
                                                                            <tr ng-repeat="row in vmr.entityData">
                                                                                <td class="text-center">{{row.id}}</td>
                                                                                <td class="text-center">{{row.auditNumber}}</td>
                                                                                <td class="text-center">{{row.emitted}}</td>
                                                                                <td class="text-center">{{row.attended}}</td>
                                                                                <td class="text-center"><a ng-click='vmr.openDetail(row.aux, "<%=Constants.RECOMMENDATION_R%>", "<c:url value='/audit/getInfoDetail.html?id=idParam&type=detailType' />");'>
                                                                                    <div style="height:100%;width:100%">
                                                                                        {{ row.recommendations }}
                                                                                    </div>
                                                                                </a></td>
                                                                                <td class="text-center"><a ng-click='vmr.openDetail(row.aux, "<%=Constants.OBSERVATION_R%>","<c:url value='/audit/getInfoDetail.html?id=idParam&type=detailType' />");'>
                                                                                    <div style="height:100%;width:100%">
                                                                                        {{ row.observations }}
                                                                                    </div>
                                                                                </a></td>
                                                                                <td class="text-center"><a ng-click='vmr.openDetail(row.aux, "<%=Constants.RESPONSIBILITY_R%>","<c:url value='/audit/getInfoDetail.html?id=idParam&type=detailType' />");'>
                                                                                    <div style="height:100%;width:100%">
                                                                                        {{ row.responsibilities }}
                                                                                    </div>
                                                                                </a></td>
                                                                                <td class="text-center">{{row.notAttended}}</td>
                                                                                <td class="text-center">{{row.progress}}</td>
                                                                            </tr>
                                                                            </tbody>
                                                                        </table>
                                                                    </td>
                                                                </tr>
                                                                </tbody>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                </table>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="row" data-ng-show="vmr.selectedSupervisory !== undefined">
                                <div class="col-xs-10 col-xs-offset-1">
                                    <p class="text-center"> R = Recomendación, PO = Pliego de observaciones, PRAS = Responsabilidad Administrativa Sancionatoria </p>
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
            </div>
        </div>
    </div>
</div>
</body>
</html>

