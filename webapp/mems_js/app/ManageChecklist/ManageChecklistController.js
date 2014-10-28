(function(){
    var injectParams = ['$scope', '$http','$sce','memsAppConfig','localStorageService'];
    var ChecklistController = function($scope, $http,$sce,memsAppConfig,localStorageService){
        $scope.trustAsHtml = function(value) {
            return $sce.trustAsHtml(value);
        }

        $scope.listDefaultChecklist = [],
        $scope.listChecklist = [],
        $scope.checklist = {},
        $scope.checklistItem = [],
        $scope.mdev_checklist = [],
        $scope.listDelete = [],
        $scope.checklistSelected = {},
        $scope.mdev_checklistSelected = {},
        $scope.measureDev1 = {},
        $scope.measureDev2 = {},
        $scope.listMeasureDev1 = [],
        $scope.listMeasureDev2 = [],
        $scope.personName = '',
        $scope.mdev_checklistItemSelected = {},
        $scope.config = {
            itemsPerPage: 5,
            fillLastPage: true
        }

        $scope.trustAsHtml = function(value) {
            return $sce.trustAsHtml(value);
        };

        $scope.setupchecklist = function(){
            setupchecklist($http,$scope,localStorageService,memsAppConfig);
        };
        $scope.applyMDevChecklist = function(){
            applyMDevChecklist($scope,localStorageService,$http,memsAppConfig);
        };
        $scope.cancelPopup = cancelPopup;
        $scope.deleteSelectedChecklist = function(){
            deleteSelectedChecklist($scope);
        };
        $scope.applyAndBack = function(){
            applyAndBack($scope,localStorageService);
        };
        $scope.discardAndBack = discardAndBack;
        $scope.rowSelect = function(row){
            rowSelect(row,$scope);
        };
        $scope.applyChecklist = function(){
            applyChecklist($scope);
        };
        $scope.discard = function(){
            discard($scope);
        };

        $scope.$watch('checklistSelected.selected', function(){
            if($scope.checklistSelected.selected){
                getMdevChecklist($scope,$http,localStorageService,memsAppConfig);
            }
        });

        getChecklist($http,$scope,localStorageService,memsAppConfig);
    };
    ChecklistController.$inject = injectParams;
    app.controller('mems.controller.checklist', ChecklistController);
}());

function getChecklist(http,scope,localStorageService,memsAppConfig){
	http({
		method:'POST',
		url:memsAppConfig.api + '/device/getChecklist',
		headers:'',
		data:angular.fromJson(localStorageService.get('currentActionLog')).actionCode
	}).success(function(data,status){
		if(data && status == 200){
			scope.listDefaultChecklist = data;
			scope.listChecklist = data;
		}
	}).error(function(data,status){
		alert('Error');
	});
}

function setupchecklist(http,scope,localStorageService,memsAppConfig){
	http({
		method:'POST',
		url:memsAppConfig.api + '/device/getMDevChecklist',
		headers:{'devCode':localStorageService.get('currentActionLog').devCode},
		data:angular.fromJson(localStorageService.get('userInfo')).officeCode
	}).success(function(data,status){
		scope.mdev_checklist = data.data;
		document.getElementById('popup').toggle();
	}).error(function(data,status){
		
	});	
}

function applyMDevChecklist(scope,localStorageService,http,memsAppConfig){
    document.getElementById('popup').toggle();
	scope.checklist.checklistName = scope.mdev_checklistSelected.selected.name;
	scope.checklist.checklistDate = null;
	scope.checklist.actionCode = angular.fromJson(localStorageService.get('currentActionLog')).actionCode;
	scope.checklist.referCklistCode = scope.mdev_checklistSelected.selected.cklistCode;
	scope.checklist.serviceOffice = angular.fromJson(localStorageService.get('userInfo')).officeCode;
	scope.checklist.psnCode = angular.fromJson(localStorageService.get('userInfo')).psnCode;
	scope.checklist.cklistLogCode = '';
	scope.checklist.isDelete = 0;
	scope.listChecklist.push(scope.checklist);
//	var checkDuplicate = false;
//	for(var i = 0; i <= scope.listChecklist.length; i++){
//		if(scope.listChecklist[i] == scope.checklist){
//			checkDuplicate = true;
//			break;
//		}
//	}
//	
//	if(checkDuplicate == false){
//		scope.listChecklist.push(scope.checklist);
//		
//		http({
//			method:'POST',
//			url:memsAppConfig.api + '/device/getMDevChecklistItem',
//			headers:{'cklistCode':scope.mdev_checklistSelected.selected.cklistCode},
//			data:angular.fromJson(localStorageService.get('userInfo')).officeCode
//		}).success(function(data,status){
//			for(var i = 0; i < data.length; i++){
//				scope.checklistItem.push({cklistLogCode:scope.checklist.cklistLogCode,referCklistCode:data[i]});
//			}
//		}).error(function(data,status){
//			
//		});
//	} else {
//		alert('you already have this checklist in list of checklist');
//	}
	
}

function cancelPopup(){
    document.getElementById('popup').toggle();
}

function getMdevChecklist(scope,http,localStorageService,memsAppConfig){
	if(scope.checklistSelected.selected){
		http({
			method:'POST',
			url:memsAppConfig.api + '/device/getChecklistInfo',
			headers:{'cklistCode':scope.checklistSelected.selected.referCklistCode,'measure1':scope.checklistSelected.selected.measureDev1,'measure2':scope.checklistSelected.selected.measureDev2,'hospPsn':scope.checklistSelected.selected.hospPsn},
			data:angular.fromJson(localStorageService.get('userInfo')).officeCode
		}).success(function(data,status){
			scope.mdev_checklist = data.data.listChecklistInfo;
			if(data.data.listMeasure1 != null)
			scope.listMeasureDev1 = data.data.listMeasure1;
			else
				scope.listMeasureDev1 = [];
			
			if(data.data.listMeasure2 != null)
				scope.listMeasureDev2 = data.data.listMeasure2;
			else
				scope.listMeasureDev2  = [];
			scope.personName = data.data.personName;
            document.getElementById('recorded_checklist').hidden = false;
		}).error(function(data,status){
			
		});
	} 
}

function deleteSelectedChecklist(scope){
	var index = scope.listChecklist.indexOf(scope.checklistSelected.selected);
	if(index != -1){
		scope.listChecklist[index].isDelete = 1;
		scope.listDelete.push(scope.listChecklist[index]);
		scope.listChecklist.splice(index,1);
		scope.checklistSelected.selected = {};
	}
}

function rowSelect(row,scope){
	var index = scope.mdev_checklist.indexOf(row);
	var listCheckbox = document.getElementById('recorded_checklist').querySelectorAll('paper-checkbox');

    for(var i = 0; i < listCheckbox.length; i++){
        if(i != index){
            listCheckbox[i].checked = false;
        }
    }

    if(listCheckbox[index].checked == true){
        scope.mdev_checklistItemSelected = {};
    } else {

        scope.mdev_checklistItemSelected.ckiCode = row.ckiCode;
        scope.mdev_checklistItemSelected.description = row.description;
        scope.mdev_checklistItemSelected.isOk = row.isOk;
        scope.mdev_checklistItemSelected.resultDescription = row.resultDescription;
        scope.mdev_checklistItemSelected.resultValue = row.resultValue;
        document.getElementById('checklistitem_info').hidden = false;
    }
}

function applyChecklist(scope){
	var listCheckbox = document.getElementsByTagName('paper-checkbox');
	var index = -1;
	for(index = 0; index < listCheckbox.length; index++){
		if(listCheckbox[index].checked){
			scope.mdev_checklist[index] = scope.mdev_checklistItemSelected;
			discard();
			break;
		}
	}
}

function discard(scope){
	scope.mdev_checklistItemSelected = {};
	var listCheckbox = document.getElementsByTagName('paper-checkbox');
	for(var i = 0; i < listCheckbox.length; i++){
		listCheckbox[i].checked = false;
	}
    document.getElementById('checklistitem_info').hidden = true;
}

function applyAndBack(scope, localStorageService){
	if(scope.listChecklist.length !=0)
		localStorageService.set('listChecklist', scope.listChecklist);
	else
        localStorageService.set('listChecklist', null);
	if(scope.mdev_checklist.length != 0)
		localStorageService.set('mdev_checklist', scope.mdev_checklist);
	else 
		localStorageService.set('dev_checklist', null);
	location.path('/repairrequest');
}

function discardAndBack(){
    location.path('/repairrequest');
}