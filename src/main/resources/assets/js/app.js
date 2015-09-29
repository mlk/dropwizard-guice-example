'use strict';

var app = angular.module('app', [
    'ui.router',
    'app.MainController',
    'app.WebsocketsController'
]);

app.config(['$urlRouterProvider', '$stateProvider', function ($urlRouteProvider, $stateProvider) {
    $urlRouteProvider.otherwise('/');

    $stateProvider.state('home', {
        url: '/',
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
    }).state('websockets', {
        url: '/websockets',
        templateUrl: 'views/websockets.html',
        controller: 'WebsocketsCtrl'
    });
}]);

//Required to pass the view state back to the display page (for top nav mainly)
app.run(function ($state,$rootScope, $log) {
   $rootScope.$state = $state;
});