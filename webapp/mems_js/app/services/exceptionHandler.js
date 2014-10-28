/**
 * Created by michael on 10/23/14.
 */
/**
 * Created by michael on 10/11/14.
 */
(function () {

    var injectParams = ['$http', '$rootScope', '$route', 'memsAppConfig', 'workingContext'];

    var factory = function ($http, $rootScope, $route, memsAppConfig, workingContext) {
        return factory;
    };

    factory.onError = function(response){
        console.log(response);
    }

    factory.$inject = injectParams;
    app.factory('exceptionHandler', factory);


}());