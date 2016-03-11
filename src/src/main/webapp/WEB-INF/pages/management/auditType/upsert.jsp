<%@ page import="com.crystal.model.shared.Constants" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
    $(document).ready(function () {
        window.showModalFormDlg("#dlgUpModalId", "#FormUpId");
    });
</script>


<div class="modal inmodal" id="dlgUpModalId" tabindex="-1" ng-controller="upsertController as up" role="dialog"
     aria-hidden="true" ng-cloak>
    <div class="modal-dialog" style="width:960px" ng-controller="auditTypeController as vm">
        <div class="modal-content animated flipInY">
            <div class="modal-header">

                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>

                <div class="row">
                    <div class="col-xs-3" align="left" style="padding-top: 20px;">
                        <img src="${pageContext.request.contextPath}/assets/img/LogoSE.png" , height="90" width="200">
                    </div>
                    <div class="col-xs-6" style="padding-top: 40px;">
                        <h4 class="modal-title">Tipo de auditor&iacute;a</h4>
                    </div>
                    <div class="col-xs-3" align="right">
                        <i class="fa fa-cog modal-icon"></i>
                    </div>
                </div>
            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="ibox">
                            <div class="ibox-title navy-bg">
                                <h5>Informaci&oacute;n del tipo de auditor&iacute;a</h5>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12">
                        <form id="FormUpId" name="FormUpId" class="form-horizontal" role="form"
                              ng-init='vm.m = ${(model == null ? "{}" : model)}; vm.init();'>

                            <input type="hidden" id="id" name="id" ng-model="vm.m.id" ng-update-hidden/>

                            <div class="row">
                                <div class="col-xs-12">

                                    <div class="col-xs-11 col-xs-offset-1 form-group">
                                        <label class="col-xs-3 control-label font-noraml">Nombre:</label>

                                        <div class="col-xs-9">
                                            <input type="text" name="name" ng-model="vm.m.name"
                                                   placeholder="Ingrese el nombre del tipo de auditor&iacute;a"
                                                   minlength="8" maxlength="200"
                                                   ng-required="true" class="form-control">
                                            <span class="error" ng-show="FormUpId.name.$error.required">*Campo requerido</span>
                                            <span class="error" ng-show="FormUpId.name.$error.minlength">*El campo debe tener entre 8 y 200 caracteres</span>
                                            <span class="error" ng-show="FormUpId.name.$error.maxlength">*El campo debe tener entre 8 y 200 caracteres</span>
                                        </div>
                                    </div>

                                    <div class="col-xs-11 col-xs-offset-1 form-group">
                                        <label class="col-xs-3 control-label font-noraml">Descripci&oacute;n:</label>

                                        <div class="col-xs-9">
                                            <textarea name="description" ng-model="vm.m.description"  placeholder="Ingrese la descripci&oacute;n del tipo de auditor&iacute;a"
                                                      minlength="8" maxlength="300"
                                                   ng-required="true" class="form-control"></textarea>
                                            <span class="error" ng-show="FormUpId.description.$error.required">*Campo requerido</span>
                                            <span class="error" ng-show="FormUpId.description.$error.minlength">*El campo debe tener entre 8 y 300 caracteres</span>
                                            <span class="error" ng-show="FormUpId.description.$error.maxlength">*El campo debe tener entre 8 y 300 caracteres</span>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </form>
                    </div>

                    <div class="col-xs-12">
                        <div ng-show="up.MsgError" ng-bind-html="up.MsgError" class="alert alert-error element-center">
                        </div>
                    </div>

                </div>

            </div>
            <div class="modal-footer">
                <button class="btn btn-white" ng-click="up.cancel()">
                    Cancelar
                </button>
                <button class="btn btn-primary " ng-disabled="up.WaitFor==true"
                        ng-click="up.submit('#FormUpId', '<c:url value='/management/auditType/doUpsert.json' />', FormUpId.$valid)">
                    Guardar
                </button>
            </div>
        </div>
    </div>
</div>
