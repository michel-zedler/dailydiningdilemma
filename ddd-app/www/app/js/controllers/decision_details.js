(function () {
  "use strict";

  ddd.controller('DecisionDetailsCtrl', function ($scope, $stateParams, DecisionService, OptionService) {
    $scope.decision = {};
    $scope.options = [];
    $scope.decisionId = $stateParams.decisionId;

    DecisionService.byId($scope.decisionId, function(decision) {
      $scope.decision = decision;
    });

    OptionService.byDecisionId($scope.decisionId, function(options) {
      $scope.options = options;
    });

  });


})();
