(function () {
  "use strict";

  ddd.controller('DecisionCreateCtrl', function ($scope, $location, $ionicBackdrop, DecisionService) {
    $scope.decision = {};

    $scope.createDecision = function () {
      $ionicBackdrop.retain();
      var decision = angular.copy($scope.decision);

      var end = moment(decision.votingCloseDate).add(moment.duration(decision.votingCloseTime));
      decision.votingCloseDate = end.format();

      delete decision.votingCloseTime;

      DecisionService.new(decision, function(err) {
        $ionicBackdrop.release();
        if (err) {
          alert('failed to create decision: ' + err);
        } else {
          $location.path('/app/options-create')
        }
      });
    };

  });


})();
