angular.module('ddd.controllers', [])

    .controller('AppCtrl', function ($scope, $state, OpenFB) {

        $scope.logout = function () {
            OpenFB.logout();
            $state.go('app.login');
        };

        $scope.revokePermissions = function () {
            OpenFB.revokePermissions().then(
                function () {
                    $state.go('app.login');
                },
                function () {
                    alert('Revoke permissions failed');
                });
        };

    })

    .controller('LoginCtrl', function ($scope, $location, OpenFB) {

        $scope.facebookLogin = function () {

            OpenFB.login('email').then(
                function () {
                    $location.path('/app/welcome');
                },
                function () {
                    alert('Facebook login failed');
                });
        };

    })

    .controller('WelcomeCtrl', function ($scope, OpenFB) {

        OpenFB.get('/me').success(function (user) {
            $scope.user = user;
          //user.name, user.email, user.first_name
        });

    });