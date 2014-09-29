angular.module('ddd', ['ionic', 'firebase', 'ddd.controllers', 'ddd.services'])

  .run(function ($rootScope, $state, $ionicPlatform, $window, globalData) {

    $ionicPlatform.ready(function () {
      if (window.StatusBar) {
        StatusBar.styleDefault();
      }
    });

    // TODO: who calls this method and when? I think we should check the sessionKey here
    $rootScope.$on('$stateChangeStart', function (event, toState) {
      if (toState.name !== "app.login" && toState.name !== "app.logout" && !globalData.user.sessionKey) {
        $state.go('app.login');
        event.preventDefault();
      }
    });

    // TODO: adapt the session handling to the new impmlementation
    $rootScope.$on('OAuthException', function () {
      $state.go('app.login');
    });

  })

  .config(function ($stateProvider, $urlRouterProvider) {
    $stateProvider

      .state('app', {
        url: "/app",
        abstract: true,
        templateUrl: "templates/menu.html",
        controller: "AppCtrl"
      })

      .state('app.login', {
        url: "/login",
        views: {
          'menuContent': {
            templateUrl: "templates/login.html",
            controller: "LoginCtrl"
          }
        }
      })

      .state('app.logout', {
        url: "/logout",
        views: {
          'menuContent': {
            templateUrl: "templates/logout.html",
            controller: "LogoutCtrl"
          }
        }
      })

      .state('app.welcome', {
        url: "/welcome",
        views: {
          'menuContent': {
            templateUrl: "templates/welcome.html",
            controller: "WelcomeCtrl"
          }
        }
      });

    // fallback route
    $urlRouterProvider.otherwise('/app/welcome');

  });

