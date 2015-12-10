(function () {
    "use strict";
    angular
        .module(window.constMainApp)
        .controller('userController', userController);

    function userController() {
        var vm = this;
        vm.m = {};
        vm.init = init;
    }
})();
