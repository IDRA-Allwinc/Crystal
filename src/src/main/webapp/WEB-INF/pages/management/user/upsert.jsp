<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
    $(document).ready(function () {
        window.showModalFormDlg("#dlgUpModalId", "#FormCatId");
    });
</script>


<div class="modal inmodal" id="dlgUpModalId" tabindex="-1" ng-controller="upsertController as up" role="dialog" aria-hidden="true" ng-cloak>
    <div class="modal-dialog" style="width:960px" ng-controller="userController as vm">
        <div class="modal-content animated flipInY">
            <div class="modal-header">

                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>

                <div class="row">
                    <div class="col-xs-9" align="right" style="padding-top: 15px;">
                        <img src="${pageContext.request.contextPath}/assets/img/Logo.png/">
                    </div>
                    <div class="col-xs-3" align="left">
                        <i class="fa fa-users modal-icon"></i>
                    </div>
                </div>

                <h4 class="modal-title">Usuario </h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="ibox">
                            <div class="ibox-title navy-bg">
                                <h5>Información del usuario</h5>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <form id="FormUpId" name="FormUpId" class="form-horizontal" role="form" ng-init="" )">
                        @Html.AntiForgeryToken()
                        <input type="hidden" id="Id" name="Id" ng-model="vm.m.Id" ng-update-hidden />
                        <div class="row">
                            <div class="col-xs-10">
                                <label class="col-xs-4 control-label font-noraml">Correo electrónico:</label>
                                <div class="col-xs-8">
                                    <input type="email" name="Email" ng-model="vm.m.Email"
                                           placeholder="Ingrese el correo electrónico"
                                           ng-required="true" ng-maxlength="200" class="form-control">
                                    <span class="error" ng-show="FormUpId.Email.$error.required">*Campo requerido</span>
                                    <span class="error" ng-show="FormUpId.Email.$error.maxlength">*Longitud máxima de 200 caracteres</span>
                                    <span class="error" ng-show="FormUpId.Email.$error.email">*El correo electrónico no es válido</span>
                                </div>
                            </div>
                        </div>
                        <div class="space-15"></div>
                        <div class="row">
                            <div class="col-xs-6">
                                <label class="col-xs-3 control-label font-noraml">Nombre(s):</label>
                                <div class="col-xs-9">
                                    <input type="text" name="FirstName" ng-model="vm.m.FirstName"
                                           placeholder="Ingrese el nombre del usuario"
                                           ng-required="true" ng-maxlength="200" class="form-control">
                                    <span class="error" ng-show="FormUpId.FirstName.$error.required">*Campo requerido</span>
                                    <span class="error" ng-show="FormUpId.FirstName.$error.maxlength">*Longitud máxima de 200 caracteres</span>
                                </div>
                            </div>

                            <div class="col-xs-6">
                                <label class="col-xs-3 control-label font-noraml">Apellidos</label>
                                <div class="col-xs-9">
                                    <input type="text" name="LastName" ng-model="vm.m.LastName"
                                           placeholder="Ingrese los apellidos del usuario"
                                           ng-required="true" ng-maxlength="200" class="form-control">
                                    <span class="error" ng-show="FormUpId.LastName.$error.required">*Campo requerido</span>
                                    <span class="error" ng-show="FormUpId.LastName.$error.maxlength">*Longitud máxima de 200 caracteres</span>
                                </div>
                            </div>
                        </div>
                        </form>
                        <br />
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
                        ng-click="up.submit('#FormUpId', '', FormUpId.$valid)">
                Guardar
                </button>
            </div>
        </div>
    </div>
</div>
