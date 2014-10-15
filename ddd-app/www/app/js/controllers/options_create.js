(function () {
  "use strict";

  ddd.controller('OptionsCreateCtrl', function ($scope, $location, $stateParams, OptionService) {
    $scope.options = [];
    $scope.decisionId = $stateParams.decisionId;

    $scope.addOption = function() {
      $scope.options.unshift({ title: ''});
    };

    $scope.finish = function() {
      OptionService.store($scope.options, $scope.decisionId, function() {
        $location.path('/app/decisions');
      });
    };

  });


})();
