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
            templateUrl: 'payment.html',
            controler: 'paymentController'

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

routerApp.directive('loadingPane', function ($timeout, $window) {
    return {
        restrict: 'A',
        link: function (scope, element, attr) {
            var directiveId = 'loadingPane';

            var targetElement;
            var paneElement;
            var throttledPosition;

            function init(element) {
                targetElement = element;

                paneElement = angular.element('<div>');
                paneElement.addClass('loading-pane');

                if (attr['id']) {
                    paneElement.attr('data-target-id', attr['id']);
                }

                var spinnerImage = angular.element('<div>');
                spinnerImage.addClass('spinner-image');
                spinnerImage.appendTo(paneElement);

                angular.element('body').append(paneElement);

                setZIndex();

                //reposition window after a while, just in case if:
                // - watched scope property will be set to true from the beginning
                // - and initial position of the target element will be shifted during page rendering
                $timeout(position, 100);
                $timeout(position, 200);
                $timeout(position, 300);

                throttledPosition = _.throttle(position, 50);
                angular.element($window).scroll(throttledPosition);
                angular.element($window).resize(throttledPosition);
            }

            function updateVisibility(isVisible) {
                if (isVisible) {
                    show();
                } else {
                    hide();
                }
            }

            function setZIndex() {
                var paneZIndex = 500;

                paneElement.css('zIndex', paneZIndex).find('.spinner-image').css('zIndex', paneZIndex + 1);
            }

            function position() {
                paneElement.css({
                    'left': targetElement.offset().left,
                    'top': targetElement.offset().top - $(window).scrollTop(),
                    'width': targetElement.outerWidth(),
                    'height': targetElement.outerHeight()
                });
            }

            function show() {
                paneElement.show();
                position();
            }

            function hide() {
                paneElement.hide();
            }

            init(element);

            scope.$watch(attr[directiveId], function (newVal) {
                updateVisibility(newVal);
            });

            scope.$on('$destroy', function cleanup() {
                paneElement.remove();
                $(window).off('scroll', throttledPosition);
                $(window).off('resize', throttledPosition);
            });
        }
    };
});

routerApp.directive('loading', function () {
    return {
        restrict: 'E',
        replace:true,
        template: '<div class="loading"><img src="6.gif" width="20" height="20" />LOADING...</div>',
        link: function (scope, element, attr) {
            scope.$watch('loading', function (val) {
                if (val)
                    element.show();
                else
                    element.hide();
            });
        }
    }
});

routerApp.directive('fredloading', function () {
    return {
        restrict: 'E',
        replace:true,
        template: '<div class="fredloading"><img src="6.gif"  />LOADING...</div>',
        link: function (scope, element, attr) {
            scope.$watch('fredloading', function (val) {
                if (val)
                    scope.loadingStatus = 'true';
                else
                    scope.loadingStatus = 'false';
            });
        }
    }
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

routerApp.controller('paymentController', function($scope, $http) {

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

    $scope.clickMe = function() {
        $scope.loading = true;
        $scope.loadingStatus = 'true';

        $http.get('test.json')
            .success(function(data) {
                $scope.cars = data[0].cars;
                $scope.loading = false;
                $scope.loadingStatus = 'false';
            });
    }

});

