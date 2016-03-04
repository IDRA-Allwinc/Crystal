<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-xs-12" ng-controller="auditController as vm" ng-init="vm.isModal= ${isModal};">
    <div class="ibox">
        <div class="row">
            <div class="col-xs-12">
                <div class="ibox-title navy-bg">
                    <span class="label-icon pull-left"><i
                            class="fa fa-bookmark-o i-big"></i></span>
                    <h5>Datos de la auditor&iacute;a</h5>
                </div>
            </div>
        </div>
    </div>

    <div class="col-xs-12">
        <%@ include file="/WEB-INF/pages/audit/audit_data.jsp" %>
    </div>

    <div class="col-xs-12">
        <div ng-show="up.MsgError" ng-bind-html="up.MsgError" class="alert alert-error element-center">
        </div>
        <div ng-show="up.MsgSuccess" ng-bind-html="up.MsgSuccess"
             class="alert alert-success element-center">
        </div>
    </div>

    <div class="col-xs-12 modal-footer" ng-if="vm.isModal==false">
        <div class="col-xs-12 ">
            <button class="col-xs-2 pull-right btn btn-primary" ng-show="up.WaitFor===false"
                    ng-click="vm.validateAudit() === true ? up.submit('#FormUpId', '<c:url value='/audit/doUpsert.json' />', FormUpId.$valid):''">
                Guardar
            </button>
            <button class="btn btn-warning" ng-disabled="up.WaitFor===true" data-ng-show="up.WaitFor===true">
                <i class="fa fa-refresh fa-spin"></i> &nbsp; Procesando
            </button>
        </div>
    </div>

    <div class="modal-footer" ng-if="vm.isModal==true">
        <button class="btn btn-white" ng-click="up.cancel()">
            Cancelar
        </button>
        <button class="btn btn-primary " ng-show="up.WaitFor===false"
                ng-click="vm.validateAudit() === true ? up.submitRedirect('#FormUpId', '<c:url value='/audit/doUpsert.json' />', FormUpId.$valid):''">
            Guardar
        </button>
        <button class="btn btn-warning" ng-disabled="up.WaitFor===true" data-ng-show="up.WaitFor===true">
            <i class="fa fa-refresh fa-spin"></i> &nbsp; Procesando
        </button>
    </div>

</div>