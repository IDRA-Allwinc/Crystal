<%@ page import="com.crystal.model.shared.Constants" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
    $(document).ready(function () {
        window.showModalFormDlg("#dlgUpModalId", "#FormUpEventId");
    });
</script>

<div class="modal inmodal" id="dlgUpModalId" tabindex="-1" ng-controller="upsertController as up" role="dialog"
     aria-hidden="true" ng-cloak>
    <div class="modal-dialog" style="width:960px" ng-controller="eventController as vm">
        <div class="modal-content animated flipInY">
            <div class="modal-header">

                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>

                <div class="row">
                    <div class="col-xs-3" align="left" style="padding-top: 20px;">
                        <img src="${pageContext.request.contextPath}/assets/img/LogoSE.png" , height="90" width="200">
                    </div>
                    <div class="col-xs-6" style="padding-top: 40px;">
                        <h4 class="modal-title">Eventos de auditor&iacute;a</h4>
                    </div>
                    <div class="col-xs-3" align="right">
                        <i class="fa fa-bars modal-icon"></i>
                    </div>
                </div>
            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="ibox">
                            <div class="ibox-title navy-bg">
                                <h5>Informaci&oacute;n del evento</h5>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12">
                        <form id="FormUpEventId" name="FormUpEventId" class="form-horizontal" role="form"
                              ng-init='vm.m = ${(model == null ? "{}" : model)}; vm.urlGetAssistants="<c:url value='/previousRequest/request/getPossibleAssistants.json'/>"; vm.lstSelectedAssistants = ${(lstSelectedAssistants == null ? "[]" : lstSelectedAssistants)};
                               vm.lstEventType=${(lstEventType == null ? "[]" : lstEventType)}; vm.lstMeetingType=${(lstMeetingType == null ? "[]" : lstMeetingType)}; vm.init();'>

                            <input type="hidden" id="id" name="id" ng-model="vm.m.id" ng-update-hidden/>
                            <input type="hidden" id="auditId" name="auditId" ng-model="vm.m.auditId" ng-update-hidden/>

                            <div class="row">
                                <div class="col-xs-12">


                                    <div class="col-xs-12 form-group">
                                        <input type="hidden" ng-update-hidden ng-model="vm.m.eventTypeId"
                                               name="eventTypeId" id="eventTypeId">

                                        <label class="col-xs-3 control-label font-noraml">Tipo de evento:</label>

                                        <div class="col-xs-8">
                                            <select class="form-control m-b" id="chosen-select"
                                                    ng-disabled="vm.m.id!==undefined"
                                                    ng-required="true"
                                                    ng-change="vm.m.eventTypeId = vm.m.eventType.id;"
                                                    ng-options="c.name for c in vm.lstEventType"
                                                    ng-model="vm.m.eventType"></select>

                                        <span class="error"
                                              ng-show="FormUpId.eventTypeId.$error.required">*Campo requerido</span>
                                        </div>
                                    </div>

                                    <div class="col-xs-12 form-group">
                                        <label class="col-xs-3 control-label font-noraml">Comentarios:</label>

                                        <div class="col-xs-8">
                                            <textarea name="description" ng-model="vm.m.description"
                                                      ng-disabled="vm.m.id!==undefined"
                                                      placeholder="Ingrese los comentarios del evento"
                                                      minlength="8"
                                                      maxlength="2000"
                                                      ng-required="true" class="form-control"></textarea>
                                            <span class="error" ng-show="FormUpEventId.description.$error.required">*Campo requerido</span>
                                            <span class="error" ng-show="FormUpEventId.description.$error.minlength">*El campo debe tener entre 8 y 2000 caracteres</span>
                                            <span class="error" ng-show="FormUpEventId.description.$error.maxlength">*El campo debe tener entre 8 y 2000 caracteres</span>
                                        </div>
                                    </div>

                                    <div ng-show="vm.m.eventType.name === 'Reuni&oacute;n'">
                                        <div class="col-xs-12">

                                            <div class="col-xs-4 form-group">
                                                <div class="col-xs-12">
                                                    <label class="font-noraml">Fecha:</label>

                                                    <div>
                                                        <p class="input-group">
                                                            <input type="text" class="form-control" name="meetingDate"
                                                                   uib-datepicker-popup="<%=Constants.DATE_FORMAT_STR%>"
                                                                   ng-disabled="vm.m.id!==undefined"
                                                                   placeholder="<%=Constants.DATE_FORMAT_STR_PLACE_HOLDER%>"
                                                                   ng-model="vm.m.meetingDate"
                                                                   is-open="vm.m.initDateIsOpened"
                                                                   ng-required="vm.m.eventType.name === 'Reuni&oacute;n'"
                                                                   current-text="Hoy"
                                                                   clear-text="Limpiar"
                                                                   close-text="Cerrar"
                                                                   min-date="2000/01/01"
                                                                   max-date="vm.today"
                                                                   ng-change="vm.onChangeDate()"
                                                                   alt-input-formats="<%=Constants.DATE_FORMAT_STR%>"
                                                                   ng-disabled="vm.m.id!==undefined"/>
                                                            <span class="input-group-btn">
                                                                <button type="button" class="btn btn-default"
                                                                        ng-click="vm.m.initDateIsOpened=true;">
                                                                    <i class="glyphicon glyphicon-calendar"></i>
                                                                </button>
                                                            </span>
                                                    </div>
                                                    <span class="error"
                                                          ng-show="FormUpEventId.meetingDate.$error.required">*Campo requerido</span>
                                                    <span class="error"
                                                          ng-show="FormUpEventId.meetingDate.$invalid && !FormUpEventId.meetingDate.$pristine">*La fecha debe tener el formato dd/mm/aaaa</span>
                                                    <input type="hidden" name="meetingDate" ng-model="vm.m.meetingDate"
                                                           ng-update-hidden ng-if="vm.m.id!==undefined"/>
                                                </div>
                                            </div>

                                            <div class="col-xs-3 form-group">
                                                <div class="col-xs-12">
                                                    <label class="font-noraml">Hora:</label>
                                                    <div>
                                                        <p class="input-group">
                                                            <uib-timepicker ng-model="vm.ctrlHour" ng-change="vm.changed()" hour-step="1" ng-init="vm.changed(true)"
                                                                            ng-hide="vm.m.id!==undefined" ng-required="vm.m.eventType.name === 'Reuni&oacute;n'"
                                                                            minute-step="5" show-meridian="false"></uib-timepicker>
                                                            <input type="hidden" id="meetingHour" name="meetingHour" ng-hide="vm.m.id!==undefined"
                                                                   ng-required="vm.m.eventType.name === 'Reuni&oacute;n'" ng-model="vm.ctrlHourTx" ng-update-hidden>
                                                            <%--<input type="time" class="form-control" id="meetingHour"--%>
                                                                   <%--name="meetingHour" ng-hide="vm.m.id!==undefined"--%>
                                                                   <%--ng-model="vm.m.meetingHour"--%>
                                                                   <%--placeholder="HH:mm" min="00:00" max="23:59"--%>
                                                                   <%--ng-required="vm.m.eventType.name === 'Reuni&oacute;n'"/>--%>

                                                            <input type="text" class="form-control" ng-model="vm.m.hour"
                                                                   ng-disabled="true"
                                                                   ng-show="vm.m.id!==undefined">
                                                        </p>
                                                    <span class="error"
                                                          ng-show="FormUpEventId.meetingHour.$error.required">*Campo requerido</span>
                                                        <span class="error"
                                                              ng-show="FormUpEventId.meetingHour.$error.time">No es una hora valida!</span>
                                                    </div>
                                                </div>
                                                <%--<div class="col-xs-12">--%>
                                                <%--<uib-timepicker ng-model="vm.ctrlHour" ng-change="changed()" hour-step="1" minute-step="5" show-meridian="false"></uib-timepicker>--%>
                                                <%--</div>--%>
                                                <%--<div class="col-xs-12">--%>
                                                <%--<label class="font-noraml">Hora: {{vm.ctrlHour}}</label>--%>

                                                <%--<div>--%>
                                                <%--<p class="input-group">--%>
                                                <%--<input type="time" class="form-control" id="meetingHour" name="meetingHour" ng-hide="vm.m.id!==undefined" ng-model="vm.m.meetingHour"--%>
                                                <%--placeholder="HH:mm" min="00:00" max="23:59" ng-required="vm.m.eventType.name === 'Reuni&oacute;n'" />--%>

                                                <%--<input type="text" class="form-control" ng-model="vm.m.hour" ng-disabled="vm.m.id!==undefined"  ng-show="vm.m.id!==undefined">--%>
                                                <%--</p>--%>
                                                <%--<span class="error"--%>
                                                <%--ng-show="FormUpEventId.meetingHour.$error.required">*Campo requerido</span>--%>
                                                <%--<span class="error" ng-show="FormUpEventId.meetingHour.$error.time">No es una hora valida!</span>--%>
                                                <%--</div>--%>
                                                <%--</div>--%>
                                                <%--<span class="error"--%>
                                                <%--ng-show="FormUpEventId.endDate.$invalid && !FormUpEventId.endDate.$pristine">*La fecha debe tener el formato dd/mm/aaaa</span>--%>
                                                <%--<input type="hidden" name="endDate" ng-model="vm.m.endDate" ng-update-hidden ng-if="vm.m.id!==undefined"/>--%>
                                            </div>

                                            <div class="col-xs-5 form-group">
                                                <div class="col-xs-12 form-group">
                                                    <input type="hidden" ng-update-hidden ng-model="vm.m.meetingTypeId"
                                                           name="meetingTypeId" id="meetingTypeId">

                                                    <label class="font-noraml">Tipo de reuni&oacute;n:</label>

                                                    <div class="col-xs-12">
                                                        <select class="form-control m-b" id="chosen-select-meeting"
                                                                ng-disabled="vm.m.id!==undefined"
                                                                ng-required="vm.m.eventType.name === 'Reuni&oacute;n'"
                                                                ng-change="vm.m.meetingTypeId = vm.m.meetingType.id;"
                                                                ng-options="c.name for c in vm.lstMeetingType"
                                                                ng-model="vm.m.meetingType"></select>

                                                        <span class="error"
                                                              ng-show="FormUpId.meetingTypeId.$error.required">*Campo requerido</span>
                                                    </div>
                                                </div>

                                            </div>

                                        </div>


                                        <div class="col-xs-12 form-group typeahead-demo">
                                            <label class="col-xs-2  col-xs-offset-1 control-label font-noraml"
                                                   ng-hide="vm.m.id!==undefined">Buscar usuario</label>

                                            <div class="col-xs-7">
                                                <input type="text"
                                                       ng-hide="vm.m.id!==undefined"
                                                       ng-model="vm.m.assistantSel"
                                                       placeholder="Escriba el nombre del usuario..."
                                                       uib-typeahead="assistant.desc for assistant in vm.getAssistants($viewValue)"
                                                       typeahead-on-select="vm.pushAssistant($item);"
                                                       typeahead-loading="vm.loadingAssistants"
                                                       typeahead-no-results="vm.noResults"
                                                       typeahead-min-length="1"
                                                       class="form-control">
                                                <i class="col-xs-1" ng-show="vm.loadingAssistants"
                                                   class="glyphicon glyphicon-refresh"></i>

                                                <div ng-show="vm.noResults">
                                                    <i class="glyphicon glyphicon-remove"></i> No se encontraron
                                                    resultados.
                                                </div>
                                            </div>

                                        </div>

                                        <div class="space-10">&nbsp;</div>
                                        <div class="ibox float-e-margins col-xs-8 col-xs-offset-2">
                                            <div class="ibox-title">
                                                <h5>Asistentes</h5>
                                            </div>
                                            <div class="ibox-content no-padding">
                                                <input type="hidden" value="{{vm.lstSelectedAssistants}}"
                                                       name="lstSelectedAssistants">

                                                <ul class="list-group">
                                                    <li class="list-group-item"
                                                        ng-if="!vm.lstSelectedAssistants.length>0">
                                                    <span class="badge badge-warning"><i
                                                            class="fa fa-exclamation-triangle"></i></span>
                                                        No hay usuarios seleccionadas
                                                    </li>
                                                    <li class="list-group-item animated fadeInDown"
                                                        ng-repeat="assignedAssistant in vm.lstSelectedAssistants track by $index">
                                                        <span class="badge badge-danger"
                                                              ng-click="vm.popAssistant(assignedAssistant.id)"><i
                                                                class="fa fa-minus-circle"
                                                                ></i></span>
                                                        {{assignedAssistant.desc}}
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>

                                    </div>

                                </div>
                            </div>
                        </form>
                    </div>

                    <div class="col-xs-12">
                        <div ng-show="up.MsgError" ng-bind-html="up.MsgError" class="alert alert-error element-center">
                        </div>
                        <div ng-show="up.MsgSuccess" ng-bind-html="up.MsgSuccess"
                             class="alert-success alert-error element-center">
                        </div>
                    </div>

                </div>

            </div>
            <div class="modal-footer">
                <button class="btn btn-white" ng-click="up.cancel()">
                    {{vm.m.id!==undefined ? "Regresar" : "Cancelar"}}
                </button>
                <button class="btn btn-primary" ng-show="up.WaitFor===false" ng-hide="vm.m.id!==undefined"
                        ng-click="vm.validateAll()== false ? up.submit('#FormUpEventId', '<c:url value='/audit/event/doUpsert.json' />', FormUpEventId.$valid):''">
                    Guardar
                </button>
                <button class="btn btn-warning" ng-disabled="up.WaitFor===true" data-ng-show="up.WaitFor===true">
                    <i class="fa fa-refresh fa-spin"></i> &nbsp; Procesando
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
