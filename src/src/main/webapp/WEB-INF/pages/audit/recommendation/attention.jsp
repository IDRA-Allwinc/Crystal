<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
    $(document).ready(function () {
        window.showModalFormDlg("#dlgUpModalId", "#FormUpRecommendationAttentionId");
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
                                <h5 ng-if="vm.m.isAttended==false">Indicar atenci&oacute;n de la recomendaci&oacute;n n&uacute;mero <b>{{vm.m.recommendationNumber}}</b></h5>
                                <h5 ng-if="vm.m.isAttended==true">Informaci&oacute;n de atenci&oacute;n de la recomendaci&oacute;n n&uacute;mero <b>{{vm.m.recommendationNumber}}</b></h5>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12">
                        <form id="FormUpRecommendationAttentionId" name="FormUpRecommendationAttentionId" class="form-horizontal" role="form"
                              ng-init='vm.m = ${(model == null ? "{}" : model)}; vm.init();'>
                            <input type="hidden" id="id" name="id" ng-model="vm.m.id" ng-update-hidden/>
                            <div class="row" ng-if="vm.m.isAttended==false">
                                <div class="col-xs-12">

                                    <div class="col-xs-12 form-group">
                                        <label class="col-xs-3 control-label font-noraml">Comentario:</label>

                                        <div class="col-xs-8">
                                            <textarea name="attentionComment" ng-model="vm.m.attentionComment"
                                                      placeholder="Ingrese el comentario para indicar la atenci&oacute;n de la recomendacio&oacute;n"
                                                      minlength="8"
                                                      maxlength="2000"
                                                      ng-required="true" class="form-control"></textarea>
                                            <span class="error" ng-show="FormUpRecommendationAttentionId.attentionComment.$error.required">*Campo requerido</span>
                                            <span class="error" ng-show="FormUpRecommendationAttentionId.attentionComment.$error.minlength">*El campo debe tener entre 8 y 2000 caracteres</span>
                                            <span class="error" ng-show="FormUpRecommendationAttentionId.attentionComment.$error.maxlength">*El campo debe tener entre 8 y 2000 caracteres</span>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="panel panel-primary" ng-if="vm.m.isAttended==true">
                                <div class="panel-heading">
                                    Recomendacio&oacute;n atendida n&uacute;mero <b>{{vm.m.recommendationNumber}}</b>
                                </div>
                                <div class="panel-body">
                                    <div>
                                        <label class="control-label font-noraml"><b>Auditoria:</b></label>

                                        <p>{{vm.m.auditName}}</p>
                                    </div>
                                    <div>
                                        <label class="control-label font-noraml"><b>Comentario:</b></label>

                                        <p>{{vm.m.attentionComment}}</p>
                                    </div>
                                    <div>
                                        <label class="control-label font-noraml"><b>Usuario que
                                            atendi&oacute;:</b></label>

                                        <p>{{vm.m.attentionUser}}</p>
                                    </div>
                                    <div>
                                        <label class="control-label font-noraml"><b>Fecha de
                                            atenci&oacute;n:</b></label>

                                        <p>{{vm.m.attentionDateStr}}</p>
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
            <div class="modal-footer" ng-if="vm.m.isAttended==true">
                <button class="btn btn-primary" ng-click="up.cancel()">
                    Regresar
                </button>
            </div>
            <div class="modal-footer" ng-if="vm.m.isAttended==false">
                <button class="btn btn-white" ng-click="up.cancel()">
                    Cancelar
                </button>
                <button class="btn btn-primary " ng-disabled="up.WaitFor==true"
                        ng-click="up.submit('#FormUpRecommendationAttentionId', '<c:url value='/audit/recommendation/doAttention.json' />', FormUpRecommendationAttentionId.$valid)">
                    Guardar
                </button>
            </div>
        </div>
    </div>
</div>