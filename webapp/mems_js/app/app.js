'use strict';

// Declare app level module which depends on views, and components
var dependencies = ['ngRoute','ngLocale','memsLocale', 'ngCookies','LocalStorageModule', 'ngResource',
  'mems.proxies.Device', 'mems.proxies.ActionLog','mems.proxies.ActionType','mems.proxies.HospitalDepartment',
   'ui.select', 'ui.bootstrap', 'ui.grid', 'smart-table', 'ng-polymer-elements', 'config' ];
var app = angular.module('memsJsApp', dependencies);


// Config route table
app.config(['$routeProvider', function($routeProvider) {

  $routeProvider.when('/login', {
    templateUrl: 'login/login.html',
    controller: 'mems.controller.login'
  });

  $routeProvider.when('/dashboard', {templateUrl: 'dashboard/dashboard.html', controller:'mems.controller.dashboard', secure:true});

  $routeProvider.when('/repairrequest/:actionCode?', {templateUrl:'repair_request/openRepairRequest.html', controller:'mems.controller.repairrequest', secure:true});

  $routeProvider.when('/ManageReplaceParts', {templateUrl:'ManageReplaceParts/ManageReplaceParts.html', controller:'mems.controller.replacedparts', secure:true});

  $routeProvider.when('/ManageChecklist', {templateUrl:'ManageChecklist/ManageChecklist.html', controller:'mems.controller.checklist', secure:true});

  $routeProvider.when('/ManagePartsEstimation', {templateUrl:'ManagePartsEstimation/ManagePartsEstimation.html', controller:'mems.controller.partsestimation', secure:true});

  $routeProvider.when('/ManagePartsOrder', {templateUrl:'ManagePartsOrder/ManagePartsOrder.html', controller:'mems.controller.partsorder', secure:true});

  $routeProvider.otherwise({redirectTo: '/login'});
}]);

// Config local storage
app.config(['memsAppConfig', 'localStorageServiceProvider', function(memsAppConfig, localStorageServiceProvider){
  localStorageServiceProvider.setPrefix('mems');
  localStorageServiceProvider.setStorageCookieDomain(memsAppConfig.host);
  localStorageServiceProvider.setStorageType('sessionStorage');
}]);

app.run(['$rootScope', 'workingContext','messages','$sce', function($rootScope, workingContext, messages, $sce){
  $rootScope.messages = messages;

  $rootScope.trustAsHtml = function(value) {
    return $sce.trustAsHtml(value);
  };

  var currentContext = function(){
    return workingContext;
  };

  $rootScope.logout = function(){
    $rootScope.$broadcast("loggingOut");
  };
  $rootScope.context = currentContext();

}
]);

