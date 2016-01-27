<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
    $(document).ready(function () {
        window.showModalFormDlg("#dlgUpModalId", "#FormUpId");
    });
</script>

<div class="modal inmodal" id="dlgUpModalId" tabindex="-1" ng-controller="upsertController as up" role="dialog"
     aria-hidden="true" ng-cloak>
    <div class="modal-dialog" style="width:80%" ng-controller="auditController as vm">
        <div class="modal-content animated flipInY">
            <div class="modal-header">

                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>

                <div class="row">
                    <div class="col-xs-3" align="left" style="padding-top: 20px;">
                        <img src="${pageContext.request.contextPath}/assets/img/LogoSE.png" , height="90" width="200">
                    </div>
                    <div class="col-xs-6" style="padding-top: 40px;">
                        <h4 class="modal-title">Datos de auditor&iacute;a</h4>
                    </div>
                    <div class="col-xs-3" align="right">
                        <i class="fa fa-briefcase modal-icon"></i>
                    </div>
                </div>
            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="ibox">
                            <div class="ibox-title navy-bg">
                                <h5>Informaci&oacute;n de la auditor&iacute;a</h5>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <%@ include file="/WEB-INF/pages/audit/partial-audit.jsp" %>
                </div>

                <div class="col-xs-12">
                    <div ng-show="up.MsgError" ng-bind-html="up.MsgError" class="alert alert-error element-center">
                    </div>
                </div>

            </div>

            <div class="modal-footer">
                <button class="btn btn-white" ng-click="up.cancel()">
                    Cancelar
                </button>
                <button class="btn btn-primary " ng-disabled="up.WaitFor==true"
                        ng-click="vm.validateAudit() == true ? up.submitRedirect('#FormUpId', '<c:url value='/audit/doUpsert.json' />', false):''">
                    Guardar
                </button>
            </div>

        </div>

    </div>
</div>
</div>
