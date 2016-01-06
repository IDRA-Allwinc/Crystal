<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<HTML lang="es-ES">
<html>
<head>
    <%@ include file="/WEB-INF/pages/shared/headTb.jsp" %>
    <script src="${pageContext.request.contextPath}/assets/scripts/client-app/ctrl/management/area.ctrl.js"></script>

    <script type="text/javascript">
        window.upsert = function (id) {
            window.showUpsert(id, "#angJsjqGridId", '<c:url value='/management/area/upsert.html' />', "#tblGrid");
        };
    </script>

    <script type="text/javascript">
        var $table = $('#tblGrid');

        $(function () {
            $table.bootstrapTable();
        });

        function actionsFormatter(value, row, index) {
            return [
                '<button class="btn btn-success dim act-edit btn-tiny" data-toggle="tooltip" data-placement="top" title="Editar informaci&oacute;n del ente fiscalizado" type="button"><i class="fa fa-edit"></i></button>',
                '<button class="btn btn-danger dim act-delete btn-tiny" data-toggle="tooltip" data-placement="top" title="Eliminar ente fiscalizado" type="button"><i class="fa fa-times-circle"></i></button>'
            ].join('');
        }

        window.upsert = function (id) {
            window.showUpsert(id, "#angJsjqGridId", "<c:url value='/shared/area/upsert.json' />", "#tblGrid");
        };

        window.actionEvents = {

            'click .act-edit': function (e, value, row) {
                window.upsert(row.id);
            },
            'click .act-delete': function (e, value, row) {
                window.showObsolete(row.id, "#angJsjqGridId", "<c:url value='/shared/area/doObsolete.json' />", "#tblGrid");
            }
        };
    </script>

</head>
<body scroll="no" ng-app="crystal" class="pace-done">
<div id="wrapper">
    <div data-ng-controller="menuController as mn" data-ng-init="mn.menu=1; mn.subMenu=1002;">
        <%@ include file="/WEB-INF/pages/shared/menu.jsp" %>
    </div>
    <div id="page-wrapper" class="gray-bg">
        <%@ include file="/WEB-INF/pages/shared/header-bar.jsp" %>

        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-xs-12">
                <h2>&Aacute;reas</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="">&Aacute;reas</a>
                    </li>
                </ol>
                <br />
                <div class="alert alert-info alert-10">
                    <i class="fa fa-lightbulb-o fa-lg"></i> &nbsp En esta secci&oacute;n puede administrar las &aacute;reas que participan en el proceso de audito&iacute;a.
                </div>
            </div>
        </div>

        <div class="wrapper wrapper-content animated fadeInRight" id="angJsjqGridId" data-ng-controller="modalDlgController as vm">
            <div class="row">
                <div class="col-xs-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <span class="label label-success pull-right">Entes fiscalizados</span>
                            <h2 class="text-navy">
                                <i class="fa fa-sitemap"></i> &nbsp; Administraci&oacute;n de &aacute;reas
                            </h2>
                        </div>
                        <div class="ibox-content">
                            <div id="toolbar">
                                <button class="btn btn-success" onclick=" window.upsert() ">
                                    <i class="fa fa-plus"></i> Agregar
                                </button>
                            </div>
                            <table id="tblGrid"
                                   data-toggle="table"
                                   data-url="<c:url` value='/shared/area/list.json' />"
                                   data-height="auto"
                                   data-side-pagination="server"
                                   data-pagination="true"
                                   data-page-list="[5, 10, 20, 50, 100, 200]"
                                   data-search="true"
                                   data-sort-name="name"
                                   data-toolbar="#toolbar"
                                   data-show-refresh="true"
                                   data-show-toggle="true"
                                   data-show-columns="true"
                                   data-show-export="true"
                                   data-single-select="true"
                                   data-show-footer="true"
                                   data-id-field="Id">
                                <thead>
                                <tr>
                                    <th data-field="id" data-visible="false" data-card-visible="false" data-switchable="false">Identificador</th>
                                    <th data-field="name" data-align="center" data-sortable="true"  >Nombre</th>
                                    <th data-field="responsible" data-align="center" data-sortable="true" >Responsable</th>
                                    <th data-field="phone" data-align="center" data-sortable="true">Tel&eacute;fono</th>
                                    <th data-field="email" data-align="center" data-sortable="true">Email</th>
                                    <th data-field="auditedEntityName" data-align="center" data-sortable="true">Pertenece a</th>
                                    <th data-field="Actions" data-formatter="actionsFormatter" data-align="center" data-width="200px" data-events="actionEvents">Acci&oacute;n</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </div>
            </div>



            <div class="blocker" ng-show="vm.working">
                <div>
                    Procesando...<img src="${pageContext.request.contextPath}/assets/img/ajax_loader.gif" alt="" />
                </div>
            </div>
        </div>

        <hr />
        <div id="dlgUpsert"></div>
        <div class="row">
            <div class="col-xs-8 col-xs-offset-2">
                <%@ include file="/WEB-INF/pages/shared/sharedSvc.jsp" %>
                <%@ include file="/WEB-INF/pages/shared/footer.jsp" %>
            </div>
        </div>
    </div>
</div>
</body>
</html>