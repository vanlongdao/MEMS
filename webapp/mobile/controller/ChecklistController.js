var myconfig,dialog;
app.controller('ChecklistController', function($scope, $http,$localStorage,$sce,myconfig){
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
	scope = $scope;
	http = $http;
	localStorage = $localStorage;
	
	$scope.trustAsHtml = function(value) {
		return $sce.trustAsHtml(value);
	};
	
	config = myconfig;
	
	checkLogin();
	
	
	$scope.setupchecklist = setupchecklist;
	$scope.applyMDevChecklist = applyMDevChecklist;
	$scope.cancelPopup = cancelPopup;
	$scope.deleteSelectedChecklist = deleteSelectedChecklist;	
	$scope.applyAndBack = applyAndBack;
	$scope.discardAndBack = discardAndBack;
	$scope.rowSelect = rowSelect;
	$scope.applyChecklist = applyChecklist;
	$scope.discard = discard;
	
	$scope.$watch('checklistSelected.selected', function(){
		if(scope.checklistSelected.selected){
			getMdevChecklist();
		}
	});
	

});

function getChecklist(){
	http({
		method:'POST',
		url:config.url + 'device/getChecklist',
		headers:'',
		data:angular.fromJson(window.sessionStorage.actionLog).actionCode 
	}).success(function(data,status){
		if(data && status == 200){
			scope.listDefaultChecklist = data;
			scope.listChecklist = data;
		}
	}).error(function(data,status){
		alert('Error');
	});
}

function setupchecklist(){
	http({
		method:'POST',
		url:config.url + 'device/getMDevChecklist',
		headers:'',
		data:angular.fromJson(localStorage.userInfo).officeCode 
	}).success(function(data,status){
		scope.mdev_checklist = data;
		dialog = document.getElementById('popup');
		dialog.toggle();
	}).error(function(data,status){
		
	});	
}

function applyMDevChecklist(){
	dialog.toggle();
	scope.checklist.checklistName = scope.mdev_checklistSelected.selected.name;
	scope.checklist.checklistDate = null;
	scope.checklist.actionCode = angular.fromJson(window.sessionStorage.actionLog).actionCode;
	scope.checklist.referCklistCode = scope.mdev_checklistSelected.selected.cklistCode;
	scope.checklist.serviceOffice = angular.fromJson(localStorage.userInfo).officeCode;
	scope.checklist.psnCode = angular.fromJson(localStorage.userInfo).psnCode; 
	scope.checklist.cklistLogCode = Math.floor((Math.random() * 100) + 1);
	scope.checklist.isDelete = 0;
	var checkDuplicate = false;
	for(var i = 0; i <= scope.listChecklist.length; i++){
		if(scope.listChecklist[i] == scope.checklist){
			checkDuplicate = true;
			break;
		}
	}
	
	if(checkDuplicate == false){
		scope.listChecklist.push(scope.checklist);
		
		http({
			method:'POST',
			url:config.url + 'device/getMDevChecklistItem',
			headers:{'cklistCode':scope.mdev_checklistSelected.selected.cklistCode},
			data:angular.fromJson(localStorage.userInfo).officeCode
		}).success(function(data,status){
			for(var i = 0; i < data.length; i++){
				scope.checklistItem.push({cklistLogCode:scope.checklist.cklistLogCode,referCklistCode:data[i]});
			}
		}).error(function(data,status){
			
		});
	} else {
		alert('you already have this checklist in list of checklist');
	}
	
}

function cancelPopup(){
	dialog.toggle();
}

function getMdevChecklist(){
	if(scope.checklistSelected.selected.length > 0){
		http({
			method:'POST',
			url:config.url + 'device/getChecklistInfo',
			headers:{'cklistCode':scope.checklistSelected.selected.referCklistCode,'measure1':scope.checklistSelected.selected.measureDev1,'measure2':scope.checklistSelected.selected.measureDev2,'hospPsn':scope.checklistSelected.selected.hospPsn},
			data:angular.fromJson(localStorage.userInfo).officeCode
		}).success(function(data,status){
			scope.mdev_checklist = data.listChecklistInfo;
			scope.listMeasureDev1 = data.listMeasure1;
			scope.listMeasureDev2 = data.listMeasure2;
			scope.personName = data.personName;
		}).error(function(data,status){
			
		});
	} 
}

function deleteSelectedChecklist(){
	var index = scope.listChecklist.indexOf(scope.checklistSelected.selected);
	if(index != -1){
		scope.listChecklist[index].isDelete = 1;
		scope.listDelete.push(scope.listChecklist[index]);
		scope.listChecklist.splice(index,1);
		scope.checklistSelected.selected = {};
	}
}

function rowSelect(row){
	var index = scope.mdev_checklist.indexOf(row);
	var listCheckbox = document.getElementsByTagName('paper-checkbox');
	for(var i = 0; i < listCheckbox.length; i++){
		if(i != index){
			listCheckbox[i].checked = false;
		} else {
			var checkboxSelected = listCheckbox[index];
			if(checkboxSelected.checked == false){
				scope.mdev_checklistItemSelected.ckiCode = row.ckiCode;
				scope.mdev_checklistItemSelected.description = row.description;
				scope.mdev_checklistItemSelected.isOk = row.isOk;
				scope.mdev_checklistItemSelected.resultDescription = row.resultDescription;
				scope.mdev_checklistItemSelected.resultValue = row.resultValue;
				checkboxSelected.checked = true;			
			} else {
				scope.mdev_checklistItemSelected = {};
				checkboxSelected.checked = false;
			}
		}
	}
	
}

function applyChecklist(){
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

function discard(){
	scope.mdev_checklistItemSelected = {};
	var listCheckbox = document.getElementsByTagName('paper-checkbox');
	for(var i = 0; i < listCheckbox.length; i++){
		listCheckbox[i].checked = false;
	}
}

function applyAndBack(){
	if(scope.listChecklist.length !=0)
		window.sessionStorage.listChecklist = angular.toJson(scope.listChecklist);
	else
		window.sessionStorage.listChecklist = null;
	if(scope.mdev_checklist.length != 0)
		window.sesstionStorage.mdev_checklist = angular.toJson(scope.mdev_checklist);
	else 
		window.sesstionStorage.mdev_checklist = null;
	window.location.href = 'RepairRequest.html';
}

function discardAndBack(){
	window.location.href = 'RepairRequest.html';
}