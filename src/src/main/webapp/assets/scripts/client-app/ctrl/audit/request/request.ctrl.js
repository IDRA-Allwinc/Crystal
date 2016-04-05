window.angJsDependencies.push('ui.bootstrap');
(function () {
    "use strict";
    angular
        .module(window.constMainApp)
        .controller('requestController', requestController);

    requestController.$inject = ["$http", "$rootScope"];

    function requestController($http, $rootScope) {
        var vm = this;
        vm.init = init;
        vm.popArea = popArea;
        vm.pushArea = pushArea;
        vm.lstSelectedAreas = [];
        vm.validateSelectedAreas = validateSelectedAreas;
        vm.addError = addError;
        vm.containsArea = containsArea;
        vm.getAreas = getAreas;
        vm.findAssignedArea = findAssignedArea;
        vm.validateDates = validateDates;
        vm.validateAll = validateAll;
        vm.onChangeDate = onChangeDate;
        var milisDay = 86400000;

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
        }

        function validateAll() {
            var f = vm.validateSelectedAreas() || vm.validateDates();
            return f;
        }

        function popArea(elementId) {
            for (var i = 0; i < vm.lstSelectedAreas.length; i++) {
                if (vm.lstSelectedAreas[i].id === elementId) {
                    vm.lstSelectedAreas.splice(i, 1);
                    break;
                }
            }
        }

        function validateSelectedAreas() {
            if (!(vm.lstSelectedAreas.length > 0)) {
                vm.addError("Debe seleccionar al menos un &aacute;rea.")
                return true;
            }
            return false;
        }

        function onChangeDate() {
            try {
                var days = (vm.m.endDate - vm.m.initDate) / milisDay;
                if (days > 0) {
                    vm.m.limitTimeDays = Math.round(days);
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
                var init = vm.m.initDate;
                var end = vm.m.endDate;

                if (init >= end) {
                    vm.addError("La fecha l&iacute;mite debe ser mayor a la fecha inicio.");
                    return true;
                }
            }

            return false;
        }

        function addError(msgError) {
            $rootScope.$broadcast('showMsgErrorUpsert', msgError);
        };

        function containsArea(element) {
            for (var i = 0; i < vm.lstSelectedAreas.length; i++) {
                if (vm.lstSelectedAreas[i].id == element.id) {
                    return true;
                }
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
                        if (vm.findAssignedArea(response.data[x]) == true) {
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
