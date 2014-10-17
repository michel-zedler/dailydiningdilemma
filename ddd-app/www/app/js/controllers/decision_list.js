(function () {
  "use strict";

  ddd.controller('DecisionListCtrl', function ($scope, $location, $ionicBackdrop, GlobalDataService, DecisionService) {
    $scope.user = { name: GlobalDataService.user.name, email: GlobalDataService.user.email, key: GlobalDataService.user.sessionKey };
    $scope.decisions = [];

    var formatTimeDifference = function(diff, closed) {
      var text = 'closes ';
      if (closed) {
        text = 'closed ';
      }
      var duration = moment.duration(diff);
      if (duration.years() > 0) {
        text += moment.duration(duration.years(), 'years').humanize(true);
      } else if (duration.months() > 0) {
        text += moment.duration(duration.months(), 'months').humanize(true);
      } else if (duration.days() > 0) {
        text += moment.duration(duration.days(), 'days').humanize(true);
      } else if (duration.hours() > 0) {
        text += moment.duration(duration.hours(), 'hours').humanize(true);
      } else if (duration.minutes() > 0) {
        text += moment.duration(duration.minutes(), 'minutes').humanize(true);
      } else {
        text += moment.duration(duration.seconds(), 'seconds').humanize(true);
      }
      return text;
    };

    $ionicBackdrop.retain();
    DecisionService.all(function (decisions) {
      decisions.forEach(function (decision) {
        var diff = decision.votingCloseDate.diff(moment());
        decision.dateText = formatTimeDifference(diff, decision.isClosed);
      });
      $ionicBackdrop.release();
      $scope.decisions = decisions;
    });

    $scope.detailsFor = function(decisionId) {
      $location.path('/app/decision-details/' + decisionId);
    };
  });

})();
