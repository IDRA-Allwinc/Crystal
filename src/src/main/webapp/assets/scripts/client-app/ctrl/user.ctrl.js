(function () {
    "use strict";
    angular
        .module(window.constMainApp)
        .controller('userController', userController);

    function userController() {
        var vm = this;
        vm.m = {};
        vm.init = init;

        function init(){
            vm.m.role = window.initCatalog(vm.lstRoles, vm.m.roleId);
            vm.m.roleId = vm.m.role.id;
            vm.m.auditedEntity = window.initCatalog(vm.lstAuditedEntities, vm.m.auditedEntityId);
            vm.m.auditedEntityId = vm.m.auditedEntity.id;
        }
    }
})();
