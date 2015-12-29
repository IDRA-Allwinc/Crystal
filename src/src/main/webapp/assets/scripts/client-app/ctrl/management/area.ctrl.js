(function () {
    "use strict";
    angular
        .module(window.constMainApp)
        .controller('areaController', areaController);

    function areaController() {
        var vm = this;
        vm.m = {};
        vm.init = init;

        function init(){
            vm.m.auditedEntity = window.initCatalog(vm.lstAuditedEntity, vm.m.auditedEntityId);
            if(vm.m.auditedEntity!=undefined)
                vm.m.auditedEntityId = vm.m.auditedEntity.id;
        }
    }
})();
