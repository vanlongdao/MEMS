/**
 * Created by michael on 10/11/14.
 */
(function () {

    var injectParams = ['$http', '$rootScope', 'memsAppConfig'];

    var factory = function ($http, $rootScope, memsAppConfig) {
        var serviceBase = memsAppConfig.api;
        var factory = {
            getAllDevicesPath: "/device",
            getAllDevicesOfDepartmentPath : "/device/department/{deptCode}",
            getAllScheduleOfDevice: "/device/{devCode}/actions"
        };

        factory.findDevices = function (query, selectedDepartment) {
            if(!selectedDepartment){
            return $http.get(serviceBase + factory.getAllDevicesPath + (!query ? "": "?filter=" + query));
            }
            else {
                return $http.get(serviceBase + factory.getAllDevicesOfDepartmentPath.replace("{deptCode}", selectedDepartment.deptCode) + (!query ? "": "?filter=" + query));
            }
        };

        factory.findActionsByDevice = function(query, selectedDevice){
            return $http.get(serviceBase + factory.getAllScheduleOfDevice.replace("{devCode}", selectedDevice.devCode) + (!query ? "" : "?filter=" + query));
        }

        return factory;
    };

    factory.$inject = injectParams;
    app.factory('deviceService', factory);


}());