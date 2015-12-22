(function () {
    "use strict";
    angular
        .module(window.constMainApp)
        .controller('supervisoryEntityController', supervisoryEntityController);

    function supervisoryEntityController() {
        var vm = this;
        vm.m = {};
        vm.init = init;

        function init(){

        }
    }
})();
