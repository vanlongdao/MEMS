/**
 * Created by michael on 10/15/14.
 */

app.factory('TokenInterceptor',['$q', '$rootScope', 'workingContext', function($q, $rootScope, workingContext){
    var tokenInterceptor =  {

        request: function(config){
            config.headers.token = workingContext.credential().token;
            config.headers.userId = workingContext.credential().userId;
            // auto add office code into request header.
            config.headers.officeCode = workingContext.userInfo() ? workingContext.userInfo().officeCode : null;
            return config;
        },

        // On response failture
        responseError: function (rejection) {
            console.log(rejection); // Contains the data about the error.

            // Return the promise rejection.
            if(rejection.status == 401){
                $rootScope.$broadcast("loggedOut");
            }
            return $q.reject(rejection);
        }
    }

    return tokenInterceptor;
}]);


app.config(function ($httpProvider) {
    $httpProvider.interceptors.push('TokenInterceptor');
    //$httpProvider.defaults.headers.common['Access-Control-Allow-Origin'] = "http://localhost:8080";
});