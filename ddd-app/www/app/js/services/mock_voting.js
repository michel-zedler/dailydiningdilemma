(function () {
  "use strict";

  ddd.factory('VotingService', function () {
    var closing = moment();
    closing.add(1, 'minute');

    var votings = [
      {
        id: "1",
        title: 'Mittagessen',
        description: 'testessen nr. 1',
        votingCloseDate: closing,
        isClosed: false,
        options: [
          {
            id: 1,
            name: 'location 1'
          },
          {
            id: 2,
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
            id: 21,
            name: 'location 1'
          },
          {
            id: 22,
            name: 'location 2'
          }
        ]
      }
    ];

    var findVotingById = function(id) {
      var currentVoting = {};
      votings.forEach(function(voting) {
        if (voting.id === id) {
          currentVoting = voting;
        }
      });
      return currentVoting;
    }

    return {
      all: function(cb) {
        cb(angular.copy(votings).reverse());
      },
      store: function(voting, cb) {
        var newId = votings.length + 1;
        voting.id = newId.toString();
        voting.isClosed = false;
        voting.votingCloseDate = moment(voting.votingCloseDate);
        votings.push(voting);
        cb(voting.id);
      },
      byId: function(id, cb) {
        cb(findVotingById(id));
      },
      update: function(voting, cb) {
        angular.extend(findVotingById(voting.id), voting);
        cb();
      }
    };
  });


})();
