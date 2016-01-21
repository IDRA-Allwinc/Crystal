<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div ng-controller="upsertController as up">
    <div class="row" ng-controller="auditController as vm">
        <div class="col-xs-8">
            <div class="ibox">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="ibox-title navy-bg">
                            <h5>Informaci&oacute;n de la auditor&iacute;a</h5>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-12">
                    <form id="FormUpId" name="FormUpId" class="form-horizontal" role="form"
                          ng-init='vm.m = ${(model == null ? "{}" : model)};
                              vm.lstAuditTypes= ${(lstAuditTypes == null ? "[]" : lstAuditTypes)};
                              vm.urlGetSupervisoryEntities = "<c:url value='/audit/getSupervisoryEntities.json'/>";
                              vm.urlGetAuditedEntities = "<c:url value='/audit/getAreas.json'/>";
                              vm.urlGetAreas="<c:url value='/audit/request/getAreas.json'/>";
                              vm.upsertCtrl = up;
                              vm.lstSelectedAreas = ${(lstSelectedAreas == null ? "[]" : lstSelectedAreas)};
                              vm.init();'>

                        <input type="hidden" id="id" name="id" ng-model="vm.m.id" ng-update-hidden/>

                        <div class="row">
                            <div class="col-xs-12">

                                <div class="col-xs-3 form-group">
                                    <div class="col-xs-12">
                                        <label class="control-label font-noraml">N&uacute;mero de oficio:</label>

                                        <div>
                                            <input type="text" name="letterNumber" ng-model="vm.m.letterNumber"
                                                   placeholder="Ingrese el n&uacute;mero de oficio"
                                                   minlength="8" maxlength="200"
                                                   ng-required="true" class="form-control">
                                        <span class="error"
                                              ng-show="FormUpId.letterNumber.$error.required">*Campo requerido</span>
                                            <span class="error" ng-show="FormUpId.letterNumber.$error.minlength">*El campo debe tener entre 8 y 200 caracteres</span>
                                            <span class="error" ng-show="FormUpId.letterNumber.$error.maxlength">*El campo debe tener entre 8 y 200 caracteres</span>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-xs-3 form-group">
                                    <label class="font-noraml">Fecha del oficio:</label>

                                    <div>
                                        <p class="input-group">
                                            <input type="text" class="form-control" name="letterDate"
                                                   uib-datepicker-popup="yyyy/MM/dd" ng-model="vm.m.letterDate"
                                                   is-open="vm.m.letterDateIsOpened" ng-required="true"
                                                   current-text="Hoy"
                                                   clear-text="Limpiar"
                                                   close-text="Cerrar"
                                                   min-date="2000/01/01"
                                                   max-date="vm.today"
                                                   alt-input-formats="yyyy/MM/dd"/>
                                                  <span class="input-group-btn">
                                                    <button type="button" class="btn btn-default"
                                                            ng-click="vm.m.letterDateIsOpened=true;"><i
                                                            class="glyphicon glyphicon-calendar"></i></button>
                                                  </span>
                                        </p>
                                    <span class="error"
                                          ng-show="FormUpId.letterDate.$error.required">*Campo requerido</span>
                                    </div>
                                        <span class="error"
                                              ng-show="FormUpId.letterDate.$invalid && !FormUpId.letterDate.$pristine">*La fecha debe tener el formato aaaa/mm/dd</span>
                                </div>

                                <div class="col-xs-6">
                                    <div class="col-xs-12">
                                        <input type="hidden" name="supervisoryEntityId"
                                               value="{{vm.m.supervisoryEntityId}}">
                                        <label class="font-noraml">&Oacute;rgano fiscalizador:</label>
                                        <input type="text" ng-model="vm.m.supervisoryEntity"
                                               placeholder="Escriba el nombre del &oacute;rgano fiscalizador o del responsable..."
                                               uib-typeahead="entitie.desc for entitie in vm.getSupervisoryEntities($viewValue)"
                                               typeahead-on-select="vm.m.supervisoryEntityId = $item.id;"
                                               typeahead-loading="vm.loadingSupervisoryEntities"
                                               typeahead-no-results="vm.noResultsSupervisoryEntities"
                                               typeahead-min-length="1"
                                               class="form-control">
                                        <i class="col-xs-1" ng-show="vm.loadingSupervisoryEntities"
                                           class="glyphicon glyphicon-refresh"></i>

                                        <div ng-show="vm.noResultsSupervisoryEntities">
                                            <i class="glyphicon glyphicon-remove"></i> No se encontraron
                                            resultados.
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>

                        <div class="row">
                            <div class="col-xs-12">
                                <div class="col-xs-5 form-group">
                                    <div class="col-xs-12">
                                        <label class="control-label font-noraml">N&uacute;mero de la
                                            auditor&iacute;a:</label>

                                        <div>
                                            <input type="text" name="number" ng-model="vm.m.number"
                                                   placeholder="Ingrese el n&uacute;mero de la auditor&iacute;a"
                                                   minlength="2"
                                                   maxlength="8"
                                                   ng-required="true" class="form-control">
                                        <span class="error"
                                              ng-show="FormUpId.number.$error.required">*Campo requerido</span>
                                            <span class="error" ng-show="FormUpId.number.$error.minlength">*El campo debe tener entre 2 y 8 caracteres</span>
                                            <span class="error" ng-show="FormUpId.number.$error.maxlength">*El campo debe tener entre 2 y 8 caracteres</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xs-7 form-group">
                                    <div class="col-xs-12">
                                        <label class="control-label font-noraml">Nombre de la
                                            auditor&iacute;a:</label>

                                        <div>
                                            <input type="text" name="name" ng-model="vm.m.name"
                                                   placeholder="Ingrese el nombre de la auditor&iacute;a"
                                                   minlength="8"
                                                   maxlength="200"
                                                   ng-required="true" class="form-control">
                                        <span class="error"
                                              ng-show="FormUpId.name.$error.required">*Campo requerido</span>
                                            <span class="error" ng-show="FormUpId.name.$error.minlength">*El campo debe tener entre 8 y 200 caracteres</span>
                                            <span class="error" ng-show="FormUpId.name.$error.maxlength">*El campo debe tener entre 8 y 200 caracteres</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-xs-12">
                                <div class="col-xs-12 form-group">
                                    <div class="col-xs-6">
                                        <label class="control-label font-noraml">Objetivo de la
                                            auditor&iacute;a:</label>

                                        <div>
                                            <textarea name="objective" ng-model="vm.m.objective"
                                                      placeholder="Ingrese el objetivo de la auditor&iacute;a"
                                                      minlength="8"
                                                      maxlength="2000"
                                                      ng-required="true" class="form-control"></textarea>
                                            <span class="error" ng-show="FormUpId.objective.$error.required">*Campo requerido</span>
                                            <span class="error" ng-show="FormUpId.objective.$error.minlength">*El campo debe tener entre 8 y 2000 caracteres</span>
                                            <span class="error" ng-show="FormUpId.objective.$error.maxlength">*El campo debe tener entre 8 y 2000 caracteres</span>
                                        </div>
                                    </div>
                                    <div class="col-xs-3">
                                        <label class="control-label font-noraml">Tipo de
                                            auditor&iacute;a:</label>

                                        <div>
                                            <input type="hidden" value="{{vm.m.auditTypeId}}"
                                                   name="auditTypeId">
                                            <select class="form-control m-b"
                                                    ng-required="true"
                                                    ng-change="vm.m.auditTypeId = vm.m.auditType.id;"
                                                    ng-options="c.name for c in vm.lstAuditTypes"
                                                    ng-model="vm.m.auditType"></select>
                                            <span class="error" ng-show="FormUpId.auditType.$error.required">*Campo requerido</span>
                                        </div>
                                    </div>
                                    <div class="col-xs-3">
                                        <label class="control-label font-noraml">A&ntilde;o fiscalizado</label>

                                        <div>
                                            <div>
                                                <p class="input-group">
                                                    <input type="text" class="form-control" name="auditedYear"
                                                           uib-datepicker-popup="yyyy/MM/dd"
                                                           ng-model="vm.m.auditedYear"
                                                           is-open="vm.m.auditedYearIsOpened"
                                                           ng-required="true"
                                                           current-text="Hoy"
                                                           clear-text="Limpiar"
                                                           close-text="Cerrar"
                                                           datepicker-options="vm.reviewRangeOptionsYear"
                                                           readonly/>
                                                  <span class="input-group-btn">
                                                    <button type="button" class="btn btn-default"
                                                            ng-click="vm.m.auditedYearIsOpened=true;"><i
                                                            class="glyphicon glyphicon-calendar"></i></button>
                                                  </span>
                                                </p>
                                                    <span class="error"
                                                          ng-show="FormUpId.auditedYear.$error.required">*Campo requerido</span>
                                            <span class="error"
                                                  ng-show="FormUpId.auditedYear.$invalid && !FormUpId.auditedYear.$pristine">*La fecha debe tener el formato aaaa/mm/dd</span>

                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>


                        <div class="row">
                            <div class="col-xs-12">
                                <div class="col-xs-12">
                                    <label class="control-label font-noraml">Periodo de la revisi&oacute;n:</label>
                                </div>
                                <div class="col-xs-12">
                                    <div class="col-xs-3">
                                        <label class="font-noraml">Inicio</label>

                                        <div>
                                            <p class="input-group">
                                                <input type="text" class="form-control" name="reviewInitDate"
                                                       uib-datepicker-popup="yyyy/MM/dd"
                                                       ng-model="vm.m.reviewInitDate"
                                                       is-open="vm.m.reviewInitDateIsOpened"
                                                       ng-required="true"
                                                       current-text="Hoy"
                                                       clear-text="Limpiar"
                                                       close-text="Cerrar"
                                                       datepicker-options="vm.reviewRangeOptionsMonth"
                                                       readonly/>
                                                  <span class="input-group-btn">
                                                    <button type="button" class="btn btn-default"
                                                            ng-click="vm.m.reviewInitDateIsOpened=true;"><i
                                                            class="glyphicon glyphicon-calendar"></i></button>
                                                  </span>
                                            </p>
                                                    <span class="error"
                                                          ng-show="FormUpId.reviewInitDate.$error.required">*Campo requerido</span>
                                            <span class="error"
                                                  ng-show="FormUpId.reviewInitDate.$invalid && !FormUpId.reviewInitDate.$pristine">*La fecha debe tener el formato aaaa/mm/dd</span>

                                        </div>
                                    </div>


                                    <div class="col-xs-3">
                                        <label class="font-noraml">Fin</label>

                                        <div>
                                            <p class="input-group">
                                                <input type="text" class="form-control" name="reviewEndDate"
                                                       uib-datepicker-popup="yyyy/MM/dd"
                                                       ng-model="vm.m.reviewEndDate"
                                                       is-open="vm.m.reviewEndDateIsOpened"
                                                       ng-required="true"
                                                       current-text="Hoy"
                                                       clear-text="Limpiar"
                                                       close-text="Cerrar"
                                                       datepicker-options="vm.reviewRangeOptionsMonth"
                                                       readonly/>
                                                  <span class="input-group-btn">
                                                    <button type="button" class="btn btn-default"
                                                            ng-click="vm.m.reviewEndDateIsOpened=true;"><i
                                                            class="glyphicon glyphicon-calendar"></i></button>
                                                  </span>
                                            </p>
                                                    <span class="error"
                                                          ng-show="FormUpId.reviewEndDate.$error.required">*Campo requerido</span>
                                            <span class="error"
                                                  ng-show="FormUpId.reviewEndDate.$invalid && !FormUpId.reviewEndDate.$pristine">*La fecha debe tener el formato aaaa/mm/dd</span>

                                        </div>
                                    </div>

                                    <div class="col-xs-6 form-group">
                                        <div class="col-xs-12">
                                            <label class="control-label font-noraml">Programa
                                                presupuestario:</label>

                                            <div>
                                                <input type="text" name="budgetProgram"
                                                       ng-model="vm.m.budgetProgram"
                                                       placeholder="Ingrese el objetivo de la auditor&iacute;a"
                                                       minlength="8"
                                                       maxlength="2000"
                                                       ng-required="true" class="form-control">
                                        <span class="error"
                                              ng-show="FormUpId.budgetProgram.$error.required">*Campo requerido</span>
                                                    <span class="error"
                                                          ng-show="FormUpId.budgetProgram.$error.minlength">*El campo debe tener entre 8 y 2000 caracteres</span>
                                                    <span class="error"
                                                          ng-show="FormUpId.budgetProgram.$error.maxlength">*El campo debe tener entre 8 y 2000 caracteres</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <div class="row">
                            <div class="col-xs-12">

                                <div class="col-xs-6" ng-if="vm.m.role==='<%=Constants.ROLE_DGPOP%>'">
                                    <input type="hidden" name="auditedEntityId" value="{{vm.m.auditedEntityId}}">
                                    <label class="control-label font-noraml">Ente fiscalizado:</label>
                                    <input type="text" ng-model="vm.m.auditedEntity"
                                           placeholder="Escriba el nombre del ente fiscalizado o del responsable..."
                                           uib-typeahead="entity.desc for entity in vm.getAuditedEntities($viewValue)"
                                           typeahead-on-select="vm.m.auditedEntityId = $item.id;"
                                           typeahead-loading="vm.loadingAuditedEntities"
                                           typeahead-no-results="vm.noResultsAuditedEntities"
                                           typeahead-min-length="1"
                                           class="form-control">
                                    <i class="col-xs-1" ng-show="vm.loadingAuditedEntities"
                                       class="glyphicon glyphicon-refresh"></i>

                                    <div ng-show="vm.noResultsAuditedEntities">
                                        <i class="glyphicon glyphicon-remove"></i> No se encontraron
                                        resultados.
                                    </div>
                                </div>

                                <div class="col-xs-6" ng-if="vm.m.role==='<%=Constants.ROLE_LINK%>'">
                                    <label class="control-label font-noraml">Ente fiscalizado:</label>
                                    <input type="hidden" name="auditedEntityId" value="{{vm.m.auditedEntity.id}}">
                                    <input type="text" readonly ng-model="vm.m.auditedEntity.name"
                                           class="form-control">
                                </div>

                                <div class="col-xs-6">
                                    <div>
                                        <label class="control-label font-noraml">&Aacute;rea que atiende:</label>
                                        <input type="text" ng-model="vm.m.areaSel"
                                               placeholder="Escriba el nombre del &aacute;rea o del responsable..."
                                               uib-typeahead="area.desc for area in vm.getAreas($viewValue)"
                                               typeahead-on-select="vm.pushArea($item);"
                                               typeahead-loading="vm.loadingAreas"
                                               typeahead-no-results="vm.noResultsAreas"
                                               typeahead-min-length="1"
                                               class="form-control">
                                        <i class="col-xs-1" ng-show="vm.loadingAreas"
                                           class="glyphicon glyphicon-refresh"></i>

                                        <div ng-show="vm.noResultsAreas">
                                            <i class="glyphicon glyphicon-remove"></i> No se encontraron
                                            resultados.
                                        </div>
                                    </div>

                                    <div class="ibox float-e-margins col-xs-8 col-xs-offset-2">
                                        <div class="ibox-title">
                                            <h5>&Aacute;reas seleccionadas</h5>
                                        </div>
                                        <div class="ibox-content no-padding">
                                            <input type="hidden" value="{{vm.lstSelectedAreas}}"
                                                   name="lstSelectedAreas">

                                            <ul class="list-group">
                                                <li class="list-group-item"
                                                    ng-if="!vm.lstSelectedAreas.length>0">
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
                        </div>
                    </form>
                </div>
            </div>

            <div class="col-xs-12">
                <div ng-show="up.MsgError" ng-bind-html="up.MsgError" class="alert alert-error element-center">
                </div>
            </div>

            <button class="btn btn-primary " ng-disabled="up.WaitFor==true"
                    ng-click="vm.validateAudit() == true ? up.submit('#FormUpId', '<c:url value='/audit/doUpsert.json' />', FormUpId.$valid):''">
                Guardar
            </button>

        </div>
    </div>
</div>