(function () {
  "use strict";

  ddd.controller('DecisionListCtrl', function ($scope, $interval, $location, $ionicBackdrop, GlobalDataService, DecisionService, DecisionHelperService) {
    $scope.user = { name: GlobalDataService.user.name, email: GlobalDataService.user.email, key: GlobalDataService.user.sessionKey };
    $scope.decisions = [];

    $ionicBackdrop.retain();
    DecisionService.all(function (decisions) {
      decisions.forEach(function (decision) {
        console.log(DecisionService);
        DecisionHelperService.updateCountdownLabel(decision);
      });
      $ionicBackdrop.release();
      $scope.decisions = decisions;
    });

    var updateCountdownLabelEverySecond = $interval(function() {
      $scope.decisions.forEach(function (decision) {
        DecisionHelperService.updateCountdownLabel(decision)
      });
    }, 1000);

    $scope.detailsFor = function(decisionId) {
      $location.path('/app/decision-details/' + decisionId);
    };
  });

})();
