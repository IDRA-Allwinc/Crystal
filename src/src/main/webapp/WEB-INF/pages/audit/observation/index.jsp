<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">

    function observationFormatter(value, row, index) {
        var arr = [];
        if (row.attended == true)
            arr = [
                '<button class="btn btn-primary dim act-view-docs-observation btn-tiny" data-toggle="tooltip" data-placement="top" title="Visualizar documentos del pliego" type="button"><i class="fa fa-copy"></i></button>',
                '<button class="btn btn-info dim act-attention-observation btn-tiny" data-toggle="tooltip" data-placement="top" title="Visualizar informaci&oacute;n de la atenci&oacute;n de la observaci&oacute;n" type="button"><i class="fa fa-eye"></i></button>',
                '<button class="btn btn-warning dim act-extension-observation btn-tiny" data-toggle="tooltip" data-placement="top" title="Prorrogas" type="button"><i class="fa fa-clock-o"></i></button>'
            ];
        else
            arr = [
                '<button class="btn btn-success dim act-edit-observation btn-tiny" data-toggle="tooltip" data-placement="top" title="Editar la informaci&oacute;n del pliego" type="button"><i class="fa fa-edit"></i></button>',
                '<button class="btn btn-danger dim act-delete-observation btn-tiny" data-toggle="tooltip" data-placement="top" title="Eliminar el requerimiento" type="button"><i class="fa fa-times-circle"></i></button>',
                '<button class="btn btn-primary dim act-view-docs-observation btn-tiny" data-toggle="tooltip" data-placement="top" title="Visualizar documentos del pliego" type="button"><i class="fa fa-copy"></i></button>',
                '<button class="btn btn-warning dim act-extension-observation btn-tiny" data-toggle="tooltip" data-placement="top" title="Prorrogas" type="button"><i class="fa fa-clock-o"></i></button>',
                '<button class="btn btn-info dim act-attention-observation btn-tiny" data-toggle="tooltip" data-placement="top" title="Indicar atenci&oacute;n del pliego" type="button"><i class="fa fa-thumbs-up"></i></button>'
            ];

        return arr.join('');
    }

    function uploadObservationFileFormatter(value, row, index) {
        var arr = [];
        arr.push('<button class="btn btn-primary dim act-download btn-tiny" data-toggle="tooltip" data-placement="top" title="Descargar documento" type="button"><i class="fa fa-download"></i></button>');

        if (row.isAttended !== true)
            arr.push('<button class="btn btn-danger dim act-upf-delete btn-tiny" data-toggle="tooltip" data-placement="top" title="Eliminar documento" type="button"><i class="fa fa-times-circle"></i></button>');

        return arr.join('');
    }

    window.upsertObservation = function (id) {
        var params;
        if (id != undefined)
            params = {auditId: ${auditId}, id: id};
        else
            params = {auditId: ${auditId}};
        window.showUpsertParams(params, "#angJsjqGridIdObservation", "<c:url value='/audit/observation/upsert.json' />", "#tblGridObservation");
    };

    window.attentionObservation = function (idObservation) {
        window.showUpsert(idObservation, "#angJsjqGridIdObservation", "<c:url value='/audit/observation/attention.json' />", "#tblGridObservation");
    };

    window.extensionObservation = function (idObservation) {
        window.showUpsert(idObservation, "#angJsjqGridIdComment", "<c:url value='/audit/observation/extension.json' />", "#tblGridComment");
    };

    window.actionObservationEvents = {
        'click .act-edit-observation': function (e, value, row) {
            window.upsertObservation(row.id);
        },
        'click .act-attention-observation': function (e, value, row) {
            window.attentionObservation(row.id);
        },
        'click .act-delete-observation': function (e, value, row) {
            window.showObsolete(row.id, "#angJsjqGridIdObservation", "<c:url value='/audit/observation/doObsolete.json' />", "#tblGridObservation");
        },
        'click .act-view-docs-observation': function (e, value, row) {
            window.showUpsert(row.id, "#angJsjqGridIdObservation", "<c:url value='/audit/observation/upsertViewDocs.json' />");
        },
        'click .act-upf-delete': function (e, value, row) {
            window.showObsoleteParam({
                observationId: row.observationId,
                upfileId: row.id
            }, "#angJsjqGridIdObservation", "<c:url value='/audit/observation/doDeleteUpFile.json' />", "#tblUfObservationGrid");
        },
        'click .act-download': function (e, value, row) {
            var params = [];
            params["idParam"] = row.id;
            window.goToNewWnd("<c:url value='/shared/uploadFileGeneric/downloadFile.html?id=idParam' />", params);
        },
        'click .act-extension-observation': function (e, value, row) {
            window.extensionObservation(row.id);
        }
    };
</script>

<div class="col-xs-12">
    <div class="row animated fadeIn" id="angJsjqGridIdObservation" data-ng-controller="modalDlgController as vm">

        <div class="col-xs-12">

            <div class="row">
                <div class="col-xs-12">
                    <div class="col-xs-12 ibox-title navy-bg">
                        <span class="label-icon pull-left"><i
                                class="fa fa-files-o i-big"></i></span>
                        <h5>&nbsp;&nbsp;Pliego de observaciones</h5>
                    </div>

                    <div class="col-xs-12">
                        <div class="space-5"></div>
                        <div id="toolbarObservation">
                            <button class="btn btn-success" onclick=" window.upsertObservation() ">
                                <i class="fa fa-plus"></i> Agregar pliego de observaciones
                            </button>
                        </div>

                        <table id="tblGridObservation"
                               data-toggle="table"
                               data-url="<c:url value='/audit/observation/list.json?id=${auditId}'/>"
                               data-height="auto"
                               data-side-pagination="server"
                               data-pagination="true"
                               data-page-list="[5, 10, 20, 50, All]"
                               data-search="true"
                               data-sort-name="number"
                               data-toolbar="#toolbarObservation"
                               data-show-refresh="true"
                               data-show-toggle="true"
                               data-show-columns="true"
                               data-show-export="true"
                               data-single-select="true"
                               data-show-footer="true"
                               data-row-style="rowStyle"
                               data-id-field="id">
                            <thead>
                            <tr>
                                <th data-field="id" data-visible="false">Identificador
                                </th>
                                <th data-field="number" data-align="center" data-sortable="true">N&uacute;mero
                                </th>
                                <th data-field="description" data-align="center" data-sortable="true">Descripci&oacute;n
                                </th>
                                <th data-field="endDate" data-align="center" data-sortable="true">Fecha l&iacute;mite
                                </th>
                                <th data-field="Actions" data-formatter="observationFormatter" data-align="center"
                                    data-width="250px" data-events="actionObservationEvents">Acci&oacute;n
                                </th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </div>

                <div class="blocker" ng-show="vm.working">
                    <div>
                        Procesando...<img src="${pageContext.request.contextPath}/assets/img/ajax_loader.gif" alt=""/>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>