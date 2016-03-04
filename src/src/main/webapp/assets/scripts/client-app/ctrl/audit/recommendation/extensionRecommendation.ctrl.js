(function () {
    "use strict";
    angular
        .module(window.constMainApp)
        .controller('extensionRecommendationController', extensionRecommendationController);

    extensionRecommendationController.$inject = ["$scope", "$sce", "$timeout"];

    function extensionRecommendationController($scope, $sce, $timeout) {
        var ervm = this;
        ervm.setOutError = setOutError;
        ervm.setSuccess = setSuccess;
        ervm.refreshParentGrid = refreshParentGrid;
        ervm.today = new Date();

        function setOutError(msg) {
            $scope.$apply(function () {
                ervm.MsgError = $sce.trustAsHtml(msg);
                $timeout(function () {
                    ervm.MsgError = $sce.trustAsHtml("");
                }, 8000);
            });
        }

        function setSuccess(result) {
            $scope.$apply(function () {
                ervm.m.extensionComment = undefined;
                ervm.m.endDateExtComm = undefined;
            });
        }

        function refreshParentGrid(tableId) {
            $(tableId).bootstrapTable('refresh', 'showLoading');
        }
    }
})();
