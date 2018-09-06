// /**
//  * Created by admin on 9/4/18.
//  * Fredrick Oluoch
//  * http://www.blaqueyard.com
//  * 0720106420 | 0722508906
//  * email: menty44@gmail.com
//  */
// // var routerApp =  angular.module('routerApp', ['ui.router']);
//
// var routerApp = angular.module('alertDemo', ['oitozero.ngSweetAlert']);
//
// routerApp.controller('demoCtrl',['SweetAlert', function(SweetAlert){
//         //var vm = this;
//         $scope.alert = function alert(){
//             console.log('Im a fancy Alert');
//             SweetAlert.swal("I'm a fancy Alert"); //simple alert
//         };
//         $scope.confirm = function confirm(){
//             console.log('Are you sure?');
//             SweetAlert.swal({
//                     title: "Are you sure?", //Bold text
//                     text: "Your will not be able to recover this imaginary file!", //light text
//                     type: "warning", //type -- adds appropiriate icon
//                     showCancelButton: true, // displays cancel btton
//                     confirmButtonColor: "#DD6B55",
//                     confirmButtonText: "Yes, delete it!",
//                     closeOnConfirm: false, //do not close popup after click on confirm, usefull when you want to display a subsequent popup
//                     closeOnCancel: false
//                 },
//                 function(isConfirm){ //Function that triggers on user action.
//                     if(isConfirm){
//                         SweetAlert.swal("Deleted!");
//                     } else {
//                         SweetAlert.swal("Your file is safe!");
//                     }
//                 });
//         }
//     }]);
