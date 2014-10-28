/**
 * 
 */
(function(){
    var injectParams = ['$scope', 'messages', '$http', '$window', 'authService', '$location','$sce','memsAppConfig','localStorageService'];
    var PartsEstimateController = function($scope,message, $http, $window, authService, $location ,$sce,memsAppConfig,localStorageService){
        var serviceBase = memsAppConfig.api;
        $scope.trustAsHtml = function(value) {
            return $sce.trustAsHtml(value);
        }
//        $scope.listPartsEstimate = [],
//        $scope.defaultListPartEstimate = [],
        $scope.repairCode = '',
        $scope.partEstimateSelected = {},
        $scope.partEstimateItemSelected = {},
        $scope.partItemsEstimateSelected = {},
        //mode: action mode, with mode = 0 mean user in view mode, if mode = 1 mean user in add new item mode
        $scope.mode = 0,
        $scope.itemIndex = 0,
        $scope.newPartItemEstimate = {
        	name: '',
        	itemPrice:'',
        	numItems:'',
        	totPrice:'',
        	taxRate:'',
        	tax:'',
        	priceWithTax:''
        }
        
       
        	getPartsEstimate($http,$window,serviceBase,localStorageService,$scope);
       

        $scope.$watch('partEstimateSelected.selected', function(){
            if($scope.listPartsEstimate) {
            	$scope.mode = 0;
                getPartEstimateItems($scope, $http, $window, serviceBase);
            }
        });
        
        $scope.$watch('partEstimateItemSelected.selected', function(){
        	if($scope.listPartEstimateItems){
        		$scope.mode = 0;
        		$scope.itemIndex = $scope.listPartEstimateItems.indexOf($scope.partEstimateItemSelected.selected);
            	getPartItemsEstimate($scope, $http, $window, serviceBase,localStorageService);
        	}
        });
        
        $scope.delPartEstimate = function(){
        	delPartEstimate($scope);
        }
        
        $scope.delPartEstimateItem = function(){
        	if($scope.listPartsEstimate.length > 0){
        		delPartEstimateItem($scope);
        	}
        }
        
        $scope.addNewItem = function(){
        	addNewItem($scope);
        }
        
        $scope.applyItem = function(){
        	applyItem($scope);
        }
        
        $scope.applyAndBack = function(){
            saveToLocalstorage($scope, localStorageService,$location);
        }
        
        $scope.discardAndBack = function(){
            $scope.listPartsEstimate = scope.defaultListPartEstimate;
            $scope.listPartEstimateItems = scope.defaultListPartEstimateItems;
            saveToLocalstorage($scope, localStorageService,$location);
        }
    };

    PartsEstimateController.$inject = injectParams;
    app.controller('mems.controller.partsestimation', PartsEstimateController);
}());

function getPartsEstimate(http,window,serviceBase,localStorageService,scope){
	var t = localStorageService.get('actionLog');
	http({
		method:'POST',
		url:serviceBase + '/device/getPartsEstimate',
		data:localStorageService.get('actionLog').actionCode 
	}).success(function(data,status){
		scope.listPartsEstimate =  data.data;
		scope.defaultListPartEstimate = data.data;
	}).error(function(data,status){
		
	});
}

function getPartEstimateItems(scope,http,window,serviceBase){
	http({
		method:'POST',
		url:serviceBase + '/device/getPartEstimateItems',
		data:scope.partEstimateSelected.selected.peCode
	}).success(function(data,status){
		scope.listPartEstimateItems = data.data;
        scope.defaultListPartEstimateItems = data.data;
	}).error(function(data,status){
		
	});
}

function getPartItemsEstimate(scope,http,window,serviceBase,localStorageService){
	var t = localStorageService.get('actionLog').devCode;
	http({
        method:'POST',
        url:serviceBase + '/device/getPartItemsEstimate',
        data:localStorageService.get('actionLog').devCode
    }).success(function(data,status){
    	scope.listPartItemsEstimate = data.data;
    	scope.newPart = {
    		name:'Other'
    	};
    	scope.listPartItemsEstimate.push(scope.newPart);
    }).error(function(data,status){

    });
}

function delPartEstimate(scope){
	for(var i = 0; i < scope.listPartsEstimate.length; i++){
		scope.listPartsEstimate.isDeleted = 1;
	}
}

function delPartEstimateItem(scope){
	for(var i = 0; i < scope.listPartsEstimate.length; i++){
		scope.listPartEstimateItems.isDeleted = 1;
	}
}

function addNewItem(scope){
	scope.mode = 1;
	scope.itemIndex = -1;
	scope.partEstimateItemSelected = {};
}

function applyItem(scope){
	var t = scope.partItemsEstimateSelected.selected;
	if(scope.itemIndex != -1){
		scope.listPartEstimateItems[scope.itemIndex] = scope.partEstimateItemSelected.selected;
	} else {
		if(!scope.partItemsEstimateSelected.selected){
			alert('You don\'n chose part to estimate. \n Please chose a part or create new part to estimate.');
			document.getElementById('txtItemEstimate').selected = true;
		} else {
			if(scope.partItemsEstimateSelected.selected.name != 'Other'){
				scope.newPartItemEstimate.partName = scope.partItemsEstimateSelected.selected.name;
			} else {
                scope.newPartItemEstimate.partName = scope.newPartItemEstimate.name;
            }
            scope.newPartItemEstimate.partModelNo = 'unknow';
			scope.listPartEstimateItems.push(scope.newPartItemEstimate);
		}
		
	}
}


function saveToLocalstorage(scope, localStorageService,location){
	localStorageService.set('listPartsEstimate', angular.toJson(scope.listPartsEstimate));
    localStorageService.set('listPartEstimateItems', angular.toJson(scope.listPartEstimateItems));
    location.path('/repairrequest');
}

