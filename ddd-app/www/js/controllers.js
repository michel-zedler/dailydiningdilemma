angular.module('ddd.controllers', ['ddd.services'])

  .controller('AppCtrl', function ($scope, $state, authService, globalData) {

    $scope.logout = function () {
      authService.logout();
      globalData.user.sessionKey = undefined;
      $state.go('app.login');
    };

    $scope.revokePermissions = function () {
      // TODO: implement this!
      /*OpenFB.revokePermissions().then(
        function () {
          $state.go('app.login');
        },
        function () {
          alert('Revoke permissions failed');
        });
        */
    };

  })

  .controller('LoginCtrl', function ($scope, $location, authService, globalData) {

    function handleLoginResult(result) {
      if (result.err) {
        alert('Login failed: ' + result.err);
      } else {
        globalData.user.id = result.user.id;
        globalData.user.name = result.user.displayName;
        if (result.user.email) {
          globalData.user.email = result.user.email;
        } else if (result.user.thirdPartyUserData.email) {
          globalData.user.email = result.user.thirdPartyUserData.email;
        }
        globalData.user.sessionKey = 'key';
        $location.path('/app/welcome');
      }
    }

    $scope.facebookLogin = function() {
      authService.loginFacebook(handleLoginResult);
    };

    $scope.googleLogin = function () {
      authService.loginGoogle(handleLoginResult);
    };

  })

  .controller('WelcomeCtrl', function ($scope, globalData) {

    $scope.user = { name: globalData.user.name, email: globalData.user.email };

  });