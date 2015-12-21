<%@ page import="com.crystal.model.shared.Constants" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
    $(document).ready(function () {
        window.showModalFormDlg("#dlgUpModalId", "#FormUpId");
    });
</script>


<div class="modal inmodal" id="dlgUpModalId" tabindex="-1" ng-controller="upsertController as up" role="dialog"
     aria-hidden="true" ng-cloak>
    <div class="modal-dialog" style="width:960px" ng-controller="userController as vm">
        <div class="modal-content animated flipInY">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <div class="row">
                    <div class="col-xs-3" align="left" style="padding-top: 20px;">
                        <img src="${pageContext.request.contextPath}/assets/img/LogoSE.png" height="90" width="200">
                    </div>
                    <div class="col-xs-6" style="padding-top: 40px;">
                        <h4 class="modal-title">Cambio de contrase&ntilde;a</h4>
                    </div>
                    <div class="col-xs-3" align="right">
                        <i class="fa fa-key modal-icon"></i>
                    </div>
                </div>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="ibox">
                            <div class="ibox-title navy-bg">
                                <h5>Cambio de contrase&ntilde;a del usuario</h5>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <form id="FormUpId" name="FormUpId" class="form-horizontal" role="form"
                              ng-init='vm.m.id = ${id}; vm.m.username = "${username}";'>

                            <input type="hidden" id="id" name="id" ng-model="vm.m.id" ng-update-hidden/>

                            <div class="row">
                                <div class="col-xs-6">
                                    <label class="col-xs-3 control-label font-noraml">Usuario:</label>

                                    <div class="col-xs-9">
                                        <input type="text" name="username" readonly="readonly" ng-model="vm.m.username" class="form-control">
                                    </div>
                                </div>

                                <div class="col-xs-6">
                                </div>
                            </div>
                            <div class="space-15"></div>
                            <div class="row">
                                <div class="col-xs-6">
                                    <label class="col-xs-3 control-label font-noraml">Contrase&ntilde;a:</label>
                                    <div class="col-xs-9">
                                        <input type="password" name="password" ng-model="vm.m.password"
                                               placeholder="Ingrese la contrase&ntilde;a" ng-minlength="8"
                                               ng-required="true" ng-maxlength="200" class="form-control">
                                        <span class="error"
                                              ng-show="FormUpId['password'].$error.required">*Campo requerido</span>
                                        <span class="error" ng-show="FormUpId['password'].$error.minlength">*Longitud m&iacute;nima de 8 caracteres</span>
                                        <span class="error" ng-show="FormUpId['password'].$error.maxlength">*Longitud m&aacute;xima de 200 caracteres</span>
                                    </div>
                                </div>
                                <div class="col-xs-6">
                                    <label class="col-xs-3 control-label font-noraml">Confirmaci&oacute;n:</label>
                                    <div class="col-xs-9">
                                        <input type="password" name="confirm" ng-model="vm.m.confirm"
                                               placeholder="Ingrese la confirmaci&oacute;n"  ng-minlength="8"
                                               ng-required="true" ng-maxlength="200" class="form-control">
                                        <span class="error"
                                              ng-show="FormUpId['confirm'].$error.required">*Campo requerido</span>
                                        <span class="error" ng-show="FormUpId['confirm'].$error.minlength">*Longitud m&iacute;nima de 8 caracteres</span>
                                        <span class="error" ng-show="FormUpId['confirm'].$error.maxlength">*Longitud m&aacute;xima de 200 caracteres</span>
                                        <span class="error" ng-show="vm.m.password !== vm.m.confirm"><br/>*La contrase&ntilde;a no concuerda con la confirmaci&oacute;n</span>
                                    </div>
                                </div>
                            </div>
                        </form>
                        <br/>

                        <div class="row">
                            <div class="col-xs-12">
                                <div ng-show="up.MsgError" ng-bind-html="up.MsgError" class="alert alert-error  element-center">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-white" ng-click="up.cancel()">
                    Cancelar
                </button>
                <button class="btn btn-primary" ng-show="up.WaitFor===false"
                        ng-click="up.submit('#FormUpId', '<c:url value='/management/user/doChangePassword.json' />', FormUpId.$valid)">
                    Guardar
                </button>
                <button class="btn btn-warning" ng-disabled="up.WaitFor===true" data-ng-show="up.WaitFor===true">
                    <i class="fa fa-refresh fa-spin"></i> &nbsp; Procesando
                </button>
            </div>
        </div>
    </div>
</div>
