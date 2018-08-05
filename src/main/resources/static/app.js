/**
 * Created by admin on 8/5/18.
 * Fredrick Oluoch
 * http://www.blaqueyard.com
 * 0720106420 | 0722508906
 * email: menty44@gmail.com
 */

var routerApp = angular.module('routerApp', ['ui.router']);

routerApp.config(function($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/home');

    $stateProvider

    // HOME STATES AND NESTED VIEWS ========================================
        .state('home', {
            url: '/home',
            templateUrl: 'partial-home.html'
        })

        // nested list with custom controller
        .state('home.list', {
            url: '/list',
            templateUrl: 'partial-home-list.html',
            controller: function($scope) {
                $scope.dogs = ['Bernese', 'Husky', 'Goldendoodle'];
            }
        })

        // nested list with custom controller
        .state('leaked', {
            url: '/leaked',
            templateUrl: 'leaked.html'
            // controller: function($scope) {
            //     $scope.dogs = ['Bernese', 'Husky', 'Goldendoodle'];
            // }
        })

        // nested list with custom controller
        .state('uload', {
            url: '/uload',
            templateUrl: 'upload.html'
            // controller: function($scope) {
            //     $scope.dogs = ['Bernese', 'Husky', 'Goldendoodle'];
            // }
        })

        // nested list with custom controller
        .state('payment', {
            url: '/payment',
            templateUrl: 'payment.html'
            // controller: function($scope) {
            //     $scope.dogs = ['Bernese', 'Husky', 'Goldendoodle'];
            // }
        })

        // nested list with custom controller
        .state('login', {
            url: '/login',
            templateUrl: 'login.html'
            // controller: function($scope) {
            //     $scope.dogs = ['Bernese', 'Husky', 'Goldendoodle'];
            // }
        })

        // nested list with custom controller
        .state('register', {
            url: '/register',
            templateUrl: 'register.html'
            // controller: function($scope) {
            //     $scope.dogs = ['Bernese', 'Husky', 'Goldendoodle'];
            // }
        })

        // nested list with custom controller
        .state('users', {
            url: '/users',
            templateUrl: 'users.html'
            // controller: function($scope) {
            //     $scope.dogs = ['Bernese', 'Husky', 'Goldendoodle'];
            // }
        })

        // nested list with custom controller
        .state('videos', {
            url: '/videos',
            templateUrl: 'videos.html'
            // controller: function($scope) {
            //     $scope.dogs = ['Bernese', 'Husky', 'Goldendoodle'];
            // }
        })

        // nested list with custom controller
        .state('photos', {
            url: '/photos',
            templateUrl: 'photos.html'
            // controller: function($scope) {
            //     $scope.dogs = ['Bernese', 'Husky', 'Goldendoodle'];
            // }
        })

        // nested list with custom controller
        .state('uncensored', {
            url: '/uncensored',
            templateUrl: 'uncensored.html'
            // controller: function($scope) {
            //     $scope.dogs = ['Bernese', 'Husky', 'Goldendoodle'];
            // }
        })
        // nested list with custom controller
        .state('profile', {
            url: '/profile',
            templateUrl: 'profile.html'
            // controller: function($scope) {
            //     $scope.dogs = ['Bernese', 'Husky', 'Goldendoodle'];
            // }
        })

        // nested list with custom controller
        .state('audit', {
            url: '/audit',
            templateUrl: 'audit.html'
            // controller: function($scope) {
            //     $scope.dogs = ['Bernese', 'Husky', 'Goldendoodle'];
            // }
        })

        // nested list with custom controller
        .state('dashboard', {
            url: '/dashboard',
            templateUrl: 'dashboard.html'
            // controller: function($scope) {
            //     $scope.dogs = ['Bernese', 'Husky', 'Goldendoodle'];
            // }
        })


        // nested list with just some random string data
        .state('home.paragraph', {
            url: '/paragraph',
            template: 'I could sure use a drink right now.'
        })

        .state('/', {
            url: '/',
            templateUrl: 'main.html'
        })

        // ABOUT PAGE AND MULTIPLE NAMED VIEWS =================================
        .state('about', {
            url: '/about',
            views: {
                '': { templateUrl: 'partial-about.html' },
                'columnOne@about': { template: 'Look I am a column!' },
                'columnTwo@about': {
                    templateUrl: 'table-data.html',
                    controller: 'scotchController'
                }
            }

        });

});

routerApp.controller('scotchController', function($scope) {

    $scope.message = 'test';

    $scope.scotches = [
        {
            name: 'Macallan 12',
            price: 50
        },
        {
            name: 'Chivas Regal Royal Salute',
            price: 10000
        },
        {
            name: 'Glenfiddich 1937',
            price: 20000
        }
    ];

});