<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row" ng-controller="upsertController as up">
    <div class="col-xs-12" ng-controller="auditController as vm">
        <div class="ibox">
            <div class="row">
                <div class="col-xs-12">
                    <div class="ibox-title navy-bg">
                        <h5>Informaci&oacute;n de la auditor&iacute;a</h5>
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

        <div class="col-xs-12 modal-footer">
            <div class="col-xs-12 ">
                <button class="col-xs-2 pull-right btn btn-primary" ng-disabled="up.WaitFor==true"
                        ng-click="vm.validateAudit() == true ? up.submit('#FormUpId', '<c:url value='/audit/doUpsert.json' />', FormUpId.$valid):''">
                    Guardar
                </button>
            </div>
        </div>

    </div>
</div>