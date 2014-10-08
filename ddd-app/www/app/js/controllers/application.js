(function () {
  "use strict";

  ddd.controller('ApplicationCtrl', function ($scope, $state, AuthService, GlobalDataService) {

    $scope.logout = function () {
      AuthService.logout();
      GlobalDataService.user.sessionKey = undefined;
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

  });


})();
