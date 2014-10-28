/**
 * Created by michael on 10/17/14.
 */
(function () {

    var injectParams = ['$http', '$rootScope', 'ActionLog'];

    var factory = function ($http, $rootScope, ActionLog) {

        factory.getAllActions = function (query) {
            return ActionLog.query();
        };

        return factory;
    };

    factory.$inject = injectParams;
    app.factory('actionLogService', factory);


}());