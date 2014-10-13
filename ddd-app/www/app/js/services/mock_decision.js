(function () {
  "use strict";

  ddd.factory('DecisionService', function () {
    var opening = new Date();
    opening.setHours(opening.getHours() - 1);
    var closing = new Date();
    closing.setHours(closing.getHours() + 1);

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
