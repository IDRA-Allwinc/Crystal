<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">

    function notificationFormatter(value, row, index) {
        var arr = [];
            arr = [
                '<button class="btn btn-success dim act-edit-notification btn-tiny" data-toggle="tooltip" data-placement="top" title="Editar destinatarios de la notificaci&oacute;n" type="button"><i class="fa fa-edit"></i></button>',
                '<button class="btn btn-info dim act-resend-notification btn-tiny" data-toggle="tooltip" data-placement="top" title="Reenviar correo de notificaci&oacute;n" type="button"><i class="fa fa-share-square"></i></button>',
                '<button class="btn btn-danger dim act-delete-notification btn-tiny" data-toggle="tooltip" data-placement="top" title="Eliminar la notificaci&oacute;n" type="button"><i class="fa fa-times-circle"></i></button>'
            ];

        return arr.join('');
    }

    window.upsertNotification = function (id) {
        var params;
        if (id != undefined)
            params = {auditId: ${auditId}, id: id};
        else
            params = {auditId: ${auditId}};
        window.showUpsertParams(params, "#angJsjqGridIdNotification", "<c:url value='/audit/notification/upsert.json' />", "#tblGridNotification");
    };

    window.actionNotificationEvents = {
        'click .act-edit-notification': function (e, value, row) {
            window.upsertNotification(row.id);
        },
        'click .act-resend-notification': function (e, value, row) {
            window.showConfirmService(row.id, "#angJsjqGridIdNotification", "<c:url value='/audit/notification/resend.json' />", "Reenviar notificaci&oacute;n", "&iquest;Desea reenviar la notificaci&oacute;n a los destinatarios que tiene seleccionados?", "#tblGridNotification");
        },
        'click .act-delete-notification': function (e, value, row) {
            window.showObsolete(row.id, "#angJsjqGridIdNotification", "<c:url value='/audit/notification/doObsolete.json' />", "#tblGridNotification");
        }
    };


</script>

<div class="col-xs-12">
    <div class="row animated fadeIn" id="angJsjqGridIdNotification" data-ng-controller="modalDlgController as vm">

        <div class="col-xs-12">

            <div class="row">
                <div class="col-xs-12">
                    <div class="col-xs-12 ibox-title navy-bg">
                        <span class="label-icon pull-left"><i
                                class="fa fa-comments-o i-big"></i></span>
                        <h5>&nbsp;&nbsp;Notificaciones</h5>
                    </div>

                    <div class="col-xs-12">
                        <div class="space-5"></div>
                        <div id="toolbarNotification">
                            <button class="btn btn-success" onclick="window.upsertNotification()" data-toggle="tooltip"
                                    data-placement="top" title="Agregar nueva notificaci&oacute;n">
                                <i class="fa fa-plus"></i> Agregar notificaci&oacute;n
                            </button>
                        </div>

                        <table id="tblGridNotification"
                               data-toggle="table"
                               data-url="<c:url value='/audit/notification/list.json?id=${auditId}'/>"
                               data-height="auto"
                               data-side-pagination="server"
                               data-pagination="true"
                               data-page-list="[5, 10, 20, 50, All]"
                               data-search="true"
                               data-sort-name="id"
                               data-toolbar="#toolbarNotification"
                               data-show-refresh="true"
                               data-show-toggle="true"
                               data-show-columns="true"
                               data-show-export="true"
                               data-single-select="true"
                               data-show-footer="true"
                               data-id-field="id">
                            <thead>
                            <tr>
                                <th data-field="id" data-visible="false" data-card-visible="false" data-switchable="false">Identificador</th>
                                <th data-field="auditId" data-visible="false" data-card-visible="false" data-switchable="false">Identificador auditor&iacute;a</th>
                                <th data-field="title" data-align="center" class="col-xs-2" data-sortable="true">Asunto</th>
                                <th data-field="message" data-align="center" class="col-xs-6" data-sortable="true">Mensaje</th>
                                <th data-field="calIns" class="col-xs-2" data-align="center" data-sortable="true">Fecha</th>
                                <th data-field="Actions" data-formatter="notificationFormatter" data-align="center" data-width="250px" data-events="actionNotificationEvents">Acci&oacute;n</th>
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

