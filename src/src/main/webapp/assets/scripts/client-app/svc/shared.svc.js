﻿(function () {
    "use strict";
    angular
        .module(window.constMainApp)
        .service('sharedSvc', sharedSvc);

    sharedSvc.$inject = ["$timeout", "$q", "$interval", "connSvc"];

    function sharedSvc($timeout, $q, $interval, connSvc) {
        var vm = this;
        var dlgProcessing = $('#ProcessingDlgId');
        //Dialogo para mensajes con acciones de éxito, información, advertencia o error
        var dlgMsgBox = $('#MessageBoxDlgId');
        vm.cfgProc = {toProcessing: undefined, procCount: 0};
        vm.cfgMsg = {Title: "", Message: "", Type: ""};
        vm.respMsg = {};

        vm.initCatalog = initCatalog;
        vm.onProcTimeOut = onProcTimeOut;
        vm.showProcessing = showProcessing;
        vm.hideProcessing = hideProcessing;
        vm.showDlg = showDlg;
        vm.showMsg = showMsg;
        vm.showConf = showConf;
        vm.hideMsg = hideMsg;
        vm.connSvc = connSvc;

        function initCatalog(lstCatalog, m, keyItem, keyId) {
            var item = lstCatalog[0];

            if (m[keyId] !== undefined && m[keyId] > 0) {
                item = undefined;
                for (var i = 0; i < lstCatalog.length; i++) {
                    var itemCat = lstCatalog[i];
                    if (itemCat.Key === m[keyId]) {
                        item = itemCat;
                        break;
                    }
                }

                if (item === undefined) {
                    item = lstCatalog[0];
                }
            }

            m[keyId] = item.Key;
            m[keyItem] = item;
        };


        function onProcTimeOut() {
            vm.cfgProc.procCount++;
            if (vm.cfgProc.procCount > 100)
                vm.cfgProc.procCount = 0;
            vm.cfgProc.toProcessing = $timeout(vm.onProcTimeOut, 150);
        };

        function showProcessing() {
            dlgProcessing.modal('show');
            vm.cfgProc.procCount = 1;
            vm.cfgProc.toProcessing = $timeout(vm.onProcTimeOut, 400);
        };

        function hideProcessing() {
            $timeout.cancel(vm.cfgProc.toProcessing);
            dlgProcessing.modal('hide');
        };

        function showDlg(cfg) {
            vm.cfgMsg = cfg;
            var def = $q.defer();

            $timeout(function () {
                dlgMsgBox.modal('show');
                dlgMsgBox.on('hidden.bs.modal', function () {
                    if (vm.respMsg.vm.IsOk === true) {
                        def.resolve();
                    }
                    else {
                        def.reject();
                    }
                });
            }, 1);

            return def.promise;
        };

        function showMsg(cfg) {
            dlgMsgBox = $('#MessageBoxDlgId');
            return vm.showDlg(cfg);
        };


        function showConf(cfg) {
            dlgMsgBox = $('#ConfirmationDlgId');
            return vm.showDlg(cfg);
        };

        function hideMsg(rMsg) {
            vm.respMsg = rMsg;
            dlgMsgBox.modal('hide');
        };

        vm.showingSessionDlg = false;
        vm.isExtended = false;
        vm.interval = 1000; //intervalo en milisegundos
        vm.count = 120; //contador en segundos
        vm.updateCount = updateCount;
        vm.updateMessage = updateMessage;
        vm.logoutUrl = "";
        vm.extendUrl = "";
        vm.showCountdown = showCountdown;
        vm.extend = extend;
        vm.countdownMessage = 'La sesi&oacute;n est&aacute; por terminar y el sistema se cerrar&aacute; en <b>$count segundos</b>. <br/>&iquest;Desea extender su sesi&oacute;n?';
        vm.doLogout = doLogout;
        vm.doCountDown = doCountDown;


        function doCountDown() {

            $timeout(function () {
                try {
                    vm.updateCount();
                } catch (e) {

                }
            }, vm.interval).then(function () {
                if (vm.isExtended == true) {
                    vm.isExtended = false;
                    return;
                } else if (vm.count == 0) {
                    vm.doLogout();
                    return;
                } else {
                    vm.doCountDown();
                }
            });
        }

        function showCountdown(cfg) {

            vm.showingSessionDlg = true;
            dlgMsgBox = $('#CountDownDlgId');
            vm.cfgMsg = cfg;
            vm.updateMessage();

            var def = $q.defer();


            $timeout(function () {
                dlgMsgBox.modal('show');

                vm.doCountDown();

                dlgMsgBox.on('hidden.bs.modal', function () {

                    dlgMsgBox.off();
                    vm.showSessionDlg = false;

                    if (vm.respMsg.vm.IsOk === true) {
                        vm.extend();
                        def.resolve();
                    } else {
                        vm.doLogout();
                        def.reject();
                    }

                });

            }, 1);
            return def.promise;
        }

        function doLogout() {
            vm.count = 120;
            if (window.top !== window.self && window.top.logout !== undefined) {
                window.top.logout();
            }
        }


        function updateCount() {
            if (vm.count > 0) {
                vm.count--;
                vm.updateMessage();
                return true;
            }
            else {
                return false;
            }
        }

        function updateMessage() {
            vm.cfgMsg.message = vm.countdownMessage.replace("$count", vm.count.toString());
        };

        function extend() {
            vm.connSvc.post(vm.extendUrl, vm, undefined, true).then(function (res) {
                if (res.hasError == false && res.returnData == true) {
                    vm.showingSessionDlg = false;
                    vm.isExtended = true;
                    vm.count = 120;
                }
            });
        }

    }
})();

