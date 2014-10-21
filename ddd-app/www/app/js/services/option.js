(function () {
  "use strict";

  ddd.factory('OptionService', function(Restangular) {

    var _options = Restangular.all('options');

    return {
      store: function(options, votingId, cb) {
        var json = angular.toJson({
          // TODO: should be votingId
          decisionId: votingId,
          options: options
        });
        _options.post(json).then(function() {
          cb();
        }, function(res) {
          console.error('storage failed', res);
          cb(undefined, 'could not store the voting, check client logs');
        });
      },
      byVotingId: function(votingId, cb) {
        // TODO: parameter should be called votingId
        _options.getList({ decisionId: votingId }).then(function(options) {
          cb(options);
        });
      }
    }
  });


})();
