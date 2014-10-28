/**
 * Created by michael on 10/11/14.
 */
(function () {

    var injectParams = ['$http', '$rootScope', 'memsAppConfig'];

    var factory = function ($http, $rootScope, memsAppConfig) {
        var serviceBase = memsAppConfig.api;
        var factory = {
            findDepartmentPath: "/departments"
        };


        factory.findDepartments = function(query){
            return $http.get(serviceBase + factory.findDepartmentPath + (query ? "?filter=" + query : ""));
        };

        return factory;
    };

    factory.$inject = injectParams;
    app.factory('hospitalDepartmentService', factory);


}());