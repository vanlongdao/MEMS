var scope,http,window,localStorage;

//var app = angular.module('IRR_Parent',['ngStorage']);
app.controller('IRR_ParentController', function($scope,$http,$window,$localStorage){
	$scope.login = {
		username:"",
		password:""
	}
	
	scope = $scope;
	http = $http;
	window = $window;
	localStorage = $localStorage;
	
	if((localStorage.userInfo && (window.sessionStorage.token == 'false' || !window.sessionStorage.token))){
		
		$scope.login = JSON.parse(localStorage.userInfo);
		$scope.login.isLogged = true;
		window.sessionStorage.token = 'true';
		localStorage.pageCount = parseInt(localStorage.pageCount, 10) + 1;
		directPage(localStorage.userInfo.accountType);
	} else {
		if(localStorage.userInfo && window.sessionStorage.token == 'true'){
			localStorage.pageCount = parseInt(localStorage.pageCount, 10) + 1;
		} else {
			if(window.sessionStorage.token == 'true' && !localStorage.userInfo && localStorage.cache){
				$scope.login = JSON.parse(localStorage.cache);
				localStorage.userInfo = localStorage.cache;
				window.sessionStorage.token = 'true';
				if(localStorage.pageCount)
					localStorage.pageCount = localStorage.pageCount + 1;
				else
					localStorage.pageCount = 1;
			} else {
				$scope.login.isLogged = false;
				window.sessionStorage.token = 'false';
			}
		}  
	}
	$scope.addNew = addNewDevice;
	$scope.editDevice = editDevice;
});

angular.element(window).bind("beforeunload", function(){
	if(localStorage.userInfo && window.sessionStorage.token == 'true' ){
		localStorage.pageCount = parseInt(localStorage.pageCount, 10) - 1;
		if(localStorage.pageCount == 0){
			delete localStorage.userInfo;
			delete localStorage.pageCount;
		}
	}
	
});

function addNewDevice(){
	
}

function editDevice(){
	http({
		method:'POST',
		url:'http://localhost:8080/mems/rest/device/getactionlog',
		data:'1'
	}).success(function(data,status){
		if(data && status == 200){
			//window.sessionStorage.actionLog = data;
			window.sessionStorage.actionLog = angular.toJson(data.data);
			window.location.href = 'RepairRequest.html';
		}
	}).error(function(data,status){
		alert('');
	});
}