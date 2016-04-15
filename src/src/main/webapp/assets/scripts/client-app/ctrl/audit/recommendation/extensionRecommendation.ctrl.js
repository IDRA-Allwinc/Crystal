(function () {
    "use strict";
    angular
        .module(window.constMainApp)
        .controller('extensionRecommendationController', extensionRecommendationController);

    extensionRecommendationController.$inject = ["$scope", "$sce", "$timeout", "$rootScope"];

    function extensionRecommendationController($scope, $sce, $timeout) {
        var ervm = this;
        ervm.setOutError = setOutError;
        ervm.setSuccess = setSuccess;
        ervm.refreshParentGrid = refreshParentGrid;
        ervm.handleSuccessRefresh = handleSuccessRefresh;
        ervm.handleErrorRefresh = handleErrorRefresh;
        ervm.refreshExtensionRecommendation = refreshExtensionRecommendation;
        ervm.today = new Date();
        ervm.tokenCsrf = document.getElementById("token-csrf");


        $timeout(function () {
            ervm.urlRefresh = ervm.urlRefresh + "?" + ervm.tokenCsrf.name + "=" + ervm.tokenCsrf.value;
        }, 100);


        function setOutError(msg) {
            $scope.$apply(function () {
                ervm.MsgError = $sce.trustAsHtml(msg);
                $timeout(function () {
                    ervm.MsgError = $sce.trustAsHtml("");
                }, 8000);
            });
        }

        function setSuccess() {
            ervm.refreshExtensionRecommendation();
            ervm.m.extensionComment = undefined;
            ervm.m.endDateExtRecomm = undefined;
        }

        function refreshParentGrid(tableId) {
            $(tableId).bootstrapTable('refresh', 'showLoading');
        }

        function refreshExtensionRecommendation() {
            var param = {"id": ervm.m.id};
            $.post(ervm.urlRefresh, param)
                .success(ervm.handleSuccessRefresh)
                .error(ervm.handleErrorRefresh);
        }

        function handleSuccessRefresh(resp) {
            if (resp.hasError === undefined) {
                ervm.setOutError("Error de red. Por favor intente más tarde.");
            }

            if (resp.hasError === false) {
                ervm.m = JSON.parse(resp.returnData);
                ervm.refreshParentGrid(ervm.tableId);
                $scope.$apply();
            }
        }

        function handleErrorRefresh() {
            ervm.setOutError("Error de red. Por favor intente más tarde.");
        }

    }
})
();
