(function () {
  "use strict";

  ddd.factory('AuthService', function($firebase, $firebaseSimpleLogin, $http, GlobalDataService, Restangular) {

    var _authClient = $firebaseSimpleLogin(new Firebase("https://ddd-app.firebaseio.com"));

    var login = function(provider, cb) {

      _authClient.$login(provider).then(function(user) {

        var data = {
          'service': provider,
          'token': user.accessToken,
          "deviceInfo": {
            "uuid": "uuid",
            "model": "model",
            "platform": "browser"
          }
        };

        $http({method: 'POST', url: 'https://tools.eckert-partner.it/dailydining-int/api/rest/login', data: data}).
          success(function (data, status, header) {
            if (data.errorMessage) {
              cb(data.errorMessage);
            } else {
              GlobalDataService.user.sessionKey = data.apiKey;

              //delete $http.defaults.headers.common['X-Requested-With'];
              //$http.defaults.headers.common.apikey = data.apiKey;

              Restangular.setDefaultHeaders({ apikey: data.apiKey });

              GlobalDataService.user.id = user.id;
              GlobalDataService.user.name = user.displayName;
              if (user.email) {
                GlobalDataService.user.email = user.email;
              } else if (user.thirdPartyUserData.email) {
                GlobalDataService.user.email = user.thirdPartyUserData.email;
              }

              cb();
            }
          }
        );
      }, function(error) {
        cb(error);
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
