(function () {
    "use strict";
    angular
        .module(window.constMainApp)
        .controller('letterController', letterController);

    function letterController() {
        var vm = this;
        vm.m = {};
    }
})();
