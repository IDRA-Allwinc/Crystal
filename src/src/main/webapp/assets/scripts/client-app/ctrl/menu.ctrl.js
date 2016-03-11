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

        vm.getElapsedTime = getElapsedTime;
        vm.init = init;
        vm.checkSession = checkSession;

        //intervalo de repeticion en milisengundos
        vm.interval = 30000;

        function checkSession() {
            $timeout(function () {
                try {
                    vm.getElapsedTime();
                }
                catch (e) {

                }
            }, vm.interval).then(function(){
                vm.checkSession();
            });
        }

        function getElapsedTime() {

            if (sharedSvc.showingSessionDlg == false) {

                vm.svc.post(vm.checkUrl, vm, null, true).then(function (res) {

                    if (res.hasToLogout == true && window.top !== window.self && window.top.logout !== undefined) {
                        window.top.logout();
                        return;
                    }
                    else if (parseInt(res.returnData) > 0) {
                        var cfg = {title: 'Extender sesi&oacute;n', message: '', type: 'warning'};
                        sharedSvc.showCountdown(cfg);
                    }
                });

            }
        }

        function init() {
            vm.checkSession();
            sharedSvc.logoutUrl = vm.logoutUrl;
            sharedSvc.extendUrl = vm.extendUrl;
        }

        $timeout(vm.init, 500);
    }
})();