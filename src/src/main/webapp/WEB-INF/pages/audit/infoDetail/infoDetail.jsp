<%@ page import="com.crystal.model.shared.Constants" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
    <%@ include file="/WEB-INF/pages/shared/headTb.jsp" %>
    <script src="${pageContext.request.contextPath}/assets/scripts/client-app/ctrl/audit/auditInfoDetail.ctrl.js"></script>
</head>

<body>

<div ng-app="crystal" ng-cloak>
    <div id="wrapper" ng-controller="auditInfoDetailController as vm" ng-init='vm.m = ${model == null ? "{}" : model}'>

        <div class="wrapper wrapper-content  animated fadeInRight">

            <div class="row">
                <div class="col-xs-8 col-xs-offset-2">

                    <div class="ibox">
                        <div class="ibox-content">

                            <div class="row">
                                <div class="col-xs-3" align="left" style="padding-top: 20px;">
                                    <img src="${pageContext.request.contextPath}/assets/img/LogoSE.png" , height="90"
                                         width="200">
                                </div>
                                <div class="col-xs-6 element-center" style="padding-top: 40px;">

                                    <h2><b>Sector Econom&iacute;a</b></h2>
                                    <br/>

                                    <h3 ng-if="vm.m.detailType=='<%=Constants.RECOMMENDATION_R%>'">Situaci&oacute;n de
                                        las
                                        recomendaciones</h3>

                                    <h3 ng-if="vm.m.detailType=='<%=Constants.OBSERVATION_R%>'">Situaci&oacute;n de los
                                        pliegos de
                                        observaciones</h3>

                                    <h3 ng-if="vm.m.detailType=='<%=Constants.RESPONSIBILITY_R%>'">Situaci&oacute;n de
                                        las
                                        promociones de responsabilidad</h3>

                                </div>
                                <div class="col-xs-3" align="right" style="padding-top: 20px;">
                                    <i class="fa fa-clock-o mid-icon"></i>
                                </div>
                            </div>
                            <br/>

                            <h4><b>Auditor&iacute;a n&uacute;mero:</b> {{vm.m.number}}</h4>
                            <br/>
                            <h4><b>Nombre de la auditor&iacute;a: </b>{{vm.m.name}}</h4>
                            <br/>
                            <h4><b>Tipo de auditor&iacute;a: </b>{{vm.m.auditType.name}}</h4>

                            <ul class="sortable-list connectList agile-list"
                                ng-if="vm.m.lstUnattendedEntities!=undefined">
                                <li class="danger-element"
                                    ng-repeat="entity in vm.m.lstUnattendedEntities track by $index">

                                    <b>N&uacute;mero:</b>
                                    <br/>
                                    {{entity.name}}
                                    <br/>
                                    <br/>
                                    <b>Descripci&oacute;n:</b>
                                    <br/>
                                    {{entity.description}}


                                    <div class="agile-detail pull-right">
                                        <i class="fa fa-clock-o red"></i> <b>Fecha l&iacute;mite:</b>
                                        {{entity.calendarStr}}
                                    </div>

                                </li>
                            </ul>

                            <ul class="sortable-list connectList agile-list"
                                ng-if="!(vm.m.lstUnattendedEntities.length>0)">
                                <li class="success-element">
                                    <b>No se encontraron elementos sin atender.</b>
                                </li>
                            </ul>

                        </div>
                    </div>
                </div>
            </div>

        </div>

    </div>
</div>
</div>
</body>
</html>

