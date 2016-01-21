window.angJsDependencies.push('ui.bootstrap');
(function () {
    "use strict";
    angular
        .module(window.constMainApp)
        .controller('auditController', auditController);

    auditController.$inject = ["$http", "$sce"];

    function auditController($http, $sce) {
        var vm = this;
        vm.m = {};
        vm.today = new Date();
        vm.getSupervisoryEntities = getSupervisoryEntities;
        vm.findSupervisoryEntitySelected = findSupervisoryEntitySelected;
        vm.init = init;
        vm.upsertCtrl = null;
        vm.getAreas = getAreas;
        vm.findAssignedArea = findAssignedArea;
        vm.pushArea = pushArea;
        vm.popArea = popArea;
        vm.validateAudit = validateAudit;

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
            if (vm.m.auditedEntity != undefined)
                vm.m.auditedEnttityId = vm.m.auditedEntity.id;
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

        function validateAudit() {
            var valid = true
            var msg = "";
            vm.upsertCtrl.MsgError = "";

            if (vm.m.supervisoryEntityId == undefined) {
                msg += "Debe seleccionar un &oacute;rgano fiscalizador v&aacute;lido <br/>";
                valid = false
            }

            if (vm.m.auditedEnttityId == undefined) {
                msg += "Debe seleccionar una ente fiscalizado v&aacute;lida <br/>";
                valid = false
            }

            if (!vm.lstSelectedAreas.length > 0) {
                msg += "Debe seleccionar al menos un &aacute;rea <br/>";
                valid = false
            }

            if (msg != "")
                vm.upsertCtrl.MsgError = vm.upsertCtrl.formatHtml(msg);

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
