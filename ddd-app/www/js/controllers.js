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

  .controller('LoginCtrl', function ($scope, $location, authService, globalData, $http, $ionicBackdrop) {

    function handleLoginResult(result, provider) {
      if (result.err) {
        alert('Login failed: ' + result.err);
      } else {

        $ionicBackdrop.retain();

        var data = 'service=' + provider + '&token=' + result.user.accessToken;

        $http({method: 'POST', url: 'https://tools.eckert-partner.it/dailydining-int/api/rest/login',
               headers: {'Content-Type': 'application/x-www-form-urlencoded'}, data: data}).
          success(function (data, status, header) {
            $ionicBackdrop.release();
            if (data.errorMessage) {
              alert(data.errorMessage);
            } else {
              globalData.user.sessionKey = data.apiKey;

              globalData.user.id = result.user.id;
              globalData.user.name = result.user.displayName;
              if (result.user.email) {
                globalData.user.email = result.user.email;
              } else if (result.user.thirdPartyUserData.email) {
                globalData.user.email = result.user.thirdPartyUserData.email;
              }
              $location.path('/app/welcome');
            }
          });
      }
    }

    $scope.facebookLogin = function () {
      $ionicBackdrop.retain();
      authService.loginFacebook(handleLoginResult);
      $ionicBackdrop.release();
    };

    $scope.googleLogin = function () {
      authService.loginGoogle(handleLoginResult);
    };

  })

  .controller('WelcomeCtrl', function ($scope, globalData) {

    $scope.user = { name: globalData.user.name, email: globalData.user.email, key: globalData.user.sessionKey };

  });