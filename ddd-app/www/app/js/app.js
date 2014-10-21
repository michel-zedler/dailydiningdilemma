var ddd = angular.module('ddd', ['ionic', 'firebase', 'restangular', 'nvd3ChartDirectives']);

ddd.run(function ($rootScope, $state, $ionicPlatform, $window, GlobalDataService, Restangular) {

    Restangular.setBaseUrl("https://tools.eckert-partner.it/dailydining-int/api/rest/");

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

    .state('app.votings', {
      url: "/votings",
      views: {
        'menuContent': {
          templateUrl: "app/templates/votings.html",
          controller: "VotingListCtrl"
        }
      }
    })

    .state('app.voting-create', {
      url: "/voting-create",
      views: {
        'menuContent': {
          templateUrl: "app/templates/voting_create.html",
          controller: "VotingCreateCtrl"
        }
      }
    })

    .state('app.options-create', {
      url: "/options-create/:votingId",
      views: {
        'menuContent': {
          templateUrl: "app/templates/options_create.html",
          controller: "OptionsCreateCtrl"
        }
      }
    })

    .state('app.voting-details', {
      url: "/voting-details/:votingId",
      views: {
        'menuContent': {
          templateUrl: "app/templates/voting_details.html",
          controller: "VotingDetailsCtrl"
        }
      }
    })

    .state('app.vote', {
      url: "/vote/:votingId",
      views: {
        'menuContent': {
          templateUrl: "app/templates/vote.html",
          controller: "VoteCtrl"
        }
      }
    });

  // fallback route
  $urlRouterProvider.otherwise('/app/votings');

});

