/**
 * Created by admin on 6/3/18.
 * Fredrick Oluoch
 * http://www.blaqueyard.com
 * 0720106420 | 0722508906
 * email: menty44@gmail.com
 */
var routerApp = angular.module("routerApp");

routerApp.controller("fredCtrl", function($scope, $http) {

    $scope.categories = function categories() {
        // Simple GET request example:
        $http({
            method: "GET",
            url: "http://localhost:8080/api/category"
        }).then(function successCallback(response) {
            // this callback will be called asynchronously
            // when the response is available
            console.log("response", response);
            console.log("response", response.data);

            $scope.navbar = response.data;

        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            console.log("error response", response);
        });
    };


});

