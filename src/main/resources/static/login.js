// /**
//  * Created by admin on 9/10/18.
//  * Fredrick Oluoch
//  * http://www.blaqueyard.com
//  * 0720106420 | 0722508906
//  * email: menty44@gmail.com
//  */
// //this is the login controller
//
// // routerApp.module('routerApp', [])
//     .controller('loginController',  function($scope, $http, $rootScope, showAlertSrvc, $timeout, $state) {
//
//     $scope.loginacc = function loginacc(email, password) {
//
//         const myaccount = email ? email : 'Unknown Account';
//
//         $.LoadingOverlay("show", {
//             image: "",
//             background: "rgba(165, 190, 100, 0.5)",
//             text: "Looking for your Account "+ myaccount
//         });
//
//         if(email && password){
//
//             console.log('login controller has been clicked');
//             console.log('my email ', email);
//             console.log('my password ', password);
//
//             var loginnurl = "http://localhost:8080/api/login?email="+email+"&password="+password;
//
//             // Simple GET request
//             $http({
//                 method: 'GET',
//                 url: loginnurl
//             }).then(function successCallback(response) {
//                 // this callback will be called asynchronously
//                 // when the response is available
//                 //console.log('the time ', _.now);
//                 var loginresponse = response.data;
//                 var loginresponse1 = response;
//
//                 console.log("myloginresonse "+ JSON.stringify(loginresponse));
//                 console.log("myloginresonseheadres "+ JSON.stringify(loginresponse1));
//
//                 if(loginresponse.ok == '00'){
//
//                     $scope.fred = response.data;
//
//                     var test = $scope.fred;
//
//                     $scope.fred = test.ok;
//
//                     $scope.test = "Success login";
//
//                     swal("hey fred");
//                     acivateaccount(email,password);
//                 }else {
//                     $scope.letshide = showAlertSrvc(5000);
//                     console.log('i have entered here ', response.status);
//                     $scope.testfinal = "Server Error, Please contact admin at info@blaqueyard.com";
//                     $.LoadingOverlay("hide");
//                     hidethem()
//                 }
//
//             }, function errorCallback(response) {
//                 // called asynchronously if an error occurs
//                 // or server returns response with an error status.
//                 //console.log('the time ', _.now);
//                 var loginresponse = response.data;
//                 var loginresponse1 = response;
//
//                 console.log("myloginresonse "+ JSON.stringify(loginresponse));
//                 console.log("myloginresonseheadres "+ JSON.stringify(loginresponse1.status));
//
//                 if(loginresponse.code == "03"){
//                     $scope.bad = '03';
//                     $scope.testerror = "Missing Parameters";
//                     $scope.letshide = showAlertSrvc(5000);
//                     hidethem()
//                 } else if(response.status == 500){
//                     $scope.statustest500 = '500';
//                     console.log('i have entered here ', response.status);
//                     $scope.test500 = "Invalid login credentials";
//                     $scope.letshide = showAlertSrvc(5000);
//                     $.LoadingOverlay("hide");
//                     hidethem()
//                 }else if (response.status == 400){
//                     console.log('i have entered here ', response.status);
//                     $scope.test400 = "Invalid login credentials";
//                     $scope.letshide = showAlertSrvc(5000);
//                     $.LoadingOverlay("hide");
//                     hidethem()
//                 }else if (response.status == 404){
//                     console.log('i have entered here ', response.status);
//                     $scope.test404 = "Invalid login credentials";
//                     $scope.letshide = showAlertSrvc(5000);
//                     $.LoadingOverlay("hide");
//                     hidethem()
//                 }else {
//                     console.log('i have entered here ', response.status);
//                     $scope.testfinal = "Server Error, Please contact admin at info@blaqueyard.com";
//                     $scope.letshide = showAlertSrvc(5000);
//                     $.LoadingOverlay("hide");
//                     hidethem()
//                 }
//
//                 $scope.headerstatus = loginresponse1.status;
//                 $scope.testing = "Incorect credentials, Please Supply Correct credentials or change password";
//                 $scope.letshide = showAlertSrvc(5000);
//                 $.LoadingOverlay("hide");
//                 hidethem()
//             });
//         }else {
//             console.log("myloginresonse "+"Missing Parameters");
//             $scope.badmissin = '23';
//             $scope.letshide = showAlertSrvc(5000);
//             $scope.testing = {
//                 "code" : "03",
//                 "message" : "Missing Parameters"};
//             $.LoadingOverlay("hide");
//             hidethem()
//         }
//
//     };
//
//     function acivateaccount(email,password) {
//
//         console.info('activate has been called '+ email+' '+password);
//
//         var activateurl = "http://localhost:8080/api/acivatelogin?email="+email+"&password="+password;
//
//         // Simple GET request
//         $http({
//             method: 'GET',
//             url: activateurl
//         }).then(function successCallback(response) {
//             var secure = response.data;
//             $rootScope.activated = secure;
//
//             console.log('loginobject ', secure);
//             $.LoadingOverlay("hide");
//             swal("hello");
//             hidethem();
//             $state.go("dashboard");
//         }, function errorCallback(response) {
//
//             var loginresponse = response.data;
//             var loginresponse1 = response;
//
//             console.log("myloginresonse "+ JSON.stringify(loginresponse));
//             console.log("myloginresonseheadres "+ JSON.stringify(loginresponse1.status));
//
//             if(loginresponse.code == "03"){
//                 $scope.bad = '03';
//                 $scope.testerror = "Missing Parameters";
//                 $scope.letshide = showAlertSrvc(5000);
//                 hidethem()
//             } else if(response.status == 500){
//                 $scope.statustest500 = '500';
//                 console.log('i have entered here ', response.status);
//                 $scope.test500 = "Invalid login credentials";
//                 $scope.letshide = showAlertSrvc(5000);
//                 $.LoadingOverlay("hide");
//                 hidethem()
//             }else if (response.status == 400){
//                 console.log('i have entered here ', response.status);
//                 $scope.test400 = "Invalid login credentials";
//                 $scope.letshide = showAlertSrvc(5000);
//                 $.LoadingOverlay("hide");
//                 hidethem()
//             }else if (response.status == 404){
//                 console.log('i have entered here ', response.status);
//                 $scope.test404 = "Invalid login credentials";
//                 $scope.letshide = showAlertSrvc(5000);
//                 $.LoadingOverlay("hide");
//                 hidethem()
//             }else {
//                 console.log('i have entered here ', response.status);
//                 $scope.testfinal = "Server Error, Please contact admin at info@blaqueyard.com";
//                 $scope.letshide = showAlertSrvc(5000);
//                 $.LoadingOverlay("hide");
//                 hidethem()
//             }
//
//             $scope.headerstatus = loginresponse1.status;
//             $scope.testing = "Incorect credentials, Please Supply Correct credentials or change password";
//             $scope.letshide = showAlertSrvc(5000);
//             $.LoadingOverlay("hide");
//             hidethem()
//         });
//
//     }
//     function hidethem(){
//         $timeout(function () {
//             console.log("interval has been initiated");
//             $scope.mydivs = true;
//         }, 3500);
//     }
//
//
// });
//
//
