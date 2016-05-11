<%@ page import="com.crystal.model.shared.Constants" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
    $(document).ready(function () {
        window.showModalFormDlg("#dlgUpModalId", "#FormUpCommentAttentionId");
    });
</script>

<div class="modal inmodal" id="dlgUpModalId" tabindex="-1" ng-controller="upsertController as up" role="dialog"
     aria-hidden="true" ng-cloak>
    <div class="modal-dialog" style="width:800px" ng-controller="commentController as vm">
        <div class="modal-content animated flipInY">
            <div class="modal-header">

                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>

                <div class="row">
                    <div class="col-xs-3" align="left" style="padding-top: 20px;">
                        <img src="${pageContext.request.contextPath}/assets/img/LogoSE.png" , height="90" width="200">
                    </div>
                    <div class="col-xs-6" style="padding-top: 40px;">
                        <h4 class="modal-title">Observaci&oacute;n</h4>
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
                                <h5>Replicar la observaci&oacute;n n&uacute;mero <b>{{vm.m.commentNumber}}</b></h5>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12">
                        <form id="FormUpCommentAttentionId" name="FormUpCommentAttentionId" class="form-horizontal"
                              role="form"
                              ng-init='vm.m = ${(model == null ? "{}" : model)};
                                       vm.attention = ${(attention == null ? "{}" : attention)};
                                       vm.urlGetAreas="<c:url value='/previousRequest/request/getAreas.json'/>";
                                       vm.lstSelectedAreas = ${(lstSelectedAreas == null ? "[]" : lstSelectedAreas)};
                                       vm.lstObservationType=${(lstObservationType == null ? "[]" : lstObservationType)}; vm.init();'>

                            <input type="hidden" id="id" name="id" ng-model="vm.m.id" ng-update-hidden/>
                            <input type="hidden" id="auditId" name="auditId" ng-model="vm.m.auditId" ng-update-hidden/>

                            <div class="row" ng-if="vm.attention.isAttended==false">


                                <div class="col-xs-12 form-group">
                                    <label class="col-xs-3 control-label font-noraml">Comentario de replica:</label>

                                    <div class="col-xs-8">
                                            <textarea name="attentionComment" ng-model="vm.attention.attentionComment"
                                                      placeholder="Ingrese el comentario para la replicaci&oacute;n de la observaci&oacute;n"
                                                      minlength="8"
                                                      maxlength="2000"
                                                      ng-required="true" class="form-control"></textarea>
                                        <span class="error"
                                              ng-show="FormUpCommentAttentionId.attentionComment.$error.required">*Campo requerido</span>
                                        <span class="error"
                                              ng-show="FormUpCommentAttentionId.attentionComment.$error.minlength">*El campo debe tener entre 8 y 2000 caracteres</span>
                                        <span class="error"
                                              ng-show="FormUpCommentAttentionId.attentionComment.$error.maxlength">*El campo debe tener entre 8 y 2000 caracteres</span>
                                    </div>
                                </div>

                                <div class="col-xs-12 form-group">
                                    <input type="hidden" id="replicateAs" name="replicateAs" ng-model="vm.attention.replicateAs"
                                           ng-required="true" ng-update-hidden/>
                                    <label class="col-xs-3 control-label font-noraml">Replicar como:</label>

                                    <div class="col-xs-8">
                                        <label class="radio-inline"><input type="radio" ng-model="vm.attention.replicateAs"
                                                                           ng-value="'<%=Constants.RECOMMENDATION_R%>'">Recomendaci&oacute;n</label>
                                        <label class="radio-inline"><input type="radio" ng-model="vm.attention.replicateAs"
                                                                           ng-value="'<%=Constants.OBSERVATION_R%>'">Pliego
                                            de observaciones</label>
                                        <label class="radio-inline"><input type="radio" ng-model="vm.attention.replicateAs"
                                                                           ng-value="'<%=Constants.RESPONSIBILITY_R%>'">Promoci&oacute;n</label>
                                        <br/>
                                        <span class="error"
                                              ng-show="FormUpCommentAttentionId.replicateAs.$error.required">*Campo requerido</span>
                                    </div>
                                </div>


                                <div class="col-xs-12 form-group"
                                     ng-show="vm.attention.replicateAs === '<%=Constants.OBSERVATION_R%>'">
                                    <input type="hidden" ng-update-hidden ng-model="vm.m.observationTypeId"
                                           name="observationTypeId" id="observationTypeId">

                                    <label class="col-xs-3 control-label font-noraml">Tipo de pliego:</label>

                                    <div class="col-xs-8">
                                        <select class="form-control m-b" id="chosen-select"
                                                ng-required="vm.attention.replicateAs === '<%=Constants.OBSERVATION_R%>'"
                                                ng-change="vm.m.observationTypeId = vm.m.observationType.id;"
                                                ng-options="c.name for c in vm.lstObservationType"
                                                ng-model="vm.m.observationType"></select>

                                        <span class="error"
                                              ng-show="FormUpId.observationTypeId.$error.required">*Campo requerido</span>
                                    </div>
                                </div>


                                <div class="col-xs-12">

                                    <div class="col-xs-12 form-group">
                                        <label class="col-xs-3 control-label font-noraml">Numeral:</label>

                                        <div class="col-xs-8">
                                            <input type="text" name="number" ng-model="vm.m.number"
                                                   placeholder="Ingrese el numeral de la observaci&oacute;n"
                                                   minlength="8"
                                                   maxlength="50"
                                                   ng-required="true" class="form-control">
                                        <span class="error"
                                              ng-show="FormUpCommentId.number.$error.required">*Campo requerido</span>
                                            <span class="error" ng-show="FormUpCommentId.number.$error.minlength">*El campo debe tener entre 8 y 50 caracteres</span>
                                            <span class="error" ng-show="FormUpCommentId.number.$error.maxlength">*El campo debe tener entre 8 y 50 caracteres</span>
                                        </div>
                                    </div>

                                    <div class="col-xs-12 form-group">
                                        <label class="col-xs-3 control-label font-noraml">Descripci&oacute;n:</label>

                                        <div class="col-xs-8">
                                            <textarea name="description" ng-model="vm.m.description"
                                                      placeholder="Ingrese la descripci&oacute;n del elemento"
                                                      minlength="8"
                                                      maxlength="2000"
                                                      ng-required="true" class="form-control"></textarea>
                                            <span class="error" ng-show="FormUpCommentId.description.$error.required">*Campo requerido</span>
                                            <span class="error" ng-show="FormUpCommentId.description.$error.minlength">*El campo debe tener entre 8 y 2000 caracteres</span>
                                            <span class="error" ng-show="FormUpCommentId.description.$error.maxlength">*El campo debe tener entre 8 y 2000 caracteres</span>
                                        </div>
                                    </div>


                                    <div class="col-xs-12">

                                        <div class="col-xs-4 form-group">
                                            <div class="col-xs-12">
                                                <label class="font-noraml">Fecha de inicio:</label>

                                                <div>
                                                    <p class="input-group">
                                                        <input type="text" class="form-control" name="initDate"
                                                               uib-datepicker-popup="<%=Constants.DATE_FORMAT_STR%>"
                                                               placeholder="<%=Constants.DATE_FORMAT_STR_PLACE_HOLDER%>"
                                                               ng-model="vm.m.initDate"
                                                               is-open="vm.m.initDateIsOpened" ng-required="true"
                                                               current-text="Hoy"
                                                               clear-text="Limpiar"
                                                               close-text="Cerrar"
                                                               min-date="2000/01/01"
                                                               max-date="vm.today"
                                                               ng-change="vm.onChangeDate()"
                                                               alt-input-formats="<%=Constants.DATE_FORMAT_STR%>"/>
                                                  <span class="input-group-btn">
                                                    <button type="button" class="btn btn-default"
                                                            ng-click="vm.m.initDateIsOpened=true;"><i
                                                            class="glyphicon glyphicon-calendar"></i></button>
                                                  </span>
                                                    </p>
                                                    <span class="error"
                                                          ng-show="FormUpCommentId.initDate.$error.required">*Campo requerido</span>
                                                </div>
                                        <span class="error"
                                              ng-show="FormUpCommentId.initDate.$invalid && !FormUpCommentId.initDate.$pristine">*La fecha debe tener el formato dd/mm/aaaa</span>

                                                <input type="hidden" name="initDate" ng-model="vm.m.initDate"
                                                       ng-update-hidden ng-if="vm.m.id!==undefined"/>
                                            </div>
                                        </div>

                                        <div class="col-xs-4 form-group">
                                            <div class="col-xs-12">
                                                <label class="font-noraml">Fecha l&iacute;mite:</label>

                                                <div>
                                                    <p class="input-group">
                                                        <input type="text" class="form-control" name="endDate"
                                                               uib-datepicker-popup="<%=Constants.DATE_FORMAT_STR%>" ng-model="vm.m.endDate"
                                                               is-open="vm.m.endDateIsOpened" ng-required="true"
                                                               placeholder="<%=Constants.DATE_FORMAT_STR_PLACE_HOLDER%>"
                                                               current-text="Hoy"
                                                               clear-text="Limpiar"
                                                               close-text="Cerrar"
                                                               min-date="2000/01/01"
                                                               max-date="vm.today"
                                                               ng-change="vm.onChangeDate()"
                                                               alt-input-formats="<%=Constants.DATE_FORMAT_STR%>"/>
                                                  <span class="input-group-btn">
                                                    <button type="button" class="btn btn-default"
                                                            ng-click="vm.m.endDateIsOpened=true;"><i
                                                            class="glyphicon glyphicon-calendar"></i></button>
                                                  </span>
                                                    </p>
                                                    <span class="error"
                                                          ng-show="FormUpCommentId.endDate.$error.required">*Campo requerido</span>
                                                </div>
                                            </div>
                                        <span class="error"
                                              ng-show="FormUpCommentId.endDate.$invalid && !FormUpCommentId.endDate.$pristine">*La fecha debe tener el formato dd/mm/aaaa</span>
                                            <input type="hidden" name="endDate" ng-model="vm.m.endDate" ng-update-hidden
                                                   ng-if="vm.m.id!==undefined"/>
                                        </div>

                                        <div class="col-xs-4 form-group">
                                            <label class="col-xs-12 font-noraml">Plazo otorgado:</label>
                                            <br/>

                                            <div class="col-xs-12">
                                                <input type="text" name="limitTimeDays" ng-model="vm.m.limitTimeDays"
                                                       class="form-control" disabled>
                                            </div>
                                        </div>
                                    </div>


                                    <div class="col-xs-12 form-group typeahead-demo">
                                        <label class="col-xs-2  col-xs-offset-1 control-label font-noraml">Buscar &aacute;rea</label>

                                        <div class="col-xs-7">
                                            <input type="text" ng-model="vm.m.areaSel"
                                                   placeholder="Escriba el nombre del &aacute;rea o del responsable..."
                                                   uib-typeahead="area.desc for area in vm.getAreas($viewValue)"
                                                   typeahead-on-select="vm.pushArea($item);"
                                                   typeahead-loading="vm.loadingAreas"
                                                   typeahead-no-results="vm.noResults"
                                                   typeahead-min-length="1"
                                                   class="form-control">
                                            <i class="col-xs-1" ng-show="vm.loadingAreas"
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
                                            <h5>&Aacute;reas seleccionadas</h5>
                                        </div>
                                        <div class="ibox-content no-padding">
                                            <input type="hidden" value="{{vm.lstSelectedAreas}}"
                                                   name="lstSelectedAreas">

                                            <ul class="list-group">
                                                <li class="list-group-item" ng-if="!vm.lstSelectedAreas.length>0">
                                                    <span class="badge badge-warning"><i
                                                            class="fa fa-exclamation-triangle"></i></span>
                                                    No hay &aacute;reas seleccionadas
                                                </li>
                                                <li class="list-group-item animated fadeInDown"
                                                    ng-repeat="assignedArea in vm.lstSelectedAreas track by $index">
                                            <span class="badge badge-danger" ng-click="vm.popArea(assignedArea.id)"><i
                                                    class="fa fa-minus-circle"
                                                    ></i></span>
                                                    {{assignedArea.desc}}
                                                </li>
                                            </ul>
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
                        ng-click="vm.validateDates()==false ? up.submit('#FormUpCommentAttentionId', '<c:url value='/audit/comment/doReplication.json' />', FormUpCommentAttentionId.$valid) : ''">
                    Guardar
                </button>
            </div>
        </div>
    </div>
</div>