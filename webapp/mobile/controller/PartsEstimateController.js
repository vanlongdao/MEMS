/**
 * 
 */
var scope,http,localStorage,config;
app.controller("PartsEstimateController", function($scope, $http,$localStorage,$sce,myconfig){
	
	scope = $scope;
	http = $http;
	localStorage = $localStorage;
	
	$scope.trustAsHtml = function(value) {
		return $sce.trustAsHtml(value);
	};
	scope = $scope;
	http = $http;
	localStorage = $localStorage;
	
	config = myconfig;
	
	checkLogin();
});

function getPartsEstimate(){
	http({
		method:'POST',
		url:config.url + 'device/getPartsEstimate',
		data:angular.fromJson(window.sessionStorage.actionLog).actionCode 
	}).success(function(data,status){
		
	}).error(function(data,status){
		
	});
}