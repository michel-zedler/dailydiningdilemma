(function () {
  "use strict";

  ddd.controller('VotingCreateCtrl', function ($scope, $location, $ionicBackdrop, VotingService) {
    $scope.voting = {};

    var buildEndDateWithTime = function(voting) {
      if (!voting.votingCloseDate || !voting.votingCloseTime) {
        return undefined;
      }
      return moment(voting.votingCloseDate).add(moment.duration(voting.votingCloseTime));
    };

    $scope.createVoting = function () {
      if (!$scope.isEndDateValid()) {
        return;
      }
      $ionicBackdrop.retain();
      var voting = angular.copy($scope.voting);

      var end = buildEndDateWithTime(voting);
      voting.votingCloseDate = end.format();

      delete voting.votingCloseTime;

      VotingService.store(voting, function(votingId, err) {
        $ionicBackdrop.release();
        if (err) {
          alert('failed to create voting: ' + err);
        } else {
          $location.path('/app/options-create/' + votingId);
        }
      });
    };

    $scope.isEndDateValid = function() {
      var endDate = buildEndDateWithTime($scope.voting);
      if (endDate) {
        var referenceDate = moment().add(2, 'minutes');
        return referenceDate.isBefore(endDate);
      }
      return true;
    };

  });


})();
