(function () {
  "use strict";

  ddd.factory('DecisionService', function () {
    var now = new Date();
    var opening = new Date().setHours(now.getHours() - 1);
    var closing = new Date().setHours(now.getHours() + 1);

    var decisions = [
      {
        id: 1,
        title: 'Mittagessen',
        description: 'testessen nr. 1',
        votingOpenDate: opening,
        votingEndDate: closing
      },
      {
        id: 2,
        title: 'Abendessen',
        description: 'das abendessen...',
        votingOpenDate: opening,
        votingEndDate: closing
      }
    ];

    return {
      all: function() {
        return decisions;
      }
    };
  });


})();
