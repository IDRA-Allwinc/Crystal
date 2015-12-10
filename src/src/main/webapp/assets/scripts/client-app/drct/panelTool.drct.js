angular
.module(window.constMainApp)
.directive('paneltool', ["$compile", "$timeout", function ($compile, $timeout) {
    var templates = {
        /* jshint multistr: true */
        collapse: "<a href='#{{panelId}}' panel-collapse='' tooltip='Minimizar panel' ng-click='{{panelId}} = !{{panelId}}'> \
                <em ng-show='{{panelId}}' class='fa fa-plus {{colorCss}}'></em> \
                <em ng-show='!{{panelId}}' class='fa fa-minus {{colorCss}}'></em> \
              </a>",
        dismiss: "<a href='#' panel-dismiss='' tooltip='Cerrar panel'>\
               <em class='fa fa-times'></em>\
             </a>",
        refresh: "<a href='#' panel-refresh='' data-spinner='{{spinner}}' tooltip='Refresar panel'>\
               <em class='fa fa-refresh'></em>\
             </a>"
    };

    function getTemplate(elem, attrs) {
        var temp = '';
        attrs = attrs || {};
        if (attrs.toolCollapse) {
            var color = elem.parent().parent().attr('colorcss');
            temp += templates.collapse.replace(/{{panelId}}/g, (elem.parent().parent().attr('id'))).replace(/{{colorCss}}/g, (color ? color : ''));
        }
        if (attrs.toolDismiss) {
            temp += templates.dismiss;
        }
        if (attrs.toolRefresh) {
            temp += templates.refresh.replace(/{{spinner}}/g, attrs.toolRefresh);
        }
        return temp;
    }

    return {
        restrict: 'E',
        scope: false,
        link: function (scope, element, attrs) {

            var tools = scope.panelTools || attrs;

            $timeout(function () {
                element.html(getTemplate(element, tools)).show();
                $compile(element.contents())(scope);

                element.addClass('pull-right');
            });

        }
    };
}])
.directive('panelDismiss', ["$q", "Utils", function ($q, Utils) {
    'use strict';
    return {
        restrict: 'A',
        controller: ["$scope", "$element", function ($scope, $element) {
            var removeEvent = 'panel-remove',
                removedEvent = 'panel-removed';

            $element.on('click', function () {

                // find the first parent panel
                var parent = $(this).closest('.panel');

                removeElement();

                function removeElement() {
                    var deferred = $q.defer();
                    var promise = deferred.promise;

                    // Communicate event destroying panel
                    $scope.$emit(removeEvent, parent.attr('id'), deferred);
                    promise.then(destroyMiddleware);
                }

                // Run the animation before destroy the panel
                function destroyMiddleware() {
                    if (Utils.support.animation) {
                        parent.animo({ animation: 'bounceOut' }, destroyPanel);
                    }
                    else destroyPanel();
                }

                function destroyPanel() {

                    var col = parent.parent();
                    parent.remove();
                    // remove the parent if it is a row and is empty and not a sortable (portlet)
                    col
                      .filter(function () {
                          var el = $(this);
                          return (el.is('[class*="col-"]:not(.sortable)') && el.children('*').length === 0);
                      }).remove();

                    // Communicate event destroyed panel
                    $scope.$emit(removedEvent, parent.attr('id'));

                }
            });
        }]
    };
}])
.directive('panelCollapse', ['$timeout', function ($timeout) {
    'use strict';

    window.statePanels = undefined;

    return {
        restrict: 'A',
        scope: false,
        controller: ["$scope", "$element", function ($scope, $element) {

            // Prepare the panel to be collapsible
            var $elem = $($element),
                parent = $elem.closest('.panel'), // find the first parent panel
                panelId = parent.attr('id');


            // Load the saved state if exists
            var currentState = loadPanelState(panelId);
            if (typeof currentState !== 'undefined') {
                $timeout(function () {
                    $scope[panelId] = currentState;
                },
                  10);
            }

            // bind events to switch icons
            $element.bind('click', function () {
                savePanelState(panelId, !$scope[panelId]);
            });
        }]
    };

    function savePanelState(id, state) {
        if (!id) return false;

        var data = undefined;
        var info = window.statePanels;
        if (info == undefined)
            data = angular.fromJson(info);
        if (!data) { data = {}; }
        data[id] = state;
        window.statePanels = angular.toJson(data);
        return false;
    }

    function loadPanelState(id) {
        if (!id) return false;
        try {
            var info = window.statePanels;
            if (info == undefined)
                return false;
            var data = angular.fromJson(info);
        } catch (e) {
            return false;
        } 
        if (data) {
            return data[id];
        }
        return false;
    }

}])
.directive('panelRefresh', ["$q", function ($q) {
    'use strict';

    return {
        restrict: 'A',
        scope: false,
        controller: ["$scope", "$element", function ($scope, $element) {

            var refreshEvent = 'panel-refresh',
                whirlClass = 'whirl',
                defaultSpinner = 'standard';


            // catch clicks to toggle panel refresh
            $element.on('click', function () {
                var $this = $(this),
                    panel = $this.parents('.panel').eq(0),
                    spinner = $this.data('spinner') || defaultSpinner
                ;

                // start showing the spinner
                panel.addClass(whirlClass + ' ' + spinner);

                // Emit event when refresh clicked
                $scope.$emit(refreshEvent, panel.attr('id'));

            });

            // listen to remove spinner
            $scope.$on('removeSpinner', removeSpinner);

            // method to clear the spinner when done
            function removeSpinner(ev, id) {
                if (!id) return;
                var newid = id.charAt(0) == '#' ? id : ('#' + id);
                angular
                  .element(newid)
                  .removeClass(whirlClass);
            }
        }]
    };
}]);
