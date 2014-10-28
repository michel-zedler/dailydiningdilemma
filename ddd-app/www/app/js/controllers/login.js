(function () {
  "use strict";

  ddd.controller('LoginCtrl', function ($scope, $location, AuthService, $ionicBackdrop) {

    var handleLoginResult = function (err) {
      $ionicBackdrop.release();
      if (err) {
        alert('Login failed: ' + err);
      } else {
        $location.path('/app/votings');
        //note that we handle server side expiry of apikey (reauthenticated or not) in Restangular ErrorInterceptor for error code 403
      }
    };

    var login = function(provider) {
      $ionicBackdrop.retain();
      AuthService.tryReAuthentication(function(success) {
        if (success) {          
          handleLoginResult();
        } else {
          AuthService.login(provider, handleLoginResult);
        }
      });
    }

    $scope.facebookLogin = function () {
      login('facebook');
    };

    $scope.googleLogin = function () {
      login('google');
    }
  });


})();
