<div id="ProcessingDlgId" class="modal fade" ng-controller="processingController as vm" data-backdrop="static"
     data-keyboard="false" ng-cloak>
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <div class="alert alert-info"><h4>Procesando</h4></div>
                <br/>
                <progressbar class="progress-striped active" value="count" type="info"></progressbar>
            </div>
        </div>
    </div>
</div>


<div id="MessageBoxDlgId" class="modal fade" ng-controller="messageController as vm" data-backdrop="static" ng-cloak>
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="alert alert-{{vm.type=='primary'?'info':vm.type}}">
                    <button type="button" class="close" ng-click="vm.ok()">&times;</button>
                    <h4 class="modal-title element-center" ng-bind-html="vm.title"></h4>
                </div>
            </div>
            <div class="modal-body">
                <div class="element-center" ng-bind-html="vm.message"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default btn-{{vm.type}}" ng-click="vm.ok()">Aceptar</button>
            </div>
        </div>
    </div>
</div>


<div id="ConfirmationDlgId" class="modal fade" ng-controller="confirmationController as vm" data-backdrop="static"
     ng-cloak>
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="alert alert-{{vm.type=='primary'?'info':vm.type}}">
                    <button type="button" class="close" ng-click="vm.no()">&times;</button>
                    <%--<h4 class="modal-title element-center" ng-bind-html="vm.title"></h4>--%>
                    <div class="element-center" ng-bind-html="vm.title"></div>
                </div>
            </div>
            <div class="modal-body">
                <div class="element-center" ng-bind-html="vm.message"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default btn-{{vm.type}}" ng-click="vm.yes()">Si</button>
                <button type="button" class="btn btn-default" ng-click="vm.no()">No</button>
            </div>
        </div>
    </div>
</div>

<div id="CountDownDlgId" class="modal fade" ng-controller="countdownController as vm" data-backdrop="static" ng-cloak>
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="alert alert-{{vm.type=='primary'?'info':vm.type}}">
                    <h4 class="modal-title element-center" ng-bind-html="vm.title"></h4>
                </div>
            </div>
            <div class="modal-body">
                <div class="element-center" ng-bind-html="vm.message"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default btn-{{vm.type}}" ng-click="vm.yes()">Continuar</button>
                <button type="button" class="btn btn-default" ng-click="vm.no()">No</button>
            </div>
        </div>
    </div>
</div>