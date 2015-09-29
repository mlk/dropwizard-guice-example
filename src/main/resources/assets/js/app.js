var app = angular.module('app', []);

app.controller('MainCtrl', ['$scope', '$http', function ($scope, $http) {
    $scope.name = "";
    $scope.message = "click the squirrel";

     socket = new WebSocket("ws://localhost:8080/ws/");

     socket.onopen = function() {
        console.log("Connected!");
        $status.prepend("<p>Connected websocket!</p>");
     };

     socket.onclose = function() {
        console.log("Closed!");
        $status.prepend("<p>Closed websocket</p>");
     };

     socket.onmessage = function(msg) {
        console.log("Gots message", msg, this);
        alert(msg.data);
     };


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