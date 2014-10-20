(function () {
  "use strict";

  ddd.controller('DecisionDetailsCtrl', function ($scope, $interval, $location, $stateParams, DecisionService, OptionService, DecisionHelperService) {
    $scope.decision = {};
    $scope.options = [];
    $scope.decisionId = $stateParams.decisionId;

    DecisionService.byId($scope.decisionId, function(decision) {
      DecisionHelperService.updateCountdownLabel(decision);
      $scope.decision = decision;
    });

    OptionService.byDecisionId($scope.decisionId, function(options) {
      $scope.options = options;
    });

    $scope.voteFor = function(decisionId) {
      $location.path('/app/vote/' + decisionId);
    };

    var updateCountdownLabelEverySecond = $interval(function() {
      DecisionHelperService.updateCountdownLabel($scope.decision);
    }, 1000);

  });

})();
