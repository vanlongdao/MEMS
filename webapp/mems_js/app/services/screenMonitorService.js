/**
 * Created by michael on 10/17/14.
 */
/**
 * Created by michael on 10/11/14.
 */
(function () {

    var injectParams = ['$http', '$rootScope', '$location', 'localStorageService'];

    var factory = function ($http, $rootScope, $location) {
        $rootScope.$on("openDevice", function(event, selectedDevice){
            $location.path("/device");
            localStorageService.set("device", selectedDevice);
        } );

        return factory;
    };

    factory.$inject = injectParams;
    app.factory('screenMonitorService', factory);

}());