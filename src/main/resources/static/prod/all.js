var mainApp;(mainApp=angular.module("mainApp",[])).controller("fredCtrl",function(a,e){a.categories=function(){e({method:"GET",url:"http://localhost:8080/api/category"}).then(function(e){console.log("response",e),console.log("response",e.data),a.navbar=e.data},function(e){console.log("error response",e)})}}),(mainApp=angular.module("mainApp",[])).controller("studentController",function(a){a.student={firstName:"Mahesh",lastName:"Parashar",fees:500,subjects:[{name:"Physics",marks:70},{name:"Chemistry",marks:80},{name:"Math",marks:65}],fullName:function(){var e;return(e=a.student).firstName+" "+e.lastName}}}),(mainApp=angular.module("mainApp",[])).config("mainApp",["$routeProvider",function(e){e.when("/",{templateUrl:"index.html"}).when("/leak",{templateUrl:"leak.html"}).when("/upload",{templateUrl:"upload.html"}).when("/photos",{templateUrl:"photos.html"})}]);