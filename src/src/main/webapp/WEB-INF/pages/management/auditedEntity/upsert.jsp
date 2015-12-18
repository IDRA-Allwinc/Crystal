<%@ page import="com.crystal.model.shared.Constants" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
    $(document).ready(function () {
        window.showModalFormDlg("#dlgUpModalId", "#FormUpId");
    });
</script>


<div class="modal inmodal" id="dlgUpModalId" tabindex="-1" ng-controller="upsertController as up" role="dialog"
     aria-hidden="true" ng-cloak>
    <div class="modal-dialog" style="width:960px" ng-controller="auditedEntityController as vm">
        <div class="modal-content animated flipInY">
            <div class="modal-header">

                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>

                <div class="row">
                    <div class="col-xs-3" align="left" style="padding-top: 20px;">
                        <img src="${pageContext.request.contextPath}/assets/img/LogoSE.png" , height="90" width="200">
                    </div>
                    <div class="col-xs-6" style="padding-top: 40px;">
                        <h4 class="modal-title">Ente fiscalizado</h4>
                    </div>
                    <div class="col-xs-3" align="right">
                        <i class="fa fa-sitemap modal-icon"></i>
                    </div>
                </div>
            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="ibox">
                            <div class="ibox-title navy-bg">
                                <h5>Informaci&oacute;n del ente fiscalizado</h5>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <form id="FormUpId" name="FormUpId" class="form-horizontal" role="form"
                              ng-init='vm.m = ${(model == null ? "{}" : model)}; vm.lstAuditedEntityTypes=${lstAuditedEntityTypes};  vm.init();'>

                            <input type="hidden" id="id" name="id" ng-model="vm.m.Id" ng-update-hidden/>

                            <div class="row">
                                <div class="col-xs-10 form-group">
                                    <label class="col-xs-3 control-label font-noraml">Nombre:</label>

                                    <div class="col-xs-9">
                                        <input type="text" name="name" ng-model="vm.m.name"
                                               placeholder="Ingrese el nombre del ente fiscalizado"
                                               ng-required="true" ng-maxlength="200" class="form-control">
                                        <span class="error"
                                              ng-show="FormUpId.name.$error.required">*Campo requerido</span>
                                        <span class="error" ng-show="FormUpId.name.$error.maxlength">*Longitud m&aacute;xima de 200 caracteres</span>
                                    </div>
                                </div>

                                <div class="col-xs-10 form-group">
                                    <label class="col-xs-3 control-label font-noraml">Responsable:</label>

                                    <div class="col-xs-9">
                                        <input type="text" name="responsible" ng-model="vm.m.responsible"
                                               placeholder="Ingrese el nombre completo del responsable"
                                               ng-required="true" ng-maxlength="200" class="form-control">
                                        <span class="error" ng-show="FormUpId.responsible.$error.required">*Campo requerido</span>
                                        <span class="error" ng-show="FormUpId.responsible.$error.maxlength">*Longitud m&aacute;xima de 200 caracteres</span>
                                    </div>
                                </div>
                                <div class="col-xs-10 form-group">
                                    <label class="col-xs-3 control-label font-noraml">Responsable:</label>

                                    <div class="col-xs-9">
                                        <input type="text" name="phone" ng-model="vm.m.phone"
                                               placeholder="Ingrese el tel&oacute;fono "
                                               ng-required="true" ng-maxlength="200" class="form-control">
                                        <span class="error"
                                              ng-show="FormUpId.phone.$error.required">*Campo requerido</span>
                                        <span class="error" ng-show="FormUpId.phone.$error.maxlength">*Longitud m&aacute;xima de 200 caracteres</span>
                                    </div>
                                </div>

                                <div class="col-xs-10 form-group">
                                    <label class="col-xs-3 control-label font-noraml">Email:</label>

                                    <div class="col-xs-9">
                                        <input type="text" name="email" ng-model="vm.m.email"
                                               placeholder="Ingrese el email del reponsable"
                                               ng-required="true" ng-maxlength="200" class="form-control">
                                        <span class="error"
                                              ng-show="FormUpId.email.$error.required">*Campo requerido</span>
                                        <span class="error" ng-show="FormUpId.email.$error.maxlength">*Longitud m&aacute;xima de 200 caracteres</span>
                                    </div>
                                </div>

                                <div class="col-xs-10 form-group">
                                    <input type="hidden" ng-update-hidden ng-model="vm.m.auditedEntityTypeId"
                                           name="auditedEntityTypeId" id="auditedEntityTypeId">

                                    <label class="col-xs-3 control-label font-noraml">Tipo de entidad fiscalizada:</label>

                                    <div class="col-xs-9">
                                        <select class="form-control m-b" id="chosen-select"
                                                ng-required="true"
                                                ng-change="vm.m.auditedEntityTypeId = vm.m.auditedEntity.id;"
                                                ng-options="c.name for c in vm.lstAuditedEntityTypes "
                                                ng-model="vm.m.auditedEntityType"></select>
                                    <span class="error"
                                          ng-show="FormUpId.auditedEntityTypeIdroleId.$error.required">*Campo requerido</span
                                    </div>
                                </div>
                                <%--<div class="row">--%>
                                <%--<div class="col-xs-10">--%>
                                <%--<label class="col-xs-4 control-label font-noraml">Correo electr&oacute;nico:</label>--%>

                                <%--<div class="col-xs-8">--%>
                                <%--<input type="email" name="email" ng-model="vm.m.email"--%>
                                <%--placeholder="Ingrese el correo electr&oacute;nico"--%>
                                <%--ng-required="true" ng-maxlength="200" class="form-control">--%>
                                <%--<span class="error"--%>
                                <%--ng-show="FormUpId.email.$error.required">*Campo requerido</span>--%>
                                <%--<span class="error" ng-show="FormUpId.email.$error.maxlength">*Longitud m&aacute;xima de 1000 caracteres</span>--%>
                                <%--<span class="error" ng-show="FormUpId.email.$error.email">*El correo electr&oacute;nico no es v&aacute;lido</span>--%>
                                <%--</div>--%>
                                <%--</div>--%>
                                <%--</div>--%>
                                <%--<div class="space-15"></div>--%>
                                <%--<div class="row">--%>
                                <%--<div class="col-xs-6">--%>
                                <%--<label class="col-xs-3 control-label font-noraml">Perfil:</label>--%>

                                <%--<div class="col-xs-9">--%>
                                <%--<input type="hidden" ng-update-hidden ng-model="vm.m.roleId"--%>
                                <%--name="roleId" id="roleId">--%>
                                <%--<select class="form-control m-b" id="chosen-select"--%>
                                <%--ng-required="true" ng-change="vm.m.roleId = vm.m.role.id;"--%>
                                <%--ng-options="c.name for c in vm.lstRoles " ng-model="vm.m.role"></select>--%>
                                <%--<span class="error" ng-show="FormUpId.roleId.$error.required">*Campo requerido</span>--%>
                                <%--</div>--%>
                                <%--</div>--%>

                                <%--<div class="col-xs-6" ng-show="vm.m.role.description === '<%=Constants.ROLE_LINK%>'">--%>
                                <%--<label class="col-xs-3 control-label font-noraml">&Oacute;rgano al que pertenece:</label>--%>

                                <%--<div class="col-xs-9">--%>
                                <%--<input type="hidden" ng-update-hidden ng-model="vm.m.auditedEntityId"--%>
                                <%--name="auditedEntityId" id="auditedEntityId">--%>
                                <%--<select class="form-control m-b" id="chosen-select"--%>
                                <%--ng-required="true" ng-change="vm.m.auditedEntityId = vm.m.auditedEntity.id;"--%>
                                <%--ng-options="c.name for c in vm.lstAuditedEntities " ng-model="vm.m.auditedEntity"></select>--%>
                                <%--<span class="error" ng-show="FormUpId.auditedEntityId.$error.required">*Campo requerido</span>--%>
                                <%--</div>--%>
                                <%--</div>--%>
                                <%--</div>--%>
                        </form>
                        <br/>

                        <div class="row">
                            <div class="col-xs-12">
                                <div ng-show="up.MsgError" class="alert alert-danger element-center">
                                    {{up.MsgError}}
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
                <button class="btn btn-primary " ng-disabled="up.WaitFor==true"
                        ng-click="up.submit('#FormUpId', '<c:url value='/management/auditedEntity/doUpsert.json' />', FormUpId.$valid)">
                    Guardar
                </button>
            </div>
        </div>
    </div>
</div>
