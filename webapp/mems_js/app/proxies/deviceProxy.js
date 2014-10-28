/**
 * Created by michael on 10/23/14.
 */
var deviceProxy = angular.module('mems.proxies.Device', ['ngResource']);

deviceProxy.factory('Device', ['$resource', 'memsAppConfig',
    function($resource, memsAppConfig){
        return $resource(memsAppConfig.api + '/device/:devCode', {}, {
            query: function(){
                return {method:'GET', params:{}, isArray:true};
            }
        });
    }]);