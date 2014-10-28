var scope,http,localStorage;

(function(){
    var injectParams = ['$scope', '$http','localStorageService','$sce','memsAppConfig'];
    var ReplacePartController = function($scope, $http,localStorageService,$sce,memsAppConfig){
        var serviceBase = memsAppConfig.api;
        $scope.replacePartList = [],
            $scope.partOrderItem = [],
            $scope.defaultParts = [],
            $scope.oldParts = [],
            $scope.newParts = [],
            $scope.oldPartSelected = {},
            $scope.newPartSelected = {},
            $scope.discription = '',
            scope = $scope;
        
        $scope.trustAsHtml = function(value) {
            return $sce.trustAsHtml(value);
        };

        getReplaceParts($scope,$http,memsAppConfig,localStorageService);
        $scope.addNewPart = function(){
            addNewPart($scope,$http,localStorageService,memsAppConfig);
        };
        $scope.applyAndReturn = function(){
            //applyAndReturn($scope, localStorageService);
            saveToDatabase($scope,$http, localStorageService,memsAppConfig)
        };
        $scope.discardAndReturn = function(){
            discardAndReturn($scope,localStorageService)
        };
        $scope.getReplacePartInfo = function(item){
        	getReplacePartInfo(item,$scope,$http,localStorageService);
        };
        $scope.dellAPart = function(e){
        	dellAPart(e,$scope);
        };
        $scope.fillFromOrder = function(){
            fillFromOrder($scope,$http,localStorageService)
        };
        $scope.applyReplacePart = function(){
            applyReplacePart($scope);
        };
        $scope.discardChange = function(){
            discardChange($scope);
        };
    }

    ReplacePartController.$inject = injectParams;
    app.controller('mems.controller.replacedparts',ReplacePartController);
}());



function getReplaceParts(scope,http,memsAppConfig,localStorageService){
    http({
        method:'POST',
        url:memsAppConfig.api + '/device/getReplaceParts',
        headers:{'officeCode':angular.fromJson(localStorageService.get('userInfo')).officeCode},
        data:angular.fromJson(localStorageService.get('currentActionLog')).actionCode
    }).success(function(data,status){
        scope.replacePartList = data.data;
        for(var i = 0; i < scope.replacePartList.length; i++){
            scope.defaultParts.push(scope.replacePartList[i]);
        }
    }).error(function(data,status){

    });
}

function addNewPart(scope, http, localStorageService,memsAppConfig){
    document.getElementById('partInfo').hidden = false;
    var devCode = angular.fromJson(localStorageService.get('currentActionLog')).devCode;
    http({
        method:'POST',
        url:memsAppConfig.api + '/device/addNewPart',
        headers:{'devCode':devCode,'officeCode':angular.fromJson(localStorageService.get('userInfo')).officeCode},
        data:angular.fromJson(localStorageService.get('currentActionLog')).actionCode
    }).success(function(data,status){
        scope.oldParts = data.data.listOldPart;
        scope.newParts = data.data.listNewPart;
        scope.oldPartSelected = {};
        scope.newPartSelected = {};
    }).error(function(data,status){

    });
}

function getReplacePartInfo(item,scope,http,localStorageService){
    var cbList = document.getElementById('ReplacedParts').querySelectorAll('paper-checkbox');
    var index = scope.replacePartList.indexOf(item);
    for(var i = 0; i < cbList.length;i++){
        if(i != index)
            cbList[i].checked = false;
    }
    if(cbList[index].checked == true){
        scope.oldParts = [];
        scope.newParts = [];
        scope.oldPartSelected = {};
        scope.newPartSelected = {};
        scope.partSelected = null;
    } else{
    	scope.partSelected = item;
        http({
            method:'POST',
            url:'http://localhost:8080/mems/rest/device/getReplacePartInfo',
            headers:{'devCode':item.deviceCode,'mdevCode':item.device.mdevCode,'officeCode':angular.fromJson(localStorageService.get('userInfo')).officeCode},
            data:angular.fromJson(localStorageService.get('currentActionLog')).actionCode
        }).success(function(data,status){
            scope.oldParts = data.listOldPart;
            scope.newParts = data.listNewPart;
            document.getElementById('partInfo').hidden = false;
            scope.oldPartSelected = {};
            scope.newPartSelected = {};
            document.getElementById('btnDelete').disabled = false;
        }).error(function(data,status){
            clearPartInfo();
        });
    }

}

function dellAPart(e,scope){
    var cbList = document.getElementsByTagName("paper-checkbox");
    var index;
    for(var i = 0; i < cbList.length; i++){
        if(cbList[i].checked){
            index = i;
            break;
        }
    }

    scope.replacePartList.splice(index,1);
    clearPartInfo();

}

function clearPartInfo(scope){
    if(scope.oldParts)
        scope.oldParts = [];
    if(scope.newParts)
        scope.newParts = [];
    document.getElementById('btnDelete').disabled = true;
    scope.newPartSelected = {};
    scope.oldPartSelected = {};
}

function fillFromOrder(scope, http, localStorageService){
    if(scope.partOrderItem.length == 0){
        http({
            method:'POST',
            url:'http://localhost:8080/mems/rest/device/getPartOrderItem',
            headers:{'userId':angular.fromJson(localStorageService.get('userInfo')).userID,'officeCode':angular.fromJson(localStorageService.get('userInfo')).officeCode},
            data:angular.fromJson(localStorageService.get('currentActionLog')).actionCode
        }).success(function(data,status){
            scope.partOrderItem = data.data;
            for(var i = 0; i < data.data.length; i++){
                scope.replacePartList.push(data.data[i]);
            }
        }).error(function(data,status){

        });
    }
}

function discardAndReturn(scope, localStorageService,location){
	localStorageService.set('replacePartList',scope.defaultParts);
	location.path('/repairrequest');
}

function applyReplacePart(scope){
    if(scope.partSelected){
        if(scope.oldPartSelected.selected)
            scope.partSelected.removeDevice = scope.oldPartSelected.selected;
        if(scope.newPartSelected.selected)
        scope.partSelected.device = scope.newPartSelected.selected;
    } else {
        if(scope.oldPartSelected.selected && scope.newPartSelected.selected)
            scope.replacePartList.push({
                removeDevice:scope.oldPartSelected.selected,
                device:scope.newPartSelected.selected,
                deviceCode:scope.newPartSelected.selected.devCode,
                removedDevCode:scope.oldPartSelected.selected.devCode
            });
    }
    //document.getElementById('partInfo').hidden = true;
}

function discardChange(scope){
    var cbList = document.getElementsByTagName("paper-checkbox");
    var index = -1;
    for(var i = 0; i < cbList.length; i++){
        if(cbList[i].checked)
            alert(i);
    }
    //document.getElementById('partInfo').hidden = true;
}

function saveToDatabase(scope,http, localStorageService,memsAppConfig){
    alert();
    http({
        method:'POST',
        url:memsAppConfig.api + '/device/saveReplacedParts',
        data:scope.replacePartList
    }).success(function(data, status){

    }).error(function(data,status){

    });
}