(function () {
  "use strict";

  ddd.factory('DecisionService', function (Restangular, GlobalDataService) {

    var decisions = {};

    return {
      all: function() {
        decisions = Restangular.all("decisions").getList();
        return decisions;
      },
      new: function(decision) {
        decisions.post(decision);
      }
    };
  });


})();
