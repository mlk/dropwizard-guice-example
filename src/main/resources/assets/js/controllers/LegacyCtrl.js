angular.module('app.LegacyController', [])
	.controller('LegacyCtrl', ['$scope', '$http', function ($scope, $http) {
    $scope.squirrel = function() {
        $http.get('/legacy/servlet')
                   .then(function(res){
                        alert(res.data);
                    });
    }}]);

