(function () {
    "use strict";
    angular
        .module(window.constMainApp)
        .controller('recommendationViewDocsController', recommendationViewDocsController);

    recommendationViewDocsController.$inject = ["$scope", "$sce", "$timeout"];

    function recommendationViewDocsController($scope, $sce, $timeout) {
        var vm = this;
        vm.m = {};

        vm.setOutError = setOutError;
        vm.setSuccess = setSuccess;

        function setOutError(msg){
            $scope.$apply(function(){
                vm.MsgError = $sce.trustAsHtml(msg);
                $timeout(function(){
                    vm.MsgError = $sce.trustAsHtml("");
                }, 8000)
            });
        }

        function setSuccess(result){
            $scope.$apply(function(){
                vm.m.description = "";
                //vm.MsgSuccess = $sce.trustAsHtml(result.message);
            });
        }
    }
})();
