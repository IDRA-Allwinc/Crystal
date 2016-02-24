(function () {
    "use strict";
    angular
        .module(window.constMainApp)
        .controller('observationController', observationController);

    observationController.$inject = ["$scope", "$rootScope", "$sce", "$timeout", "$http"];

    function observationController($scope, $rootScope, $sce, $timeout, $http) {
        var vm = this;
        var milisDay = 24 * 60 * 60 * 1000; //hrs*min*seg*mil
        vm.setOutError = setOutError;
        vm.setSuccess = setSuccess;
        vm.init = init;
        vm.getAreas = getAreas;
        vm.lstSelectedAreas = [];
        vm.findAssignedArea = findAssignedArea;
        vm.validateSelectedAreas = validateSelectedAreas;
        vm.pushArea = pushArea;
        vm.popArea = popArea;
        vm.validateAll = validateAll;
        vm.validateDates = validateDates;
        vm.addError = addError;
        vm.onChangeDate = onChangeDate;

        function init() {

            if (vm.m.initDate !== undefined) {
                vm.m.initDate = new Date(vm.m.initDate);
            }
            if (vm.m.endDate !== undefined) {
                vm.m.endDate = new Date(vm.m.endDate);
            }

            vm.onChangeDate();

            for (var i = 0; i < vm.lstSelectedAreas.length; i++) {
                vm.lstSelectedAreas[i].desc = vm.lstSelectedAreas[i].name + " (" + vm.lstSelectedAreas[i].description + ") ";
            }


            vm.m.observationType = window.initCatalog(vm.lstObservationType, vm.m.observationTypeId);
            if(vm.m.observationType!=undefined)
                vm.m.observationTypeId = vm.m.observationType.id;
        }

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
                vm.MsgSuccess = $sce.trustAsHtml(result.message);
                vm.m.lstFiles = [];
                vm.m.lstFiles.push(JSON.parse(result.returnData));
            });
        }

        function addError(msgError) {
            $rootScope.$broadcast('showMsgErrorUpsert', msgError);
        }

        function containsArea(element) {
            for (var i = 0; i < vm.lstSelectedAreas.length; i++) {
                if (vm.lstSelectedAreas[i].id === element.id) {
                    return true;
                }
            }
            return false;
        }

        function popArea(elementId) {
            for (var i = 0; i < vm.lstSelectedAreas.length; i++) {
                if (vm.lstSelectedAreas[i].id === elementId) {
                    vm.lstSelectedAreas.splice(i, 1);
                    break;
                }
            }
        }

        function validateAll() {
            return vm.validateSelectedAreas() || vm.validateDates();
        }

        function onChangeDate() {
            try {
                var init = new Date(vm.m.initDate);
                var end = new Date(vm.m.endDate);
                var days = (end - init) / milisDay;
                if (days > 0) {
                    vm.m.limitTimeDays = days;
                }
                else {
                    vm.m.limitTimeDays = "";
                }
            } catch (e) {
                vm.m.limitTimeDays = "";
            }
        }

        function validateDates() {
            if (vm.m.initDate !== undefined && vm.m.endDate !== undefined) {
                var init = new Date(vm.m.initDate);
                var end = new Date(vm.m.endDate);

                if (init >= end) {
                    vm.addError("La fecha l&iacute;mite debe ser mayor a la fecha inicio.");
                    return true;
                }
            }

            return false;
        }

        function validateSelectedAreas() {
            if (!(vm.lstSelectedAreas.length > 0)) {
                vm.addError("Debe seleccionar al menos un &aacute;rea.");
                return true;
            }
            return false;
        }

        function pushArea(element) {
            if (!containsArea(element)) {
                vm.lstSelectedAreas.push(element);
            }
            else {
                vm.addError("El &aacute;rea ya ha sido seleccionada.");
            }
            vm.m.areaSel = undefined;
        }

        function getAreas(str) {
            return $http.get(vm.urlGetAreas, {params: {areaStr: str}})
                .then(function (response) {

                    for (var x = response.data.length - 1; x > -1; x--) {
                        if (vm.findAssignedArea(response.data[x]) === true) {
                            response.data.splice(x, 1);
                        }
                    }

                    return response.data.map(function (item) {
                        item.desc = item.name + " (" + item.description + ") ";
                        return item;
                    });
                });
        }

        function findAssignedArea(obj) {
            for (var x = 0; x < vm.lstSelectedAreas.length; x++) {
                if (obj.id === vm.lstSelectedAreas[x].id) {
                    return true;
                }
            }
            return false;
        }
    }
})();
