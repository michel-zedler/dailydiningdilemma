(function () {
  "use strict";

  ddd.factory('AuthService', function($firebase, $firebaseSimpleLogin, $http, GlobalDataService, Restangular, localStorageService) {
    var LAST_APIKEY_KEY = 'lastApiKey';
    var _authClient = $firebaseSimpleLogin(new Firebase("https://ddd-app.firebaseio.com"));

    var login = function(provider, cb) {
      _authClient.$login(provider).then(function (user) {

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

                Restangular.setDefaultHeaders({apikey: data.apiKey});

                GlobalDataService.user.id = user.id;
                GlobalDataService.user.name = user.displayName;
                if (user.email) {
                  GlobalDataService.user.email = user.email;
                } else if (user.thirdPartyUserData.email) {
                  GlobalDataService.user.email = user.thirdPartyUserData.email;
                }
                console.log('storing apikey ' + data.apiKey);
                localStorageService.set(LAST_APIKEY_KEY, data.apiKey );
                
                cb();
              }
            }
        );
      }, function (error) {
        cb(error);
      });
    }

    var logout = function() {
      console.log("discarded " + LAST_APIKEY_KEY)
      localStorageService.remove(LAST_APIKEY_KEY);
      // TODO: implement cleanup of GlobalDataService and Restangular default headers
    };

    return {
      tryReAuthentication: function(cb) {
        var storedApiKey = localStorageService.get(LAST_APIKEY_KEY);      
        console.log('apikey is ' + storedApiKey);
        if (storedApiKey) {      
          GlobalDataService.user.sessionKey = storedApiKey;
          Restangular.setDefaultHeaders({apikey: storedApiKey});  
          console.log("successfully reauthenticated");
          return cb(true);
        }
        console.log("no reauthentication possible");
        return cb(false);
      },
      login: function(provider, cb) {
        return login(provider, cb);
      },
      logout: logout
    };
  });

})();
