(function () {
  "use strict";

  ddd.factory('DecisionService', function (Restangular, GlobalDataService) {
    return {
      all: function() {
        return Restangular.all("decisions").getList();
      }
    };
  });


})();
