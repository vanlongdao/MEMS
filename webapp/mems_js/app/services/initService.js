'use strict';
angular.module("initService", [], ["$provide", "workingContext", function($provide, workingContext) {
    var labels = {
        "username":"Username",
        "password":"Password",
        "forgotPassword":"Forgot Password",
        "login":"Login",
        "dashboard":"Dashboard",
        "logout":"Log Out",
        systemTitle:"Medical Equipment Management System",
        openRepairRequest:"Open Repair Request"
    }

    var messages = {
        "MAU00006": "Login Failed"
    }

    var i18n = angular.extend({}, labels, messages);

    $provide.value("messages", i18n);
}]);