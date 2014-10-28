/**
 * Created by michael on 10/23/14.
 */

'use strict';

var proxy = angular.module('mems.proxies.HospitalDepartment', ['ngResource']);
proxy.factory('HospitalDepartment', ['$resource', 'memsAppConfig',
    function ($resource, memsAppConfig) {
        return $resource(memsAppConfig.api + '/departments/:departCode/:action', {departCode: '@departCode', action: '@action'}, {
            query: function () {
                return {method: 'GET', params: {}, isArray: true};
            },
            get: function(departCode){
                return {method: 'GET', params: {departCode:departCode}, isArray: false};
            },
            getPersons: function(departCode){
                return {method:'GET', params:{departCode: departCode, action:'persons'}, isArray:true};
            },
            findPersons: function(departCode, query){
                return {method:'GET', params:{departCode: departCode, action:'persons', filter:query}};
            }
        });
    }]);