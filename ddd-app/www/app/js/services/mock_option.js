(function () {
  "use strict";

  ddd.factory('OptionService', function(DecisionService) {

    return {
      store: function(options, decisionId, cb) {
        DecisionService.byId(decisionId, function(decision) {
          if (!decision.options) {
            decision.options = [];
          }
          decision.options.push.apply(decision.options, options);
          cb();
        });
      },
      byDecisionId: function(decisionId, cb) {
        DecisionService.byId(decisionId, function(decision) {
          cb(decision.options);
        });
      }
    }
  });


})();
