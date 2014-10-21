(function () {
  "use strict";

  ddd.factory('VoteService', function(Restangular) {

    var _votes = Restangular.all('votes');

    return {
      store: function(votes, votingId, cb) {
        var data = {
          // TODO: should be called votingId
          decisionId: votingId,
          votes: []
        };

        votes.forEach(function(vote) {
          data.votes.push({
            optionId: vote.optionId,
            value: vote.value
          });
        });

        _votes.post(angular.toJson(data)).then(function() {
          cb();
        }, function(res) {
          console.error('storage failed', res);
          cb(undefined, 'could not store the voting, check client logs');
        });
      }
    }
  });


})();
