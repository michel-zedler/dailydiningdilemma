(function () {
  "use strict";

  ddd.factory('VoteService', function(VotingService) {

    return {
      store: function(votes, votingId, cb) {
        VotingService.byId(votingId, function(voting) {
          voting.numberOfParticipants++;
          cb();
        });
      },
      latest: function(votingId, cb) {
        cb({votes: []});
      }
    }
  });


})();
