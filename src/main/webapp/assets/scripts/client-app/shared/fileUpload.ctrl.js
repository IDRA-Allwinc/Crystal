(function () {
    "use strict";
    angular
        .module(window.constMainApp)
        .controller('fileUploadController', fileUploadController);

    fileUploadController.$inject = ["$scope", "$rootScope", "FileUploader"];

    function fileUploadController($scope, $rootScope, FileUploader) {
        $scope.m = {};
        var th = this;
        var uploader = $scope.uploader = new FileUploader({
            url: "/"
        });
        th.uploader = uploader;
        // FILTERS

        uploader.filters.push({
            name: 'customFilter',
            fn: function (item /*{File|FileLikeObject}*/, options) {
                console.log('customFilter: ' + JSON.stringify(item));
                return this.queue.length < 1;
            }
        });

        // CALLBACKS
        uploader.onWhenAddingFileFailed = function (item /*{File|FileLikeObject}*/, filter, options) {
            //console.info('onWhenAddingFileFailed', item, filter, options);
        };
        uploader.onAfterAddingFile = function (fileItem) {
            $scope.m.msgError = "";
            //console.info('onAfterAddingFile', fileItem);
            fileItem.upload();
        };
        uploader.onAfterAddingAll = function (addedFileItems) {
            //console.info('onAfterAddingAll', addedFileItems);
        };
        uploader.onBeforeUploadItem = function (item) {
            console.info('onBeforeUploadItem', item);
        };
        uploader.onProgressItem = function (fileItem, progress) {
            //console.info('onProgressItem', fileItem, progress);
        };
        uploader.onProgressAll = function (progress) {
            //console.info('onProgressAll', progress);
        };
        uploader.onSuccessItem = function (fileItem, response, status, headers) {
            try {
                if (response.HasError) {
                    $scope.m.msgError = response.Message;
                } else {
                    //Exitoso
                }
            } catch (e) {
                $scope.m.msgError = "No fue posible subir el archivo, por favor revise que no haya excedido el tamaño máximo";
            } finally {
                fileItem.remove();
            }
            $rootScope.$broadcast("onFileUploadSuccess", response);
        };
        uploader.onErrorItem = function (fileItem, response, status, headers) {
            console.info('onErrorItem', fileItem, response, status, headers);
            $scope.m.msgError = "Error de red, por favor intente más tarde o su archivo es demasiado grande";
            fileItem.remove();
            $rootScope.$broadcast("onFileUploadError", response);
        };
        $scope.init = function() {
            th.uploader.url = $scope.m.url;
            th.uploader.formData = [{ type: $scope.m.type }];
        }
    }

})();
