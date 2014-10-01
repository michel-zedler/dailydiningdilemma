var servicesModule = angular.module('ddd.services', []);

servicesModule.factory('authService', function($firebase, $firebaseSimpleLogin) {

  var _authClient = $firebaseSimpleLogin(new Firebase("https://ddd-app.firebaseio.com"));


  var login = function(provider, cb) {

    var result = {
      user: undefined,
      err: undefined
    };

    _authClient.$login(provider).then(function(user) {
      // user.displayName, user.email
      result.user = user;
      cb(result, provider);
    }, function(error) {
      result.err = error;
      cb(result, provider);
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

servicesModule.factory('globalData', function() {

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