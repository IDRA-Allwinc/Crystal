<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
    $(document).ready(function () {
        window.showModalFormDlg("#dlgUpModalId", "#FormUpId");
    });
</script>


<div class="modal inmodal" id="dlgUpModalId" tabindex="-1" ng-controller="upsertController as up" role="dialog"
     aria-hidden="true" ng-cloak>
    <div class="modal-dialog" style="width:960px; " ng-controller="catalogController as vm">
        <div class="modal-content animated flipInY">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <div class="row">
                    <div class="col-xs-3" align="left" style="padding-top: 20px;">
                        <img src="${pageContext.request.contextPath}/assets/img/LogoSE.png" height="90" width="200">
                    </div>
                    <div class="col-xs-6" style="padding-top: 40px;">
                        <h4 class="modal-title">Tipo de reuni&oacute;n</h4>
                    </div>
                    <div class="col-xs-3" align="right">
                        <i class="fa fa-bookmark modal-icon"></i>
                    </div>
                </div>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="ibox">
                            <div class="ibox-title navy-bg">
                                <h5>Informaci&oacute;n del tipo de reuni&oacute;n</h5>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <form id="FormUpId" name="FormUpId" class="form-horizontal" role="form"
                              ng-init='vm.m = ${(model == null ? "{}" : model)};'>

                            <input type="hidden" id="id" name="id" ng-model="vm.m.id" ng-update-hidden/>

                            <div class="row">
                                <div class="col-xs-12">
                                    <label class="col-xs-3 control-label font-noraml">Tipo de reuni&oacute;n:</label>
                                    <div class="col-xs-6">
                                        <input type="text" name="name" ng-model="vm.m.name"
                                               placeholder="Ingrese el tipo de reuni&oacute;n"
                                               minlength="8" maxlength="200"
                                               ng-required="true" class="form-control">
                                        <span class="error" ng-show="FormUpId.name.$error.required">*Campo requerido</span>
                                        <span class="error" ng-show="FormUpId.name.$error.minlength">*El campo debe tener entre 8 y 200 caracteres</span>
                                        <span class="error" ng-show="FormUpId.name.$error.maxlength">*El campo debe tener entre 8 y 200 caracteres</span>
                                    </div>
                                </div>
                            </div>
                            <div class="space-15"></div>
                            <div class="row">
                                <div class="col-xs-12">
                                    <label class="col-xs-3 control-label font-noraml">Descripci&oacute;n:</label>
                                    <div class="col-xs-6">
                                        <textarea rows="5" name="description" ng-model="vm.m.description"
                                               placeholder="Ingrese la descripci&oacute;n del tipo de reuni&oacute;n"
                                               minlength="8" maxlength="300"
                                               ng-required="true"  class="form-control" />
                                        <span class="error"
                                              ng-show="FormUpId.description.$error.required">*Campo requerido</span>
                                        <span class="error" ng-show="FormUpId.description.$error.minlength">*El campo debe tener entre 8 y 300 caracteres</span>
                                        <span class="error" ng-show="FormUpId.description.$error.maxlength">*El campo debe tener entre 8 y 300 caracteres</span>
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
                        ng-click="up.submit('#FormUpId', '<c:url value='/management/meetingType/doUpsert.json' />', FormUpId.$valid)">
                    Guardar
                </button>
                <button class="btn btn-warning" ng-disabled="up.WaitFor===true" data-ng-show="up.WaitFor===true">
                    <i class="fa fa-refresh fa-spin"></i> &nbsp; Procesando
                </button>
            </div>
        </div>
    </div>
</div>
