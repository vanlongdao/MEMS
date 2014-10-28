var app = angular.module("MyApp",['ngStorage','ng-polymer-elements','ui.select','smart-table']);
app.factory('AuthenticationService', function(){
	var auth = {
		username:'',
		password:'',
		isLogged: false
	}
	return auth;
});

app.factory('TokenInterceptor', function(){
	return {
		request: function(config){
			config.headers = config.headers || {};
			if(window.sessionStorage.token == 'true'){
				config.headers.Authorization =  window.sessionStorage.token;
			}
			return config;
		},
		response: function(response){
			return response || q.when(response);
		}
	}
});

app.config(function ($httpProvider) {
    $httpProvider.interceptors.push('TokenInterceptor');
});

function checkLogin(){
	if((localStorage.userInfo && (window.sessionStorage.token == 'false' || !window.sessionStorage.token))){
		
		scope.login = JSON.parse(localStorage.userInfo);
		scope.login.isLogged = true;
		window.sessionStorage.token = 'true';
		localStorage.pageCount = parseInt(localStorage.pageCount, 10) + 1;
		directPage(localStorage.userInfo.accountType);
	} else {
		if(localStorage.userInfo && window.sessionStorage.token == 'true'){
			localStorage.pageCount = parseInt(localStorage.pageCount, 10) + 1;
		} else {
			if(window.sessionStorage.token == 'true' && !localStorage.userInfo && localStorage.cache){
				scope.login = JSON.parse(localStorage.cache);
				localStorage.userInfo = localStorage.cache;
				window.sessionStorage.token = 'true';
				if(localStorage.pageCount)
					localStorage.pageCount = localStorage.pageCount + 1;
				else
					localStorage.pageCount = 1;
			} else {
				scope.login.isLogged = false;
				window.sessionStorage.token = 'false';
			}
		}  
	}
}

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

app.constant('myconfig',
{
	url:'http://localhost:8080/mems/rest/'
});