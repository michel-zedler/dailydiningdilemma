(function () {
  "use strict";

  ddd.factory('AuthService', function($firebase, $firebaseSimpleLogin, $http, GlobalDataService) {

    var _authClient = $firebaseSimpleLogin(new Firebase("https://ddd-app.firebaseio.com"));

    var login = function(provider, cb) {

      var result = {
        user: undefined,
        err: undefined
      };

      _authClient.$login(provider).then(function(user) {
        result.user = user;

        var data = {
          'service': provider,
          'token': result.user.accessToken
        };

        $http({method: 'POST', url: 'https://tools.eckert-partner.it/dailydining-int/api/rest/login', data: data}).
          success(function (data, status, header) {
            if (data.errorMessage) {
              cb(undefined, data.errorMessage);
            } else {
              result.sessionKey = data.apiKey;

              GlobalDataService.user.sessionKey = data.apiKey;

              GlobalDataService.user.id = result.user.id;
              GlobalDataService.user.name = result.user.displayName;
              if (result.user.email) {
                GlobalDataService.user.email = result.user.email;
              } else if (result.user.thirdPartyUserData.email) {
                GlobalDataService.user.email = result.user.thirdPartyUserData.email;
              }

              cb(result);
            }
          }
        );
      }, function(error) {
        cb(undefined, error);
      });
    };

    var logout = function() {
      // TODO: implement this
    };

    return {
      loginFacebook: function(cb) {
        return login('facebook', cb);
      },
      loginGoogle: function(cb) {
        return login('google', cb);
      },
      logout: logout
    };
  });

})();
