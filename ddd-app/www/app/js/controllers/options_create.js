(function () {
  "use strict";

  ddd.controller('OptionsCreateCtrl', function ($scope, $location, $stateParams, DecisionService) {
    $scope.options = [];
    $scope.decisionId = $stateParams.decisionId;

    $scope.addOption = function() {
      $scope.options.unshift({ title: ''});
    };

    $scope.finish = function() {
      DecisionService.byId($scope.decisionId, function(decision) {
        decision.options = angular.copy($scope.options);
        DecisionService.update(decision, function(err) {
          if (err) {
            alert('failed to add options to decisions: ' + err);
          } else {
            $location.path('/app/decisions');
          }
        });
      });
    };

  });


})();
