(function () {
    "use strict";
    angular
        .module(window.constMainApp)
        .controller('letterController', letterController);

    letterController.$inject = ["$scope", "$sce", "$timeout"];

    function letterController($scope, $sce, $timeout) {
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
        };

        function setSuccess(result){
            $scope.$apply(function(){
                vm.MsgSuccess = $sce.trustAsHtml(result.message);
                vm.m.lstFiles = [];
                vm.m.lstFiles.push(JSON.parse(result.returnData));
            });
        };
    }
})();
