/**
 * Created by admin on 5/30/18.
 * Fredrick Oluoch
 * http://www.blaqueyard.com
 * 0720106420 | 0722508906
 * email: menty44@gmail.com
 */

// var routerApp = angular.module("routerApp");

// routerApp.controller('studentController', function($scope) {
//     console.log('studentController');
//     $scope.student = {
//         firstName: "Mahesh",
//         lastName: "Parashar",
//         fees:500,
//
//         subjects:[
//             {name:'Physics',marks:70},
//             {name:'Chemistry',marks:80},
//             {name:'Math',marks:65}
//         ],
//
//         fullName: function() {
//             var studentObject;
//             studentObject = $scope.student;
//             return studentObject.firstName + " " + studentObject.lastName;
//         }
//     };
// });

// routerApp.controller('registerController', function($scope, $http, $location, $cookieStore, $rootScope) {
//
//
//     $scope.test = 'tetetette';
//     $scope.master = function master() {
//
//         console.log('test register');
//         $scope.test = {
//             "firstname": "",
//             "lastname": "",
//             "mobile": "",
//             "email": "",
//             "password": "",
//             "activated": "",
//             "createdAt": "",
//             "updatedAt": ""
//         };
//
//         var data = {
//             "UserID": $scope.user.id,
//             "JackpotID": $scope.fred.JackpotID,
//             "Amount": $scope.betStake,
//             "games": betslip
//         };
//
//         console.log("THIS IS THE JACKPOTID POSTED TO CITYBET SERVER");
//         console.log($scope.fred.JackpotID);
//         console.log("THIS IS THE DATA POSTED TO CITYBET SERVER");
//         console.log(data);
//         var url = 'https://api.citybet.co.ke/v1/jackpot/bet';
//
//         $http.post(url, data).then(function (response) {
//             console.log("This was the response", response);
//             //$scope.clearBet();
//             $rootScope.$broadcast("globalDialogs.addDialog", {
//                 type: 'success',
//                 title: 'Success',
//                 content: 'Your Bet was submitted Successfully. '+ 'Your BetID is ' + response.data.BetID
//             });
//             $scope.removeall();
//         })
//
//
//
// }
// });

// routerApp.controller('loginController', function($scope, $http, $location, $cookieStore, $rootScope) {
//     $scope.student = {
//         firstName: "Mahesh",
//         lastName: "Parashar",
//         fees:500,
//
//         subjects:[
//             {name:'Physics',marks:70},
//             {name:'Chemistry',marks:80},
//             {name:'Math',marks:65}
//         ],
//
//         fullName: function() {
//             var studentObject;
//             studentObject = $scope.student;
//             return studentObject.firstName + " " + studentObject.lastName;
//         }
//     };
// });