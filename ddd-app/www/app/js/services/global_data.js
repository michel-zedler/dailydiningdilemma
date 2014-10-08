(function () {
  "use strict";

  ddd.factory('GlobalDataService', function() {

    var user = {
      id: undefined,
      name: undefined,
      email: undefined,
      sessionKey: undefined
    };

    return {
      user: user
    };
  });


})();
