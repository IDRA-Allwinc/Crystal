<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>
    $(document).ready(function () {
        window.showModalFormDlg("#dlgUpModalId", "#FormUpId");
    });
</script>


<div class="modal inmodal" id="dlgUpModalId" tabindex="-1" ng-controller="upsertController as up" role="dialog"
     aria-hidden="true" ng-cloak>
    <div class="modal-dialog" style="width:960px" ng-controller="areaController as vm">
        <div class="modal-content animated flipInY">
            <div class="modal-header">

                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>

                <div class="row">
                    <div class="col-xs-3" align="left" style="padding-top: 20px;">
                        <img src="${pageContext.request.contextPath}/assets/img/LogoSE.png" , height="90" width="200">
                    </div>
                    <div class="col-xs-6" style="padding-top: 40px;">
                        <h4 class="modal-title">&Aacute;rea</h4>
                    </div>
                    <div class="col-xs-3" align="right">
                        <i class="fa fa-cubes modal-icon"></i>
                    </div>
                </div>
            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="ibox">
                            <div class="ibox-title navy-bg">
                                <h5>Informaci&oacute;n del &aacute;rea</h5>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12">
                        <form id="FormUpId" name="FormUpId" class="form-horizontal" role="form"
                              ng-init='vm.m = ${(model == null ? "{}" : model)}; vm.lstAuditedEntity=${(lstAuditedEntity == null ? "[]" : lstAuditedEntity)};  vm.init();'>

                            <input type="hidden" id="id" name="id" ng-model="vm.m.id" ng-update-hidden/>

                            <div class="row">
                                <div class="col-xs-12">

                                    <div class="col-xs-11 col-xs-offset-1 form-group">
                                        <label class="col-xs-3 control-label font-noraml">Nombre:</label>

                                        <div class="col-xs-9">
                                            <input type="text" name="name" ng-model="vm.m.name"
                                                   minlength="8"
                                                   maxlength="200"
                                                   placeholder="Ingrese el nombre del &aacute;rea"
                                                   ng-required="true" class="form-control">
                                        <span class="error"
                                              ng-show="FormUpId.name.$error.required">*Campo requerido</span>
                                            <span class="error" ng-show="FormUpId.name.$error.minlength">*El campo debe tener  entre 8 y de 200 caracteres</span>
                                            <span class="error" ng-show="FormUpId.name.$error.maxlength">*El campo debe tener  entre 8 y de 200 caracteres</span>
                                        </div>
                                    </div>

                                    <div class="col-xs-11 col-xs-offset-1 form-group">
                                        <label class="col-xs-3 control-label font-noraml">Responsable:</label>

                                        <div class="col-xs-9">
                                            <input type="text" name="responsible" ng-model="vm.m.responsible"
                                                   minlength="8"
                                                   maxlength="200"
                                                   placeholder="Ingrese el nombre completo del responsable"
                                                   ng-required="true" class="form-control">
                                            <span class="error" ng-show="FormUpId.responsible.$error.required">*Campo requerido</span>
                                            <span class="error" ng-show="FormUpId.responsible.$error.minlength">*El campo debe tener  entre 8 y de 200 caracteres</span>
                                            <span class="error" ng-show="FormUpId.responsible.$error.maxlength">*El campo debe tener  entre 8 y de 200 caracteres</span>
                                        </div>
                                    </div>
                                    <div class="col-xs-11 col-xs-offset-1 form-group">
                                        <label class="col-xs-3 control-label font-noraml">Tel&eacute;fono:</label>

                                        <div class="col-xs-9">
                                            <input type="text" name="phone" ng-model="vm.m.phone"
                                                   minlength="8"
                                                   maxlength="200"
                                                   placeholder="Ingrese el tel&oacute;fono "
                                                   ng-required="true" class="form-control">
                                            <span class="error" ng-show="FormUpId.phone.$error.required">*Campo requerido</span>
                                            <span class="error" ng-show="FormUpId.phone.$error.minlength">*El campo debe tener  entre 8 y de 200 caracteres</span>
                                            <span class="error" ng-show="FormUpId.phone.$error.maxlength">*El campo debe tener  entre 8 y de 200 caracteres</span>
                                        </div>
                                    </div>

                                    <div class="col-xs-11 col-xs-offset-1 form-group">
                                        <label class="col-xs-3 control-label font-noraml">Email:</label>

                                        <div class="col-xs-9">
                                            <input type="email" name="email" ng-model="vm.m.email"
                                                   placeholder="Ingrese el email del reponsable"
                                                   ng-required="true" class="form-control">
                                        <span class="error"
                                              ng-show="FormUpId.email.$error.required">*Campo requerido</span>
                                            <span class="error" ng-show="FormUpId.email.$error.maxlength">*Longitud m&aacute;xima de 200 caracteres</span>
                                            <span class="error" ng-show="FormUpId.email.$error.email">*El correo electr&oacute;nico no es v&aacute;lido</span>
                                        </div>
                                    </div>

                                    <sec:authorize access="hasAuthority('DGPOP')">
                                        <div class="col-xs-11 col-xs-offset-1 form-group">
                                            <input type="hidden" ng-update-hidden ng-model="vm.m.auditedEntityId"
                                                   name="auditedEntityId" id="auditedEntityId">

                                            <label class="col-xs-3 control-label font-noraml">Entidad fiscalizada a la
                                                que pertenece:</label>

                                            <div class="col-xs-9">
                                                <select class="form-control m-b" id="chosen-select"
                                                        ng-required="true"
                                                        ng-change="vm.m.auditedEntityId = vm.m.auditedEntity.id;"
                                                        ng-options="c.name for c in vm.lstAuditedEntity"
                                                        ng-model="vm.m.auditedEntity"></select>
                                        <span class="error"
                                              ng-show="FormUpId.auditedEntityId.$error.required">*Campo requerido</span>
                                            </div>
                                        </div>
                                    </sec:authorize>
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
                <button class="btn btn-primary" ng-disabled="up.WaitFor==true"
                        ng-click="up.submit('#FormUpId', '<c:url value='/shared/area/doUpsert.json' />', FormUpId.$valid)">
                    Guardar
                </button>
            </div>
        </div>
    </div>
</div>
