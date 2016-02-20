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
        vm.validateLimitDays = validateLimitDays;
        vm.validateAll = validateAll;
        vm.changeLimitDate = changeLimitDate;
        vm.millisecondsDay = 86400000;

        function init() {
            if (vm.m.limitTimeDays != undefined)
                vm.changeLimitDate();

            for (var i = 0; i < vm.lstSelectedAreas.length; i++) {
                vm.lstSelectedAreas[i].desc = vm.lstSelectedAreas[i].name + " (" + vm.lstSelectedAreas[i].description + ") ";
            }
        }

        function validateAll() {
            var f = vm.validateSelectedAreas() || vm.validateLimitDays();
            return f;
        }

        function popArea(elementId) {
            for (var i = 0; i < vm.lstSelectedAreas.length; i++) {
                if (vm.lstSelectedAreas[i].id === elementId) {
                    vm.lstSelectedAreas.splice(i, 1);
                    break;
                }
            }
        };

        function changeLimitDate() {
            try {
                var limit = parseInt(vm.m.limitTimeDays);
                if (isNaN(limit) || limit == 0)
                    vm.m.limitDate = undefined;
                else
                    vm.m.limitDate = convertDate(limit);
            } catch (ex) {
                vm.m.limitDate = undefined;
            }
        };

        function convertDate(addDays) {
            function pad(s) {
                return (s < 10) ? '0' + s : s;
            }

            var mil = new Date().getTime() + (addDays * vm.millisecondsDay);
            var d = new Date(mil);
            return [pad(d.getDate()), pad(d.getMonth() + 1), d.getFullYear()].join('/');
        }


        function validateSelectedAreas() {
            if (!(vm.lstSelectedAreas.length > 0)) {
                vm.addError("Debe seleccionar al menos un &aacute;rea.")
                return true;
            }
            return false;
        };

        function validateLimitDays() {
            if (!(parseInt(vm.m.limitTimeDays) > 0)) {
                vm.addError("El plazo debe ser mayor a cero.")
                return true;
            }
            return false;
        };

        function addError(msgError) {
            $rootScope.$broadcast('showMsgErrorUpsert', msgError);
        };

        function containsArea(element) {
            for (var i = 0; i < vm.lstSelectedAreas.length; i++) {
                if (vm.lstSelectedAreas[i].id == element.id) {
                    return true;
                }
            }
            return false
        };


        function pushArea(element) {
            if (!containsArea(element))
                vm.lstSelectedAreas.push(element);
            else
                vm.addError("El &aacute;rea ya ha sido seleccionada.");
            vm.m.areaSel = undefined;
        };

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
        };

        function findAssignedArea(obj) {
            for (var x = 0; x < vm.lstSelectedAreas.length; x++) {
                if (obj.id === vm.lstSelectedAreas[x].id) {
                    return true;
                }
            }
            return false;
        };
    }
})();
