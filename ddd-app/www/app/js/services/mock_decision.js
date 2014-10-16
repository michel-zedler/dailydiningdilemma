(function () {
  "use strict";

  ddd.factory('DecisionService', function () {
    var closing = moment();
    closing.add(1, 'hours');

    var decisions = [
      {
        id: "1",
        title: 'Mittagessen',
        description: 'testessen nr. 1',
        votingCloseDate: closing,
        isClosed: false,
        options: [
          {
            name: 'location 1'
          },
          {
            name: 'location 2'
          }
        ]
      },
      {
        id: "2",
        title: 'Abendessen',
        description: 'das abendessen...',
        votingCloseDate: closing,
        isClosed: false,
        options: [
          {
            name: 'location 1'
          },
          {
            name: 'location 2'
          }
        ]
      }
    ];

    var findDecisionById = function(id) {
      var currentDecision = {};
      decisions.forEach(function(decision) {
        if (decision.id === id) {
          currentDecision = decision;
        }
      });
      return currentDecision;
    }

    return {
      all: function(cb) {
        cb(angular.copy(decisions).reverse());
      },
      store: function(decision, cb) {
        var newId = decisions.length + 1;
        decision.id = newId.toString();
        decision.isClosed = false;
        decision.votingCloseDate = moment(decision.votingCloseDate);
        decisions.push(decision);
        cb(decision.id);
      },
      byId: function(id, cb) {
        cb(findDecisionById(id));
      },
      update: function(decision, cb) {
        angular.extend(findDecisionById(decision.id), decision);
        cb();
      }
    };
  });


})();
