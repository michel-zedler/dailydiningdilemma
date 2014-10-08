var ddd = angular.module('ddd', ['ionic', 'firebase']);

ddd.run(function ($rootScope, $state, $ionicPlatform, $window, GlobalDataService) {

    $ionicPlatform.ready(function () {
      if (window.StatusBar) {
        StatusBar.styleDefault();
      }
    });

    // TODO: Store session key in app?
    $rootScope.$on('$stateChangeStart', function (event, toState) {
      if (toState.name !== "app.login" && toState.name !== "app.logout" && !GlobalDataService.user.sessionKey) {
        $state.go('app.login');
        event.preventDefault();
      }
    });

    // TODO: adapt the session handling to the new impmlementation
    $rootScope.$on('OAuthException', function () {
      $state.go('app.login');
    });

  }
);

ddd.config(function ($stateProvider, $urlRouterProvider) {
  $stateProvider

    .state('app', {
      url: "/app",
      abstract: true,
      templateUrl: "app/templates/menu.html",
      controller: "ApplicationCtrl"
    })

    .state('app.login', {
      url: "/login",
      views: {
        'menuContent': {
          templateUrl: "app/templates/login.html",
          controller: "LoginCtrl"
        }
      }
    })

    .state('app.logout', {
      url: "/logout",
      views: {
        'menuContent': {
          templateUrl: "app/templates/logout.html",
          controller: "ApplicationCtrl"
        }
      }
    })

    .state('app.decisions', {
      url: "/decisions",
      views: {
        'menuContent': {
          templateUrl: "app/templates/decisions.html",
          controller: "DecisionListCtrl"
        }
      }
    });

  // fallback route
  $urlRouterProvider.otherwise('/app/decisions');

});
