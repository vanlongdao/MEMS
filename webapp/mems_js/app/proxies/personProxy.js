/**
 * Created by michael on 10/23/14.
 */

'use strict';

var proxy = angular.module('mems.proxies.Person', ['ngResource']);
proxy.factory('Person', ['$resource', 'memsAppConfig',
    function ($resource, memsAppConfig) {
        return $resource(memsAppConfig.api + '/persons/:psnCode', {psnCode:'@psnCode'}, {
            query: function () {
                return {method: 'GET', params: {}, isArray: true};
            }
        });
    }]);