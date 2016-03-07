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
        vm.today = new Date();

        function setOutError(msg) {
            $scope.$apply(function () {
                vm.MsgError = $sce.trustAsHtml(msg);
                $timeout(function () {
                    vm.MsgError = $sce.trustAsHtml("");
                }, 8000);
            });
        }

        function setSuccess(result) {
            $scope.$apply(function () {
                vm.m.extensionComment = undefined;
                vm.m.endDate = undefined;
            });
        }

        function refreshParentGrid(tableId) {
            $(tableId).bootstrapTable('refresh', 'showLoading');
        }
    }
})();
