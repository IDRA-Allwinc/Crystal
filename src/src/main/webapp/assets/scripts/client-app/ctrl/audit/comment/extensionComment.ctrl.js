(function () {
    "use strict";
    angular
        .module(window.constMainApp)
        .controller('extensionCommentController', extensionCommentController);

    extensionCommentController.$inject = ["$scope", "$sce", "$timeout"];

    function extensionCommentController($scope, $sce, $timeout) {
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
                vm.m.endDateExtComm = undefined;
            });
        }

        function refreshParentGrid(tableId) {
            $(tableId).bootstrapTable('refresh', 'showLoading');
        }
    }
})();
