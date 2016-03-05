(function () {
    "use strict";
    angular
        .module(window.constMainApp)
        .controller('upsertController', upsertController);

    upsertController.$inject = ["$scope", "$rootScope", "$sce", "notify", "$timeout"];

    function upsertController($scope, $rootScope, $sce, notify, $timeout) {
        var vm = this;
        vm.WaitFor = false;
        vm.MsgError = "";
        vm.MsgSuccess = "";
        vm.Model = {};
        vm.submit = submit;
        vm.fillSelect = fillSelect;
        vm.submitRedirect = submitRedirect;
        vm.returnUrl = returnUrl;
        vm.handleSuccessRedirect = handleSuccessRedirect;
        vm.handleSuccessWithId = handleSuccessWithId;
        vm.handleSuccess = handleSuccess;
        vm.handleError = handleError;
        vm.cancel = cancel;
        vm.setDlg = setDlg;
        vm.setReason = setReason;
        vm.formatHtml = formatHtml;
        vm.config = {isModal: true, hasNotify: false, hasNotifyError: false};
        vm.tokenCsrf = document.getElementById("token-csrf");
        vm.tokenCsrfForm = "&" + vm.tokenCsrf.name + "=" + vm.tokenCsrf.value;
        vm.tokenCsrfFormReplace = {_csrf:vm.tokenCsrf.name + " = " + vm.tokenCsrf.value};

        function submit(formId, urlToPost, isValid, hasReturnId) {
            var dataSer;
            vm.MsgError = "";
            vm.MsgSuccess = $sce.trustAsHtml("");

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

            vm.WaitFor = true;
            dataSer = $(formId).serialize() + vm.tokenCsrfForm;

            if (hasReturnId === true) {
                $.post(urlToPost, dataSer)
                    .success(vm.handleSuccessWithId)
                    .error(vm.handleError);
            }
            else {
                $.post(urlToPost, dataSer)
                    .success(vm.handleSuccess)
                    .error(vm.handleError);
            }
            return true;
        };

        function fillSelect(model, list) {

            if (vm[list] === undefined || vm[list].length <= 0)
                return;

            if (vm[model] === undefined) {
                vm[model] = vm[list][0];
                $scope.$apply();
            }
        };

        function submitRedirect(formId, urlToPost, isValid, hasReturnId) {
            var dataSer, stVal = true;

            vm.MsgError = "";
            vm.MsgSuccess = $sce.trustAsHtml("");

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

            vm.WaitFor = true;
            dataSer = $(formId).serialize() + vm.tokenCsrfForm;

            if (hasReturnId === true) {
                $.post(urlToPost, dataSer)
                    .success(vm.handleSuccessWithId)
                    .error(vm.handleError);
            }
            else {
                $.post(urlToPost, dataSer)
                    .success(vm.handleSuccessRedirect)
                    .error(vm.handleError);
            }
            return true;
        };

        function returnUrl(urlToGo) {
            window.goToUrlMvcUrl(urlToGo, vm.tokenCsrfFormReplace);
        };


        function handleSuccessRedirect(resp) {
            vm.WaitFor = false;
            try {
                if (resp.hasError === undefined) {
                    resp = resp.message;
                }
                if (resp.hasError === false) {
                    window.goToUrlMvcUrl(resp.urlToGo, vm.tokenCsrfFormReplace);
                    vm.WaitFor = false;
                    $scope.$apply();
                    return;
                }

                vm.MsgError = $sce.trustAsHtml(resp.message);

                $scope.$apply();

            } catch (e) {
                vm.MsgError = $sce.trustAsHtml("Error inesperado de datos. Por favor intente más tarde.");
                try{$scope.$apply();}catch (e) {console.log(e);}
            } finally{
                $timeout(function () {
                    vm.MsgSuccess = $sce.trustAsHtml("");
                    vm.MsgError = $sce.trustAsHtml("");
                }, 8000);
            }
        };

        function handleSuccessWithId(resp) {
            vm.WaitFor = false;

            try {
                if (resp.hasError === undefined) {
                    resp = resp.message;
                }
                if (resp.hasError === false) {
                    $rootScope.$broadcast("onLastId", resp.Id);
                    vm.Model.dlg.modal('hide');
                    vm.Model.def.resolve({isCancel: false});
                    return;
                }

                vm.MsgError = $sce.trustAsHtml(resp.message);
                $scope.$apply();

            } catch (e) {
                vm.MsgError = $sce.trustAsHtml("Error inesperado de datos. Por favor intente más tarde.");
                try{$scope.$apply();}catch (e) {console.log(e);}
            } finally{
                $timeout(function () {
                    vm.MsgSuccess = $sce.trustAsHtml("");
                    vm.MsgError = $sce.trustAsHtml("");
                }, 8000);
            }
        };

        function handleSuccess(resp) {
            vm.WaitFor = false;

            try {

                if (resp.hasError === undefined) {
                    vm.handleError();
                }

                if (resp.hasError === false) {

                    if (vm.Model.dlg == undefined) {
                        if (resp.message != undefined) {
                            vm.MsgSuccess = $sce.trustAsHtml(resp.message);
                            $scope.$apply();
                        }
                        return;
                    }


                    if (vm.config.isModal === true) {
                        vm.Model.dlg.modal('hide');
                        vm.Model.def.resolve({isCancel: false, resp: resp});
                    }

                    if (vm.config.hasNotify === true) {
                        notify({message: resp.message, classes: 'alert-info'});
                    }

                    $rootScope.$broadcast("onSuccessNotification", resp);

                    return;
                }

                vm.MsgError = $sce.trustAsHtml(resp.message);

                if (vm.config.hasNotifyError === true) {
                    notify({message: resp.message, classes: 'alert-danger'});
                }

                $scope.$apply();

            } catch (e) {
                vm.MsgError = $sce.trustAsHtml("Error inesperado de datos. Por favor intente más tarde.");
                if (vm.config.hasNotifyError === true) {
                    notify({message: resp.message, classes: 'alert-danger'});
                }
                $scope.$apply();
            }  finally{
                $timeout(function () {
                    vm.MsgSuccess = $sce.trustAsHtml("");
                    vm.MsgError = $sce.trustAsHtml("");
                }, 8000);
            }
        };

        function handleError() {
            vm.WaitFor = false;
            vm.MsgError = $sce.trustAsHtml("Error de red. Por favor intente más tarde.");
            if (vm.config.hasNotifyError === true) {
                notify({message: resp.message, classes: 'alert-danger'});
            }
            $scope.$apply();

            $timeout(function () {
                vm.MsgError = $sce.trustAsHtml("");
            }, 8000);
        };

        function cancel(isOk) {
            vm.Model.dlg.modal('hide');

            if (isOk === true)
                vm.Model.def.resolve({isCancel: false});
            else
                vm.Model.def.reject({isCancel: true});
        };

        function setDlg(dlg, urlToSubmit) {
            vm.Model.dlg = dlg;
            vm.Model.url = urlToSubmit;

            dlg.on('hidden.bs.modal', function () {
                dlg.data('modal', null);
                dlg.replaceWith("");
            });
        };

        function setReason(opc) {
            if (opc.isFinal && vm.Model.reason != "") {
                vm.Model.reason = opc.reason;
            }
        }

        function formatHtml(sHtml) {
            return $sce.trustAsHtml(sHtml);
        };

        $rootScope.$on('showMsgErrorUpsert', function (event, data) {
            vm.MsgError = vm.formatHtml(data);
        });

    }
})();

