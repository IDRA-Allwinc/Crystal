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

        }
    }
})();
