'use strict';

var app = angular.module('app', [
    'ui.router',
    'angular.atmosphere',
    'app.MainController',
    'app.ChatController',
    'app.LegacyController'
]);

app.config(['$urlRouterProvider', '$stateProvider', function ($urlRouteProvider, $stateProvider) {
    $urlRouteProvider.otherwise('/');

    $stateProvider.state('home', {
        url: '/',
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
    }).state('chat', {
          url: '/chatpage',
          templateUrl: 'views/chat.html',
          controller: 'ChatCtrl'
    }).state('legacy', {
            url: '/legacy',
            templateUrl: 'views/legacy.html',
            controller: 'LegacyCtrl',
        });
}]);

//Required to pass the view state back to the display page (for top nav mainly)
app.run(function ($state,$rootScope, $log) {
   $rootScope.$state = $state;
});
