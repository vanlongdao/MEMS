/**
 * 
 */
var db,form,device,scope,http,window,rootScope,compile,localStorage;

var listReplacedParts= [];
var listMeasureResult = [];
var listEstimateResult = [];
var listOrderResult = [];
var countReplacedParts = [{id:'inputReplacedParts_1'}];
var countMeasureResult = [{id:'inputMeasureResult_1'}];
var countEstimateResult = [{id:'inputEstimateResult_1'}];
var countOrderResult = [{id:'inputOrderResult_1'}];

//var app = angular.module('MyApp',['ng-polymer-elements']);
app.controller('RepairRequestController',function($scope, $http, $window, $rootScope, $interval,$compile,$localStorage){
	$scope.login = {
			username:"",
			password:""
	}
	$scope.input = '',
    $scope.count = 0;
    $scope.requestReg = {
        RequestNo:'',
        Type:'',
        DeviceID:'',
        DeviceName:'',
        ModelNo:'',
        SN:'',
        Manufacturer:'',
        Supplier:'',
        Location:'',
        FoundBy:{},
        FoundDate:'',
        Compliant:'',
        CallRepairDate:'',
        ContactPerson:{},
        WorkStartDate:'',
        WorkEndDate:'',
        Failure:'',
        Repair:'',
        ReplacedParts:{},
        Cause:'',
        Prevention:'',
        Measurement1:'',
        Measurement2:'',
        MeasureResult:{},
        Signature:'',
        EstimateCode:'',
        EstimateResult:{},
        EstimateTotal:'',
        OrderCode:'',
        OrderResult:{},
        OrderETA:'',
        OrderTotal:'',
        FeePart:'',
        Shipping:'',
        SundryExpenses:'',
        Tax:'',
        FeeTotal:''
    }

    $scope.listRequestReg = []
    
    scope = $scope;
    http = $http;
    window = $window;
    rootScope = $rootScope;
    compile = $compile;
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
    getDevInfo();
    rootScope.onLine = navigator.onLine;
    $window.addEventListener('online',function(){
        $rootScope.$apply(function(){
            $rootScope.onLine = true;
        });
    });
    $window.addEventListener('offline',function(){
        $rootScope.$apply(function(){
            $rootScope.onLine = false;
        });
    },false);

    //set check if local storage have index then send data to server after a specific time (eg: 20s)
//    $interval(function(){
//        onSubmit();
//    },20000);
    
    
    
    $scope.submit = databaseAdd;
    $scope.getStore = getStore;
    $scope.addRow = addRow;
    $scope.removeRow = removeRow;
    $scope.valueBind = valueBind;
    $scope.goToManage = goToManage;
});

databaseOpen(function(){
    scope = angular.element(document.getElementById('divContainer')).scope();
});

function databaseOpen(callback){
    var version = 1;
    var request = indexedDB.open('RepairRequestRegistration',version);

    request.onupgradeneeded = function(e){
        db = e.target.result;
        e.target.transaction.onerror = databaseError;
        db.createObjectStore('repairrequest',{keyPath: 'createDate'});
    };

    request.onsuccess = function(e){
        db = e.target.result;
        callback();
    };

    request.onerror = databaseError;
}

function onSubmit(){
    if(rootScope.onLine){
        getStore();
        http({method: 'POST', url:'', data: scope.listRequestReg}).
            success(function(data, status){
                if(status == 200)
                    indexedDB.deleteDatabase('RepairRequestRegistration')
            }).
            error(function(data, status){
                //do something
            });
    }
}

function databaseAdd(){
    var transaction = db.transaction(['repairrequest'],'readwrite');
    var store = transaction.objectStore('repairrequest');

    scope.requestReg.ReplacedParts = listReplacedParts;
    scope.requestReg.MeasureResult = listMeasureResult;
    scope.requestReg.EstimateResult = listEstimateResult;
    scope.requestReg.OrderResult = listOrderResult;

    var request = store.put({
        createDate:new Date(),
        data:scope.requestReg
    });

    transaction.oncomplete = function(e){
        getStore();
    };

    request.onerror = databaseError;
}

function getStore(){
    var transaction = db.transaction(['repairrequest'],'readwrite');
    var store = transaction.objectStore('repairrequest');

    //get all data into store
    var keyRange = IDBKeyRange.lowerBound(0);
    var cursorRequest = store.openCursor(keyRange);

    var data = [];
    cursorRequest.onsuccess = function(e){
        var result = e.target.result;

        if(result){
            data.push(result.value);
            result.continue();
        } else {
            scope.listRequestReg = data;
            scope.$apply();
        }
    };
    return data;
}

function databaseError(e){
    console.error('An IndexedDB error has occurred',e);
}

function addRow(event){
    var id = event.target.getAttribute('id');
    var listInput = [];
    var prefix = "";
    switch (id){
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

    addDivInput(listInput,prefix);
}

function addDivInput(listInput,prefix){
    var index = 1;
    for(index = 1; index <= listInput.length; index++){
        var input = document.getElementById(listInput[index - 1].id);
        if(input.inputValue == "" || input.inputValue == null)
            break;
    }

    if(index == listInput.length + 1 && index > 0){
        var container = document.getElementById(event.target.getAttribute('id')).parentNode.parentNode;

        var div = document.createElement('DIV');
        var pInput = document.createElement('PAPER-INPUT');
        var ciButton = document.createElement('CORE-ICON-BUTTON');

        //set attributes here
        ciButton.setAttribute('id','del' + prefix + listInput.length + 1);
        ciButton.setAttribute('icon','clear');
        ciButton.setAttribute('ng-click','removeRow($event)');
        pInput.setAttribute('ng-blur','valueBind($event)');
        pInput.setAttribute('id',prefix + index);

        div.appendChild(pInput);
        div.appendChild(ciButton);

        container.appendChild(div);

        compile(div)(scope);

        listInput.push({id:prefix + index});
    }
}

function removeRow(event){
    var button  = document.getElementById(event.target.getAttribute('id'));
    var input = button.parentNode.childNodes[0];

    var listInput = [];
    var listResult = [];

    switch (button.parentNode.parentNode.getAttribute('id')){
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

    removeIndex(listResult,input);
    removeIndex(listInput,input);

    var parentContainer = input.parentNode;
    parentContainer.parentNode.removeChild(parentContainer);
}

function valueBind(event){
    var input  = document.getElementById(event.target.getAttribute('id'));

    var listResult = [];
var t = input.parentNode.parentNode.getAttribute('id');
    switch (input.parentNode.parentNode.getAttribute('id')){
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

    if(input.inputValue != ""){
        addIndex(listResult,event,input);
    } else {
        removeIndex(listResult,input);
    }
}

function addIndex(list,event,input){
    if(list.length == 0){
        list.push({id:event.target.getAttribute('id'),value:input.inputValue});
    } else {
        var i = 0;
        for(var i = 0; i < list.length; i++){
            if(list[i].id == event.target.getAttribute('id')){
                if(list[i].value != input.inputValue){
                    list[i].value = input.inputValue;
                    break;
                }
            }
        }
        if(i == list.length)
            list.push({id:event.target.getAttribute('id'),value:input.inputValue});
    }
}

function removeIndex(list,input){
    for(var i = 0; i < list.length; i++){
        if(list[i].id == input.getAttribute('id')){
            list.splice(i,1);
            break;
        }
    }
}

function goToManage(event){
    var id = event.target.getAttribute('id');

    switch (id){
        case 'addReplacedParts':
            window.location.href = 'ManageReplaceParts.html';
            break;
        case 'addMeasureResult':
            window.location.href = 'ManageChecklistResult.html';
            break;
        case 'addEstimateResult':
            window.location.href = 'ManagePartsEstimation.html';
            break;
        case 'addOrderResult':
            window.location.href = 'PartsOrder.html';
            break;
    }
}

function getDevInfo(){
	var t = angular.fromJson(window.sessionStorage.actionLog);
	http({
		method:'POST',
		url:'http://localhost:8080/mems/rest/device/getDeviceInfo',
		data:angular.fromJson(window.sessionStorage.actionLog).actionCode
	}).success(function(data,status){
		var t = angular.fromJson(window.sessionStorage.actionLog);
		scope.requestReg.DeviceID = data.devId;
		scope.requestReg.DeviceName = data.name;
		scope.requestReg.ModelNo = data.model;
		scope.requestReg.SN = data.serial;
		scope.requestReg.Manufacturer = data.manufacturer;
		scope.requestReg.Supplier = data.supplier;
		scope.requestReg.Location = data.location;
		scope.requestReg.FaultBy = data.listHumanResource;
		scope.requestReg.ContactPerson = data.listHumanResource;
	}).error(function(data,status){
		
	});
}