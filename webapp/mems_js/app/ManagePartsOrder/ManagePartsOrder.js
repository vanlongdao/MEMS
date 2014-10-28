/**
 *
 */
(function(){
    var injectParams = ['$scope', 'messages', '$http', '$window', 'authService', '$location','$sce','memsAppConfig','localStorageService'];
    var PartsOrderController = function($scope,message, $http, $window, authService, $location ,$sce,memsAppConfig,localStorageService){
        var serviceBase = memsAppConfig.api;
        $scope.trustAsHtml = function(value) {
            return $sce.trustAsHtml(value);
        }
        $scope.repairCode = '',
        $scope.partOrderSelected = {},
        $scope.partOrderItemSelected = {},
        $scope.partItemsOrderSelected = {},
        $scope.partEstimateSelected = {},
        $scope.partEstimateItemSelected = {},
        //mode: action mode, with mode = 0 mean user in view mode, if mode = 1 mean user in add new item mode
        $scope.mode = 0,
        $scope.itemIndex = 0,
        $scope.newPartItemOrder = {
            name: '',
            itemPrice:'',
            numItems:'',
            totPrice:'',
            taxRate:'',
             tax:'',
             priceWithTax:''
        }
        $scope.rdoEstimateChoice = '',
        getPartsOrder($http,serviceBase,localStorageService,$scope);


        $scope.$watch('partOrderSelected.selected', function(){
            if($scope.listPartsOrder) {
                $scope.mode = 0;
                getPartOrderItems($scope, $http, serviceBase,localStorageService);
            }
        });

        $scope.$watch('partOrderItemSelected.selected', function(){
            if($scope.listPartOrderItems){
                $scope.mode = 0;
                $scope.itemIndex = $scope.listPartOrderItems.indexOf($scope.partOrderItemSelected.selected);
                getPartItemsOrder($scope, $http, serviceBase,localStorageService);
            }
        });

        $scope.$watch('partEstimateSelected.selected', function(){
            if($scope.listPartsEstimate) {
                $scope.mode = 0;
                getPartEstimateItemsPopup($scope, $http, serviceBase);
            }
        });

        $scope.delPartOrder = function(){
            delPartOrder($scope);
        }

        $scope.delPartOrderItem = function(){
            if($scope.listPartsOrder.length > 0){
                delPartOrderItem($scope);
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
            $scope.listPartsOrder = scope.defaultListPartOrder;
            $scope.listPartOrderItems = scope.defaultListPartOrderItems;
            saveToLocalstorage($scope, localStorageService,$location);
        }
        
        $scope.copyFromEstimation = function(){
        	getPartsEstimatePopup($http,serviceBase,localStorageService,$scope);
            document.getElementById('partOrderDialog').toggle();
        }

        $scope.selectEstimate = function(){
            var copyData = $scope.partEstimateSelected.selected;
            var copyPart = {
            	actionCode:copyData.actionCode,
            	distributorPerson: copyData.distributorPerson,
                poCode : copyData.peCode,
                etaDate : '',
                payDate : '',
                orderDate : '',
                destOffice : '',
                paymentTerms : '',
                status : '',
                distributorOffice:copyData.distributorOffice,
                entityServiceOffice:copyData.supplierOffice,
                entityRequestOffice:copyData.supplierOffice,
                destinationOffice:copyData.deliveryToOffice,
                totalPrice:copyData.totalPrice,
                tax:copyData.tax
            };
            $scope.listPartsOrder.push(copyPart);
            if(!$scope.listPartOrderItems)
            	$scope.listPartOrderItems = [];
            $scope.listPartOrderItems.push($scope.partEstimateItemSelected.selected);
            document.getElementById('partOrderDialog').toggle();
        }

        $scope.back = function(){
            $scope.listPartsEstimate = [];
            $scope.listPartEstimateItems = [];
            document.getElementById('partOrderDialog').toggle();
        }
    };

    PartsOrderController.$inject = injectParams;
    app.controller('mems.controller.partsorder', PartsOrderController);
}());

function getPartsOrder(http,serviceBase,localStorageService,scope){
	var t = localStorageService.get('userInfo');
    http({
        method:'POST',
        url:serviceBase + '/device/getPartsOrder',
        headers:{'officeCode':localStorageService.get('userInfo').officeCode},
        data:localStorageService.get('actionLog').actionCode
    }).success(function(data,status){
        scope.listPartsOrder =  data.data;
        scope.defaultListPartOrder = data.data;
    }).error(function(data,status){

    });
}

function getPartOrderItems(scope,http,serviceBase,localStorageService){
    http({
        method:'POST',
        url:serviceBase + '/device/getPartOrderItems',
        headers:{'officeCode':localStorageService.get('userInfo').officeCode},
        data:scope.partOrderSelected.selected.poCode
    }).success(function(data,status){
        scope.listPartOrderItems = data.data;
        scope.defaultListPartOrderItems = data.data;
    }).error(function(data,status){

    });
}

function getPartItemsOrder(scope,http,serviceBase,localStorageService){
    http({
        method:'POST',
        url:serviceBase + '/device/getPartItemsOrder',
        data:localStorageService.get('actionLog').devCode
    }).success(function(data,status){
        scope.listPartItemsOrder = data.data;
        scope.newPart = {
            name:'Other'
        };
        scope.listPartItemsOrder.push(scope.newPart);
    }).error(function(data,status){

    });
}

function delPartOrder(scope){
    for(var i = 0; i < scope.listPartsOrder.length; i++){
        scope.listPartsOrder.isDeleted = 1;
    }
}

function delPartOrderItem(scope){
    for(var i = 0; i < scope.listPartsOrder.length; i++){
        scope.listPartOrderItems.isDeleted = 1;
    }
}

function addNewItem(scope){
    scope.mode = 1;
    scope.itemIndex = -1;
    scope.partOrderItemSelected = {};
}

function applyItem(scope){
    var t = scope.partItemsOrderSelected.selected;
    if(scope.itemIndex != -1){
        scope.listPartOrderItems[scope.itemIndex] = scope.partOrderItemSelected.selected;
    } else {
        if(!scope.partItemsOrderSelected.selected){
            alert('You don\'n chose part to Order. \n Please chose a part or create new part to Order.');
            document.getElementById('txtItemOrder').selected = true;
        } else {
            if(scope.partItemsOrderSelected.selected.name != 'Other'){
                scope.newPartItemOrder.partName = scope.partItemsOrderSelected.selected.name;
            } else {
                scope.newPartItemOrder.partName = scope.newPartItemOrder.name;
            }
            scope.newPartItemOrder.partModelNo = 'unknow';
            scope.listPartOrderItems.push(scope.newPartItemOrder);
        }

    }
}


function saveToLocalstorage(scope, localStorageService,location){
    localStorageService.set('listPartsOrder', angular.toJson(scope.listPartsOrder));
    localStorageService.set('listPartOrderItems', angular.toJson(scope.listPartOrderItems));
    location.path('/repairrequest');
}

function addNewPartOrder(){

}

function getPartsEstimatePopup(http,serviceBase,localStorageService,scope){
	http({
		method:'POST',
		url:serviceBase + '/device/getPartsEstimate',
		data:localStorageService.get('actionLog').actionCode 
	}).success(function(data,status){
		scope.listPartsEstimate =  data.data;
	}).error(function(data,status){
		
	});
}

function getPartEstimateItemsPopup(scope,http,serviceBase){
	http({
		method:'POST',
		url:serviceBase + '/device/getPartEstimateItems',
		data:scope.partEstimateSelected.selected.peCode
	}).success(function(data,status){
		scope.listPartEstimateItems = data.data;
	}).error(function(data,status){
		
	});
}