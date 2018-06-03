/**
 * Created by admin on 5/30/18.
 * Fredrick Oluoch
 * http://www.blaqueyard.com
 * 0720106420 | 0722508906
 * email: menty44@gmail.com
 */

var mainApp = angular.module("mainApp", []);

mainApp.controller('studentController', function($scope) {
    $scope.student = {
        firstName: "Mahesh",
        lastName: "Parashar",
        fees:500,

        subjects:[
            {name:'Physics',marks:70},
            {name:'Chemistry',marks:80},
            {name:'Math',marks:65}
        ],

        fullName: function() {
            var studentObject;
            studentObject = $scope.student;
            return studentObject.firstName + " " + studentObject.lastName;
        }
    };
});