(function () {
    "use strict";
    angular
        .module(window.constMainApp)
        .controller('menuController', menuController);

    menuController.$inject = ["$interval", "$timeout", "connSvc", "sharedSvc"];

    function menuController($interval, $timeout, connSvc, sharedSvc) {
        var vm = this;
        vm.m = {};
        vm.svc = connSvc;
        vm.checkUrl = "";
        vm.logoutUrl = "";
        vm.extendUrl = "";

        vm.startTimer = startTimer;
        vm.stopTimer = stopTimer;
        vm.timer = null;

        vm.getElapsedTime = getElapsedTime;
        vm.init = init;
        vm.tokenCSRF = document.getElementById("tokenCSRF");

        //intervalo de repeticion en milisengundos
        vm.interval = 30000;

        function startTimer() {
            vm.timer = $interval(vm.getElapsedTime, vm.interval);
        };

        function stopTimer() {
            if (angular.isDefined(vm.timer)) {
                $interval.cancel(vm.timer);
            }
        };

        function getElapsedTime() {
            var data = vm.tokenCSRF.name + "=" + vm.tokenCSRF.value;
            vm.svc.post(vm.checkUrl, vm, data, true).then(function (res) {

                if (res.hasToLogout == true) {
                    window.location.replace(vm.logoutUrl);
                }

                if (parseInt(res.returnData) > 0 && sharedSvc.showingSessionDlg == false) {
                    var cfg = {title: 'Continuar sesi&oacute;n.', message: '', type: 'warning'};
                    sharedSvc.showCountdown(cfg);
                }
            });
        };

        function init() {
            vm.startTimer();
            sharedSvc.logoutUrl = vm.logoutUrl;
            sharedSvc.extendUrl = vm.extendUrl;
        };

        $timeout(vm.init, 500);
    }
})();