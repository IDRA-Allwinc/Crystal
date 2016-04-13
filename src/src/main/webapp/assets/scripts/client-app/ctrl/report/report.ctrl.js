window.angJsDependencies.push('ui.bootstrap');
(function () {
    "use strict";
    angular
        .module(window.constMainApp)
        .controller('reportController', reportController);


    reportController.$inject = ["$http", "$sce"];

    function reportController($http, $sce) {
        var vm = this;
        vm.m = {};
        vm.init = init;
        vm.selectSupervisory = selectSupervisory;
        vm.selectYear = selectYear;
        vm.selectEntityType = selectEntityType;
        vm.selectEntity = selectEntity;
        vm.exportToPDF = exportToPDF;

        function init() {
            vm.m.auditedEntity = window.initCatalog(vm.lstAuditedEntity, vm.m.auditedEntityId);
            if (vm.m.auditedEntity != undefined)
                vm.m.auditedEntityId = vm.m.auditedEntity.id;
        }

        function selectSupervisory(item, urlToPost) {
            vm.selectedSupervisory = item;
            vm.selectedYear = undefined;
            vm.selectedEntityType = undefined;
            vm.selectedEntity = undefined;
            $http.get(urlToPost, {params: {id: vm.selectedSupervisory.id}})
                .then(function (response) {
                    try {
                        response = response.data;
                        if (response.hasError === false) {
                            vm.supervisoryData = response.returnData;
                        }

                    } catch (e) {
                        vm.MsgError = $sce.trustAsHtml("Error inesperado de datos. Por favor intente más tarde.");
                    }
                });
        }

        function selectYear(item, urlToPost) {
            if (vm.selectedYear === item) {
                vm.selectedYear = undefined;
                return;
            }
            vm.selectedYear = item;
            vm.selectedEntityType = undefined;
            vm.selectedEntity = undefined;
            $http.get(urlToPost, {params: {id: vm.selectedSupervisory.id, year: vm.selectedYear}})
                .then(function (response) {
                    try {
                        response = response.data;
                        if (response.hasError === false) {
                            vm.yearData = response.returnData;
                        }
                    } catch (e) {
                        vm.MsgError = $sce.trustAsHtml("Error inesperado de datos. Por favor intente más tarde.");
                    }
                });
        }


        function selectEntityType(item, urlToPost) {
            if (vm.selectedEntityType === item) {
                vm.selectedEntityType = undefined;
                return;
            }
            vm.selectedEntityType = item;
            vm.selectedEntity = undefined;
            $http.get(urlToPost, {
                params: {
                    id: vm.selectedSupervisory.id,
                    year: vm.selectedYear,
                    entityType: vm.selectedEntityType
                }
            })
                .then(function (response) {
                    try {
                        response = response.data;
                        if (response.hasError === false) {
                            vm.entityTypeData = response.returnData;
                        }
                    } catch (e) {
                        vm.MsgError = $sce.trustAsHtml("Error inesperado de datos. Por favor intente más tarde.");
                    }
                });
        }

        function selectEntity(item, urlToPost) {
            vm.selectedEntity = item;
            $http.get(urlToPost, {
                params: {
                    id: vm.selectedSupervisory.id,
                    year: vm.selectedYear,
                    entity: vm.selectedEntity
                }
            })
                .then(function (response) {
                    try {
                        response = response.data;
                        if (response.hasError === false) {
                            vm.entityData = response.returnData;
                        }
                    } catch (e) {
                        vm.MsgError = $sce.trustAsHtml("Error inesperado de datos. Por favor intente más tarde.");
                    }
                });
        }


        function exportToPDF() {
            var columns = [
                {title: "Cuenta Pública", dataKey: "id"},
                {title: "No.\nAuditorias", dataKey: "auditNumber"},
                {title: "Emitidas\n(E)", dataKey: "emitted"},
                {title: "Atendidas\n(A)", dataKey: "attended"},
                {title: "R", dataKey: "recommendations"},
                {title: "PO", dataKey: "observations"},
                {title: "PRAS", dataKey: "responsibilities"},
                {title: "Total", dataKey: "notAttended"},
                {title: "Avance\n(A/E) %", dataKey: "progress"},
            ];


            var pdfData = makePDFObject();
            var rows = pdfData;


            var doc = new jsPDF('p', 'pt');
            doc.autoTable(columns, rows, {
                theme: 'grid',
                startY: 60,
                styles: {overflow: 'linebreak'},
                headerStyles: {halign: 'center', valign: 'middle', fontSize: 8, fillColor: [158, 158, 158]},
                columnStyles: {
                    auditNumber: {halign: 'center'},
                    emitted: {halign: 'center'},
                    attended: {halign: 'center'},
                    recommendations: {halign: 'center'},
                    observations: {halign: 'center'},
                    responsibilities: {halign: 'center'},
                    notAttended: {halign: 'center'},
                    progress: {halign: 'center'}
                },
                drawHeaderRow: function (row, data) {
                    doc.setFontStyle('bold');
                    doc.setFontSize(8);
                    doc.setTextColor(255,255,255);
                    doc.setFillColor(158, 158, 158);
                    doc.rect(data.settings.margin.left, data.cursor.y, data.table.width, 20, 'F');
                    doc.autoTableText("Acciones", data.settings.margin.left + data.table.columns[0].width + data.table.columns[1].width + data.table.columns[2].width, data.cursor.y + row.height / 3, {
                        halign: 'center',
                        valign: 'middle'
                    });
                    doc.autoTableText(
                        "Pendientes",
                        data.settings.margin.left +
                        data.table.columns[0].width +
                        data.table.columns[1].width +
                        data.table.columns[2].width +
                        data.table.columns[3].width +
                        data.table.columns[4].width +
                        data.table.columns[5].width,
                        data.cursor.y + row.height / 3, {
                        halign: 'center',
                        valign: 'middle'
                    });

                    data.cursor.y += 20;

                },
                drawHeaderCell: function (cell, data) {

                },
                beforePageContent: function (data) {
                    doc.text("Observaciones por órgano fiscalizador", 40, 30);
                }
            });
            doc.save('table.pdf')
        }


        function makePDFObject() {

            var finalObject = vm.supervisoryData.slice();
            var tempObjectF = undefined;
            var tempObjectS = undefined;


            if (vm.selectedEntity !== undefined) {
                tempObjectS = vm.entityTypeData.slice();
                for (var i = 0; i < tempObjectS.length; i++) {
                    if (tempObjectS[i].aux === vm.selectedEntity) {
                        i++;
                        for (var j = 0; j < vm.entityData.length; j++) {
                            tempObjectS.splice(i, 0, vm.entityData[j]);
                            i++;
                        }
                        break;
                    }
                }
            }


            if (vm.selectedEntityType !== undefined) {
                if (tempObjectS === undefined) {
                    tempObjectS = vm.entityTypeData.slice();
                }
                tempObjectF = vm.yearData.slice();
                for (var i = 0; i < tempObjectF.length; i++) {
                    if (tempObjectF[i].aux === vm.selectedEntityType) {
                        i++;
                        for (var j = 0; j < tempObjectS.length; j++) {
                            tempObjectF.splice(i, 0, tempObjectS[j]);
                            i++;
                        }
                        break;
                    }
                }
            }


            if (vm.selectedYear !== undefined) {
                if (tempObjectF === undefined) {
                    tempObjectF = vm.yearData.slice();
                }
                for (var i = 0; i < finalObject.length; i++) {
                    if (finalObject[i].id === vm.selectedYear) {
                        i++;
                        for (var j = 0; j < tempObjectF.length; j++) {
                            finalObject.splice(i, 0, tempObjectF[j]);
                            i++;
                        }
                        break;
                    }
                }
            }

            return finalObject;
        }


    }
})();
