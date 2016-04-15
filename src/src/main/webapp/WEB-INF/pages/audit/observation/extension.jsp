<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>

    function actionsFormatterObservationExtension(value, row, index) {
        var arr = [];
        arr.push('<button class="btn btn-primary dim act-download btn-tiny" data-toggle="tooltip" data-placement="top" title="Descargar documento" type="button"><i class="fa fa-download"></i></button>');

        if (row.attended !== true )//&& row.id==row.lastExtensionId)
            arr.push('<button class="btn btn-danger dim act-ext-observation-delete btn-tiny" data-toggle="tooltip" data-placement="top" title="Eliminar pr&oacute;rroga" type="button"><i class="fa fa-times-circle"></i></button>');

        return arr.join('');
    }

    $(document).ready(function () {
        window.showModalFormDlg("#dlgUpModalId", "#FormUpFileExtensionObservation");
        var tableId = '#tblUfExtensionObservationGrid';
        $(tableId).bootstrapTable();

        var tokenCsrf = document.getElementById("token-csrf");
        var url = "<c:url value='/shared/uploadFileGeneric/doUploadFileGeneric.json' />" + "?" + tokenCsrf.name + "=" + tokenCsrf.value;
        var scope = angular.element($("#FormUpFileExtensionObservation")).scope();
        scope.vm.tableId = tableId;

        $('#docfileupload').fileupload({
            url: url,
            dataType: 'json',
            done: function (e, data) {
                try {
                    var scope = angular.element($("#FormUpFileExtensionObservation")).scope();
                    if (data.result === undefined || data.result.hasError === undefined) {
                        scope.vm.setOutError("No hubo respuesta del servidor. Por favor intente de nuevo");
                        return;
                    }
                    if (data.result.hasError === true) {
                        scope.vm.setOutError(data.result.message);
                        return;
                    }

                    scope.vm.setSuccess();

                } catch (ex) {
                    scope.vm.setOutError("Hubo un error al momento de procesar la respuesta: " + ex);
                    return;
                } finally {
                    window.setTimeout(function () {
                        $('#progress .progress-bar').css('width', 0 + '%');
                    }, 2000);
                }
            }
            ,
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#progress .progress-bar').css(
                        'width',
                        progress + '%'
                );
            }
        }).prop('disabled', !$.support.fileInput)
                .parent().addClass($.support.fileInput ? undefined : 'disabled');

        window.actionEventsObservationExtension = {
            'click .act-ext-observation-delete': function (e, value, row) {
                window.showObsoleteParam({
                    observationId: row.observationId,
                    extensionId: row.id
                }, "#angJsjqGridIdObservation", "<c:url value='/audit/observation/doDeleteExtension.json' />", "#tblUfExtensionObservationGrid", undefined, undefined,scope.vm.refreshExtensionObservation);
            },
            'click .act-download': function (e, value, row) {
                var params = [];
                params["idParam"] = row.fileId;
                window.goToNewWnd("<c:url value='/shared/uploadFileGeneric/downloadFile.html?id=idParam' />", params);
            }
        };

        $('#dlgUpModalId').on('hidden.bs.modal', function () {
            scope.vm.refreshParentGrid("#tblGridObservation");
        })

    })
    ;

</script>

<div class="modal inmodal" id="dlgUpModalId" tabindex="-1" ng-controller="upsertController as up" role="dialog"
     aria-hidden="true" ng-cloak>
    <div class="modal-dialog" style="width:960px" data-ng-controller="extensionObservationController as vm"
         data-ng-init='vm.m = ${(model == null ? "{}" : model)};
         vm.urlRefresh="<c:url value='/audit/observation/refresh.json'/>";'>
         '>
        <div class="modal-content animated flipInY">
            <div class="modal-header">

                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>

                <div class="row">
                    <div class="col-xs-3" align="left" style="padding-top: 20px;">
                        <img src="${pageContext.request.contextPath}/assets/img/LogoSE.png" , height="90" width="200">
                    </div>
                    <div class="col-xs-6" style="padding-top: 40px;">
                        <h4 class="modal-title">Pr&oacute;rrogas</h4>
                    </div>
                    <div class="col-xs-3" align="right">
                        <i class="fa fa-copy modal-icon"></i>
                    </div>
                </div>
            </div>

            <div class="modal-body">
                <div data-ng-show="vm.m.isAttended !== true && vm.m.hasExtension == false">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="ibox">
                                <div class="ibox-title navy-bg">
                                    <h5>Agregar pr&oacute;rroga al pliego <b>{{vm.m.number}}</b></h5>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <form id="FormUpFileExtensionObservation" name="FormUpFileExtensionObservation" class="form-horizontal"
                              role="form"
                              enctype="multipart/form-data">
                            <input type="hidden" id="id" name="id" ng-model="vm.m.id" ng-update-hidden/>
                            <input type="hidden" id="type" name="type" ng-model="vm.m.type" ng-update-hidden/>

                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                            <div class="col-xs-3 element-center">
                                <i class="fa fa-upload modal-icon orange"></i>

                                <p class="text-muted"><i><b>Nota:</b> Debe proporcionar todos los datos requeridos para
                                    poder adjuntar el
                                    documento.</i></p>
                            </div>
                            <div class="col-xs-9">


                                <div class="row">
                                    <div class="col-xs-12 form-group">
                                        <div class="col-xs-6">
                                            <label class="font-noraml">Nueva fecha l&iacute;mite:</label>

                                            <div>
                                                <p class="input-group">
                                                    <input type="text" class="form-control" name="endDate"
                                                           uib-datepicker-popup="yyyy/MM/dd" ng-model="vm.m.endDateExtObs"
                                                           is-open="vm.m.endDateIsOpened" ng-required="true"
                                                           placeholder="yyyy/mm/dd"
                                                           current-text="Hoy"
                                                           clear-text="Limpiar"
                                                           close-text="Cerrar"
                                                           min-date="vm.today"
                                                           alt-input-formats="yyyy/MM/dd"/>
                                                  <span class="input-group-btn">
                                                    <button type="button" class="btn btn-default"
                                                            ng-click="vm.m.endDateIsOpened=true;"><i
                                                            class="glyphicon glyphicon-calendar"></i></button>
                                                  </span>
                                                </p>
                                                    <span class="error"
                                                          ng-show="FormUpFileExtensionObservation.endDate.$error.required">*Campo requerido</span>
                                                <span class="error"
                                                      ng-show="FormUpFileExtensionObservation.endDate.$invalid && !FormUpFileExtensionObservation.endDate.$pristine">*La fecha debe tener el formato aaaa/mm/dd</span>
                                            </div>
                                        </div>

                                    </div>
                                    <div class="col-xs-11">
                                        <div>
                                            <label class="font-noraml">Comentario:</label>

                                        <textarea name="extensionComment" rows="3" ng-model="vm.m.extensionComment"
                                                  placeholder="Ingrese el comentario para la pr&oacute;rroga"
                                                  ng-required="true" ng-minlength="2" ng-maxlength="2000"
                                                  class="form-control"></textarea>
                                        <span class="error"
                                              ng-show="FormUpFileExtensionObservation.extensionComment.$error.required">*Campo requerido</span>
                                            <span class="error"
                                                  ng-show="FormUpFileExtensionObservation.extensionComment.$error.minlength">*El campo debe tener entre 8 y 2000 caracteres</span>
                                            <span class="error"
                                                  ng-show="FormUpFileExtensionObservation.extensionComment.$error.maxlength">*El campo debe tener entre 8 y 2000 caracteres</span>
                                        </div>

                                    </div>
                                </div>

                                <div class="row" data-ng-show="vm.m.extensionComment&&vm.m.endDate">
                                    <br/>

                                    <div class="row">
                                        <div class="col-xs-12 element-center">
                                     <span class="btn btn-success fileinput-button element-center">
                                        <i class="glyphicon glyphicon-upload"></i>
                                        <span>Elige el archivo...</span>
                                        <input id="docfileupload" type="file" name="files[]"/>
                                    </span>
                                        </div>
                                    </div>
                                    <br/>

                                    <div class="row">
                                        <div class="col-xs-11 element-center">
                                            <div id="progress" class="progress">
                                                <div class="progress-bar progress-bar-success"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
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
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12">
                        <div class="ibox">
                            <div class="ibox-title navy-bg">
                                <h5>Pr&oacute;rrogas para el pliego <b>{{vm.m.number}}</b></h5>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12">
                        <div class="ibox float-e-margins">
                            <div class="ibox-content">
                                <table id="tblUfExtensionObservationGrid"
                                       data-toggle="table"
                                       data-url="<c:url value='/audit/observation/extension/list.json' />?id={{vm.m.id}}"
                                       data-height="auto"
                                       data-side-pagination="server"
                                       data-pagination="true"
                                       data-page-list="[5, 10, 20, 50, All]"
                                       data-search="true"
                                       data-sort-name="id"
                                       data-sort-order="asc"
                                       data-show-refresh="true"
                                       data-show-toggle="true"
                                       data-show-columns="true"
                                       data-show-export="true"
                                       data-single-select="true"
                                       data-show-footer="true"
                                       data-id-field="id">
                                    <thead>
                                    <tr>
                                        <th data-field="id" data-visible="false" data-card-visible="false" data-switchable="false">Identificador</th>
                                        <th data-field="observationId" data-visible="false" data-card-visible="false" data-switchable="false">ID requisito</th>
                                        <th data-field="isAttended" data-visible="false" data-card-visible="false" data-switchable="false">Atendido</th>
                                        <th data-field="fileName" data-align="center" data-sortable="true">Documento</th>
                                        <th data-field="extensionComment" data-align="center" data-sortable="true">Comentario</th>
                                        <th data-field="endDate" data-align="center" data-sortable="true">Fecha l&iacute;mite</th>
                                        <th data-field="Actions" data-formatter="actionsFormatterObservationExtension"
                                            data-align="center" data-width="200px" data-events="actionEventsObservationExtension">Acci&oacute;n
                                        </th>
                                    </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default" ng-click="vm.refreshParentGrid('#tblGridObservation'); up.cancel();">
                    Regresar
                </button>
            </div>
        </div>
    </div>
</div>
