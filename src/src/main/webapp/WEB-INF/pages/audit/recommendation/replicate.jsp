<%@ page import="com.crystal.model.shared.Constants" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
    $(document).ready(function () {
        window.showModalFormDlg("#dlgUpModalId", "#FormUpCommentAttentionId");
    });
</script>

<div class="modal inmodal" id="dlgUpModalId" tabindex="-1" ng-controller="upsertController as up" role="dialog"
     aria-hidden="true" ng-cloak>
    <div class="modal-dialog" style="width:800px" ng-controller="recommendationController as vm">
        <div class="modal-content animated flipInY">
            <div class="modal-header">

                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>

                <div class="row">
                    <div class="col-xs-3" align="left" style="padding-top: 20px;">
                        <img src="${pageContext.request.contextPath}/assets/img/LogoSE.png" , height="90" width="200">
                    </div>
                    <div class="col-xs-6" style="padding-top: 40px;">
                        <h4 class="modal-title">Recomendaci&oacute;n</h4>
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
                                <h5>Replicar la recomendaci&oacute;n n&uacute;mero <b>{{vm.m.recommendationNumber}}</b></h5>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12">
                        <form id="FormUpCommentAttentionId" name="FormUpCommentAttentionId" class="form-horizontal" role="form"
                              ng-init='vm.m = ${(model == null ? "{}" : model)}; vm.lstObservationType=${(lstObservationType == null ? "[]" : lstObservationType)}; vm.init();'>
                            <input type="hidden" id="id" name="id" ng-model="vm.m.id" ng-update-hidden/>
                            <div class="row" ng-if="vm.m.isAttended==false">


                                <div class="col-xs-12 form-group">
                                    <input type="hidden" id="replicateAs" name="replicateAs" ng-model="vm.m.replicateAs" ng-required="true" ng-update-hidden/>
                                    <label class="col-xs-3 control-label font-noraml">Replicar como:</label>

                                    <div class="col-xs-8">
                                        <label class="radio-inline"><input type="radio" ng-model="vm.m.replicateAs" ng-value="'<%=Constants.OBSERVATION_R%>'">Pliego de observaciones</label>
                                        <label class="radio-inline"><input type="radio" ng-model="vm.m.replicateAs" ng-value="'<%=Constants.RESPONSIBILITY_R%>'">Promoci&oacute;n de responsabilidades</label>
                                        <br/>
                                        <span class="error" ng-show="FormUpCommentAttentionId.replicateAs.$error.required">*Campo requerido</span>
                                    </div>
                                </div>




                                <div class="col-xs-12 form-group" ng-show="vm.m.replicateAs === '<%=Constants.OBSERVATION_R%>'">
                                    <input type="hidden" ng-update-hidden ng-model="vm.m.observationTypeId"
                                           name="observationTypeId" id="observationTypeId">

                                    <label class="col-xs-3 control-label font-noraml">Tipo de pliego:</label>

                                    <div class="col-xs-8">
                                        <select class="form-control m-b" id="chosen-select"
                                                ng-required="vm.m.replicateAs === '<%=Constants.OBSERVATION_R%>'"
                                                ng-change="vm.m.observationTypeId = vm.m.observationType.id;"
                                                ng-options="c.name for c in vm.lstObservationType"
                                                ng-model="vm.m.observationType"></select>

                                        <span class="error"
                                              ng-show="FormUpId.observationTypeId.$error.required">*Campo requerido</span>
                                    </div>
                                </div>




                                <div class="col-xs-12 form-group">
                                    <label class="col-xs-3 control-label font-noraml">Comentario:</label>

                                    <div class="col-xs-8">
                                            <textarea name="attentionComment" ng-model="vm.m.attentionComment"
                                                      placeholder="Ingrese el comentario para la replicaci&oacute;n de la observaci&oacute;n"
                                                      minlength="8"
                                                      maxlength="2000"
                                                      ng-required="true" class="form-control"></textarea>
                                        <span class="error" ng-show="FormUpCommentAttentionId.attentionComment.$error.required">*Campo requerido</span>
                                        <span class="error" ng-show="FormUpCommentAttentionId.attentionComment.$error.minlength">*El campo debe tener entre 8 y 2000 caracteres</span>
                                        <span class="error" ng-show="FormUpCommentAttentionId.attentionComment.$error.maxlength">*El campo debe tener entre 8 y 2000 caracteres</span>
                                    </div>
                                </div>

                                <div class="col-xs-12">

                                    <div class="col-xs-4 form-group">
                                        <div class="col-xs-12">
                                            <label class="font-noraml">Fecha de inicio:</label>

                                            <div>
                                                <p class="input-group">
                                                    <input type="text" class="form-control" name="initDate"
                                                           uib-datepicker-popup="yyyy/MM/dd"
                                                           placeholder="yyyy/mm/dd"
                                                           ng-model="vm.m.initDate"
                                                           is-open="vm.m.initDateIsOpened" ng-required="true"
                                                           current-text="Hoy"
                                                           clear-text="Limpiar"
                                                           close-text="Cerrar"
                                                           min-date="2000/01/01"
                                                           max-date="vm.today"
                                                           ng-change="vm.onChangeDate()"
                                                           alt-input-formats="yyyy/MM/dd"
                                                            />
                                                  <span class="input-group-btn">
                                                    <button type="button" class="btn btn-default"
                                                            ng-click="vm.m.initDateIsOpened=true;"><i
                                                            class="glyphicon glyphicon-calendar"></i></button>
                                                  </span>
                                                </p>
                                                <span class="error" ng-show="FormUpCommentAttentionId.initDate.$error.required">*Campo requerido</span>
                                            </div>
                                        <span class="error"
                                              ng-show="FormUpCommentAttentionId.initDate.$invalid && !FormUpCommentAttentionId.initDate.$pristine">*La fecha debe tener el formato aaaa/mm/dd</span>
                                        </div>
                                    </div>

                                    <div class="col-xs-4 form-group">
                                        <div class="col-xs-12">
                                            <label class="font-noraml">Fecha l&iacute;mite:</label>

                                            <div>
                                                <p class="input-group">
                                                    <input type="text" class="form-control" name="endDate"
                                                           uib-datepicker-popup="yyyy/MM/dd" ng-model="vm.m.endDate"
                                                           is-open="vm.m.endDateIsOpened" ng-required="true"
                                                           placeholder="yyyy/mm/dd"
                                                           current-text="Hoy"
                                                           clear-text="Limpiar"
                                                           close-text="Cerrar"
                                                           min-date="2000/01/01"
                                                           max-date="vm.today"
                                                           ng-change="vm.onChangeDate()"
                                                           alt-input-formats="yyyy/MM/dd"
                                                            />
                                                  <span class="input-group-btn">
                                                    <button type="button" class="btn btn-default"
                                                            ng-click="vm.m.endDateIsOpened=true;"><i
                                                            class="glyphicon glyphicon-calendar"></i></button>
                                                  </span>
                                                </p>
                                                    <span class="error"
                                                          ng-show="FormUpCommentAttentionId.endDate.$error.required">*Campo requerido</span>
                                            </div>
                                        </div>
                                        <span class="error"
                                              ng-show="FormUpCommentAttentionId.endDate.$invalid && !FormUpCommentAttentionId.endDate.$pristine">*La fecha debe tener el formato aaaa/mm/dd</span>
                                    </div>

                                    <div class="col-xs-4 form-group">
                                        <label class="col-xs-12 font-noraml">Plazo otorgado:</label>
                                        <br/>
                                        <div class="col-xs-12">
                                            <input type="text" name="limitTimeDays" ng-model="vm.m.limitTimeDays" class="form-control" disabled>
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
            <div class="modal-footer" ng-if="vm.m.isAttended==false">
                <button class="btn btn-white" ng-click="up.cancel()">
                    Cancelar
                </button>
                <button class="btn btn-primary " ng-disabled="up.WaitFor==true"
                        ng-click="vm.validateDates()==false ? up.submit('#FormUpCommentAttentionId', '<c:url value='/audit/recommendation/doReplication.json' />', FormUpCommentAttentionId.$valid):''">
                    Guardar
                </button>
            </div>
        </div>
    </div>
</div>