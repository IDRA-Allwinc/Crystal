<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">

    function commentFormatter(value, row, index) {
        var arr = [];
        if (row.attended == true)
            arr = [
                '<button class="btn btn-primary dim act-view-docs-comment btn-tiny" data-toggle="tooltip" data-placement="top" title="Visualizar documentos de la observaci&oacute;n" type="button"><i class="fa fa-copy"></i></button>',
                '<button class="btn btn-info dim act-attention-comment btn-tiny" data-toggle="tooltip" data-placement="top" title="Visualizar informaci&oacute;n de la atenci&oacute;n de la observaci&oacute;n" type="button"><i class="fa fa-eye"></i></button>',
                '<button class="btn btn-warning dim act-extension-comment btn-tiny" data-toggle="tooltip" data-placement="top" title="Pr&oacute;rrogas" type="button"><i class="fa fa-clock-o"></i></button>'
            ];
        else
            arr = [
                '<button class="btn btn-success dim act-edit-comment btn-tiny" data-toggle="tooltip" data-placement="top" title="Editar la informaci&oacute;n de la observaci&oacute;n" type="button"><i class="fa fa-edit"></i></button>',
                '<button class="btn btn-danger dim act-delete-comment btn-tiny" data-toggle="tooltip" data-placement="top" title="Eliminar la observaci&oacute;n" type="button"><i class="fa fa-times-circle"></i></button>',
                '<button class="btn btn-primary dim act-view-docs-comment btn-tiny" data-toggle="tooltip" data-placement="top" title="Visualizar documentos de la observaci&oacute;n" type="button"><i class="fa fa-copy"></i></button>',
                '<button class="btn btn-info dim act-attention-comment btn-tiny" data-toggle="tooltip" data-placement="top" title="Indicar atenci&oacute;n de la observaci&oacute;n" type="button"><i class="fa fa-thumbs-up"></i></button>',
                '<button class="btn btn-warning dim act-extension-comment btn-tiny" data-toggle="tooltip" data-placement="top" title="Pr&oacute;rrogas" type="button"><i class="fa fa-clock-o"></i></button>',
                '<button class="btn btn-info dim act-replicate-comment btn-tiny" data-toggle="tooltip" data-placement="top" title="Replicar como" type="button"><i class="fa fa-hand-o-right"></i></button>'
            ];

        return arr.join('');
    }

    function uploadCommentFileFormatter(value, row, index) {
        var arr = [];
        arr.push('<button class="btn btn-primary dim act-download btn-tiny" data-toggle="tooltip" data-placement="top" title="Descargar documento" type="button"><i class="fa fa-download"></i></button>');

        if (row.isAttended !== true) {
            arr.push('<button class="btn btn-danger dim act-upf-delete btn-tiny" data-toggle="tooltip" data-placement="top" title="Eliminar documento" type="button"><i class="fa fa-times-circle"></i></button>');
        }

        return arr.join('');
    }

    window.upsertComment = function (id) {
        var params;
        if (id != undefined)
            params = {auditId: ${auditId}, id: id};
        else
            params = {auditId: ${auditId}};
        window.showUpsertParams(params, "#angJsjqGridIdComment", "<c:url value='/audit/comment/upsert.json' />", "#tblGridComment");
    };

    window.attentionComment = function (idComment) {
        window.showUpsert(idComment, "#angJsjqGridIdComment", "<c:url value='/audit/comment/attention.json' />", "#tblGridComment");
    };

    window.extensionComment = function (idComment) {
        window.showUpsert(idComment, "#angJsjqGridIdComment", "<c:url value='/audit/comment/extension.json' />", "#tblGridComment");
    };


    window.actionCommentEvents = {
        'click .act-edit-comment': function (e, value, row) {
            window.upsertComment(row.id);
        },
        'click .act-attention-comment': function (e, value, row) {
            window.attentionComment(row.id);
        },
        'click .act-delete-comment': function (e, value, row) {
            window.showObsolete(row.id, "#angJsjqGridIdComment", "<c:url value='/audit/comment/doObsolete.json' />", "#tblGridComment");
        },
        'click .act-view-docs-comment': function (e, value, row) {
            window.showUpsert(row.id, "#angJsjqGridIdComment", "<c:url value='/audit/comment/upsertViewDocs.json' />");
        },
        'click .act-upf-delete': function (e, value, row) {
            window.showObsoleteParam({
                commentId: row.commentId,
                upfileId: row.id
            }, "#angJsjqGridIdComment", "<c:url value='/audit/comment/doDeleteUpFile.json' />", "#tblUfCommentGrid");
        },
        'click .act-download': function (e, value, row) {
            var params = [];
            params["idParam"] = row.id;
            window.goToNewWnd("<c:url value='/shared/uploadFileGeneric/downloadFile.html?id=idParam' />", params);
        },
        'click .act-replicate-comment': function (e, value, row) {
            window.showUpsert(row.id, "#angJsjqGridIdComment", "<c:url value='/audit/comment/replicate.json' />", "#tblGridComment");
        },
        'click .act-extension-comment': function (e, value, row) {
            window.extensionComment(row.id);
        }
    };
</script>

<div class="col-xs-12">
    <div class="row animated fadeIn" id="angJsjqGridIdComment" data-ng-controller="modalDlgController as vm">

        <div class="col-xs-12">

            <div class="row">
                <div class="col-xs-12">
                    <div class="col-xs-12 ibox-title navy-bg">
                        <span class="label-icon pull-left"><i
                                class="fa fa-eye i-big"></i></span>
                        <h5>&nbsp;&nbsp;Observaciones</h5>
                    </div>

                    <div class="col-xs-12">
                        <div class="space-5"></div>
                        <div id="toolbarComment">
                            <button class="btn btn-success" onclick=" window.upsertComment() ">
                                <i class="fa fa-plus"></i> Agregar observaci&oacute;n
                            </button>
                        </div>

                        <table id="tblGridComment"
                               data-toggle="table"
                               data-url="<c:url value='/audit/comment/list.json?id=${auditId}'/>"
                               data-height="auto"
                               data-side-pagination="server"
                               data-pagination="true"
                               data-page-list="[5, 10, 20, 50, All]"
                               data-search="true"
                               data-sort-name="number"
                               data-toolbar="#toolbarComment"
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
                                <th data-field="id" data-visible="false" data-card-visible="false" data-switchable="false">Identificador
                                </th>
                                <th data-field="number" data-align="center" data-sortable="true">N&uacute;mero
                                </th>
                                <th data-field="description" data-align="center" data-sortable="true">Descripci&oacute;n
                                </th>
                                <th data-field="endDate" data-align="center" data-sortable="true">Fecha l&iacute;mite
                                </th>
                                <th data-field="Actions" data-formatter="commentFormatter" data-align="center"
                                    data-width="250px" data-events="actionCommentEvents">Acci&oacute;n
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