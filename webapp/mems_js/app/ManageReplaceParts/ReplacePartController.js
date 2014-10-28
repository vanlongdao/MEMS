var scope,http,localStorage;

(function(){
	var injectParams = ['$scope', '$http','$localStorage','$sce'];
	var ReplacePartController = function($scope, $http,$localStorage,$sce){
		$scope.replacePartList = [],
		$scope.partOrderItem = [],
		$scope.defaultParts = [],
		$scope.oldParts = [],
		$scope.newParts = [],
		$scope.partSelected = {},
		$scope.oldPartSelected = {},
		$scope.newPartSelected = {},
		$scope.discription = '',
		scope = $scope;
		http = $http;
		localStorage = $localStorage;
		$scope.trustAsHtml = function(value) {
			return $sce.trustAsHtml(value);
		};
		
		getReplaceParts();
		$scope.addNewPart = addNewPart;
		$scope.applyAndReturn = applyAndReturn;
		$scope.discardAndReturn = discardAndReturn;
		$scope.getReplacePartInfo = getReplacePartInfo;
		$scope.dellAPart = dellAPart;
		$scope.fillFromOrder = fillFromOrder;
		$scope.applyReplacePart = applyReplacePart;
		$scope.discardChange = discardChange;
	}
	
	ReplacePartController.$inject = injectParams;
	app.controller('mems.controller.replacedparts',ReplacePartController);
}());



function getReplaceParts(){
	http({
		method:'POST',
		url:'http://localhost:8080/mems/rest/device/getReplaceParts',
		headers:{'officeCode':angular.fromJson(localStorage.userInfo).officeCode},
		data:angular.fromJson(window.sessionStorage.actionLog).actionCode 
	}).success(function(data,status){
		scope.replacePartList = data;
		scope.defaultParts = data;
	}).error(function(data,status){
		
	});
}

function addNewPart(){
	document.getElementById('partInfo').hidden = false;
	var devCode = angular.fromJson(window.sessionStorage.actionLog).devCode;
	http({
		method:'POST',
		url:'http://localhost:8080/mems/rest/device/addNewPart',
		headers:{'devCode':devCode,'officeCode':angular.fromJson(localStorage.userInfo).officeCode},
		data:angular.fromJson(window.sessionStorage.actionLog).actionCode 
	}).success(function(data,status){
		scope.oldParts = data.listOldPart;
		scope.newParts = data.listNewPart;
		var t = document.getElementsByTagName('option');
	}).error(function(data,status){
		
	});
}

function getReplacePartInfo(item){
		var cbList = document.getElementsByTagName("paper-checkbox");
		var index = scope.replacePartList.indexOf(item);
		
		for(var i = 0; i < cbList.length; i++){
			if(index == i){
				if(cbList[i].checked == true){
					cbList[i].checked = false;
					scope.oldParts = [];
					scope.newParts = [];
				} else{
					cbList[i].checked = true;
					http({
						method:'POST',
						url:'http://localhost:8080/mems/rest/device/getReplacePartInfo',
						headers:{'devCode':item.devCode,'officeCode':angular.fromJson(localStorage.userInfo).officeCode},
						data:angular.fromJson(window.sessionStorage.actionLog).actionCode 
					}).success(function(data,status){
						scope.oldParts = data.listOldPart;
						scope.newParts = data.listNewPart;
						document.getElementById('btnDelete').disabled = false;
					}).error(function(data,status){
						clearPartInfo();
					});
				}
			}				
			else
				cbList[i].checked = false;
		}		
		scope.partSelected = item;
		
}

function dellAPart(e){
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

function clearPartInfo(){
	scope.oldParts = [];
	scope.newParts = [];
	document.getElementById('btnDelete').disabled = true;
	scope.newPartSelected = {};
	scope.oldPartSelected = {};
}

function fillFromOrder(){
	if(scope.partOrderItem.length == 0){
		http({
			method:'POST',
			url:'http://localhost:8080/mems/rest/device/getPartOrderItem',	

			headers:{'userId':angular.fromJson(localStorage.userInfo).userID,'officeCode':angular.fromJson(localStorage.userInfo).officeCode},
			data:angular.fromJson(window.sessionStorage.actionLog).actionCode
		}).success(function(data,status){
			scope.partOrderItem = data;
			for(var i = 0; i < data.length; i++){
				scope.replacePartList.push({devCode:'',remove_devCode:'',modelNo:data[i].modelNo,serialNo:'',old_serialNo:''});
			}
		}).error(function(data,status){
			
		});
	} else {
		
	}
}

function applyAndReturn(){
	window.sessionStorage.replacedParts = angular.toJson(scope.replacePartList);
	window.location.href = 'RepairRequest.html';
}

function discardAndReturn(){
	window.sessionStorage.replacedParts = angular.toJson(scope.defaultParts);
	window.location.href = 'RepairRequest.html';
}

function applyReplacePart(){
	if(scope.partSelected.length > 0){
		for(var i = 0; i < scope.replacePartList.length; i++){
			if(scope.replacePartList[i] == scope.partSelected)
				scope.replacePartList[i] = {devCode:scope.newPartSelected.selected.devCode,remove_devCode:scope.oldPartSelected.selected.devCode,modelNo:scope.newPartSelected.selected.modelNo,serialNo:scope.newPartSelected.selected.serialNo,old_serialNo:scope.oldPartSelected.selected.serialNo,discription:scope.discription};
		}		
	} else {
		scope.replacePartList.push({devCode:scope.newPartSelected.selected.devCode,remove_devCode:scope.oldPartSelected.selected.devCode,modelNo:scope.newPartSelected.selected.modelNo,serialNo:scope.newPartSelected.selected.serialNo,old_serialNo:scope.oldPartSelected.selected.serialNo,discription:scope.discription});
		scope.oldPartSelected = {};
		scope.newPartSelected = {};
	}	
	document.getElementById('partInfo').hidden = true;
}

function discardChange(){
	clearPartInfo();
	document.getElementById('partInfo').hidden = true;
}