(function () {
    "use strict";

    angular
        .module(window.constMainApp)
        .service("connSvc", connSvc);

    connSvc.$inject = ["$http", "$timeout", "$q"];

    function connSvc($http, $timeout, $q) {
        var vm = this;
        vm.m = {};
        vm.post = post;
        vm.handleSuccess = handleSuccess;
        vm.handleError = handleError;
        vm.hideMsgErr = hideMsgErr;

        function post(urlToGo, vmxt, data, keepWorking) {
            var def = $q.defer();
            vm.vmxt = vmxt;
            vm.vmxt.working = true;
            $http({
                method: 'POST',
                url: urlToGo,
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                data: data
            }).success(function (res) {
                vm.handleSuccess(res, def, keepWorking);
            }).error(function (res) {
                vm.handleError(res, def);
            });
            return def.promise;
        }

        function handleSuccess(res, def, keepWorking) {

            if (res === undefined || res === null || typeof res == 'string'){
                vm.vmxt.working = false;
                vm.handleError(null, def);
                return;
            }
            else if (res.hasError === true) {
                vm.vmxt.working = false;
                vm.vmxt.msgErr = res.message;
                vm.hideMsgErr();
            }
            else if (res.hasError === false) {
                if (!keepWorking)
                    vm.vmxt.working = false;
                def.resolve(res);
            }
            else{
                vm.vmxt.working = false;
                vm.handleError(null, def);
                return;
            }
            def.reject(res);
        };

        function handleError(res, def) {
            vm.vmxt.working = false;
            vm.vmxt.msgErr = "Error de red. Por favor intente en un momento";
            vm.hideMsgErr();
            def.reject(res);
        };


        function hideMsgErr() {
            $timeout(function () {
                vm.vmxt.msgErr = "";
            }, 10000);
        };

    }

})();

