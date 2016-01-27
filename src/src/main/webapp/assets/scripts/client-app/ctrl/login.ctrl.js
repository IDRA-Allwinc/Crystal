(function () {
    "use strict";
    angular
        .module(window.constMainApp)
        .controller('loginController', loginController);

    loginController.$inject = ["connSvc"];

    function loginController(connSvc) {
        var vm = this;
        vm.m = {};
        vm.isLogin = true;
        vm.login = login;
        vm.svc = connSvc;
        vm.tokenCSRF = document.getElementById("tokenCSRF");

        function login(formId, msgError, url) {

            var data = $(formId).serialize() + "&"+vm.tokenCSRF.name+"=" + vm.tokenCSRF.value;
            vm.svc.post(url, vm, data, true).then(function (res) {
                window.location.replace(res.urlToGo);
            });
            vm.m.password = "";
        }
    }
})();
