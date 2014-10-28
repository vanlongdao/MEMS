/**
 * Created by michael on 10/13/14.
 */
'use strict';

var db, form, device, scope, http, window, rootScope, compile, localStorage;

var listReplacedParts = [];
var listMeasureResult = [];
var listEstimateResult = [];
var listOrderResult = [];
var countReplacedParts = [{id: 'inputReplacedParts_1'}];
var countMeasureResult = [{id: 'inputMeasureResult_1'}];
var countEstimateResult = [{id: 'inputEstimateResult_1'}];
var countOrderResult = [{id: 'inputOrderResult_1'}];

(function () {
    var injectParams = ['$scope', 'messages', '$http', '$window', '$location', 'authService', '$interval', '$compile', '$rootScope', 'localStorageService', '$routeParams',
        'Device', 'ActionLog', 'ActionType', 'HospitalDepartment', 'memsAppConfig',
        'exceptionHandler'];

    var repairController = function ($scope, messages, $http, $window, $location, authService, $interval, $compile, $rootScope, localStorageService, $routeParams, Device,
                                     ActionLog, ActionType, HospitalDepartment, memsAppConfig,
                                     exceptionHandler) {
        var factory = {
            actionCode: null,
            isOnEdit: false
        };

        factory.refreshDepartmentPerson = function (query) {
            if (!$scope.deviceInfo || !$scope.deviceInfo.hospDeptCode) {
                return;
            }

            HospitalDepartment.findPersons({
                departCode: $scope.deviceInfo.hospDeptCode,
                action: 'persons',
                filter: query
            }, function (successResp) {
                $scope.allDepartmentPersons = successResp.data;
            }, function (errorResp) {
                exceptionHandler.onError(errorResp);
            });

        };

        factory.refreshHospitalContactPerson = function (query) {
            if (!$scope.deviceInfo || !$scope.deviceInfo.hospDeptCode) {
                return;
            }

            HospitalDepartment.findPersons({
                departCode: $scope.deviceInfo.hospDeptCode,
                action: 'persons',
                filter: query
            }, function (successResp) {
                $scope.allHospitalContactPersons = successResp.data;
            }, function (errorResp) {
                exceptionHandler.onError(errorResp);
            });

        };

        factory.getDevInfo = function (devCode) {
            Device.query({devCode: devCode},
                function (successResp) {
                    if (successResp.success) {
                        console.log("Device:", successResp.data);
                        $scope.selectedDevice.selected = successResp.data;
                        $scope.deviceInfo = successResp.data;
                        localStorageService.set("currentDevice", successResp.data);
                        $scope.faultFoundBy.selected = $scope.requestReg.contactPerson;
                        $scope.hospitalContactPerson.selected = $scope.requestReg.hospitalContactPerson;

                        // load Fault by person information
                        factory.refreshDepartmentPerson('');
                        factory.refreshHospitalContactPerson('');
                    }
                });
        };

        factory.onSuccessGetActionlog = function (successResp) {
            if (successResp.success) {
                // Store into Local
                localStorageService.set("currentActionLog", successResp.data);
                $scope.requestReg = successResp.data;
                $scope.selectedActionType.selected = successResp.data.actionTypeMaster;
                factory.getDevInfo(successResp.data.devCode);
            }
        };

        factory.refreshActionTypes = function (query) {
            var allActionTypes = localStorageService.get("actionTypes");
            if (!!query) {

                allActionTypes = allActionTypes.filter(function (item) {
                    return (item.label.toUpperCase().indexOf(query.toUpperCase()) >= 0);
                });
            }
            $scope.allActionTypes = allActionTypes;
        }


        factory.init = function () {
            // 1. setup master data
            factory.getAllActionTypes();


            // 2. Load processing data
            //PURPOSE: Init data when opening screen
            factory.actionCode = $routeParams.actionCode;
            var isOnEdit = !!factory.actionCode;

            // On Edit mode, get data of action log
            if (isOnEdit) {
                factory.loadActionLog(factory.actionCode);
            }
            else {
                //TODO: on add new
            }

            // 3. setup listeners
            $scope.$watch("selectedActionType.selected", function () {
                if (!!$scope.requestReg && !!$scope.selectedActionType.selected) {
                    $scope.requestReg.actionType = $scope.selectedActionType.selected.actionTypeId;
                }
            });
        };

        factory.loadActionLog = function (actionCode) {
            // reload action log information
            ActionLog.query({actionCode: actionCode}, factory.onSuccessGetActionlog, function (errorResp) {
                exceptionHandler.onError(errorResp);
            });
        };

        factory.getAllActionTypes = function () {
            // get all action types
            ActionType.query(function (successResp) {
                if (successResp.success) {
                    $scope.allActionTypes = successResp.data;
                    localStorageService.set("actionTypes", successResp.data);
                }
            });
        };

        factory.openFoundDatePicker = function ($event) {
            $event.preventDefault();
            $event.stopPropagation();

            $scope.foundDatePickerOpened = true;
        };

        // setup data
        $rootScope.messages = messages;
        $rootScope.configs = memsAppConfig;

        $scope.refreshActionTypes = factory.refreshActionTypes;
        $scope.refreshDepartmentPerson = factory.refreshDepartmentPerson;

        $scope.openFoundDatePicker = factory.openFoundDatePicker;
        // init setup:
        $rootScope.getMenuPath = function () {
            return "repair_request/includes/toolbar.html";
        };

        $scope.dateOptions = {
            formatYear: 'yyyy',
            startingDay: 1
        };

        $scope.input = '';
        $scope.count = 0;
        $scope.requestReg = {};
        $scope.selectedActionType = {};
        $scope.deviceInfo = {};
        $scope.listRequestReg = [];
        $scope.selectedDevice = {};
        $scope.allDevices = [{}];

        // Fault found by
        $scope.foundDatePickerOpened = false;
        $scope.faultFoundBy = {};

        $scope.allDepartmentPersons = [{}];

        $scope.hospitalContactPerson={};
        $scope.allHospitalContactPersons = [{}];


        scope = $scope;
        http = $http;
        window = $window;
        rootScope = $rootScope;
        compile = $compile;


        //TODO: support Offline
//        rootScope.onLine = navigator.onLine;
//        $window.addEventListener('online',function(){
//            $rootScope.$apply(function(){
//                $rootScope.onLine = true;
//            });
//        });
//        $window.addEventListener('offline',function(){
//            $rootScope.$apply(function(){
//                $rootScope.onLine = false;
//            });
//        },false);

        var pages = document.querySelector('core-pages');
        var tabs = document.querySelector('paper-tabs');
        //tabs.addEventListener('core-select', function () {
        //    pages.selected = tabs.selected;
        //});

        $scope.submit = databaseAdd;
        $scope.getStore = getStore;
        $scope.addRow = addRow;
        $scope.removeRow = removeRow;
        $scope.valueBind = valueBind;
        $scope.goToManage = function (event) {
            goToManage(event, $location);
        };

        // call init
        factory.init();

        return factory;
    }


    repairController.$inject = injectParams;

    app.controller('mems.controller.repairrequest', repairController);

}());

databaseOpen(function () {
    scope = angular.element(document.getElementById('divContainer')).scope();
});

function databaseOpen(callback) {
    var version = 1;
    var request = indexedDB.open('RepairRequestRegistration', version);

    request.onupgradeneeded = function (e) {
        db = e.target.result;
        e.target.transaction.onerror = databaseError;
        db.createObjectStore('repairrequest', {keyPath: 'createDate'});
    };

    request.onsuccess = function (e) {
        db = e.target.result;
        callback();
    };

    request.onerror = databaseError;
}

function onSubmit() {
    if (rootScope.onLine) {
        getStore();
        http({method: 'POST', url: '', data: scope.listRequestReg}).
            success(function (data, status) {
                if (status == 200)
                    indexedDB.deleteDatabase('RepairRequestRegistration')
            }).
            error(function (data, status) {
                //do something
            });
    }
}

function databaseAdd() {
    var transaction = db.transaction(['repairrequest'], 'readwrite');
    var store = transaction.objectStore('repairrequest');

    scope.requestReg.ReplacedParts = listReplacedParts;
    scope.requestReg.MeasureResult = listMeasureResult;
    scope.requestReg.EstimateResult = listEstimateResult;
    scope.requestReg.OrderResult = listOrderResult;

    var request = store.put({
        createDate: new Date(),
        data: scope.requestReg
    });

    transaction.oncomplete = function (e) {
        getStore();
    };

    request.onerror = databaseError;
}

function getStore() {
    var transaction = db.transaction(['repairrequest'], 'readwrite');
    var store = transaction.objectStore('repairrequest');

    //get all data into store
    var keyRange = IDBKeyRange.lowerBound(0);
    var cursorRequest = store.openCursor(keyRange);

    var data = [];
    cursorRequest.onsuccess = function (e) {
        var result = e.target.result;

        if (result) {
            data.push(result.value);
            result.continue();
        } else {
            scope.listRequestReg = data;
            scope.$apply();
        }
    };
    return data;
}

function databaseError(e) {
    console.error('An IndexedDB error has occurred', e);
}

function addRow(event) {
    var id = event.target.getAttribute('id');
    var listInput = [];
    var prefix = "";
    switch (id) {
        case 'addReplacedParts':
            listInput = countReplacedParts;
            prefix = 'inputReplacedParts_';
            break;
        case 'addMeasureResult':
            listInput = countMeasureResult;
            prefix = 'inputMeasureResult_';
            break;
        case 'addEstimateResult':
            listInput = countEstimateResult;
            prefix = 'inputEstimateResult_';
            break;
        case 'addOrderResult':
            listInput = countOrderResult;
            prefix = 'inputOrderResult_';
            break;
    }

    addDivInput(listInput, prefix);
}

function addDivInput(listInput, prefix) {
    var index = 1;
    for (index = 1; index <= listInput.length; index++) {
        var input = document.getElementById(listInput[index - 1].id);
        if (input.inputValue == "" || input.inputValue == null)
            break;
    }

    if (index == listInput.length + 1 && index > 0) {
        var container = document.getElementById(event.target.getAttribute('id')).parentNode.parentNode;

        var div = document.createElement('DIV');
        var pInput = document.createElement('PAPER-INPUT');
        var ciButton = document.createElement('CORE-ICON-BUTTON');

        //set attributes here
        ciButton.setAttribute('id', 'del' + prefix + listInput.length + 1);
        ciButton.setAttribute('icon', 'clear');
        ciButton.setAttribute('ng-click', 'removeRow($event)');
        pInput.setAttribute('ng-blur', 'valueBind($event)');
        pInput.setAttribute('id', prefix + index);

        div.appendChild(pInput);
        div.appendChild(ciButton);

        container.appendChild(div);

        compile(div)(scope);

        listInput.push({id: prefix + index});
    }
}

function removeRow(event) {
    var button = document.getElementById(event.target.getAttribute('id'));
    var input = button.parentNode.childNodes[0];

    var listInput = [];
    var listResult = [];

    switch (button.parentNode.parentNode.getAttribute('id')) {
        case 'replacedPartsContainer':
            listInput = countReplacedParts;
            listResult = listReplacedParts;
            break;
        case 'measureResultContainer':
            listInput = countMeasureResult;
            listResult = listMeasureResult;
            break;
        case 'estimateResultContainer':
            listInput = countEstimateResult;
            listResult = listEstimateResult;
            break;
        case 'orderResultContainer':
            listInput = countOrderResult;
            listResult = listOrderResult;
            break;
    }

    removeIndex(listResult, input);
    removeIndex(listInput, input);

    var parentContainer = input.parentNode;
    parentContainer.parentNode.removeChild(parentContainer);
}

function valueBind(event) {
    var input = document.getElementById(event.target.getAttribute('id'));

    var listResult = [];
    var t = input.parentNode.parentNode.getAttribute('id');
    switch (input.parentNode.parentNode.getAttribute('id')) {
        case 'replacedPartsContainer':
            listResult = listReplacedParts;
            break;
        case 'measureResultContainer':
            listResult = listMeasureResult;
            break;
        case 'estimateResultContainer':
            listResult = listEstimateResult;
            break;
        case 'orderResultContainer':
            listResult = listOrderResult;
            break;
    }

    if (input.inputValue != "") {
        addIndex(listResult, event, input);
    } else {
        removeIndex(listResult, input);
    }
}

function addIndex(list, event, input) {
    if (list.length == 0) {
        list.push({id: event.target.getAttribute('id'), value: input.inputValue});
    } else {
        var i = 0;
        for (var i = 0; i < list.length; i++) {
            if (list[i].id == event.target.getAttribute('id')) {
                if (list[i].value != input.inputValue) {
                    list[i].value = input.inputValue;
                    break;
                }
            }
        }
        if (i == list.length)
            list.push({id: event.target.getAttribute('id'), value: input.inputValue});
    }
}

function removeIndex(list, input) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id == input.getAttribute('id')) {
            list.splice(i, 1);
            break;
        }
    }
}

function goToManage(event, location) {
    var id = event.target.getAttribute('id');

    switch (id) {
        case 'addReplacedParts':
            location.path('/ManageReplaceParts');
            break;
        case 'addMeasureResult':
            location.path('/ManageChecklist');
            break;
        case 'addEstimateResult':
            location.path('/ManagePartsEstimation');
            break;
        case 'addOrderResult':
            location.path('/ManagePartsOrder');
            break;
    }
}


function openRepairRequest(http, location, localStorageService) {
    http({
        method: 'POST',
        url: 'http://localhost:8080/mems/rest/device/getactionlog',
        data: '1'
    }).success(function (data, status) {
        if (data && status == 200) {
            localStorageService.set("actionLog", angular.toJson(data.data));
            location.path('/repairrequest');
        }
    }).error(function (data, status) {
        alert('');
    });

}
