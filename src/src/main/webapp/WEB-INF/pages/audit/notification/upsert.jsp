<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
    $(document).ready(function () {
        window.showModalFormDlg("#dlgUpModalId", "#FormUpNotificationId");
    });
</script>

<div class="modal inmodal" id="dlgUpModalId" tabindex="-1" ng-controller="upsertController as up" role="dialog"
     aria-hidden="true" ng-cloak>
    <div class="modal-dialog" style="width:900px" ng-controller="notificationController as vm">
        <div class="modal-content animated flipInY">
            <div class="modal-header">

                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>

                <div class="row">
                    <div class="col-xs-3" align="left" style="padding-top: 20px;">
                        <img src="${pageContext.request.contextPath}/assets/img/LogoSE.png" , height="90" width="200">
                    </div>
                    <div class="col-xs-6" style="padding-top: 40px;">
                        <h4 class="modal-title">Notificaciones de auditor&iacute;a</h4>
                    </div>
                    <div class="col-xs-3" align="right">
                        <i class="fa fa-comment-o modal-icon"></i>
                    </div>
                </div>
            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="ibox">
                            <div class="ibox-title navy-bg">
                                <h5>Informaci&oacute;n de la notificaci&oacute;n</h5>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12">
                        <form id="FormUpNotificationId" name="FormUpNotificationId" class="form-horizontal" role="form"
                              ng-init='vm.m = ${(model == null ? "{}" : model)}; vm.lstDestination = ${lstDestination}; vm.isEdit = (vm.m && vm.m.id > 0)'>

                            <input type="hidden" id="id" name="id" ng-model="vm.m.id" ng-update-hidden/>
                            <input type="hidden" id="auditId" name="auditId" ng-model="vm.m.auditId" ng-update-hidden/>

                            <div class="row">
                                <div class="col-xs-12">

                                    <div class="col-xs-12 form-group">
                                        <label class="col-xs-3 control-label font-noraml">T&iacute;tulo:</label>

                                        <div class="col-xs-8">
                                            <input type="text" name="title" ng-model="vm.m.title" ng-disabled = "vm.isEdit"
                                                   placeholder="Ingrese el t&iacute;tulo de la notificaci&oacute;n"
                                                   minlength="5"
                                                   maxlength="100"
                                                   ng-required="true" class="form-control">
                                        <span class="error"
                                              ng-show="FormUpNotificationId.title.$error.required">*Campo requerido</span>
                                            <span class="error" ng-show="FormUpNotificationId.title.$error.minlength">*El campo debe tener entre 5 y 100 caracteres</span>
                                            <span class="error" ng-show="FormUpNotificationId.title.$error.maxlength">*El campo debe tener entre 5 y 100 caracteres</span>
                                        </div>
                                    </div>

                                    <div class="col-xs-12 form-group">
                                        <label class="col-xs-3 control-label font-noraml">Mensaje:</label>

                                        <div class="col-xs-8">
                                            <textarea name="message" ng-model="vm.m.message" rows="7" ng-disabled = "vm.isEdit"
                                                      placeholder="Ingrese el mensaje de la notificaci&oacute;n"
                                                      minlength="5"
                                                      maxlength="2000"
                                                      ng-required="true" class="form-control"></textarea>
                                            <span class="error" ng-show="FormUpNotificationId.message.$error.required">*Campo requerido</span>
                                            <span class="error" ng-show="FormUpNotificationId.message.$error.minlength">*El campo debe tener entre 5 y 2000 caracteres</span>
                                            <span class="error" ng-show="FormUpNotificationId.message.$error.maxlength">*El campo debe tener entre 5 y 2000 caracteres</span>
                                        </div>
                                    </div>

                                    <div class="col-xs-12 form-group">
                                        <label class="col-xs-3 control-label font-noraml">Destinatario(s):</label>

                                        <div class="col-xs-8">
                                            <label class="" ng-repeat="i in vm.lstDestination">
                                                <div ng-class="'icheckbox_square-green ' + (i.isSelected ? 'checked' : '')" style="position: relative;">
                                                    <input type="checkbox" ng-model="i.isSelected" style="position: absolute; opacity: 0;">
                                                    <ins class="iCheck-helper"
                                                         style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; border: 0px; opacity: 0; background: rgb(255, 255, 255);">
                                                    </ins>
                                                </div>
                                                <i></i>
                                                <small class="ng-binding">
                                                    <small>{{i.description}} &nbsp;&nbsp; ({{i.name}})</small>
                                                </small>
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>

                    <div class="col-xs-12">
                        <div ng-show="vm.MsgError" ng-bind-html="vm.MsgError" class="alert alert-error element-center">
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-white" ng-click="up.cancel()"  ng-show="vm.WaitFor===false">
                    Cancelar
                </button>
                <button class="btn btn-primary" ng-show="vm.WaitFor===false"
                        ng-click="vm.submit('#FormUpNotificationId', '<c:url value='/audit/notification/doUpsert.json' />', FormUpNotificationId.$valid, up.Model)">
                    {{vm.isEdit ? 'Guardar' : 'Guardar y enviar'}}
                </button>
                <button class="btn btn-warning" ng-disabled="vm.WaitFor===true" data-ng-show="vm.WaitFor===true">
                    <i class="fa fa-refresh fa-spin"></i> &nbsp; {{vm.isEdit ? 'Procesando' : 'Procesando y enviando'}}
                </button>
            </div>
        </div>
    </div>
</div>

<script>

    var config = {
        '.chosen-select': {},
        '.chosen-select-deselect': {allow_single_deselect: true},
        '.chosen-select-no-single': {disable_search_threshold: 10},
        '.chosen-select-no-results': {no_results_text: 'No se han encontrado resultados.'},
        '.chosen-select-width': {width: "95%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
</script>
