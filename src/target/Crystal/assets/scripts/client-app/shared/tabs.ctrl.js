window.angJsDependencies.push('ui.bootstrap');

(function () {
    "use strict";
    angular
        .module(window.constMainApp)
        .controller('tabsController', tabsController);


    function tabsController() {
        var vm = this;
    }
})();
