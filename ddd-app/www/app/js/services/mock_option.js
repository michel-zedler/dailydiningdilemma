(function () {
  "use strict";

  ddd.factory('OptionService', function(VotingService) {

    return {
      store: function(options, votingId, cb) {
        VotingService.byId(votingId, function(voting) {
          if (!voting.options) {
            voting.options = [];
          }
          voting.options.push.apply(voting.options, options);
          cb();
        });
      },
      byVotingId: function(votingId, cb) {
        VotingService.byId(votingId, function(voting) {
          cb(voting.options);
        });
      },
      update: function(options, votingId, cb) {
        VotingService.byId(votingId, function(voting) {
          voting.options = options;
          cb();
        })
      }
    }
  });


})();
