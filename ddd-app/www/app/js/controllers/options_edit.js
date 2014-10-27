(function () {
  "use strict";

  ddd.controller('OptionsEditCtrl', function ($scope, $location, $ionicBackdrop, $stateParams, OptionService) {
    $scope.options = [];
    $scope.votingId = $stateParams.votingId;
    $scope.editMode = true;

    $ionicBackdrop.retain();

    OptionService.byVotingId($scope.votingId, function(options) {
      $scope.options = options;
      $ionicBackdrop.release();
    });

    $scope.addOption = function() {
      $scope.options.unshift({ name: ''});
    };

    $scope.finish = function() {
      OptionService.update($scope.options, $scope.votingId, function() {
        $location.path('/app/vote/' + $scope.votingId);
      });
    };

  });


})();
