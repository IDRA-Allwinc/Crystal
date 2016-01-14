<%@ page import="com.crystal.model.shared.Constants" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
    $(document).ready(function () {
        window.showModalFormDlg("#dlgUpModalId", "#FormUpId");
    });

    $(function () {
        'use strict';
        var url = '<c:url value='/shared/uploadFileGeneric/doUploadFileGeneric.json' />';

        $('#fileupload').fileupload({
            url: url,
            dataType: 'json',
            done: function (e, data) {
                try {
                    var scope = angular.element($("#FormUpId")).scope();
                    if (data.result === undefined || data.result.hasError === undefined) {
                        scope.vm.setOutError("No hubo respuesta del servidor. Por favor intente de nuevo");
                        return;
                    }
                    if (data.result.hasError === true) {
                        scope.vm.setOutError(data.result.message);
                        return;
                    }

                    scope.vm.setSuccess(data.result);

                } catch (ex) {
                    scope.vm.setOutError("Hubo un error al momento de procesar la respuesta: " + ex);
                    return;
                } finally {
                    window.setTimeout(function () {
                        $('#progress .progress-bar').css('width', 0 + '%');
                    }, 2000);
                }

            },
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#progress .progress-bar').css(
                        'width',
                        progress + '%'
                );
            }
        }).prop('disabled', !$.support.fileInput)
                .parent().addClass($.support.fileInput ? undefined : 'disabled');

    });

</script>


<div class="modal inmodal" id="dlgUpModalId" tabindex="-1" ng-controller="upsertController as up" role="dialog"
     aria-hidden="true" ng-cloak>
    <div class="modal-dialog" style="width:960px" ng-controller="letterController as vm">
        <div class="modal-content animated flipInY">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <div class="row">
                    <div class="col-xs-3" align="left" style="padding-top: 20px;">
                        <img src="${pageContext.request.contextPath}/assets/img/LogoSE.png" height="90" width="200">
                    </div>
                    <div class="col-xs-6" style="padding-top: 40px;">
                        <h4 class="modal-title">Oficio del requerimiento previo</h4>
                    </div>
                    <div class="col-xs-3" align="right">
                        <i class="fa fa-list modal-icon"></i>
                    </div>
                </div>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="ibox">
                            <div class="ibox-title navy-bg">
                                <h5>Informaci&oacute;n del oficio</h5>
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
                                    <label class="col-xs-3 control-label font-noraml">Oficio:</label>

                                    <div class="col-xs-7">
                                        <input type="text" name="number" ng-model="vm.m.number"
                                               placeholder="Ingrese el n&uacute;mero de oficio"
                                               ng-required="true" ng-maxlength="200" ng-minlength="2"
                                               class="form-control">
                                        <span class="error" ng-show="FormUpId.number.$error.required">*Campo requerido</span>
                                        <span class="error" ng-show="FormUpId.number.$error.minlength">*Longitud m&iacute;nima de 2 caracteres</span>
                                        <span class="error" ng-show="FormUpId.number.$error.maxlength">*Longitud m&aacute;xima de 200 caracteres</span>
                                    </div>
                                </div>
                            </div>
                            <div class="space-15">&nbsp;</div>
                            <div class="row">
                                <div class="col-xs-12">
                                    <label class="col-xs-3 control-label font-noraml">Descripci&oacute;n:</label>

                                    <div class="col-xs-7">
                                        <textarea name="description" rows="3" ng-model="vm.m.description"
                                                  placeholder="Ingrese la descripci&oacute;n del oficio"
                                                  ng-required="true" ng-maxlength="200" ng-minlength="2"
                                                  class="form-control"></textarea>
                                        <span class="error"
                                              ng-show="FormUpId.description.$error.required">*Campo requerido</span>
                                        <span class="error" ng-show="FormUpId.description.$error.minlength">*Longitud m&iacute;nima de 2 caracteres</span>
                                        <span class="error" ng-show="FormUpId.description.$error.maxlength">*Longitud m&aacute;xima de 200 caracteres</span>
                                    </div>
                                </div>
                            </div>
                            <div class="hr-line-solid"></div>
                            <div data-ng-show="vm.m.lstFiles">
                                <div class="row">
                                    <div class="col-xs-8 col-xs-offset-2 text-center">
                                        <label class="control-label font-noraml">Archivo adjunto a este oficio: </label>
                                    </div>
                                </div>
                                <div class="space-5">&nbsp;</div>
                                <div class="row" data-ng-repeat="f in vm.m.lstFiles">
                                    <div class="col-xs-8 col-xs-offset-2">
                                        <p class="text-center"><span class="badge badge-info font-big">{{f.fileName}}</span></p>
                                        <input type="hidden" name="lstFiles[{{$index}}].id" value="{{f.id}}" />
                                    </div>
                                </div>
                            </div>
                            <div class="space-5">&nbsp;</div>
                            <div class="row">
                                <div class="col-xs-8 col-xs-offset-2 element-center">
                                     <span class="btn btn-success fileinput-button element-center">
                                        <i class="glyphicon glyphicon-upload"></i>
                                        <span>Elige el archivo...</span>
                                        <input id="fileupload" type="file" name="files[]"/>
                                    </span>
                                </div>
                            </div>
                            <br/>

                            <div class="row">
                                <div class="col-xs-8 col-xs-offset-2">
                                    <div id="progress" class="progress">
                                        <div class="progress-bar progress-bar-success"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12">
                                    <div id="files" class="files"></div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12">
                                    <div ng-show="vm.MsgError" class="alert alert-error element-center"
                                         ng-bind-html="vm.MsgError">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12">
                                    <div ng-show="vm.MsgSuccess" class="alert alert-success element-center"
                                         ng-bind-html="vm.MsgSuccess">
                                    </div>
                                </div>
                            </div>
                        </form>
                        <br/>
                        <div class="row">
                            <div class="col-xs-12">
                                <div ng-show="up.MsgError" ng-bind-html="up.MsgError"
                                     class="alert alert-error  element-center">
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
                        ng-click="up.submit('#FormUpId', '<c:url value='/audit/letter/doUpsert.json' />', FormUpId.$valid)">
                    Guardar
                </button>
                <button class="btn btn-warning" ng-disabled="up.WaitFor===true" data-ng-show="up.WaitFor===true">
                    <i class="fa fa-refresh fa-spin"></i> &nbsp; Procesando
                </button>
            </div>
        </div>
    </div>
</div>
