var app = angular.module("Test",[]);
app.controller("TestController", function($scope,$rootScope,$window){
	$rootScope.login = {
			username:"",
			password:"",
			isLogin:false
		}
	if($window.sessionStorage.token){
		$scope.isLogged = true;
	}
	$scope.test = ""
});