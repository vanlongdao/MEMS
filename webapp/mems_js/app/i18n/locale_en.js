'use strict';
angular.module("memsLocale", [], ["$provide", function($provide) {
    var labels = {
        "username":"Username",
        "password":"Password",
        "forgotPassword":"Forgot Password",
        "login":"Login",
        "dashboard":"Dashboard",
        "logout":"Log Out",
        systemTitle:"Medical Equipment Management System",
        openRepairRequest:"Open Repair Request",
        menu:"Menu",
        deviceCode:"Device Code",
        serialNo:"Serial NO",
        hello:"Hello"
    }

    var messages = {
        "MAU00006": "Login Failed"
    }

    var i18n = angular.extend({}, labels, messages);

    $provide.value("messages", i18n);
}]);