<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">

    function recommendationFormatter(value, row, index) {
        var arr = [];
        if (row.attended == true)
            arr = [
                '<button class="btn btn-primary dim act-view-docs-recommendation btn-tiny" data-toggle="tooltip" data-placement="top" title="Visualizar documentos de la recomendaci&oacute;n" type="button"><i class="fa fa-copy"></i></button>',
                '<button class="btn btn-info dim act-attention-recommendation btn-tiny" data-toggle="tooltip" data-placement="top" title="Visualizar informaci&oacute;n de la atenci&oacute;n de la recomendaci&oacute;n" type="button"><i class="fa fa-eye"></i></button>',
                '<button class="btn btn-warning dim act-extension-recommendation btn-tiny" data-toggle="tooltip" data-placement="top" title="Prorrogas" type="button"><i class="fa fa-clock-o"></i></button>'
            ];
        else
            arr = [
                '<button class="btn btn-success dim act-edit-recommendation btn-tiny" data-toggle="tooltip" data-placement="top" title="Editar la informaci&oacute;n de la recomendaci&oacute;n" type="button"><i class="fa fa-edit"></i></button>',
                '<button class="btn btn-danger dim act-delete-recommendation btn-tiny" data-toggle="tooltip" data-placement="top" title="Eliminar la recomendaci&oacute;n" type="button"><i class="fa fa-times-circle"></i></button>',
                '<button class="btn btn-primary dim act-view-docs-recommendation btn-tiny" data-toggle="tooltip" data-placement="top" title="Visualizar documentos de la recomendaci&oacute;n" type="button"><i class="fa fa-copy"></i></button>',
                '<button class="btn btn-warning dim act-extension-recommendation btn-tiny" data-toggle="tooltip" data-placement="top" title="Prorrogas" type="button"><i class="fa fa-clock-o"></i></button>',
                '<button class="btn btn-info dim act-attention-recommendation btn-tiny" data-toggle="tooltip" data-placement="top" title="Indicar atenci&oacute;n de la recomendaci&oacute;n" type="button"><i class="fa fa-thumbs-up"></i></button>',
                '<button class="btn btn-info dim act-replicate-recommendation btn-tiny" data-toggle="tooltip" data-placement="top" title="Replicar como" type="button"><i class="fa fa-hand-o-right"></i></button>'
            ];

        return arr.join('');
    }

    function uploadRecommendationFileFormatter(value, row, index) {
        var arr = [];
        arr.push('<button class="btn btn-primary dim act-download btn-tiny" data-toggle="tooltip" data-placement="top" title="Descargar documento" type="button"><i class="fa fa-download"></i></button>');

        if (row.isAttended !== true)
            arr.push('<button class="btn btn-danger dim act-upf-delete btn-tiny" data-toggle="tooltip" data-placement="top" title="Eliminar prorroga" type="button"><i class="fa fa-times-circle"></i></button>');

        return arr.join('');
    }

    window.upsertRecommendation = function (id) {
        var params;
        if (id != undefined)
            params = {auditId: ${auditId}, id: id};
        else
            params = {auditId: ${auditId}};
        window.showUpsertParams(params, "#angJsjqGridIdRecommendation", "<c:url value='/audit/recommendation/upsert.json' />", "#tblGridRecommendation");
    };

    window.attentionRecommendation = function (idRequest) {
        window.showUpsert(idRequest, "#angJsjqGridIdRecommendation", "<c:url value='/audit/recommendation/attention.json' />", "#tblGridRecommendation");
    };

    window.extensionRecommendation = function (idRequest) {
        window.showUpsert(idRequest, "#angJsjqGridIdComment", "<c:url value='/audit/recommendation/extension.json' />", "#tblGridComment");
    };

    window.actionRecommendationEvents = {
        'click .act-edit-recommendation': function (e, value, row) {
            window.upsertRecommendation(row.id);
        },
        'click .act-attention-recommendation': function (e, value, row) {
            window.attentionRecommendation(row.id);
        },
        'click .act-delete-recommendation': function (e, value, row) {
            window.showObsolete(row.id, "#angJsjqGridIdRecommendation", "<c:url value='/audit/recommendation/doObsolete.json' />", "#tblGridRecommendation");
        },

        'click .act-view-docs-recommendation': function (e, value, row) {
            window.showUpsert(row.id, "#angJsjqGridIdRecommendation", "<c:url value='/audit/recommendation/upsertViewDocs.json' />");
        },
        'click .act-upf-delete': function (e, value, row) {
            window.showObsoleteParam({
                recommendationId: row.recommendationId,
                upfileId: row.id
            }, "#angJsjqGridIdRecommendation", "<c:url value='/audit/recommendation/doDeleteUpFile.json' />", "#tblUfRecommendationGrid");
        },
        'click .act-download': function (e, value, row) {
            var params = [];
            params["idParam"] = row.id;
            window.goToNewWnd("<c:url value='/shared/uploadFileGeneric/downloadFile.html?id=idParam' />", params);
        },
        'click .act-replicate-recommendation': function (e, value, row) {
            window.showUpsert(row.id, "#angJsjqGridIdRecommendation", "<c:url value='/audit/recommendation/replicate.json' />", "#tblGridRecommendation");
        },
        'click .act-extension-recommendation': function (e, value, row) {
            window.extensionRecommendation(row.id);
        }
    };


</script>

<div class="col-xs-12">
    <div class="row animated fadeIn" id="angJsjqGridIdRecommendation" data-ng-controller="modalDlgController as vm">

        <div class="col-xs-12">

            <div class="row">
                <div class="col-xs-12">
                    <div class="col-xs-12 ibox-title navy-bg">
                        <span class="label-icon pull-left"><i
                                class="fa fa-hand-o-right i-big"></i></span>
                        <h5>&nbsp;&nbsp;Recomendaciones</h5>
                    </div>

                    <div class="col-xs-12">
                        <div class="space-5"></div>
                        <div id="toolbarRecommendation">
                            <button class="btn btn-success" onclick=" window.upsertRecommendation() ">
                                <i class="fa fa-plus"></i> Agregar recomendaci&oacute;n
                            </button>
                        </div>

                        <table id="tblGridRecommendation"
                               data-toggle="table"
                               data-url="<c:url value='/audit/recommendation/list.json?id=${auditId}'/>"
                               data-height="auto"
                               data-side-pagination="server"
                               data-pagination="true"
                               data-page-list="[5, 10, 20, 50, All]"
                               data-search="true"
                               data-sort-name="number"
                               data-toolbar="#toolbarRecommendation"
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
                                <th data-field="Actions" data-formatter="recommendationFormatter" data-align="center"
                                    data-width="250px" data-events="actionRecommendationEvents">Acci&oacute;n
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

