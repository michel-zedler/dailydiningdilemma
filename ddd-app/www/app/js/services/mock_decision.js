(function () {
  "use strict";

  ddd.factory('DecisionService', function () {
    var closing = moment();
    closing.add(1, 'hours');

    var decisions = [
      {
        id: 1,
        title: 'Mittagessen',
        description: 'testessen nr. 1',
        votingCloseDate: closing,
        isClosed: false
      },
      {
        id: 2,
        title: 'Abendessen',
        description: 'das abendessen...',
        votingCloseDate: closing,
        isClosed: false
      }
    ];

    return {
      all: function(cb) {
        cb(angular.copy(decisions));
      },
      new: function(decision, cb) {
        decision.id = decisions.length + 1;
        decision.isClosed = false;
        decision.votingCloseDate = moment(decision.votingCloseDate);
        decisions.push(decision);
        cb(decision.id);
      }
    };
  });


})();
