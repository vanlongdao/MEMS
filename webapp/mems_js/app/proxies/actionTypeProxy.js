/**
 * Created by michael on 10/23/14.
 */

'use strict';

var proxy = angular.module('mems.proxies.ActionType', ['ngResource']);
proxy.factory('ActionType', ['$resource', 'memsAppConfig',
    function ($resource, memsAppConfig) {
        return $resource(memsAppConfig.api + '/actions/actionTypes', {}, {
            query: function () {
                return {method: 'GET', params: {}, isArray: true};
            }
        });
    }]);