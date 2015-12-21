(function () {
    "use strict";
    angular
        .module(window.constMainApp)
        .controller('catalogController', catalogController);

    function catalogController() {
        var vm = this;
        vm.m = {};
    }
})();
