(function () {
  "use strict";

  ddd.controller('DecisionListCtrl', function ($scope, GlobalDataService) {
    $scope.user = { name: GlobalDataService.user.name, email: GlobalDataService.user.email, key: GlobalDataService.user.sessionKey };
  });

})();
