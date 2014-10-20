(function () {
  "use strict";

  ddd.controller('DecisionDetailsCtrl', function ($scope, $location, $stateParams, DecisionService, OptionService) {
    $scope.decision = {};
    $scope.options = [];
    $scope.decisionId = $stateParams.decisionId;

    DecisionService.byId($scope.decisionId, function(decision) {
      $scope.decision = decision;
    });

    OptionService.byDecisionId($scope.decisionId, function(options) {
      $scope.options = options;
    });

    $scope.voteFor = function(decisionId) {
      $location.path('/app/vote/' + decisionId);
    };

  });

})();
