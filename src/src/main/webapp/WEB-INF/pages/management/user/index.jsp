<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<HTML lang="es-ES">
<html>
<head>
    <%@ include file="/WEB-INF/pages/shared/headTb.jsp" %>
    <script type="text/javascript">
        window.upsert = function (id) {
            window.showUpsert(id, "#angJsjqGridId", '<c:url value='/management/user/upsert.html' />', "#tblGrid");
        };

        /*var $table = $('#tblGrid');

        $(function () {
            $table.bootstrapTable();
        });

        function actionsFormatter(value, row, index) {
            return [
                '<button class="btn btn-info dim act-sendConfirm btn-tiny" data-toggle="tooltip" data-placement="top" title="Reenviar correo de confirmación" type="button"><i class="fa fa-mail-forward"></i></button>',
                '<button class="btn btn-success dim act-edit btn-tiny" data-toggle="tooltip" data-placement="top" title="Editar información del usuario" type="button"><i class="fa fa-edit"></i></button>',
                '<button class="btn btn-danger dim act-delete btn-tiny" data-toggle="tooltip" data-placement="top" title="Eliminar usuario" type="button"><i class="fa fa-times-circle"></i></button>'
            ].join('');
        }

        window.actionEvents = {

            'click .act-edit': function (e, value, row) {
                window.upsert(row.Id);
            },
            'click .act-sendConfirm': function (e, value, row) {
                window.showConfirmService(row.Id, "#angJsjqGridId", "",
                        "Reenviar correo de confirmación", "¿Desea reenviar el correo de confirmación para este usuario? <br/>Esto sólo es posible si el usuario " +
                "no ha activado su cuenta. <br/><br/><small>Nota: Esta acción genera una nueva contraseña para el usuario</small>", "#tblGrid");
            },
            'click .act-delete': function (e, value, row) {
                window.showObsolete(row.Id, "#angJsjqGridId", "", "#tblGrid");
            }
        };  */
    </script>

</head>
<body scroll="no" ng-app="crystal" class="pace-done">
    <div id="wrapper">
        <div data-ng-controller="menuController as mn" data-ng-init="mn.menu=1; mn.subMenu=1001;">
            <%@ include file="/WEB-INF/pages/shared/menu.jsp" %>
        </div>
        <div id="page-wrapper" class="gray-bg">
            <%@ include file="/WEB-INF/pages/shared/header-bar.jsp" %>

            <div class="row wrapper border-bottom white-bg page-heading">
                <div class="col-xs-12">
                    <h2>Usuarios</h2>
                    <ol class="breadcrumb">
                        <li>
                            <a href="">Inicio</a>
                        </li>
                        <li>
                            <a href="">Usuarios</a>
                        </li>
                    </ol>
                    <br />
                    <div class="alert alert-info alert-10">
                        <i class="fa fa-lightbulb-o fa-lg"></i> &nbsp En esta secci&oacute;n puedes dar de alta nuevos usuarios al sistema.
                    </div>
                </div>
            </div>

            <div class="wrapper wrapper-content animated fadeInRight" id="angJsjqGridId" data-ng-controller="modalDlgController as vm">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="ibox float-e-margins">
                            <div class="ibox-title">
                                <span class="label label-success pull-right">Usuarios</span>
                                <h2 class="text-navy">
                                    <i class="fa fa-users"></i> &nbsp; Administraci&oacute;n de usuarios
                                </h2>
                            </div>
                            <div class="ibox-content">
                                <div id="toolbar">
                                    <button class="btn btn-success" onclick=" window.upsert() ">
                                        <i class="fa fa-plus"></i> Agregar
                                    </button>
                                </div>
                                <%--<table id="tblGrid"--%>
                                       <%--data-url=""--%>
                                <%--data-height="auto"--%>
                                <%--data-side-pagination="server"--%>
                                <%--data-pagination="true"--%>
                                <%--data-page-list="[5, 10, 20, 50, 100, 200]"--%>
                                <%--data-search="true"--%>
                                <%--data-sort-name="UserName"--%>
                                <%--data-toolbar="#toolbar"--%>
                                <%--data-show-refresh="true"--%>
                                <%--data-show-toggle="true"--%>
                                <%--data-show-columns="true"--%>
                                <%--data-show-export="true"--%>
                                <%--data-single-select="true"--%>
                                <%--data-show-footer="true"--%>
                                <%--data-id-field="Id">--%>
                                <%--<thead>--%>
                                <%--<tr>--%>
                                    <%--<th data-field="Id" data-visible="false" data-card-visible="false" data-switchable="false">Identificador</th>--%>
                                    <%--<th data-field="UserName" data-align="center" data-sortable="true" data-filter-control="input" >Usuario</th>--%>
                                    <%--<th data-field="FullName" data-align="center" data-sortable="true" data-filter-control="input">Nombre completo</th>--%>
                                    <%--<th data-field="Email" data-align="center" data-sortable="true">Email</th>--%>
                                    <%--<th data-field="Role" data-align="center" data-sortable="true">Perfil</th>--%>
                                    <%--<th data-field="Actions" data-formatter="actionsFormatter" data-align="center" data-width="150px" data-events="actionEvents">Acción</th>--%>
                                <%--</tr>--%>
                                <%--</thead>--%>
                                <%--</table>--%>
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

