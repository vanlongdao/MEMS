/**
 * Created by michael on 10/11/14.
 */
(function () {

    var injectParams = ['$http', '$rootScope', '$route', 'memsAppConfig', 'workingContext'];

    var authFactory = function ($http, $rootScope, $route, memsAppConfig, workingContext) {
        var serviceBase = memsAppConfig.api;
        var factory = {
                loginPath: '/auth/login',
                logoutPath: '/auth/logout'
            };

        factory.login = function (username, password) {

            return $http.post(serviceBase + factory.loginPath,  { username: username, password: password }).success(
                function (results) {
                    var loggedIn = results.success;
                    if(loggedIn) {
                        $rootScope.$broadcast("loggedIn", results.data);
                    }
                    return loggedIn;
                });
        };

        factory.logout = function () {
            $http.post(serviceBase + factory.logoutPath,{}).success(function(){
                $rootScope.$broadcast("loggedOut");
            });

            // force to reload page
            $route.reload();
            return false;
        };

        $rootScope.$on("loggingOut", function(){
            factory.logout();
        });
        return factory;
    };

    authFactory.$inject = injectParams;
    app.factory('authService', authFactory);


}());