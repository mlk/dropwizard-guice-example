var app = angular.module('app', []);

app.controller('MainCtrl', ['$scope', '$http', function ($scope, $http) {
    $scope.name = "";
    $scope.message = "click the squirrel";
    $scope.squirrel = function() {
        data = ""
        if($scope.name) {
            data = "name=" + encodeURIComponent($scope.name);
        }
        $http.post('/api/hello-world', data, { headers: {'Content-Type': 'application/x-www-form-urlencoded'} })
                   .then(function(res){
                        $scope.message = res.data.content;
                    });
    }
}]);