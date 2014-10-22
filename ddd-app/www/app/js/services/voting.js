(function () {
  "use strict";

  ddd.factory('VotingService', function (Restangular) {

    var _votings = Restangular.all("votings");

    var findVotingById = function(id, cb) {
      _votings.one(id).get().then(function (voting) {
        voting.votingCloseDate = moment(voting.votingCloseDate);
        cb(voting);
      });
    };

    return {
      all: function(cb) {
        _votings.getList().then(function(allvotings) {
          allvotings.forEach(function(tmpvoting) {
            tmpvoting.votingCloseDate = moment(tmpvoting.votingCloseDate);
          });
          cb(angular.copy(allvotings));
        });
      },
      store: function(voting, cb) {
        var json = angular.toJson(voting);
        _votings.post(json).then(function(votingData) {
          cb(votingData.id);
        }, function(res) {
          console.error('storage failed', res);
          cb(undefined, 'could not store the voting, check client logs');
        });
      },
      byId: function(id, cb) {
        findVotingById(id, cb);
      },
      update: function(voting, cb) {
        findVotingById(voting.id, function(_voting) {
          angular.extend(_voting, voting);
          _voting.put().then(function() {
            cb();
          });
        });
      }
    };
  });

})();
