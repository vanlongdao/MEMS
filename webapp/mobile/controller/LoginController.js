var scope,http,window,localStorage,sessionStorage,ngStorage;

//var app = angular.module("Login",['ngStorage']);
app.controller("LoginController", function($scope,$http,$window,$localStorage){
	$scope.login = {
		username:"",
		password:""
	}
			
	scope = $scope;
	http = $http;
	window = $window;
	localStorage = $localStorage;
	
	//when page load, check if user logged in or not 
	//if user logged in other tab, create new session and increase pagecount, otherwise set session = 'false'
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
	//checkLogin();
	
	$scope.submit = submit;
});

//call when user refresh page (by press F5 or reload page) or close one tab, browser
//if pageCount equal 0 mean user close all tab or browser then delete all session and store
angular.element(window).bind("beforeunload", function(){
	if(localStorage.userInfo && window.sessionStorage.token == 'true' ){
		localStorage.pageCount = parseInt(localStorage.pageCount, 10) - 1;
		if(localStorage.pageCount == 0){
			delete localStorage.userInfo;
			delete localStorage.pageCount;
		}
	}
	
});

function submit(){
	if(!localStorage.userInfo || !window.sessionStorage.token || window.sessionStorage.token == 'false'){
		http({
			method:'POST',
			url:'http://localhost:8080/mems/rest/auth/login',
			data:{
				username:scope.login.username,
				password:scope.login.password
			}
		}).success(function(data,status){
			if(status == 200 && data){
				alert("Login successful");
				scope.login.password = "";
				scope.login.userID = data.data.userID;
				scope.login.officeCode = data.data.officeCode;
				scope.login.isLogged = true;
				scope.login.accountType = data.data.accounttype;
				scope.login.psnCode = data.data.psnCode;
				window.sessionStorage.token = 'true';
				localStorage.userInfo = JSON.stringify(scope.login);
				localStorage.cache = JSON.stringify(scope.login);
				if(!localStorage.pageCount){
					localStorage.pageCount = 1;
				} else {
					localStorage.pageCount = parseInt(localStorage.pageCount, 10) + 1;
				}
				
				directPage(data.data.accounttype);
			}		
				
		}).error(function(data,status){
			window.sessionStorage.token = 'false';
			alert(data.messageCode);
		});
	}
	
}

function directPage(accountType){
	switch (accountType){
		case 1: window.location.href = "IRR_Parent.html"; break;
		case 2: window.location.href = "#"; break;
		case 3: window.location.href = "#"; break;
		case 4: window.location.href = "#"; break;
		case 5: window.location.href = "#"; break;
		case 6: window.location.href = "#"; break;
		case 7: window.location.href = "#"; break;
		case 8: window.location.href = "#"; break;
		case 9: window.location.href = "#"; break;
		case 0: window.location.href = "IRR_Parent.html"; break;
		default: break;
	}
}