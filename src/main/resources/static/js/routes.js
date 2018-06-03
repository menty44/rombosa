/**
 * Created by admin on 6/3/18.
 * Fredrick Oluoch
 * http://www.blaqueyard.com
 * 0720106420 | 0722508906
 * email: menty44@gmail.com
 */

//var mainApp = angular.module("mainApp", []);

angular.config("mainApp", [ "$routeProvider", function($routeProvider){

    $routeProvider
        .when("/", {
            templateUrl : "index.html"
        })
        .when("/leak", {
            templateUrl : "leak.html"
        })
        .when("/upload", {
            templateUrl : "upload.html"
        })
        .when("/photos", {
            templateUrl : "photos.html"
        });

}]);
//var app = angular.module("myApp", ["ngRoute"]);
// mainApp.config(function($routeProvider) {
    // $routeProvider
    //     .when("/", {
    //         templateUrl : "index.html"
    //     })
    //     .when("/leak", {
    //         templateUrl : "leak.html"
    //     })
    //     .when("/upload", {
    //         templateUrl : "upload.html"
    //     })
    //     .when("/photos", {
    //         templateUrl : "photos.html"
    //     });
// });