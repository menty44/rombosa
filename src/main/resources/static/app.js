/**
 * Created by admin on 9/2/18.
 * Fredrick Oluoch
 * http://www.blaqueyard.com
 * 0720106420 | 0722508906
 * email: menty44@gmail.com
 */
/**
 * Created by admin on 8/5/18.
 * Fredrick Oluoch
 * http://www.blaqueyard.com
 * 0720106420 | 0722508906
 * email: menty44@gmail.com
 */
angular.module('Toast',['angularjsToast']);
angular.module('routerApp', ['ui.router','Toast'])

.config(function($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/home');

    $stateProvider

    // HOME STATES AND NESTED VIEWS ========================================
        .state('home', {
            url: '/home',
            templateUrl: 'partial-home.html'
        })

        // nested list with custom controller
        // .state('home.list', {
        //     url: '/list',
        //     templateUrl: 'partial-home-list.html',
        //     controller: function($scope) {
        //         $scope.dogs = ['Bernese', 'Husky', 'Goldendoodle'];
        //     }
        // })

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
            templateUrl: 'login.html',
            controller: 'loginController'
            // controller: function($scope) {
            //     $scope.dogs = ['Bernese', 'Husky', 'Goldendoodle'];
            // }
        })

        // nested list with custom controller
        .state('register', {
            url: '/register',
            templateUrl: 'register.html',
            controller: 'regController'
            // controller: function($scope) {
            //     console.log('register has been clicked');
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

})

.factory('SweetAlert', [ '$rootScope', function ( $rootScope ) {

    var swal = window.swal;

//public methods
    var self = {

        swal: function ( arg1, arg2, arg3 ) {
            $rootScope.$evalAsync(function(){
                if( typeof(arg2) === 'function' ) {
                    swal( arg1, function(isConfirm){
                        $rootScope.$evalAsync( function(){
                            arg2(isConfirm);
                        });
                    }, arg3 );
                } else {
                    swal( arg1, arg2, arg3 );
                }
            });
        },
        success: function(title, message) {
            $rootScope.$evalAsync(function(){
                swal( title, message, 'success' );
            });
        },
        error: function(title, message) {
            $rootScope.$evalAsync(function(){
                swal( title, message, 'error' );
            });
        },
        warning: function(title, message) {
            $rootScope.$evalAsync(function(){
                swal( title, message, 'warning' );
            });
        },
        info: function(title, message) {
            $rootScope.$evalAsync(function(){
                swal( title, message, 'info' );
            });
        }
    };

    return self;
}])

.directive('loadingPane', function ($timeout, $window) {
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
})

.directive('loading', function () {
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
})

.directive('fredloading', function () {
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
})

.controller('scotchController', function($scope) {

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

})

.controller('paymentController', function($scope, $http) {

    console.log('paymentController');
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

})

// routerApp.controller('DemoController', DemoController,['sweetalert']);
.controller('regController',  function($scope, $http, toast) {

    $scope.registernew  = function registernew(firstname, lastname, email, mobile, password) {

        // Text
        $.LoadingOverlay("show", {
            image: "",
            background: "rgba(165, 190, 100, 0.5)",
            text: "Please wait as we send an Activation Link to your Email....."
        });

        console.log('firstname', firstname);
        console.log('lastname', lastname);
        console.log('email', email);
        console.log('mobile', mobile);
        console.log('password', password);

        if(firstname && lastname && email && mobile && password) {

            var emailurl = "http://localhost:8080/api/validateuseremail/" + email;
            var mobileurl = "http://localhost:8080/api/validatemobile/" + mobile;
            var url = "http://localhost:8080/api/regnewuser?firstname=" + firstname + "&lastname=" + lastname + "&mobile=" + mobile + "&email=" + email + "&password=" + password + "&v=" + Date.now();

            $http.get(emailurl).then(function (response) {

                //console.log('the time ', _.now);
                var emailresponse = response.data;

                var testemail = emailresponse;

                console.log('email data response', testemail);

                if(testemail.code == "00"){

                    $http.get(mobileurl).then(function (response) {

                        //console.log('the time ', _.now);
                        var mobileresponse = response.data;

                        var testmobile = mobileresponse;

                        $scope.testing = testmobile.code;

                        console.log('++ testmobile ++', testmobile);

                        if(testmobile.code == "00"){

                            $http.get(url).then(function (response) {

                                //console.log('the time ', _.now);
                                $scope.fred = response.data;

                                var test = $scope.fred;

                                $scope.testing = test.code;

                                toast({
                                    duration  : 10000,
                                    message   : "Hi there!",
                                    className : "alert-success"
                                });

                                console.log('++ succeess registration ++', test);
                                acivateaccount(email,password);
                                //$.LoadingOverlay("hide");
                            });

                        }else {
                            $scope.testing = {
                                "code" : "03",
                                "message" : "Mobile Phone Number has already been Used, try to Register using a new one"};
                            $.LoadingOverlay("hide");
                        }

                    });
                }else {
                    $scope.testing = {
                        "code" : "03",
                        "message" : "Email has already been Used, try to Register using a new one"
                    };
                    $.LoadingOverlay("hide");
                }

            });

        }else {
            $scope.testing = {
                "code" : "03",
                "message" : "Missing Parameters"};
            $.LoadingOverlay("hide");
        }
    }

    }
)

.controller('loginController',  function($scope, $http, $rootScope, $timeout, $state) {

    $scope.loginacc = function loginacc(email, password) {

        const myaccount = email ? email : 'Unknown Account';

        $.LoadingOverlay("show", {
            image: "",
            background: "rgba(165, 190, 100, 0.5)",
            text: "Looking for your Account "+ myaccount
        });

        if(email && password){

            console.log('login controller has been clicked');
            console.log('my email ', email);
            console.log('my password ', password);

            var loginnurl = "http://localhost:8080/api/login?email="+email+"&password="+password;

            // Simple GET request
            $http({
                method: 'GET',
                url: loginnurl
            }).then(function successCallback(response) {
                // this callback will be called asynchronously
                // when the response is available
                //console.log('the time ', _.now);
                var loginresponse = response.data;
                var loginresponse1 = response;

                console.log("myloginresonse "+ JSON.stringify(loginresponse));
                console.log("myloginresonseheadres "+ JSON.stringify(loginresponse1));

                if(loginresponse.ok == '00'){

                    $scope.fred = response.data;

                    var test = $scope.fred;

                    $scope.fred = test.ok;

                    $scope.test = "Success login";

                    //swal("hey fred");
                    // acivateaccount(email,password);
                }else {
                    $scope.letshide = showAlertSrvc(5000);
                    console.log('i have entered here ', response.status);
                    $scope.testfinal = "Server Error, Please contact admin at info@blaqueyard.com";
                    $.LoadingOverlay("hide");
                    hidethem()
                }

            }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                //console.log('the time ', _.now);
                var loginresponse = response.data;
                var loginresponse1 = response;

                console.log("myloginresonse "+ JSON.stringify(loginresponse));
                console.log("myloginresonseheadres "+ JSON.stringify(loginresponse1.status));

                if(loginresponse.code == "03"){
                    $scope.bad = '03';
                    $scope.testerror = "Missing Parameters";
                    $scope.letshide = showAlertSrvc(5000);
                    hidethem()
                } else if(response.status == 500){
                    $scope.statustest500 = '500';
                    console.log('i have entered here ', response.status);
                    $scope.test500 = "Invalid login credentials";
                    $scope.letshide = showAlertSrvc(5000);
                    $.LoadingOverlay("hide");
                    hidethem()
                }else if (response.status == 400){
                    console.log('i have entered here ', response.status);
                    $scope.test400 = "Invalid login credentials";
                    $scope.letshide = showAlertSrvc(5000);
                    $.LoadingOverlay("hide");
                    hidethem()
                }else if (response.status == 404){
                    console.log('i have entered here ', response.status);
                    $scope.test404 = "Invalid login credentials";
                    $scope.letshide = showAlertSrvc(5000);
                    $.LoadingOverlay("hide");
                    hidethem()
                }else {
                    console.log('i have entered here ', response.status);
                    $scope.testfinal = "Server Error, Please contact admin at info@blaqueyard.com";
                    $scope.letshide = showAlertSrvc(5000);
                    $.LoadingOverlay("hide");
                    hidethem()
                }

                $scope.headerstatus = loginresponse1.status;
                $scope.testing = "Incorect credentials, Please Supply Correct credentials or change password";
                $scope.letshide = showAlertSrvc(5000);
                $.LoadingOverlay("hide");
                hidethem()
            });
        }else {
            console.log("myloginresonse "+"Missing Parameters");
            $scope.badmissin = '23';
            $scope.letshide = showAlertSrvc(5000);
            $scope.testing = {
                "code" : "03",
                "message" : "Missing Parameters"};
            $.LoadingOverlay("hide");
            hidethem()
        }

    };

    function acivateaccount(email,password) {

        console.info('activate has been called '+ email+' '+password);

        var activateurl = "http://localhost:8080/api/acivatelogin?email="+email+"&password="+password;

        // Simple GET request
        $http({
            method: 'GET',
            url: activateurl
        }).then(function successCallback(response) {
            var secure = response.data;
            $rootScope.activated = secure;

            toast({
                duration  : 10000,
                message   : "Hi there!",
                className : "alert-success"
            });

            console.log('loginobject ', secure);
            $.LoadingOverlay("hide");
            //swal("hello");
            hidethem();
            $state.go("dashboard");
        }, function errorCallback(response) {

            var loginresponse = response.data;
            var loginresponse1 = response;

            console.log("myloginresonse "+ JSON.stringify(loginresponse));
            console.log("myloginresonseheadres "+ JSON.stringify(loginresponse1.status));

            if(loginresponse.code == "03"){
                $scope.bad = '03';
                $scope.testerror = "Missing Parameters";
                $scope.letshide = showAlertSrvc(5000);
                hidethem()
            } else if(response.status == 500){
                $scope.statustest500 = '500';
                console.log('i have entered here ', response.status);
                $scope.test500 = "Invalid login credentials";
                $scope.letshide = showAlertSrvc(5000);
                $.LoadingOverlay("hide");
                hidethem()
            }else if (response.status == 400){
                console.log('i have entered here ', response.status);
                $scope.test400 = "Invalid login credentials";
                $scope.letshide = showAlertSrvc(5000);
                $.LoadingOverlay("hide");
                hidethem()
            }else if (response.status == 404){
                console.log('i have entered here ', response.status);
                $scope.test404 = "Invalid login credentials";
                $scope.letshide = showAlertSrvc(5000);
                $.LoadingOverlay("hide");
                hidethem()
            }else {
                console.log('i have entered here ', response.status);
                $scope.testfinal = "Server Error, Please contact admin at info@blaqueyard.com";
                $scope.letshide = showAlertSrvc(5000);
                $.LoadingOverlay("hide");
                hidethem()
            }

            $scope.headerstatus = loginresponse1.status;
            $scope.testing = "Incorect credentials, Please Supply Correct credentials or change password";
            $scope.letshide = showAlertSrvc(5000);
            $.LoadingOverlay("hide");
            hidethem()
        });

    }
    function hidethem(){
        $timeout(function () {
            console.log("interval has been initiated");
            $scope.mydivs = true;
        }, 3500);
    }


});




angular.module('routerApp').service('showAlertSrvc', ['$timeout', function($timeout) {
        return function(delay) {
            var result = {hidden:true};
            $timeout(function() {
                result.hidden=false;
            }, delay);
            return result;
        };
    }]);


