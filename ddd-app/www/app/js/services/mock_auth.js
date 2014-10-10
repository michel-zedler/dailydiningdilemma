(function () {
  "use strict";

  ddd.factory('AuthService', function(GlobalDataService) {

    var login = function(provider, cb) {
      GlobalDataService.user.sessionKey = '1234ABCDEF';
      GlobalDataService.user.id = 1234;
      GlobalDataService.user.name = 'Mock User';
      GlobalDataService.user.email = 'user@mock.de';

      cb();
    };

    return {
      loginFacebook: function(cb) {
        return login('facebook', cb);
      },
      loginGoogle: function(cb) {
        return login('google', cb);
      },
      logout: function() {

      }
    };
  });


})();
