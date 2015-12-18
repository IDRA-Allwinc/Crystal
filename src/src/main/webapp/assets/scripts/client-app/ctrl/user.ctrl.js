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
            vm.m.Role = window.initCatalog(vm.Roles, vm.m.roleId);
        }
    }
})();
