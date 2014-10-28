(function(){
    'use strict';
    var injectParams = ['$scope', 'messages', 'authService'];
    var loginController = function ($scope, messages, authService) {
        $scope.alerts =[];
        $scope.messages = messages;
        $scope.login = {
            username:"", password:""
        }
        $scope.messageCode = '';
        $scope.hasError = false;

        $scope.login=function(){
            login($scope, authService, messages);
        }

        // reset stage of form
        $scope.dataChange= function(){
            $scope.messageCode = '';
            $scope.hasError = false;
        }
    }

    loginController.$inject = injectParams;
    app.controller('mems.controller.login', loginController);

}());

function login(scope, authService, messages){
    if(!scope.login.username || !scope.login.password){
        scope.alerts.push({type:'danger', msg:'Username/Password are required'});
        return;
    }
    authService.login(scope.login.username, scope.login.password).then(function(result){
        if(!result.data.success){
            scope.messageCode = messages['MAU00006'];
        }
        else{
            scope.messageCode = '';
        }
        scope.hasError = !result.data.success;
    });
}

function directPage(accountType, location){
    location.path("/dashboard");
}