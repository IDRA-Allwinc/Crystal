(function () {
    "use strict";
    angular
        .module(window.constMainApp)
        .controller('auditTypeController', auditTypeController);

    function auditTypeController() {
        var vm = this;
        vm.m = {};
        vm.init = init;

        function init(){

        }
    }
})();
