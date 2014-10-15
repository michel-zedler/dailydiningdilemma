(function () {
  "use strict";

  ddd.controller('DecisionCreateCtrl', function ($scope, $location, $ionicBackdrop, DecisionService) {
    $scope.decision = {};

    var buildEndDateWithTime = function(decision) {
      if (!decision.votingCloseDate || !decision.votingCloseTime) {
        return undefined;
      }
      return moment(decision.votingCloseDate).add(moment.duration(decision.votingCloseTime));
    };

    $scope.createDecision = function () {
      if (!$scope.isEndDateValid()) {
        return;
      }
      $ionicBackdrop.retain();
      var decision = angular.copy($scope.decision);

      var end = buildEndDateWithTime(decision);
      decision.votingCloseDate = end.format();

      delete decision.votingCloseTime;

      DecisionService.store(decision, function(decisionId, err) {
        $ionicBackdrop.release();
        if (err) {
          alert('failed to create decision: ' + err);
        } else {
          $location.path('/app/options-create/' + decisionId);
        }
      });
    };

    $scope.isEndDateValid = function() {
      var endDate = buildEndDateWithTime($scope.decision);
      if (endDate) {
        var referenceDate = moment().add(2, 'minutes');
        return referenceDate.isBefore(endDate);
      }
      return true;
    };

  });


})();
