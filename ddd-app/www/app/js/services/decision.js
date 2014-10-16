(function () {
  "use strict";

  ddd.factory('DecisionService', function (Restangular) {

    var _decisions = Restangular.all("decisions");

    var findDecisionById = function(id, cb) {
      _decisions.one(id).get().then(function (decision) {
        cb(decision);
      });
    }

    return {
      all: function(cb) {
        _decisions.getList().then(function(allDecisions) {
          allDecisions.forEach(function(tmpDecision) {
            tmpDecision.votingCloseDate = moment(tmpDecision.votingCloseDate);
          });
          cb(angular.copy(allDecisions));
        });
      },
      store: function(decision, cb) {
        var json = angular.toJson(decision);
        _decisions.post(json).then(function(decisionData) {
          cb(decisionData.id);
        }, function(res) {
          console.error('storage failed', res);
          cb(undefined, 'could not store the decision, check client logs');
        });
      },
      byId: function(id, cb) {
        findDecisionById(id, cb);
      },
      update: function(decision, cb) {
        findDecisionById(decision.id, function(_decision) {
          angular.extend(_decision, decision);
          _decision.put().then(function() {
            cb();
          });
        });
      }
    };
  });


})();
