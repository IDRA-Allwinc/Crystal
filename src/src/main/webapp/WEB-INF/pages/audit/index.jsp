<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<HTML lang="es-ES">
<html>
<head>
    <%@ include file="/WEB-INF/pages/shared/headTb.jsp" %>
    <link href="${pageContext.request.contextPath}/assets/content/ui-bootstrap-custom-1.1.0-csp.css" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath}/assets/scripts/client-app/ctrl/audit/audit.ctrl.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/angular-bootstrap/ui-bootstrap-custom-1.1.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/scripts/angular-bootstrap/ui-bootstrap-custom-tpls-1.1.0.min.js"></script>


    <script type="text/javascript">
        var $table = $('#tblGrid');

        $(function () {
            $table.bootstrapTable();
        });

        function actionsFormatter(value, row, index) {
            return [
                '<button class="btn btn-success dim act-audit-track btn-tiny" data-toggle="tooltip" data-placement="top" title="Seguimiento de auditor&iacute;a" type="button"><i class="fa fa-arrow-circle-o-right"></i></button>',
                '<button class="btn btn-danger dim act-delete btn-tiny" data-toggle="tooltip" data-placement="top" title="Eliminar auditor&iacute;a" type="button"><i class="fa fa-times-circle"></i></button>'
            ].join('');
        }
        ;

        window.upsert = function (id) {
            window.showUpsert(id, "#angJsjqGridId", "<c:url value='/audit/upsert.json' />", "#tblGrid");
        };

        window.actionEvents = {
            'click .act-audit-track': function (e, value, row) {
                window.goToUrlMvcUrl("<c:url value='/audit/doObsolete.json' />", {id: row.id});
            },
            'click .act-delete': function (e, value, row) {
                window.showObsolete(row.id, "#angJsjqGridId", "<c:url value='/audit/doObsolete.json' />", "#tblGrid");
            }
        };
    </script>

</head>
<body scroll="no" ng-app="crystal" class="pace-done">
<div id="wrapper">
    <div data-ng-controller="menuController as mn" data-ng-init="mn.menu=3;">
        <%@ include file="/WEB-INF/pages/shared/menu.jsp" %>
    </div>
    <div id="page-wrapper" class="gray-bg">
        <%@ include file="/WEB-INF/pages/shared/header-bar.jsp" %>

        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-xs-12">
                <h2>Auditor&iacute;as</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="">Auditor&iacute;as</a>
                    </li>
                </ol>
                <br/>

                <div class="alert alert-info alert-10">
                    <i class="fa fa-lightbulb-o fa-lg"></i> &nbsp En esta secci&oacute;n puede registrar y administrar auditor&iacute;as.
                </div>
            </div>
        </div>

        <div class="wrapper wrapper-content animated fadeInRight" id="angJsjqGridId"
             data-ng-controller="modalDlgController as vm">
            <div class="row">
                <div class="col-xs-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <span class="label label-success pull-right">Auditor&iacute;as</span>

                            <h2 class="text-navy">
                                <i class="fa fa-users"></i> &nbsp; Administraci&oacute;n de auditor&iacute;as
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
                                   data-url="<c:url value='/audit/list.json' />"
                                   data-height="auto"
                                   data-side-pagination="server"
                                   data-pagination="true"
                                   data-page-list="[5, 10, 20, 50, 100, 200]"
                                   data-search="true"
                                   data-sort-name="auditedYear"
                                   data-toolbar="#toolbar"
                                   data-show-refresh="true"
                                   data-show-toggle="true"
                                   data-show-columns="true"
                                   data-show-export="true"
                                   data-single-select="true"
                                   data-show-footer="true"
                                   data-id-field="id">
                                <thead>
                                <tr>
                                    <th data-field="id" data-visible="false" data-card-visible="false"
                                        data-switchable="false">Identificador
                                    </th>
                                    <th data-field="auditedYear" data-align="center" data-sortable="true"
                                        data-filter-control="input">A&ntilde;o fiscalizado
                                    </th>
                                    <th data-field="letterNumber" data-align="center" data-sortable="true">N&uacute;mero de oficio
                                    </th>
                                    <th data-field="number" data-align="center" data-sortable="true">N&uacute;mero de auditor&iacute;a</th>
                                    <th data-field="name" data-align="center" data-sortable="true">Nombre de N&uacute;mero de auditor&iacute;a</th>
                                    <th data-field="auditTypeStr" data-align="center" data-sortable="true">Tipo de auditor&iacute;a</th>
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
                <%@ include file="/WEB-INF/pages/shared/footer.jsp" %>
            </div>
        </div>
    </div>
</div>
</body>
</html>

