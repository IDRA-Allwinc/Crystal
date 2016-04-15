window.angJsDependencies.push('ui.bootstrap');
(function () {
    "use strict";
    angular
        .module(window.constMainApp)
        .controller('reportController', reportController);


    reportController.$inject = ["$http", "$sce", "$window"];

    function reportController($http, $sce, $window) {
        var vm = this;
        vm.m = {};
        vm.init = init;
        vm.selectSupervisory = selectSupervisory;
        vm.selectYear = selectYear;
        vm.selectEntityType = selectEntityType;
        vm.selectEntity = selectEntity;
        vm.exportToPDF = exportToPDF;
        vm.openDetail = openDetail;

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
            }).then(function (response) {
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

        function openDetail(id, type, url) {
            var params = [];
            params["idParam"] = id;
            params["detailType"] = type;
            for (var key in params) {
                if (params.hasOwnProperty(key)) {
                    var param = params[key] || '';
                    url = url.replace(key, param);
                }
            }

            try {
                window.open(url, '_blank');
            } catch (e) {
                window.open(url, '_blank');
            }
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
                    doc.setFontSize(8);
                    doc.rect(data.settings.margin.left, data.cursor.y, data.table.width, 20, 'S');
                    doc.autoTableText("Séctor Economía\nSituación de acciones emitidas por las ASF", data.settings.margin.left + data.table.width / 2, data.cursor.y + row.height / 3, {
                        halign: 'center',
                        valign: 'middle'
                    });


                    doc.setFontStyle('bold');
                    doc.setFontSize(8);
                    doc.setTextColor(255, 255, 255);
                    doc.setFillColor(158, 158, 158);
                    doc.rect(data.settings.margin.left, data.cursor.y + 20, data.table.width, 20, 'F');
                    doc.autoTableText("Acciones", data.settings.margin.left + data.table.columns[0].width + data.table.columns[1].width + data.table.columns[2].width, data.cursor.y + 20 + row.height / 3, {
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
                        data.cursor.y + 20 + row.height / 3, {
                            halign: 'center',
                            valign: 'middle'
                        });

                    data.cursor.y += 40;

                },
                drawHeaderCell: function (cell, data) {

                },
                beforePageContent: function (data) {
                    doc.setFontSize(12);
                    doc.text("Observaciones por órgano fiscalizador: " + vm.selectedSupervisory.name, 40, 40);
                },
                afterPageContent: function (data) {
                    doc.setFontSize(8);
                    var text = "R = Recomendación, PO = Pliego de observaciones, PRAS = Responsabilidad Administrativa Sancionatoria",
                        xOffset = (doc.internal.pageSize.width / 2) - (doc.getStringUnitWidth(text) * doc.internal.getFontSize() / 2);
                    doc.text(text, xOffset, data.cursor.y + 20);
                }
            });
            doc.save('table.pdf')
        }

        function makePDFObject() {

            var finalObject = angular.copy(vm.supervisoryData);
            var tempObjectF = undefined;
            var tempObjectS = undefined;


            if (vm.selectedEntity !== undefined) {
                tempObjectF = angular.copy(vm.entityTypeData);
                tempObjectS = angular.copy(vm.entityData);
                for (var j = 0; j < tempObjectF.length; j++) {
                    tempObjectF[j].id = "      "+tempObjectF[j].id;
                }
                for (var i = 0; i < tempObjectF.length; i++) {
                    if (tempObjectF[i].aux === vm.selectedEntity) {
                        i++;
                        for (var j = 0; j < tempObjectS.length; j++) {
                            tempObjectS[j].id = "         "+tempObjectS[j].id;
                            tempObjectF.splice(i, 0, tempObjectS[j]);
                            i++;
                        }
                        break;
                    }
                }
            }


            if (vm.selectedEntityType !== undefined) {
                if (tempObjectS === undefined) {
                    tempObjectS = angular.copy(vm.entityTypeData);
                    for (var j = 0; j < tempObjectS.length; j++) {
                        tempObjectS[j].id = "     - "+tempObjectS[j].id;
                    }
                }else{
                    tempObjectS = tempObjectF;
                }
                tempObjectF = angular.copy(vm.yearData);
                for (var j = 0; j < tempObjectF.length; j++) {
                    tempObjectF[j].id = "   "+tempObjectF[j].id;
                }
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
                    tempObjectF = vm.yearData.slice(0);
                    for (var j = 0; j < tempObjectF.length; j++) {
                        tempObjectF[j].id = "   "+tempObjectF[j].id;
                    }
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
