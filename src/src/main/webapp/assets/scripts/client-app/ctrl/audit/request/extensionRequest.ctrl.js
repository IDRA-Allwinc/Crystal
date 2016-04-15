(function () {
    "use strict";
    angular
        .module(window.constMainApp)
        .controller('extensionRequestController', extensionRequestController);

    extensionRequestController.$inject = ["$scope", "$sce", "$timeout"];

    function extensionRequestController($scope, $sce, $timeout) {
        var vm = this;
        vm.setOutError = setOutError;
        vm.setSuccess = setSuccess;
        vm.refreshParentGrid = refreshParentGrid;
        vm.handleSuccessRefresh = handleSuccessRefresh;
        vm.handleErrorRefresh = handleErrorRefresh;
        vm.refreshExtensionRequest = refreshExtensionRequest;
        vm.today = new Date();
        vm.tokenCsrf = document.getElementById("token-csrf");

        $timeout(function () {
            vm.urlRefresh = vm.urlRefresh + "?" + vm.tokenCsrf.name + "=" + vm.tokenCsrf.value;
        }, 100);


        function setOutError(msg) {
            $scope.$apply(function () {
                vm.MsgError = $sce.trustAsHtml(msg);
                $timeout(function () {
                    vm.MsgError = $sce.trustAsHtml("");
                }, 8000);
            });
        }
        function setSuccess() {
            vm.refreshExtensionRequest();
            vm.m.extensionComment = undefined;
            vm.m.endDateExtRecomm = undefined;
        }

        function refreshParentGrid(tableId) {
            $(tableId).bootstrapTable('refresh', 'showLoading');
        }

        function refreshExtensionRequest() {
            var param = {"id": vm.m.id};
            $.post(vm.urlRefresh, param)
                .success(vm.handleSuccessRefresh)
                .error(vm.handleErrorRefresh);
        }

        function handleSuccessRefresh(resp) {
            if (resp.hasError === undefined) {
                vm.setOutError("Error de red. Por favor intente más tarde.");
            }

            if (resp.hasError === false) {
                vm.m = JSON.parse(resp.returnData);
                vm.refreshParentGrid(vm.tableId);
                $scope.$apply();
            }
        }

        function handleErrorRefresh() {
            vm.setOutError("Error de red. Por favor intente más tarde.");
        }
    }
})();
