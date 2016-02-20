<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<HTML lang="es-ES">
<html>
<head>
    <%@ include file="/WEB-INF/pages/shared/headTb.jsp" %>
    <link href="${pageContext.request.contextPath}/assets/content/upload/jquery.fileupload.css" rel="stylesheet"
          type="text/css">
    <link href="${pageContext.request.contextPath}/assets/content/ui-bootstrap-custom-1.1.0-csp.css" rel="stylesheet"
          type="text/css">
    <script src="${pageContext.request.contextPath}/assets/scripts/upload/vendor/jquery.ui.widget.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/upload/jquery.iframe-transport.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/upload/jquery.fileupload.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/client-app/ctrl/audit/letter/letter.ctrl.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/angular-bootstrap/ui-bootstrap-custom-1.1.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/angular-bootstrap/ui-bootstrap-custom-tpls-1.1.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/client-app/ctrl/audit/request/request.ctrl.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/client-app/ctrl/audit/request/requestViewDocs.ctrl.js"></script>

    <script type="text/javascript">
        $(function () {
            $('#tblGrid').on("expand-row.bs.table", function (index, row, $detail, container) {
                $.get("<c:url value='/previousRequest/request/list.json' />", {idLetter: $detail.id}).done(function (data) {
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

        function actionsFormatter(value, row, index) {
            return [
                '<button class="btn btn-success dim act-edit btn-tiny" data-toggle="tooltip" data-placement="top" title="Editar la informaci&oacute;n del oficio" type="button"><i class="fa fa-edit"></i></button>',
                '<button class="btn btn-danger dim act-delete btn-tiny" data-toggle="tooltip" data-placement="top" title="Eliminar el oficio" type="button"><i class="fa fa-times-circle"></i></button>',
                '<button class="btn btn-primary dim act-download-by-letter-id btn-tiny" data-toggle="tooltip" data-placement="top" title="Descargar documento asociado al oficio" type="button"><i class="fa fa-download"></i></button>',
                '<button class="btn btn-info dim act-add-req btn-tiny" data-toggle="tooltip" data-placement="top" title="Agregar requerimiento al oficio" type="button"><i class="fa fa-plus-circle"></i></button>'
            ].join('');
        }

        function actionsUploadFileFormatter(value, row, index) {
            var arr = [];
            arr.push('<button class="btn btn-primary dim act-download btn-tiny" data-toggle="tooltip" data-placement="top" title="Descargar documento" type="button"><i class="fa fa-download"></i></button>');

            if (row.isAttended !== true)
                arr.push('<button class="btn btn-danger dim act-upf-delete btn-tiny" data-toggle="tooltip" data-placement="top" title="Eliminar documento" type="button"><i class="fa fa-times-circle"></i></button>');

            return arr.join('');
        }

        window.upsertLetter = function (id) {
            window.showUpsert(id, "#angJsjqGridId", "<c:url value='/previousRequest/letter/upsert.json' />", "#tblGrid");
        };

        window.upsertReq = function (idLetter, idRequest) {
            var params;
            if (idRequest != undefined)
                params = {idLetter: idLetter, idRequest: idRequest};
            else
                params = {idLetter: idLetter};
            window.showUpsertParams(params, "#angJsjqGridId", "<c:url value='/previousRequest/request/upsert.json' />", "#tblGrid");
        };

        window.attentionReq = function (idRequest) {
            window.showUpsert(idRequest, "#angJsjqGridId", "<c:url value='/previousRequest/request/attention.json' />", "#tblGrid");
        };

        window.actionEvents = {
            'click .act-edit': function (e, value, row) {
                window.upsertLetter(row.id);
            },
            'click .act-download-by-letter-id': function (e, value, row) {
                var params = [];
                params["idParam"] = row.id;
                window.goToNewWnd("<c:url value='/previousRequest/letter/downloadFile.html?id=idParam' />", params);
            },
            'click .act-delete': function (e, value, row) {
                window.showObsolete(row.id, "#angJsjqGridId", "<c:url value='/previousRequest/letter/doObsolete.json' />", "#tblGrid");
            },
            'click .act-add-req': function (e, value, row) {
                window.upsertReq(row.id);
            },
            'click .act-edit-req': function (e, value, row) {
                window.upsertReq(row.idLetter, row.id);
            },
            'click .act-delete-req': function (e, value, row) {
                window.showObsolete(row.id, "#angJsjqGridId", "<c:url value='/previousRequest/request/doObsolete.json' />", "#tblGrid");
            },
            'click .act-view-docs': function (e, value, row) {
                window.showUpsert(row.id, "#angJsjqGridId", "<c:url value='/previousRequest/request/upsertViewDocs.json' />");
            },
            'click .act-attention-req': function (e, value, row) {
                window.attentionReq(row.id);
            },
            'click .act-upf-delete': function (e, value, row) {
                window.showObsoleteParam({requestId: row.requestId, upfileId: row.id}, "#angJsjqGridId", "<c:url value='/previousRequest/request/doDeleteUpFile.json' />", "#tblUfGrid");
            },
            'click .act-download': function (e, value, row) {
                var params = [];
                params["idParam"] = row.id;
                window.goToNewWnd("<c:url value='/shared/uploadFileGeneric/downloadFile.html?id=idParam' />", params);
            }
        };
    </script>

</head>
<body scroll="no" ng-app="crystal" class="pace-done">
<div id="wrapper">
    <div data-ng-controller="menuController as mn" data-ng-init="mn.menu=2">
        <%@ include file="/WEB-INF/pages/shared/menu.jsp" %>
    </div>
    <div id="page-wrapper" class="gray-bg">
        <%@ include file="/WEB-INF/pages/shared/header-bar.jsp" %>

        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-xs-12">
                <h2>Auditor&iacute;a</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="">Requerimientos Previos</a>
                    </li>
                </ol>
                <br/>

                <div class="alert alert-info alert-10">
                    <i class="fa fa-lightbulb-o fa-lg"></i> &nbsp En esta secci&oacute;n puede administrar los
                    requerimientos previos de las auditor&iacute;as.
                </div>
            </div>
        </div>

        <div class="wrapper wrapper-content animated fadeInRight" id="angJsjqGridId"
             data-ng-controller="modalDlgController as vm">
            <div class="row">
                <div class="col-xs-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <span class="label label-success pull-right">Requerimientos Previos</span>

                            <h2 class="text-navy">
                                <i class="fa fa-list"></i> &nbsp; Administraci&oacute;n de Requerimientos Previos
                            </h2>
                        </div>
                        <div class="ibox-content">
                            <div id="toolbar">
                                <button class="btn btn-success" onclick=" window.upsertLetter() " data-toggle="tooltip"
                                        data-placement="top" title="Agregar oficio nuevo">
                                    <i class="fa fa-plus"></i> Agregar oficio
                                </button>
                            </div>
                            <table id="tblGrid"
                                   data-toggle="table"
                                   data-url="<c:url value='/previousRequest/letter/list.json' />"
                                   data-height="auto"
                                   data-side-pagination="server"
                                   data-pagination="true"
                                   data-page-list="[5, 10, 20, 50, All]"
                                   data-search="true"
                                   data-sort-name="name"
                                   data-toolbar="#toolbar"
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
                                    <th data-field="Actions" data-formatter="actionsFormatter" data-align="center"
                                        data-width="200px" data-events="actionEvents">Acci&oacute;n
                                    </th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="blocker" ng-show="vm.working">
                <div>
                    Procesando...<img src="${pageContext.request.contextPath}/assets/img/ajax_loader.gif" alt=""/>
                </div>
            </div>
        </div>

        <hr/>
        <div id="dlgUpsert"></div>
        <div class="row">
            <div class="col-xs-12">
                <%@ include file="/WEB-INF/pages/shared/sharedSvc.jsp" %>
            </div>
        </div>
    </div>
</div>
</body>
</html>