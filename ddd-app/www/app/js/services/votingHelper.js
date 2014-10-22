(function () {
  "use strict";

  ddd.factory('VotingHelperService', function () {

    var formatTimeDifference = function(closeDate) {
      var now = moment();
      return (closeDate.isAfter(now)? 'closing ' : 'closed ') + closeDate.from(now);
    };

    return {
      updateCountdownLabel: function (voting) {
        voting.countdownLabel = formatTimeDifference(voting.votingCloseDate);
      }
    };
  });

})();
