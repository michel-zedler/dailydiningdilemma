(function () {
  "use strict";

  ddd.factory('VoteService', function() {

    return {
      store: function(votes, decisionId, cb) {
        cb();
      }
    }
  });


})();
