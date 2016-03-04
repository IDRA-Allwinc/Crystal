(function () {
    "use strict";
    angular
        .module(window.constMainApp)
        .controller('notificationController', notificationController);

    notificationController.$inject = ["$http", "$sce"];

    function notificationController($http, $sce) {
        var vm = this;
        vm.WaitFor = false;
        vm.submit = submit;
        vm.successCallback = successCallback;
        vm.errorCallback = errorCallback;
        vm.tokenCsrf = document.getElementById("token-csrf");
        vm.tokenCsrfForm = "?" + vm.tokenCsrf.name + "=" + vm.tokenCsrf.value;

        function errorCallback(){
            vm.WaitFor = false;
            vm.MsgError = $sce.trustAsHtml("Error de red. Por favor intente más tarde.");
        }

        function successCallback(resp){
            vm.WaitFor = false;
            try {
                resp = resp.data;
                if (resp.hasError === undefined) {
                    resp = resp.message;
                }
                if (resp.hasError === false) {
                    vm.Model.dlg.modal('hide');
                    vm.Model.def.resolve({isCancel: false});
                    return;
                }

                vm.MsgError = $sce.trustAsHtml(resp.message);

            } catch (e) {
                vm.MsgError = $sce.trustAsHtml("Error inesperado de datos. Por favor intente más tarde.");
            }

        }

        function submit(formId, urlToPost, isValid, modelDlg){
            var i, item, lstIds = [], data;
            vm.MsgError = "";
            vm.Model = modelDlg;
            if (isValid !== undefined) {
                if (isValid !== true) {
                    vm.MsgError = $sce.trustAsHtml("Existe uno o más campos que no son válidos, son requeridos o su longitud está fuera de lo permitido.");
                    vm.Invalid = true;
                    return false;
                }
            }

            if ($(formId).valid() === false) {
                vm.MsgError = $sce.trustAsHtml("Debe proporcionar toda la información para guardar.");
                vm.Invalid = true;
                return false;
            }

            for(i=0; i < vm.lstDestination.length; i++){
                item = vm.lstDestination[i];
                if(item.isSelected === true)
                    lstIds.push(item.id);
            }

            if(lstIds.length === 0) {
                vm.MsgError = $sce.trustAsHtml("Al menos debe seleccionar un destinatario.");
                vm.Invalid = true;
                return false;
            }

            vm.WaitFor = true;
            data = {
                id: vm.m.id,
                auditId: vm.m.auditId,
                title: vm.m.title,
                message: vm.m.message,
                destinationIds: lstIds
            };

            data[vm.tokenCsrf.name] = vm.tokenCsrf.value;

            $http.post(urlToPost + vm.tokenCsrfForm, data)
                .then(successCallback, errorCallback);

        };
    }
})();
