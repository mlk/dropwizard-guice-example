angular.module('app.MainController', [])
	.controller('MainCtrl', ['$scope', '$http', function ($scope, $http) {
		$scope.message = "click the squirrel";
		$scope.squirrel = function() {
			$http.get('/api/hello-world')
					.then(function(res){
							$scope.message = res.data.content;
						});
		}
	}]);