(function () {
  "use strict";

  ddd.controller('LoginCtrl', function ($scope, $location, AuthService, $ionicBackdrop) {

    var handleLoginResult = function (err) {
      $ionicBackdrop.release();

      if (err) {
        alert('Login failed: ' + err);
      } else {
        $location.path('/app/decisions');
      }
    };

    $scope.facebookLogin = function () {
      $ionicBackdrop.retain();
      AuthService.loginFacebook(handleLoginResult);
    };

    $scope.googleLogin = function () {
      $ionicBackdrop.retain();
      AuthService.loginGoogle(handleLoginResult);
    }
  });


})();
