(function () {
  "use strict";

  ddd.controller('OptionsCreateCtrl', function ($scope, $location, $stateParams, OptionService) {
    $scope.options = [];
    $scope.votingId = $stateParams.votingId;
    $scope.editMode = false;

    $scope.addOption = function() {
      $scope.options.unshift({ name: ''});
    };

    $scope.finish = function() {
      OptionService.store($scope.options, $scope.votingId, function() {
        $location.path('/app/votings');
      });
    };

  });


})();
