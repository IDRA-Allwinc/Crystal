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
                        <h4 class="modal-title">Usuario</h4>
                    </div>
                    <div class="col-xs-3" align="right">
                        <i class="fa fa-user modal-icon"></i>
                    </div>
                </div>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="ibox">
                            <div class="ibox-title navy-bg">
                                <h5>Informaci&oacute;n del usuario</h5>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <form id="FormUpId" name="FormUpId" class="form-horizontal" role="form"
                              ng-init='vm.lstRoles = ${lstRoles}; vm.lstAuditedEntities = ${lstAuditedEntities}; vm.m = ${(model == null ? "{}" : model)}; vm.init();'>

                            <input type="hidden" id="id" name="id" ng-model="vm.m.id" ng-update-hidden/>

                            <div class="row">
                                <div class="col-xs-6">
                                    <label class="col-xs-3 control-label font-noraml">Usuario:</label>

                                    <div class="col-xs-9">
                                        <input type="text" name="username" ng-model="vm.m.username"
                                               placeholder="Ingrese el usuario"
                                               ng-required="true" ng-maxlength="200" ng-minlength="8" class="form-control">
                                        <span class="error" ng-show="FormUpId.username.$error.required">*Campo requerido</span>
                                        <span class="error" ng-show="FormUpId.username.$error.minlength">*Longitud m&iacute;nima de 8 caracteres</span>
                                        <span class="error" ng-show="FormUpId.username.$error.maxlength">*Longitud m&aacute;xima de 200 caracteres</span>
                                    </div>
                                </div>

                                <div class="col-xs-6">
                                    <label class="col-xs-3 control-label font-noraml">Nombre completo:</label>

                                    <div class="col-xs-9">
                                        <input type="text" name="fullName" ng-model="vm.m.fullName"
                                               placeholder="Ingrese el nombre completo del usuario"
                                               ng-required="true" ng-maxlength="200" ng-minlength="3" class="form-control">
                                        <span class="error"
                                              ng-show="FormUpId.fullName.$error.required">*Campo requerido</span>
                                        <span class="error" ng-show="FormUpId.fullName.$error.minlength">*Longitud m&iacute;nima de 3 caracteres</span>
                                        <span class="error" ng-show="FormUpId.fullName.$error.maxlength">*Longitud m&aacute;xima de 200 caracteres</span>
                                    </div>
                                </div>
                            </div>
                            <div class="space-15"></div>
                            <div class="row">
                                <div class="col-xs-10">
                                    <label class="col-xs-4 control-label font-noraml">Correo electr&oacute;nico:</label>

                                    <div class="col-xs-8">
                                        <input type="email" name="email" ng-model="vm.m.email"
                                               placeholder="Ingrese el correo electr&oacute;nico"
                                               ng-required="true" ng-maxlength="200" class="form-control">
                                        <span class="error"
                                              ng-show="FormUpId.email.$error.required">*Campo requerido</span>
                                        <span class="error" ng-show="FormUpId.email.$error.maxlength">*Longitud m&aacute;xima de 1000 caracteres</span>
                                        <span class="error" ng-show="FormUpId.email.$error.email">*El correo electr&oacute;nico no es v&aacute;lido</span>
                                    </div>
                                </div>
                            </div>
                            <div class="space-15"></div>
                            <div class="row">
                                <div class="col-xs-6">
                                    <label class="col-xs-3 control-label font-noraml">Perfil:</label>

                                    <div class="col-xs-9">
                                        <input type="hidden" ng-update-hidden ng-model="vm.m.roleId"
                                               name="roleId" id="roleId">
                                        <select class="form-control m-b" id="chosen-select"
                                                ng-required="true" ng-change="vm.m.roleId = vm.m.role.id;"
                                                ng-options="c.name for c in vm.lstRoles " ng-model="vm.m.role"></select>
                                        <span class="error"
                                              ng-show="FormUpId.roleId.$error.required">*Campo requerido</span>
                                    </div>
                                </div>

                                <div class="col-xs-6" ng-show="vm.m.role.description === '<%=Constants.ROLE_LINK%>'">
                                    <label class="col-xs-3 control-label font-noraml">&Oacute;rgano al que
                                        pertenece:</label>

                                    <div class="col-xs-9">
                                        <input type="hidden" ng-update-hidden ng-model="vm.m.auditedEntityId"
                                               name="auditedEntityId" id="auditedEntityId">
                                        <select class="form-control m-b" id="chosen-select"
                                                ng-required="true"
                                                ng-change="vm.m.auditedEntityId = vm.m.auditedEntity.id;"
                                                ng-options="c.name for c in vm.lstAuditedEntities"
                                                ng-model="vm.m.auditedEntity"></select>
                                        <span class="error" ng-show="FormUpId.auditedEntityId.$error.required">*Campo requerido</span>
                                    </div>
                                </div>
                            </div>
                            <div class="space-15"></div>
                            <div class="row" ng-show="vm.m.id === undefined">
                                <div class="col-xs-6">
                                    <label class="col-xs-3 control-label font-noraml">Contrase&ntilde;a:</label>
                                    <div class="col-xs-9">
                                        <input type="password" name="psw.password" ng-model="vm.m.psw.password"
                                               placeholder="Ingrese la contrase&ntilde;a" ng-minlength="8"
                                               ng-required="true" ng-maxlength="200" class="form-control">
                                        <span class="error"
                                              ng-show="FormUpId['psw.password'].$error.required">*Campo requerido</span>
                                        <span class="error" ng-show="FormUpId['psw.password'].$error.minlength">*Longitud m&iacute;nima de 8 caracteres</span>
                                        <span class="error" ng-show="FormUpId['psw.password'].$error.maxlength">*Longitud m&aacute;xima de 200 caracteres</span>
                                    </div>
                                </div>
                                <div class="col-xs-6">
                                    <label class="col-xs-3 control-label font-noraml">Confirmaci&oacute;n:</label>
                                    <div class="col-xs-9">
                                        <input type="password" name="psw.confirm" ng-model="vm.m.psw.confirm"
                                               placeholder="Ingrese la confirmaci&oacute;n"  ng-minlength="8"
                                               ng-required="true" ng-maxlength="200" class="form-control">
                                        <span class="error"
                                              ng-show="FormUpId['psw.confirm'].$error.required">*Campo requerido</span>
                                        <span class="error" ng-show="FormUpId['psw.confirm'].$error.minlength">*Longitud m&iacute;nima de 8 caracteres</span>
                                        <span class="error" ng-show="FormUpId['psw.confirm'].$error.maxlength">*Longitud m&aacute;xima de 200 caracteres</span>
                                        <span class="error" ng-show="vm.m.psw.password !== vm.m.psw.confirm"><br/>*La contrase&ntilde;a no concuerda con la confirmaci&oacute;n</span>
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
                        ng-click="up.submit('#FormUpId', '<c:url value='/management/user/doUpsert.json' />', FormUpId.$valid)">
                    Guardar
                </button>
                <button class="btn btn-warning" ng-disabled="up.WaitFor===true" data-ng-show="up.WaitFor===true">
                    <i class="fa fa-refresh fa-spin"></i> &nbsp; Procesando
                </button>
            </div>
        </div>
    </div>
</div>
