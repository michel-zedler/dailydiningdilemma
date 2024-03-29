(function () {
  "use strict";

  ddd.factory('VoteService', function(Restangular) {

    var _votes = Restangular.all('votes');

    return {
      store: function(votes, votingId, cb) {
        var data = {
          votingId: votingId,
          votes: []
        };

        votes.forEach(function(vote) {
          data.votes.push({
            optionId: vote.option.id,
            value: vote.points
          });
        });

        _votes.post(angular.toJson(data)).then(function() {
          cb();
        }, function(res) {
          console.error('storage failed', res);
          cb(undefined, 'could not store the voting, check client logs');
        });
      },
      latest: function(votingId, cb) {
        _votes.customGET("", {"votingId": votingId}).then(function (latestVote) {
          cb(latestVote);
        });
      },
      revoke: function(votingId, cb) {
        _votes.customDELETE("", {"votingId": votingId});
        cb();
      }
    }
  });


})();
