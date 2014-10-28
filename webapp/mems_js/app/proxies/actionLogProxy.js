var actionLogProxy = angular.module('mems.proxies.ActionLog', ['ngResource']);
actionLogProxy.factory('ActionLog', ['$resource', 'memsAppConfig',
    function($resource, memsAppConfig){
        return $resource(memsAppConfig.api + '/actions/:actionCode', {}, {
            query: function(){
                return {method:'GET', params:{}, isArray:true};
            }


        });
    }]);