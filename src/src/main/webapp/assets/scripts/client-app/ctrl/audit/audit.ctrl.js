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

        function getSupervisoryEntities(str) {
            return $http.get(vm.urlGetSupervisoryEntities, {params: {supervisoryStr: str}})
                .then(function (response) {

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
        }

        function findSupervisoryEntitySelected(obj) {
            if (vm.m.supervisoryEntity === undefined)
                return false;

            if (obj.id === vm.m.supervisoryEntity.id) {
                return true;
            }
            return false;
        };

    }
})();
