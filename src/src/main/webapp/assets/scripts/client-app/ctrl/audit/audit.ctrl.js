window.angJsDependencies.push('ui.bootstrap');
(function () {
    "use strict";
    angular
        .module(window.constMainApp)
        .controller('auditController', auditController);

    auditController.$inject = ["$http", "$rootScope"];

    function auditController($http, $rootScope) {
        var vm = this;
        vm.m = {};
        vm.today = new Date();
        vm.getSupervisoryEntities = getSupervisoryEntities;
        vm.findSupervisoryEntitySelected = findSupervisoryEntitySelected;
        vm.init = init;
        vm.getAreas = getAreas;
        vm.findAssignedArea = findAssignedArea;
        vm.pushArea = pushArea;
        vm.popArea = popArea;
        vm.validateAudit = validateAudit;
        vm.getAuditedEntities = getAuditedEntities;
        vm.findAuditedEntitySelected = findAuditedEntitySelected;

        vm.reviewRangeOptionsYear = {
            datepickerMode: "'year'",
            minMode: "year",
            minDate: "2000/01/01",
            showWeeks: "false"
        };
        vm.reviewRangeOptionsMonth = {
            datepickerMode: "'month'",
            minMode: "month",
            minDate: "2000/01/01",
            showWeeks: "false"
        };

        function init() {
            vm.m.auditType = window.initCatalog(vm.lstAuditTypes, vm.m.auditTypeId);

            if (vm.m.auditType != null)
                vm.m.auditTypeId = vm.m.auditType.id;

            if (vm.m.supervisoryEntity != undefined && vm.m.supervisoryEntity.id != undefined) {
                vm.m.supervisoryEntityId = vm.m.supervisoryEntity.id;
                vm.m.supervisoryEntity = vm.m.supervisoryEntity.name + " (" + vm.m.supervisoryEntity.description + ")";
            }

            if (vm.m.auditedEntity != undefined && vm.m.auditedEntity.id != undefined) {
                vm.m.auditedEntityId = vm.m.auditedEntity.id;
                vm.m.auditedEntity = vm.m.auditedEntity.name + " (" + vm.m.auditedEntity.description + ")";
            }

            for (var i = 0; i < vm.lstSelectedAreas.length; i++) {
                vm.lstSelectedAreas[i].desc = vm.lstSelectedAreas[i].name + " (" + vm.lstSelectedAreas[i].description + ")";
            }

            if (vm.m.letterDate != undefined)
                vm.m.letterDate = new Date(vm.m.letterDate);
            if (vm.m.auditedYear != undefined)
                vm.m.auditedYear = new Date(vm.m.auditedYear);
            if (vm.m.reviewInitDate != undefined)
                vm.m.reviewInitDate = new Date(vm.m.reviewInitDate);
            if (vm.m.reviewEndDate != undefined)
                vm.m.reviewEndDate = new Date(vm.m.reviewEndDate);
        };

        function getAuditedEntities(str) {
            return $http.get(vm.urlGetAuditedEntities, {params: {auditedStr: str}})
                .then(function (response) {

                    if (!response.data.length > 0)
                        vm.m.auditedEntityId = undefined;

                    for (var x = response.data.length - 1; x > -1; x--) {
                        if (vm.findAuditedEntitySelected(response.data[x]) == true) {
                            response.data.splice(x, 1);
                        }
                    }

                    return response.data.map(function (item) {
                        item.desc = item.name + " (" + item.description + ") ";
                        return item;
                    });
                });
        };


        function getSupervisoryEntities(str) {
            return $http.get(vm.urlGetSupervisoryEntities, {params: {supervisoryStr: str}})
                .then(function (response) {

                    if (!response.data.length > 0)
                        vm.m.supervisoryEntityId = undefined;

                    for (var x = response.data.length - 1; x > -1; x--) {
                        if (vm.findSupervisoryEntitySelected(response.data[x]) == true) {
                            response.data.splice(x, 1);
                        }
                    }

                    return response.data.map(function (item) {
                        item.desc = item.name + " (" + item.description + ") ";
                        return item;
                    });
                });
        };

        function findSupervisoryEntitySelected(obj) {
            if (vm.m.supervisoryEntity === undefined)
                return false;

            if (obj.id === vm.m.supervisoryEntity.id) {
                return true;
            }
            return false;
        };

        function findAuditedEntitySelected(obj) {
            if (vm.m.auditedEntity === undefined)
                return false;

            if (obj.id === vm.m.auditedEntity.id) {
                return true;
            }
            return false;
        };

        function validateAudit() {
            var valid = true
            var msg = "";

            if (vm.m.supervisoryEntityId == undefined) {
                msg += "Debe seleccionar un &oacute;rgano fiscalizador v&aacute;lido <br/>";
                valid = false
            }

            if (vm.m.auditedEntityId == undefined) {
                msg += "Debe seleccionar un ente fiscalizado v&aacute;lido <br/>";
                valid = false
            }

            if (!vm.lstSelectedAreas.length > 0) {
                msg += "Debe seleccionar al menos un &aacute;rea <br/>";
                valid = false
            }

            if (msg != "")
                $rootScope.$broadcast('showMsgErrorUpsert', msg);

            return valid;
        };

        function containsArea(element) {
            for (var i = 0; i < vm.lstSelectedAreas.length; i++) {
                if (vm.lstSelectedAreas[i].id == element.id) {
                    return true;
                }
            }
            return false
        };

        function popArea(elementId) {
            for (var i = 0; i < vm.lstSelectedAreas.length; i++) {
                if (vm.lstSelectedAreas[i].id === elementId) {
                    vm.lstSelectedAreas.splice(i, 1);
                    break;
                }
            }
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
