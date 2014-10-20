(function () {
  "use strict";

  ddd.factory('DecisionHelperService', function () {

    var formatTimeDifference = function(closeDate) {
      var now = moment();
      return (closeDate.isAfter(now)? 'closing ' : 'closed ') + closeDate.from(now);
    };

    return {
      updateCountdownLabel: function (decision) {
        decision.countdownLabel = formatTimeDifference(decision.votingCloseDate);
      }
    };
  });

})();
