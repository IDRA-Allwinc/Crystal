window.showUpsertParams = function (param, divScope, urlToGo, jqGridToUse) {
    var scope = angular.element($(divScope)).scope();
    scope.vm.show(param, urlToGo).
        then(function () { if(jqGridToUse) $(jqGridToUse).bootstrapTable('refresh', 'showLoading'); });
};

window.showUpsert = function (id, divScope, urlToGo, jqGridToUse) {
    var scope = angular.element($(divScope)).scope();
    scope.vm.show({ id: id }, urlToGo).
        then(function () { if(jqGridToUse) $(jqGridToUse).bootstrapTable('refresh', 'showLoading'); });
};

window.showCreate = function (data, divScope, urlToGo, jqGridToUse) {
    var scope = angular.element($(divScope)).scope();
    scope.vm.show(data, urlToGo).
        then(function () { if(jqGridToUse) $(jqGridToUse).bootstrapTable('refresh', 'showLoading'); });
};



window.showConfirmService = function (id, divScope, urlToGo, title, msg, jqGridToUse, callback, innerScp) {
    var scope = angular.element($(divScope)).scope();
    scope.vm.doConfirm({ id: id }, urlToGo, title, msg, innerScp).
        then(function () {
            if (jqGridToUse !== undefined)
                $(jqGridToUse).bootstrapTable('refresh', 'showLoading');

            if (callback !== undefined) {
                callback();
            }
        });
};


window.showConfirmCancelDocument = function (id, folio, divScope, urlToGo, jqGridToUse) {
    var scope = angular.element($(divScope)).scope();
    scope.vm.doCancelDocument({ uuid: id }, urlToGo, folio).
        then(function () { $(jqGridToUse).bootstrapTable('refresh', 'showLoading'); });
};

window.showObsolete = function (id, divScope, urlToGo, jqGridToUse, callback, innerScp) {
    var scope = angular.element($(divScope)).scope();
    scope.vm.doObsolete({ id: id }, urlToGo, innerScp).
        then(function () {
            if (jqGridToUse !== undefined)
                $(jqGridToUse).bootstrapTable('refresh', 'showLoading');
            if (callback !== undefined) {
                callback();
            }
        });
};

window.showObsoleteParam = function (params, divScope, urlToGo, jqGridToUse, callback, innerScp, afterResolveFunction) {
    var scope = angular.element($(divScope)).scope();
    scope.vm.doObsolete(params, urlToGo, innerScp, afterResolveFunction).
        then(function () {
            if (jqGridToUse !== undefined)
                $(jqGridToUse).bootstrapTable('refresh', 'showLoading');
            if (callback !== undefined) {
                callback();
            }
        });
};

window.showDrop = function (id, divScope, urlToGo, jqGridToUse, callback, innerScp) {
    var scope = angular.element($(divScope)).scope();
    scope.vm.doDrop({ id: id }, urlToGo, innerScp).
        then(function () {
            if (jqGridToUse !== undefined)
                $(jqGridToUse).bootstrapTable('refresh', 'showLoading');
            if (callback !== undefined) {
                callback();
            }
        });
};
window.showSelectAll = function(divScope,jqGridToUse) {
    var scope = angular.element($(divScope).scope());
    alert(divScope);
    scope.vm.askForAll(jqGridToUse);
}


window.showModalFormDlg = function(divModalid, formId) {
    var dlgCat = $(divModalid);
    dlgCat.modal('show');

    $.validator.unobtrusive.parse(formId);

    $(divModalid).injector().invoke(function ($compile, $rootScope) {
        $compile($(divModalid))($rootScope);
        $rootScope.$apply();
    });

    var scope = angular.element(dlgCat).scope();
    scope.up.setDlg(dlgCat);
};

window.goToUrlMvcUrl = function (url, params) {

    for (var key in params) {
        if (params.hasOwnProperty(key)) {
            var param = params[key] || '';
            url = url.replace(key, param);
        }
    }

    try {
        window.location.replace(url);
    } catch (e) {
        window.location = url;
    }
};


window.goToNewWnd = function (url, params) {
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
};


window.sendPostAction = function(id, divScope, urlToGo, innerScp, showSuccess) {
    var scope = angular.element($(divScope)).scope();
    scope.vm.sendPostAction({ id: id }, urlToGo, innerScp, showSuccess);
};

window.padNum = function(n, width, z) {
    z = z || '0';
    n = n + '';
    return n.length >= width ? n : new Array(width - n.length + 1).join(z) + n;
};

window.formatDt = function (dt) {
    return window.padNum(dt.getMonth() + 1, 2) + "/" + window.padNum(dt.getDate(), 2) + "/" + dt.getFullYear();
};

window.formatDtA = function (dt) {
    return dt.getFullYear() + "/" + window.padNum(dt.getMonth() + 1, 2) + "/" + window.padNum(dt.getDate(), 2);
};

Array.prototype.pushArray = function (arr) {
    this.push.apply(this, arr);
};

window.initCatalog = function (lstCatalog, catalogId) {
    if (lstCatalog === undefined || lstCatalog === null || lstCatalog.length === 0)
        return undefined;

    if (catalogId === undefined) {
        return lstCatalog[0];
    }

    var catalog;
    for (var i = 0, len = lstCatalog.length; i < len; i++) {
        catalog = lstCatalog[i];
        if (catalog.id === catalogId)
            return catalog;
    }

    return lstCatalog[0];
};

window.rowStyle = function(row, index) {
    if (row.color)
        return {
            classes: row.color
        };
};

//targetDateYYYYmmDD es un string en formato dd/mm/yyyy, regresa yyyy/mm/dd
window.parseFormatDate= function(targetDateDDmmYYYY){
return targetDateDDmmYYYY.split("/").reverse().join("/");
};