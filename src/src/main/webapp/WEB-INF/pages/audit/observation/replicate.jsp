<%@ page import="com.crystal.model.shared.Constants" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
    $(document).ready(function () {
        window.showModalFormDlg("#dlgUpModalId", "#FormUpObservationAttentionId");
    });
</script>

<div class="modal inmodal" id="dlgUpModalId" tabindex="-1" ng-controller="upsertController as up" role="dialog"
     aria-hidden="true" ng-cloak>
    <div class="modal-dialog" style="width:800px" ng-controller="observationController as vm">
        <div class="modal-content animated flipInY">
            <div class="modal-header">

                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>

                <div class="row">
                    <div class="col-xs-3" align="left" style="padding-top: 20px;">
                        <img src="${pageContext.request.contextPath}/assets/img/LogoSE.png" , height="90" width="200">
                    </div>
                    <div class="col-xs-6" style="padding-top: 40px;">
                        <h4 class="modal-title">Pliego de observaciones</h4>
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
                                <h5>Replicar el pliego n&uacute;mero <b>{{vm.m.observationNumber}}</b></h5>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12">
                        <form id="FormUpObservationAttentionId" name="FormUpObservationAttentionId" class="form-horizontal" role="form"
                              ng-init='vm.m = ${(model == null ? "{}" : model)}; vm.init();'>
                            <input type="hidden" id="id" name="id" ng-model="vm.m.id" ng-update-hidden/>
                            <div class="row" ng-if="vm.m.isAttended==false">


                                <div class="col-xs-12 form-group">
                                    <input type="hidden" id="replicateAs" name="replicateAs" ng-model="vm.m.replicateAs" ng-required="true" ng-update-hidden/>
                                    <label class="col-xs-3 control-label font-noraml">Replicar como:</label>

                                    <div class="col-xs-8">
                                        <label class="radio-inline"><input type="radio" ng-model="vm.m.replicateAs" ng-value="'<%=Constants.RESPONSIBILITY_R%>'">Promoci&oacute;n</label>
                                        <br/>
                                        <span class="error" ng-show="FormUpObservationAttentionId.replicateAs.$error.required">*Campo requerido</span>
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
                                        <span class="error" ng-show="FormUpObservationAttentionId.attentionComment.$error.required">*Campo requerido</span>
                                        <span class="error" ng-show="FormUpObservationAttentionId.attentionComment.$error.minlength">*El campo debe tener entre 8 y 2000 caracteres</span>
                                        <span class="error" ng-show="FormUpObservationAttentionId.attentionComment.$error.maxlength">*El campo debe tener entre 8 y 2000 caracteres</span>
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
                        ng-click="up.submit('#FormUpObservationAttentionId', '<c:url value='/audit/observation/doReplication.json' />', FormUpObservationAttentionId.$valid)">
                    Guardar
                </button>
            </div>
        </div>
    </div>
</div>