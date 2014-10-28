/**
 * Created by michael on 10/15/14.
 */
(function(){
    'use strict';
    var injectModules = ['$rootScope', 'localStorageService', '$location'];

    var contextFactory = function($rootScope, localStorageService, $location){
        var factory = {
        }

        factory.credential = function(){
            var credential = {
                "token": localStorageService.get("token") ? localStorageService.get("token") : "",
                "userId": localStorageService.get("userId") ? localStorageService.get("userId") : ""
            };
            return credential;
        };

        factory.openLogin = function(){
            $location.path("/login");
        };

        factory.openDashboard = function(){
            $location.path("/dashboard");
        };

        factory.onLoggedIn = function(event, restData){
            localStorageService.set("token", restData.token);
            localStorageService.set("userId", restData.userID);
            localStorageService.set("userInfo", restData);

            $rootScope.isLoggedIn = true;
            console.log(restData);
            $rootScope.userInfo = restData;

            factory.openDashboard();
        };

        factory.onLoggedOut = function(){
            localStorageService.clearAll();
            $rootScope.isLoggedIn = false;
            $rootScope.userInfo = {};
            factory.openLogin();
        };

        factory.isLoggedIn = function(){
            return !!localStorageService.get("token");
        };

        factory.userInfo = function(){
            return localStorageService.get("userInfo");
        };

        console.log("Start listenning");


        // Register listeners:
        $rootScope.$on("loggedOut", factory.onLoggedOut);
        $rootScope.$on("loggedIn", factory.onLoggedIn);

        //own security as well since client-based security is easily hacked
        $rootScope.$on("$routeChangeStart", function (event, next, current) {
            if (next && next.$$route && next.$$route.secure) {
                console.log("Route require login first");

                if (!factory.isLoggedIn()) {
                    console.log("Not loggged in, goto Login page");
                    factory.openLogin();
                }
            }
            else if(next && next.$$route && next.$$route.originalPath === "/login"){
                if(factory.isLoggedIn()){
                    factory.openDashboard();
                }
            }
        });


        return factory;
    }

    contextFactory.$inject = injectModules;
    app.factory('workingContext', contextFactory);

}());