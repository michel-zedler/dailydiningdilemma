(function () {
  "use strict";

  ddd.factory('OptionService', function(Restangular) {

    var _options = Restangular.all('options');

    return {
      store: function(options, decisionId, cb) {
        var _options = {
          decisionId: decisionId,
          options: options
        };
        var json = JSON.stringify(_options);
        _options.post(json).then(function() {
          cb();
        }, function(res) {
          console.error('storage failed', res);
          cb(undefined, 'could not store the decision, check client logs');
        });
      },
      byDecisionId: function(decisionId, cb) {
        _options.getList({ query: decisionId }).then(function(options) {
          cb(options);
        });
      }
    }
  });


})();
