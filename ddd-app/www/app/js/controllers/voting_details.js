(function () {
  "use strict";

  ddd.controller('VotingDetailsCtrl', function ($scope, $interval, $location, $stateParams, VotingService, OptionService, VotingHelperService) {
    $scope.voting = {};
    $scope.options = [];
    $scope.votingId = $stateParams.votingId;

    VotingService.byId($scope.votingId, function(voting) {
      VotingHelperService.updateCountdownLabel(voting);
      $scope.voting = voting;
      updateCountdownLabelEverySecond();
    });

    OptionService.byVotingId($scope.votingId, function(options) {
      $scope.options = options;
    });

    $scope.voteFor = function() {
      $interval.cancel(updateCountdownLabelEverySecond);
      $location.path('/app/vote/' + $scope.votingId);
    };

    var updateCountdownLabelEverySecond = function() {
      $interval(function() {
        VotingHelperService.updateCountdownLabel($scope.voting);
      }, 1000);
    };

  });

})();
