<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
    $(document).ready(function () {
        window.showModalFormDlg("#dlgUpModalId", "#FormUpFileLetterId");
        var tableId = '#tblUfLetterAuditGrid';
        $(tableId).bootstrapTable();

        var tokenCsrf = document.getElementById("token-csrf");
        var url = "<c:url value='/shared/uploadFileGeneric/doUploadFileGeneric.json' />" + "?" + tokenCsrf.name + "=" + tokenCsrf.value;

        $('#docfileupload').fileupload({
            url: url,
            dataType: 'json',
            done: function (e, data) {
                try {
                    var scope = angular.element($("#FormUpFileLetterId")).scope();
                    if (data.result === undefined || data.result.hasError === undefined) {
                        scope.lt.setOutError("No hubo respuesta del servidor. Por favor intente de nuevo");
                        return;
                    }
                    if (data.result.hasError === true) {
                        scope.lt.setOutError(data.result.message);
                        return;
                    }

                    scope.lt.setSuccess(data.result);
                    $(tableId).bootstrapTable('refresh', 'showLoading');

                } catch (ex) {
                    scope.lt.setOutError("Hubo un error al momento de procesar la respuesta: " + ex);
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
    })
    ;

</script>

<div class="modal inmodal" id="dlgUpModalId" tabindex="-1" ng-controller="upsertController as up" role="dialog"
     aria-hidden="true" ng-cloak>
    <div class="modal-dialog" style="width:960px" data-ng-controller="letterViewDocsController as lt"
         data-ng-init='lt.m = ${(model == null ? "{}" : model)};'>
        <div class="modal-content animated flipInY">
            <div class="modal-header">

                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>

                <div class="row">
                    <div class="col-xs-3" align="left" style="padding-top: 20px;">
                        <img src="${pageContext.request.contextPath}/assets/img/LogoSE.png" , height="90" width="200">
                    </div>
                    <div class="col-xs-6" style="padding-top: 40px;">
                        <h4 class="modal-title">Visualizaci&oacute;n de documentos adicionales</h4>
                    </div>
                    <div class="col-xs-3" align="right">
                        <i class="fa fa-copy modal-icon"></i>
                    </div>
                </div>
            </div>

            <div class="modal-body">
                <div data-ng-show="lt.m.isAttended !== true">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="ibox">
                                <div class="ibox-title navy-bg">
                                    <h5>Subir documentos adicionales al oficio <b>{{lt.m.number}}</b></h5>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <form id="FormUpFileLetterId" name="FormUpFileLetterId" class="form-horizontal" role="form"
                              enctype="multipart/form-data">
                            <input type="hidden" id="id" name="id" ng-model="lt.m.id" ng-update-hidden/>
                            <input type="hidden" id="type" name="type" ng-model="lt.m.type" ng-update-hidden/>

                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                            <div class="col-xs-4 element-center">
                                <i class="fa fa-upload modal-icon orange"></i>
                                <p class="text-muted"><i><b>Nota:</b> Debe proporcionar una descripci&oacute;n para poder adjuntar el
                                    documento.</i></p>
                            </div>
                            <div class="col-xs-8">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <label class="col-xs-3 control-label font-noraml">Descripci&oacute;n:</label>

                                        <div class="col-xs-7">
                                        <textarea name="description" rows="3" ng-model="lt.m.description"
                                                  placeholder="Ingrese una breve descripci&oacute;n del documento"
                                                  ng-required="true"  ng-minlength="2"
                                                  class="form-control"></textarea>
                                        <span class="error"
                                              ng-show="FormUpFileLetterId.description.$error.required">*Campo requerido</span>
                                            <span class="error" ng-show="FormUpFileLetterId.description.$error.minlength">*Longitud m&iacute;nima de 2 caracteres</span>
                                            <span class="error" ng-show="FormUpFileLetterId.description.$error.maxlength">*Longitud m&aacute;xima de 200 caracteres</span>
                                        </div>
                                    </div>
                                </div>
                                <div data-ng-show="lt.m.description">
                                    <br/>

                                    <div class="row">
                                        <div class="col-xs-8 col-xs-offset-2 element-center">
                                     <span class="btn btn-success fileinput-button element-center">
                                        <i class="glyphicon glyphicon-upload"></i>
                                        <span>Elige el archivo...</span>
                                        <input id="docfileupload" type="file" name="files[]"/>
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
                                            <div ng-show="lt.MsgError" class="alert alert-error element-center"
                                                 ng-bind-html="lt.MsgError">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <div ng-show="lt.MsgSuccess" class="alert alert-success element-center"
                                                 ng-bind-html="lt.MsgSuccess">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <br/>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12">
                        <div class="ibox">
                            <div class="ibox-title navy-bg">
                                <h5>Documentos adicionales del oficio <b>{{lt.m.number}}</b></h5>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12">
                        <div class="ibox float-e-margins">
                            <div class="ibox-content">
                                <table id="tblUfLetterAuditGrid"
                                       data-toggle="table"
                                       data-url="<c:url value='/audit/letter/listUfLetter.json' />?letterId={{lt.m.id}}"
                                       data-height="auto"
                                       data-side-pagination="server"
                                       data-pagination="true"
                                       data-page-list="[5, 10, 20, 50, All]"
                                       data-search="true"
                                       data-sort-name="id"
                                       data-sort-order="desc"
                                       data-show-refresh="true"
                                       data-show-toggle="true"
                                       data-show-columns="true"
                                       data-show-export="true"
                                       data-single-select="true"
                                       data-show-footer="true"
                                       data-id-field="id">
                                    <thead>
                                    <tr>
                                        <th data-field="id" data-visible="false">Identificador</th>
                                        <th data-field="requestId" data-visible="false">ID requisito</th>
                                        <th data-field="isAttended" data-visible="false">Atendido</th>
                                        <th data-field="fileName" data-align="center" data-sortable="true">Documento
                                        </th>
                                        <th data-field="description" data-align="center" data-sortable="true">Descripci&oacute;n</th>
                                        <th data-field="Actions" data-formatter="actionsUploadFileLetterFormatter"
                                            data-align="center" data-width="200px" data-events="actionEvents">Acci&oacute;n
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
                <button class="btn btn-default" ng-click="up.cancel()">
                    Regresar
                </button>
            </div>
        </div>
    </div>
</div>
