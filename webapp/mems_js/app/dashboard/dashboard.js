(function(){

    var injectParams = ['$rootScope', '$scope', 'messages', '$location', 'workingContext', '$http',
    	'localStorageService', 'deviceService', '$sce', 'hospitalDepartmentService', 'ActionLog', 'exceptionHandler'];
    var dashboardController = function ($rootScope, $scope, messages, $location , workingContext, $http,
    		localStorageService, deviceService, $sce, hospitalDepartmentService, ActionLog, exceptionHandler) {
        $scope.messages = messages;

		$scope.selectedDepartment = {selected:null};
		$scope.allDepartments = [{}];
		$scope.refreshDepartments = function(query){
			return hospitalDepartmentService.findDepartments(query).then(function(response){
				$scope.allDepartments = response.data.data;
			});
		};
		$scope.myData = function(){
			return [$scope.selectedDepartment];
		};

        $scope.selectedDevice = {};
		$scope.allDevices = [{}];



		$scope.refreshDevices = function(query) {
			return deviceService.findDevices(query, $scope.selectedDepartment.selected).then(function(response){
				$scope.allDevices = response.data.data;
			});
		};

		$scope.user = workingContext.userInfo();

        $scope.openRepairRequest = function(){
        	openRepairRequest($http, $location, localStorageService);
        };

        $scope.logout = function(){
        	$rootScope.$broadcast("loggingOut");
        };

        $scope.$watch(function(scope){
			return scope.selectedDepartment.selected;
		}, function(oldVal, newVal, scope){
        	$scope.refreshDevices('');
        }, true);

        $scope.allActions = [];

        $scope.$watch(function(scope){
        	return scope.selectedDevice.selected;
        }, function(oldValue, newValue, scope){
        	if(scope.selectedDevice.selected) {
				scope.getAllAction = deviceService.findActionsByDevice('', scope.selectedDevice.selected).then(function (response) {
					$scope.allActions = response.data.data;
				});
			}
        }, true);

        $scope.openDevice = function(){
			$rootScope.$broadcast("openDevice", $scope.selectedDevice.selected);
        };
        
        $scope.openRepairRequest = function(){
        	openRepairRequest($http, $location, localStorageService);
        }

        $scope.allActionsOfOffice = [];

        $scope.refreshActionsOfOffice = function(query){

        	// HOW TO CALL SERVICE: METHOD 1
        	//var allActionsOfOfficeResp = ActionLog.query(function(){
				//console.log(allActionsOfOfficeResp.data);
				//$scope.allActionsOfOffice = allActionsOfOfficeResp.data;
        	//}, function(resp){exceptionHandler.onError(resp)});


			// HOW TO CALL SERVICE: METHOD 2
            var success = function(allActionsResp){
        		console.log(allActionsResp.data);
        		$scope.allActionsOfOffice = allActionsResp.data;
            };

			ActionLog.query(success, function(resp){ exceptionHandler.onError(resp);});

        };
    }

    dashboardController.$inject = injectParams;

    app.controller('mems.controller.dashboard', dashboardController);

}());
