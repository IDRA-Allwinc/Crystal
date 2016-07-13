window.angJsDependencies.push('ui.bootstrap');
(function () {
    "use strict";
    angular
        .module(window.constMainApp)
        .controller('eventController', eventController);

    eventController.$inject = ["$scope", "$rootScope", "$sce", "$timeout", "$http"];

    function eventController($scope, $rootScope, $sce, $timeout, $http) {
        var vm = this;
        vm.setOutError = setOutError;
        vm.setSuccess = setSuccess;
        vm.init = init;
        vm.getAssistants = getAssistants;
        vm.lstSelectedAssistants = [];
        vm.findAssignedAssistant = findAssignedAssistant;
        vm.validateSelectedAreas = validateSelectedAreas;
        vm.pushAssistant = pushAssistant;
        vm.popAssistant = popAssistant;
        vm.validateAll = validateAll;
        vm.addError = addError;
        vm.changed = changed;

        function changed(bInit) {
            if(bInit)  {
                vm.ctrlHour = new Date(2016, 0, 1, 12, 0, 0, 0);
                vm.ctrlHourTx = "12:00";
            }

            try  {
                vm.ctrlHourTx = vm.ctrlHour.getHours() + ":" + vm.ctrlHour.getMinutes();
            }catch (e){ vm.ctrlHourTx = "00:00"}

        }

        function init() {

            if (vm.m.meetingDate !== undefined) {
                vm.m.meetingDate = new Date(vm.m.meetingDate);
                vm.m.hour = vm.m.meetingHour;
                vm.m.meetingHour = new Date(vm.m.meetingDate);

            }


            for (var i = 0; i < vm.lstSelectedAssistants.length; i++) {
                vm.lstSelectedAssistants[i].desc = vm.lstSelectedAssistants[i].responsible + " (" + vm.lstSelectedAssistants[i].name + ") ";
            }


            vm.m.eventType = window.initCatalog(vm.lstEventType, vm.m.eventTypeId);
            if(vm.m.eventType!=undefined)
                vm.m.eventTypeId = vm.m.eventType.id;

            vm.m.meetingType = window.initCatalog(vm.lstMeetingType, vm.m.meetingTypeId);
            if(vm.m.meetingType!=undefined)
                vm.m.meetingTypeId = vm.m.meetingType.id;

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

        function containsAssistant(element) {
            for (var i = 0; i < vm.lstSelectedAssistants.length; i++) {
                if (vm.lstSelectedAssistants[i].id === element.id) {
                    return true;
                }
            }
            return false;
        }

        function popAssistant(elementId) {
            if(vm.m.id==undefined) {
                for (var i = 0; i < vm.lstSelectedAssistants.length; i++) {
                    if (vm.lstSelectedAssistants[i].id === elementId) {
                        vm.lstSelectedAssistants.splice(i, 1);
                        break;
                    }
                }
            }
        }

        function validateAll() {
            //return vm.validateSelectedAreas() || vm.validateDates();
            return false;
        }


        function validateSelectedAreas() {
            if (!(vm.lstSelectedAssistants.length > 0)) {
                vm.addError("Debe seleccionar al menos un usuario.");
                return true;
            }
            return false;
        }

        function pushAssistant(element) {
            if (!containsAssistant(element)) {
                vm.lstSelectedAssistants.push(element);
            }
            else {
                vm.addError("El usuario ya ha sido seleccionada.");
            }
            vm.m.assistantSel = undefined;
        }

        function getAssistants(str) {
            return $http.get(vm.urlGetAssistants, {params: {assistantStr: str}})
                .then(function (response) {
                    for (var x = response.data.length - 1; x > -1; x--) {
                        if (vm.findAssignedAssistant(response.data[x]) === true) {
                            response.data.splice(x, 1);
                        }
                    }

                    return response.data.map(function (item) {
                        item.desc = item.responsible + " (" + item.name + ") ";
                        return item;
                    });
                });
        }

        function findAssignedAssistant(obj) {
            for (var x = 0; x < vm.lstSelectedAssistants.length; x++) {
                if (obj.id === vm.lstSelectedAssistants[x].id) {
                    return true;
                }
            }
            return false;
        }
    }
})();
