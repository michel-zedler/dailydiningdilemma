(function () {
  "use strict";

  ddd.factory('VotingHelperService', function () {

    var COLORS = [ '#1f77b4', '#aec7e8', '#ff7f0e', '#ffbb78', '#2ca02c', '#98df8a', '#d62728'];

    var formatTimeDifference = function(closeDate) {
      var now = moment();
      return (closeDate.isAfter(now)? 'closing ' : 'closed ') + closeDate.from(now);
    };

    return {
      updateCountdownLabel: function (voting) {
        voting.countdownLabel = formatTimeDifference(voting.votingCloseDate);
      },
      optionColor: function(index) {
        return COLORS[index];
      }
    };
  });

})();
