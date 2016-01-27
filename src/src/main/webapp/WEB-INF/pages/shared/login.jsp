<script src="${pageContext.request.contextPath}/assets/scripts/client-app/ctrl/login.ctrl.js"></script>
<div data-ng-controller="loginController as vm">
    <div class="blocker" data-ng-show="vm.working">
        <div>
            Procesando...<img src="${pageContext.request.contextPath}/assets/img/ajax_loader.gif" />
        </div>
    </div>
    <div class="middle-box text-center loginscreen animated fadeInDown">
        <div>
            <h1 class="text-white">CR</h1>
        </div>
        <h3 class="text-white">CRYSTAL</h3>
        <p class="text-white">Ingrese sus credenciales</p>
        <form id="loginForm" name="loginForm" class="m-t" role="form">
            <input type="hidden" id="tokenCSRF" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="row">
                <div class="col-xs-12">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                        <input name="username" class="form-control" id="username" type="text" placeholder="Usuario"
                               data-ng-model="vm.m.username">
                    </div>
                </div>
            </div>
            <br />
            <div class="row">
                <div class="col-xs-12">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                        <input name="password" class="form-control" id="password" type="password" placeholder="Contrase&ntilde;a"
                               data-ng-model="vm.m.password">
                    </div>
                </div>
            </div>
            <br />
            <div class="row" data-ng-show="vm.m.username && vm.m.password">
                <div class="col-xs-12">
                    <button data-ng-click="vm.login('#loginForm', 'Error de red. Intente m&aacute;s tarde', '<c:url value="/login" />')"
                            data-ng-disabled="vm.working"
                            class="btn btn-info block full-width m-l-b">Ingresar</button>
                </div>
            </div>
            <br />
            <br />
            <div class="alert alert-danger" ng-show="vm.msgErr">
                {{vm.msgErr}}
            </div>
            <br />
            <br />
            <br />

        </form>
        <p class="m-t text-white"> <small>Allwinc &copy; 2016</small> </p>
    </div>
</div>
