(function () {
  "use strict";

  ddd.factory('VoteService', function() {

    return {
      store: function(votes, votingId, cb) {
        cb();
      }
    }
  });


})();
