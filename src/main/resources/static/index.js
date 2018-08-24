/**
 * Created by admin on 8/5/18.
 * Fredrick Oluoch
 * http://www.blaqueyard.com
 * 0720106420 | 0722508906
 * email: menty44@gmail.com
 */

/**
 * Created by admin on 6/3/18.
 * Fredrick Oluoch
 * http://www.blaqueyard.com
 * 0720106420 | 0722508906
 * email: menty44@gmail.com
 */
// mainApp.controller('testController', function($scope, $http) {
//     $scope.message = "This page will be reverse";
//     //$scope.name = "";
//     // $scope.reverseme = function reverseme() {
//     //     var rv = $scope.name;
//     //     console.log(rv)
//     //     $http({
//     //         method : "GET",
//     //         url : "http://localhost:8080/numbers"
//     //     }).then(function mySuccess(response) {
//     //         var test = response.data;
//     //         console.log(test);
//     //         $scope.succ = test;
//     //     }, function myError(response) {
//     //         var test = response.data;
//     //         console.log(test);
//     //         $scope.err = response.response;
//     //     });
//     // }
// });

// var mainApp = angular.module('mainApp', ['ng-fusioncharts']);
// angular.module('mainApp').controller('operatorController', ['$rootScope', '$scope', function ($scope, $http) {

app.controller('mainController', function($scope, $http) {


    $scope.reverseme = function reverseme() {
        var rv = $scope.name;
        console.log(rv)
        $http({
            method : "GET",
            url : "http://localhost:8080/numbers"
        }).then(function mySuccess(response) {
            var test = response.data;
            console.log(test);
            $scope.succ = test;
        }, function myError(response) {
            var test = response.data;
            console.log(test);
            $scope.err = response.response;
        });
    }

    $scope.duration = function duration() {

        var rv = $scope.name;
        console.log(rv)
        $http({
            method : "GET",
            url : "http://localhost:8080/numbers"
        }).then(function mySuccess(response) {
            var test = response.data;

            console.log('operator',test);

            $scope.myDataSource = {
                chart: {
                    caption: "CALLS ACCORDING TO TELCOS",
                    // subcaption: "Last Year",
                    startingangle: "120",
                    showlabels: "0",
                    showlegend: "1",
                    enablemultislicing: "0",
                    slicingdistance: "15",
                    showpercentvalues: "1",
                    showpercentintooltip: "0",
                    plottooltext: "Network : $label Total calls : $datavalue",
                    theme: "fint"
                },
                data: [
                    {
                        label: "YU",
                        value: "1250400"
                    },
                    {
                        label: "AIRTEL",
                        value: "1463300"
                    },
                    {
                        label: "SAFARICOM",
                        value: "1050700"
                    }
                ]
            }

            Array.prototype.sum = function (prop) {
                var total = 0
                for ( var i = 0, _len = this.length; i < _len; i++ ) {
                    total += this[i][prop]
                }
                return total
            }

            // function sum(items, prop){
            //     return items.reduce( function(a, b){
            //         return a + b[prop];
            //     }, 0);
            // };

            //traveler.sum("Amount");
            var t = test.sum('duration');

            localStorage.setItem('totalduration', JSON.stringify(t));

            console.log('total duration',t );

            var text = "";

            var i;

            for (i = 0; i < test.length; i++) {
                text += test[i] ;

                console.log('the text', JSON.stringify(text));
            }

            angular.forEach(response, function(value, key) {
                //this.push(key + ': ' + value);
                if(value.name == 'Yu'){
                    console.log('values' + JSON.parse(value.duration));
                    console.log('valuesdata' + JSON.parse(response));

                }
            });



            $scope.succ = test;
        }, function myError(response) {
            var test = response.data;
            console.log(test);
            $scope.err = response.response;
        });
    }

    $scope.check = function check(num) {

        console.log('Passed Number', num);
    }
});

