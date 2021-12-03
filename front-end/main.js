var App = angular.module('myApp', []);
const URL = 'http://localhost:8080/AngularJsBackendEx_war'
App.controller("myController", function ($scope, $http, $log) {

    getPersons();

    function getPersons() {
        var success = function (response) {
            $scope.persons = response.data;
            $log.info(response);
            console.log("getPersons: ", response.data)
        };

        var error = function (reason) {
            $scope.error = reason.data;
            $log.info(reason);
        };

        $http({
            method: 'GET',
            url: URL + '/getAllPersons'
        }).then(success, error);
    }

    $scope.buttonText = "Add";

    $scope.submit = function () {
        if ($scope.buttonText == 'Add') {
            $http({
                method: 'POST',
                url: URL + '/addPerson',
                headers: { 'Content-Type': 'application/json' },
                data: $scope.Person
            });
            $scope.message = "Record added successfully, press 'RESET' button to see the changes";
        } else if ($scope.buttonText == 'Update') {
            $http({
                method: 'POST',
                url: URL + '/editPerson',
                headers: { 'Content-Type': 'application/json' },
                data: $scope.Person
            });
            $scope.message = "Record updated successfully, press 'RESET' button to see the changes";
        } else if ($scope.buttonText == 'Delete') {
            $http({
                method: 'POST',
                url: URL + '/deletePerson',
                headers: { 'Content-Type': 'application/json' },
                data: $scope.Person
            });
            $scope.message = "Record deleted successfully, press 'RESET' button to see the changes";
        }
        ;

    };

    $scope.reset = function () {
        window.location.reload(); //reset Form
    };

    $scope.updatePerson = function (person) {
        $scope.Person = person;
        $scope.buttonText = "Update";
    };

    $scope.deletePerson = function (person) {
        $scope.Person = person;
        $scope.buttonText = "Delete";
    };
});