(function () {
  "use strict";

  ddd.controller('VotingListCtrl', function ($scope, $interval, $location, $ionicBackdrop, GlobalDataService, VotingService, VotingHelperService) {
    $scope.user = { name: GlobalDataService.user.name, email: GlobalDataService.user.email, key: GlobalDataService.user.sessionKey };
    $scope.votings = [];

    $ionicBackdrop.retain();
    VotingService.all(function (votings) {
      votings.forEach(function (voting) {
        VotingHelperService.updateCountdownLabel(voting);
      });
      $ionicBackdrop.release();
      $scope.votings = votings;
    });

    var updateCountdownLabelEverySecond = $interval(function() {
      $scope.votings.forEach(function (voting) {
        VotingHelperService.updateCountdownLabel(voting)
      });
    }, 1000);

    $scope.detailsFor = function(votingId) {
      $interval.cancel(updateCountdownLabelEverySecond);
      $location.path('/app/voting-details/' + votingId);
    };
  });

})();
