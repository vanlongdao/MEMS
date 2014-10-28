/**
 * Created by michael on 10/14/14.
 */

app.directive('validSubmit', [ '$parse', function($parse) {
    return {
        link: function(scope, element, iAttrs, controller) {
            var form = element.controller('form');
            form.$submitted = false;
            var fn = $parse(iAttrs.validSubmit);

            element.on('submit', function(event) {
                scope.$apply(function() {
                    element.addClass('ng-submitted');
                    form.$submitted = true;
                    if (form.$valid) {
                        fn(scope, { $event : event });
                        form.$submitted = false;
                    }
                });
            });
        }
    };
}
]);

app.controller('MainCtrl', function($scope) {
    $scope.sendForm = function() {
        alert('form valid, sending request...');
    };
});
