(function () {
  "use strict";

  ddd.factory('DecisionService', function (Restangular) {

    var _decisions = Restangular.all("decisions");

    return {
      all: function(cb) {
        _decisions.getList().then(function(allDecisions) {
          allDecisions.forEach(function(tmpDecision) {
            tmpDecision.votingCloseDate = moment(tmpDecision.votingCloseDate);
          });
          cb(angular.copy(allDecisions));
        });
      },
      new: function(decision, cb) {
        var json = JSON.stringify(decision);
        _decisions.post(json).then(function() {
          cb();
        }, function(res) {
          console.error('storage failed', res);
          cb('could not store the decision, check client logs');
        });
      }
    };
  });


})();
