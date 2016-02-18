<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $(function () {
        $('#tblGrid').on("expand-row.bs.table", function (index, row, $detail, container) {
            $.get("<c:url value='/audit/request/list.json' />", {idLetter: $detail.id}).done(function (data) {
                var $t = container.html('<table></table>').find('table');
                $t.bootstrapTable({
                    rowStyle: rowStyle,
                    columns: [{field: "id", title: "", visible: false},
                        {field: "number", title: "Numeral", align: "center"},
                        {field: "description", title: "Descripci&oacute;n", align: "center"},
                        {field: "deadLine", title: "Fecha l&iacute;mite", align: "center"},
                        {
                            field: "action",
                            title: "Acci&oacute;n",
                            align: "center",
                            formatter: requestFormatter,
                            events: window.actionEvents
                        }
                    ],
                    data: data.rows
                });
            });
        });
    });

    function requestFormatter(value, row, index) {
        var arr = [];
        if (row.attended == true)
            arr = [
                '<button class="btn btn-primary dim act-view-docs btn-tiny" data-toggle="tooltip" data-placement="top" title="Visualizar documentos del requerimiento" type="button"><i class="fa fa-copy"></i></button>',
                '<button class="btn btn-info dim act-attention-req btn-tiny" data-toggle="tooltip" data-placement="top" title="Visualizar informaci&oacute;n de la atenci&oacute;n del requerimiento" type="button"><i class="fa fa-eye"></i></button>'
            ];
        else
            arr = [
                '<button class="btn btn-success dim act-edit-req btn-tiny" data-toggle="tooltip" data-placement="top" title="Editar la informaci&oacute;n del requerimiento" type="button"><i class="fa fa-edit"></i></button>',
                '<button class="btn btn-danger dim act-delete-req btn-tiny" data-toggle="tooltip" data-placement="top" title="Eliminar el requerimiento" type="button"><i class="fa fa-times-circle"></i></button>',
                '<button class="btn btn-primary dim act-view-docs btn-tiny" data-toggle="tooltip" data-placement="top" title="Visualizar documentos del requerimiento" type="button"><i class="fa fa-copy"></i></button>',
                '<button class="btn btn-info dim act-attention-req btn-tiny" data-toggle="tooltip" data-placement="top" title="Indicar atenci&oacute;n del requerimiento" type="button"><i class="fa fa-thumbs-up"></i></button>'
            ];

        return arr.join('');
    }

    function actionsFormatterLetterAudit(value, row, index) {
        var arr = [];
        if (row.attended == true)
            arr = [
                '<button class="btn btn-primary dim act-download-by-letter-id btn-tiny" data-toggle="tooltip" data-placement="top" title="Descargar documento asociado al oficio" type="button"><i class="fa fa-download"></i></button>',
                '<button class="btn btn-primary dim act-view-docs-letter btn-tiny" data-toggle="tooltip" data-placement="top" title="Visualizar documentos adicionales" type="button"><i class="fa fa-copy"></i></button>',
                '<button class="btn btn-warning dim act-attention-letter btn-tiny" data-toggle="tooltip" data-placement="top" title="Visualizar informaci&oacute;n de la atenci&oacute;n del oficio" type="button"><i class="fa fa-eye"></i></button>'
            ];
        else
            arr = [
                '<button class="btn btn-success dim act-edit btn-tiny" data-toggle="tooltip" data-placement="top" title="Editar la informaci&oacute;n del oficio" type="button"><i class="fa fa-edit"></i></button>',
                '<button class="btn btn-danger dim act-delete btn-tiny" data-toggle="tooltip" data-placement="top" title="Eliminar el oficio" type="button"><i class="fa fa-times-circle"></i></button>',
                '<button class="btn btn-primary dim act-download-by-letter-id btn-tiny" data-toggle="tooltip" data-placement="top" title="Descargar documento asociado al oficio" type="button"><i class="fa fa-download"></i></button>',
                '<button class="btn btn-primary dim act-view-docs-letter btn-tiny" data-toggle="tooltip" data-placement="top" title="Visualizar documentos adicionales" type="button"><i class="fa fa-copy"></i></button>',
                '<button class="btn btn-warning dim act-attention-letter btn-tiny" data-toggle="tooltip" data-placement="top" title="Indicar atenci&oacute;n de oficio" type="button"><i class="fa fa-thumbs-up"></i></button>',
                '<button class="btn btn-info dim act-add-req btn-tiny" data-toggle="tooltip" data-placement="top" title="Agregar requerimiento al oficio" type="button"><i class="fa fa-plus-circle"></i></button>'
            ];

        return arr.join('');
    }

    function actionsUploadFileFormatter(value, row, index) {
        var arr = [];
        arr.push('<button class="btn btn-primary dim act-download btn-tiny" data-toggle="tooltip" data-placement="top" title="Descargar documento" type="button"><i class="fa fa-download"></i></button>');

        if (row.isAttended !== true)
            arr.push('<button class="btn btn-danger dim act-upf-delete btn-tiny" data-toggle="tooltip" data-placement="top" title="Eliminar documento" type="button"><i class="fa fa-times-circle"></i></button>');

        return arr.join('');
    }

    window.upsertLetter = function (id) {
        var params;
        if (id != undefined)
            params = {auditId: ${auditId}, id: id};
        else
            params = {auditId: ${auditId}};
        window.showUpsertParams(params, "#angJsjqGridIdLetter", "<c:url value='/audit/letter/upsert.json' />", "#tblGrid");
    };

    window.upsertReq = function (idLetter, idRequest) {
        var params;
        if (idRequest != undefined)
            params = {idLetter: idLetter, idRequest: idRequest};
        else
            params = {idLetter: idLetter};
        window.showUpsertParams(params, "#angJsjqGridIdLetter", "<c:url value='/audit/request/upsert.json' />", "#tblGrid");
    };

    window.attentionReq = function (idRequest) {
        window.showUpsert(idRequest, "#angJsjqGridIdLetter", "<c:url value='/audit/request/attention.json' />", "#tblGrid");
    };

    window.attentionLetter = function (idLetter) {
        window.showUpsert(idLetter, "#angJsjqGridIdLetter", "<c:url value='/audit/letter/attention.json' />", "#tblGrid");
    };

    window.actionEvents = {
        'click .act-edit': function (e, value, row) {
            window.upsertLetter(row.id);
        },
        'click .act-download-by-letter-id': function (e, value, row) {
            var params = [];
            params["idParam"] = row.id;
            window.goToNewWnd("<c:url value='/audit/letter/downloadFile.html?id=idParam' />", params);
        },
        'click .act-attention-letter': function (e, value, row) {
            window.attentionLetter(row.id);
        },
        'click .act-delete': function (e, value, row) {
            window.showObsolete(row.id, "#angJsjqGridIdLetter", "<c:url value='/audit/letter/doObsolete.json' />", "#tblGrid");
        },
        'click .act-add-req': function (e, value, row) {
            window.upsertReq(row.id);
        },
        'click .act-edit-req': function (e, value, row) {
            window.upsertReq(row.idLetter, row.id);
        },
        'click .act-delete-req': function (e, value, row) {
            window.showObsolete(row.id, "#angJsjqGridIdLetter", "<c:url value='/audit/request/doObsolete.json' />", "#tblGrid");
        },
        'click .act-view-docs': function (e, value, row) {
            window.showUpsert(row.id, "#angJsjqGridIdLetter", "<c:url value='/audit/request/upsertViewDocs.json' />");
        },
        'click .act-view-docs-letter': function (e, value, row) {
            window.showUpsert(row.id, "#angJsjqGridIdLetter", "<c:url value='/audit/letter/upsertViewDocs.json' />");
        },
        'click .act-attention-req': function (e, value, row) {
            window.attentionReq(row.id);
        },
        'click .act-upf-delete': function (e, value, row) {
            window.showObsoleteParam({
                requestId: row.requestId,
                upfileId: row.id
            }, "#angJsjqGridIdLetter", "<c:url value='/audit/request/doDeleteUpFile.json' />", "#tblUfRequestAuditGrid");
        },
        'click .act-download': function (e, value, row) {
            var params = [];
            params["idParam"] = row.id;
            window.goToNewWnd("<c:url value='/shared/uploadFileGeneric/downloadFile.html?id=idParam' />", params);
        }
    };
</script>

<div class="col-xs-12">
    <div class="row animated fadeIn" id="angJsjqGridIdLetterLetter" data-ng-controller="modalDlgController as vm">

        <div class="col-xs-12">

            <div class="row">
                <div class="col-xs-12">
                    <div class="col-xs-12 ibox-title navy-bg">
                        <span class="label-icon pull-left"><i
                                class="fa fa-files-o i-big"></i></span>
                        <h5>&nbsp;&nbsp;Oficios</h5>
                    </div>

                    <div class="col-xs-12">
                        <div class="space-5"></div>
                        <div id="toolbarLetter">
                            <button class="btn btn-success" onclick=" window.upsertLetter() " data-toggle="tooltip"
                                    data-placement="top" title="Agregar oficio nuevoooo">
                                <i class="fa fa-plus"></i> Agregar oficio
                            </button>
                        </div>

                        <table id="tblGrid" 1
                               data-toggle="table"
                               data-url="<c:url value='/audit/letter/list.json?id=${auditId}'/>"
                               data-height="auto"
                               data-side-pagination="server"
                               data-pagination="true"
                               data-page-list="[5, 10, 20, 50, All]"
                               data-search="true"
                               data-sort-name="name"
                               data-toolbar="#toolbarLetter"
                               data-show-refresh="true"
                               data-show-toggle="true"
                               data-show-columns="true"
                               data-show-export="true"
                               data-single-select="true"
                               data-show-footer="true"
                               data-row-style="rowStyle"
                               data-detail-view="true"
                               data-id-field="id">
                            <thead>
                            <tr>
                                <th data-field="id" data-visible="false">Identificador
                                </th>
                                <th data-field="name" data-align="center" data-sortable="true">Oficio
                                </th>
                                <th data-field="description" data-align="center" data-sortable="true">Descripci&oacute;n
                                </th>
                                <th data-field="Actions" data-formatter="actionsFormatterLetterAudit" data-align="center"
                                    data-width="250px" data-events="actionEvents">Acci&oacute;n
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

