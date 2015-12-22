<%@ page import="com.crystal.model.shared.Constants" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
    $(document).ready(function () {
        window.showModalFormDlg("#dlgUpModalId", "#FormUpId");
    });
</script>


<div class="modal inmodal" id="dlgUpModalId" tabindex="-1" ng-controller="upsertController as up" role="dialog"
     aria-hidden="true" ng-cloak>
    <div class="modal-dialog" style="width:960px" ng-controller="supervisoryEntityController as vm">
        <div class="modal-content animated flipInY">
            <div class="modal-header">

                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>

                <div class="row">
                    <div class="col-xs-3" align="left" style="padding-top: 20px;">
                        <img src="${pageContext.request.contextPath}/assets/img/LogoSE.png" , height="90" width="200">
                    </div>
                    <div class="col-xs-6" style="padding-top: 40px;">
                        <h4 class="modal-title">&Oacute;rgano fiscalizador</h4>
                    </div>
                    <div class="col-xs-3" align="right">
                        <i class="fa fa-eye modal-icon"></i>
                    </div>
                </div>
            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="ibox">
                            <div class="ibox-title navy-bg">
                                <h5>Informaci&oacute;n del &oacute;rgano fiscalizador</h5>
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
                                                   placeholder="Ingrese el nombre del &oacute;rgano fiscalizador"
                                                   ng-required="true" class="form-control">
                                        <span class="error" ng-show="FormUpId.name.$error.required">*Campo requerido</span>
                                            <span class="error" ng-show="FormUpId.name.$error.maxlength">*Longitud m&aacute;xima de 200 caracteres</span>
                                        </div>
                                    </div>

                                    <div class="col-xs-11 col-xs-offset-1 form-group">
                                        <label class="col-xs-3 control-label font-noraml">Responsable:</label>

                                        <div class="col-xs-9">
                                            <input type="text" name="responsible" ng-model="vm.m.responsible"
                                                   placeholder="Ingrese el nombre completo del responsable"
                                                   ng-required="true" class="form-control">
                                            <span class="error" ng-show="FormUpId.responsible.$error.required">*Campo requerido</span>
                                            <span class="error" ng-show="FormUpId.responsible.$error.maxlength">*Longitud m&aacute;xima de 200 caracteres</span>
                                        </div>
                                    </div>
                                    <div class="col-xs-11 col-xs-offset-1 form-group">
                                        <label class="col-xs-3 control-label font-noraml">Tel&eacute;fono:</label>

                                        <div class="col-xs-9">
                                            <input type="text" name="phone" ng-model="vm.m.phone"
                                                   placeholder="Ingrese el tel&oacute;fono "
                                                   ng-required="true" class="form-control">
                                            <span class="error" ng-show="FormUpId.phone.$error.required">*Campo requerido</span>
                                            <span class="error" ng-show="FormUpId.phone.$error.maxlength">*Longitud m&aacute;xima de 200 caracteres</span>
                                        </div>
                                    </div>

                                    <div class="col-xs-11 col-xs-offset-1 form-group">
                                        <label class="col-xs-3 control-label font-noraml">Email:</label>

                                        <div class="col-xs-9">
                                            <input type="email" name="email" ng-model="vm.m.email"
                                                   placeholder="Ingrese el email"
                                                   ng-required="true" class="form-control">
                                        <span class="error" ng-show="FormUpId.email.$error.required">*Campo requerido</span>
                                            <span class="error" ng-show="FormUpId.email.$error.maxlength">*Longitud m&aacute;xima de 200 caracteres</span>
                                            <span class="error" ng-show="FormUpId.email.$error.email">*El correo electr&oacute;nico no es v&aacute;lido</span>
                                        </div>
                                    </div>

                                    <div class="col-xs-11 col-xs-offset-1 form-group">
                                        <label class="col-xs-3 control-label font-noraml">A quien pertenece:</label>

                                        <div class="col-xs-9">
                                            <input type="text" name="belongsTo" ng-model="vm.m.belongsTo"
                                                   placeholder="Ingrese a quien pertenece el &oacute;rgano fiscalizador"
                                                   ng-required="true" class="form-control">
                                            <span class="error" ng-show="FormUpId.belongsTo.$error.required">*Campo requerido</span>
                                            <span class="error" ng-show="FormUpId.belongsTo.$error.maxlength">*Longitud m&aacute;xima de 200 caracteres</span>
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
                        ng-click="up.submit('#FormUpId', '<c:url value='/management/supervisoryEntity/doUpsert.json' />', FormUpId.$valid)">
                    Guardar
                </button>
            </div>
        </div>
    </div>
</div>
