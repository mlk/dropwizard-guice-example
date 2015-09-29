'use strict';

var app = angular.module('app', [
    'ui.router',
    'app.MainController'
]);

app.config(['$urlRouterProvider', '$stateProvider', function ($urlRouteProvider, $stateProvider) {
    $urlRouteProvider.otherwise('/');

    $stateProvider.state('home', {
        url: '/',
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
    });
}]);

//Required to pass the view state back to the display page (for top nav mainly)
app.run(function ($state,$rootScope, $log) {
   $rootScope.$state = $state;
});