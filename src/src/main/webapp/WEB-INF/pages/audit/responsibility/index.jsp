<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">

    function responsibilityFormatter(value, row, index) {
        var arr = [];
        if (row.attended == true)
            arr = [
                '<button class="btn btn-primary dim act-view-docs-responsibility btn-tiny" data-toggle="tooltip" data-placement="top" title="Visualizar documentos de la promoci&oacute;n" type="button"><i class="fa fa-copy"></i></button>',
                '<button class="btn btn-info dim act-attention-responsibility btn-tiny" data-toggle="tooltip" data-placement="top" title="Visualizar informaci&oacute;n de la atenci&oacute;n de la observaci&oacute;n" type="button"><i class="fa fa-eye"></i></button>'
            ];
        else
            arr = [
                '<button class="btn btn-success dim act-edit-responsibility btn-tiny" data-toggle="tooltip" data-placement="top" title="Editar la informaci&oacute;n de la promoci&oacute;n" type="button"><i class="fa fa-edit"></i></button>',
                '<button class="btn btn-danger dim act-delete-responsibility btn-tiny" data-toggle="tooltip" data-placement="top" title="Eliminar el requerimiento" type="button"><i class="fa fa-times-circle"></i></button>',
                '<button class="btn btn-primary dim act-view-docs-responsibility btn-tiny" data-toggle="tooltip" data-placement="top" title="Visualizar documentos de la promoci&oacute;n" type="button"><i class="fa fa-copy"></i></button>',
                '<button class="btn btn-info dim act-attention-responsibility btn-tiny" data-toggle="tooltip" data-placement="top" title="Indicar atenci&oacute;n de la promoci&oacute;n" type="button"><i class="fa fa-thumbs-up"></i></button>'
            ];

        return arr.join('');
    }

    function uploadResponsibilityFileFormatter(value, row, index) {
        var arr = [];
        arr.push('<button class="btn btn-primary dim act-download btn-tiny" data-toggle="tooltip" data-placement="top" title="Descargar documento" type="button"><i class="fa fa-download"></i></button>');

        if (row.isAttended !== true)
            arr.push('<button class="btn btn-danger dim act-upf-delete btn-tiny" data-toggle="tooltip" data-placement="top" title="Eliminar documento" type="button"><i class="fa fa-times-circle"></i></button>');

        return arr.join('');
    }

    window.upsertResponsibility = function (id) {
        var params;
        if (id != undefined)
            params = {auditId: ${auditId}, id: id};
        else
            params = {auditId: ${auditId}};
        window.showUpsertParams(params, "#angJsjqGridIdResponsibility", "<c:url value='/audit/responsibility/upsert.json' />", "#tblGridResponsibility");
    };

    window.attentionResponsibility = function (idRequest) {
        window.showUpsert(idRequest, "#angJsjqGridIdResponsibility", "<c:url value='/audit/responsibility/attention.json' />", "#tblGridResponsibility");
    };

    window.actionResponsibilityEvents = {
        'click .act-edit-responsibility': function (e, value, row) {
            window.upsertResponsibility(row.id);
        },
        'click .act-attention-responsibility': function (e, value, row) {
            window.attentionResponsibility(row.id);
        },
        'click .act-delete-responsibility': function (e, value, row) {
            window.showObsolete(row.id, "#angJsjqGridIdResponsibility", "<c:url value='/audit/responsibility/doObsolete.json' />", "#tblGridResponsibility");
        },
        'click .act-view-docs-responsibility': function (e, value, row) {
            window.showUpsert(row.id, "#angJsjqGridIdResponsibility", "<c:url value='/audit/responsibility/upsertViewDocs.json' />");
        },
        'click .act-upf-delete': function (e, value, row) {
            window.showObsoleteParam({
                responsibilityId: row.responsibilityId,
                upfileId: row.id
            }, "#angJsjqGridIdResponsibility", "<c:url value='/audit/responsibility/doDeleteUpFile.json' />", "#tblUfResponsibilityGrid");
        },
        'click .act-download': function (e, value, row) {
            var params = [];
            params["idParam"] = row.id;
            window.goToNewWnd("<c:url value='/shared/uploadFileGeneric/downloadFile.html?id=idParam' />", params);
        }
    };
</script>

<div class="col-xs-12">
    <div class="row animated fadeIn" id="angJsjqGridIdResponsibility" data-ng-controller="modalDlgController as vm">

        <div class="col-xs-12">

            <div class="row">
                <div class="col-xs-12">
                    <div class="col-xs-12 ibox-title navy-bg">
                        <span class="label-icon pull-left"><i
                                class="fa fa-files-o i-big"></i></span>
                        <h5>&nbsp;&nbsp;Promociones de responsabilidad</h5>
                    </div>

                    <div class="col-xs-12">
                        <div class="space-5"></div>
                        <div id="toolbarResponsibility">
                            <button class="btn btn-success" onclick=" window.upsertResponsibility() ">
                                <i class="fa fa-plus"></i> Agregar promoci&oacute;n
                            </button>
                        </div>

                        <table id="tblGridResponsibility"
                               data-toggle="table"
                               data-url="<c:url value='/audit/responsibility/list.json?id=${auditId}'/>"
                               data-height="auto"
                               data-side-pagination="server"
                               data-pagination="true"
                               data-page-list="[5, 10, 20, 50, All]"
                               data-search="true"
                               data-sort-name="number"
                               data-toolbar="#toolbarResponsibility"
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
                                <th data-field="Actions" data-formatter="responsibilityFormatter" data-align="center"
                                    data-width="250px" data-events="actionResponsibilityEvents">Acci&oacute;n
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