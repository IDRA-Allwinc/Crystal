<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">

    function eventFormatter(value, row, index) {
        var arr = [
                '<button class="btn btn-success dim act-edit-event btn-tiny" data-toggle="tooltip" data-placement="top" title="Visualizar la informaci&oacute;n del evento" type="button"><i class="fa fa-eye"></i></button>',
                '<button class="btn btn-danger dim act-delete-event btn-tiny" data-toggle="tooltip" data-placement="top" title="Eliminar el evento" type="button"><i class="fa fa-times-circle"></i></button>',
                '<button class="btn btn-primary dim act-view-docs-event btn-tiny" data-toggle="tooltip" data-placement="top" title="Visualizar documentos del evento" type="button"><i class="fa fa-copy"></i></button>',
            ];

        return arr.join('');
    }

    function uploadEventFileFormatter(value, row, index) {
        var arr = [];
        arr.push('<button class="btn btn-primary dim act-download btn-tiny" data-toggle="tooltip" data-placement="top" title="Descargar documento" type="button"><i class="fa fa-download"></i></button>');

        if (row.isAttended !== true)
            arr.push('<button class="btn btn-danger dim act-upf-delete btn-tiny" data-toggle="tooltip" data-placement="top" title="Eliminar documento" type="button"><i class="fa fa-times-circle"></i></button>');

        return arr.join('');
    }

    window.upsertEvent = function (id) {
        var params;
        if (id != undefined)
            params = {auditId: ${auditId}, id: id};
        else
            params = {auditId: ${auditId}};
        window.showUpsertParams(params, "#angJsjqGridIdEvent", "<c:url value='/audit/event/upsert.json' />", "#tblGridEvent");
    };

    window.attentionEvent = function (idRequest) {
        window.showUpsert(idRequest, "#angJsjqGridIdEvent", "<c:url value='/audit/event/attention.json' />", "#tblGridEvent");
    };

    window.actionEventEvents = {
        'click .act-edit-event': function (e, value, row) {
            window.upsertEvent(row.id);
        },
        'click .act-delete-event': function (e, value, row) {
            window.showObsolete(row.id, "#angJsjqGridIdEvent", "<c:url value='/audit/event/doObsolete.json' />", "#tblGridEvent");
        },
        'click .act-view-docs-event': function (e, value, row) {
            window.showUpsert(row.id, "#angJsjqGridIdEvent", "<c:url value='/audit/event/upsertViewDocs.json' />");
        },
        'click .act-upf-delete': function (e, value, row) {
            window.showObsoleteParam({
                eventId: row.eventId,
                upfileId: row.id
            }, "#angJsjqGridIdEvent", "<c:url value='/audit/event/doDeleteUpFile.json' />", "#tblUfEventGrid");
        },
        'click .act-download': function (e, value, row) {
            var params = [];
            params["idParam"] = row.id;
            window.goToNewWnd("<c:url value='/shared/uploadFileGeneric/downloadFile.html?id=idParam' />", params);
        }
    };
</script>

<div class="col-xs-12">
    <div class="row animated fadeIn" id="angJsjqGridIdEvent" data-ng-controller="modalDlgController as vm">

        <div class="col-xs-12">

            <div class="row">
                <div class="col-xs-12">
                    <div class="col-xs-12 ibox-title navy-bg">
                        <span class="label-icon pull-left"><i
                                class="fa fa-clock-o i-big"></i></span>
                        <h5>&nbsp;&nbsp;Eventos</h5>
                    </div>

                    <div class="col-xs-12">
                        <div class="space-5"></div>
                        <div id="toolbarEvent">
                            <button class="btn btn-success" onclick=" window.upsertEvent() ">
                                <i class="fa fa-plus"></i> Agregar evento
                            </button>
                        </div>

                        <table id="tblGridEvent"
                               data-toggle="table"
                               data-url="<c:url value='/audit/event/list.json?id=${auditId}'/>"
                               data-height="auto"
                               data-side-pagination="server"
                               data-pagination="true"
                               data-page-list="[5, 10, 20, 50, All]"
                               data-search="true"
                               data-sort-name="id"
                               data-toolbar="#toolbarEvent"
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
                                <th data-field="event" data-align="center" data-sortable="true">Evento
                                </th>
                                <th data-field="createDate" data-align="center" data-sortable="true">Fecha de registro
                                </th>
                                <th data-field="Actions" data-formatter="eventFormatter" data-align="center"
                                    data-width="250px" data-events="actionEventEvents">Acci&oacute;n
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