(function () {
    "use strict";
    angular
        .module(window.constMainApp)
        .controller('auditedEntityController', auditedEntityController);

    function auditedEntityController() {
        var vm = this;
        vm.m = {};
        vm.init = init;

        function init(){
            vm.m.auditedEntityType = window.initCatalog(vm.lstAuditedEntityTypes, vm.m.auditedEntityTypeId);
            debugger;
            //vm.m.auditedEntity = window.initCatalog(vm.lstAuditedEntities, vm.m.auditedEntityId);
        }
    }
})();
