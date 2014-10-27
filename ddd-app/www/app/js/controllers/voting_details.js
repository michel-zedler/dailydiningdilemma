(function () {
  "use strict";

  ddd.controller('VotingDetailsCtrl', function ($scope, $interval, $location, $stateParams, VotingService, OptionService, VoteService, VotingHelperService) {
    $scope.voting = {};
    $scope.options = [];
    $scope.votingId = $stateParams.votingId;

    var _webSocket = new WebSocket('wss://tools.eckert-partner.it/dailydining-int/websocket/votings/' + $scope.votingId);

    $scope.pieSegments = [];
    $scope.isParticipant = false;

    $scope.pieSegmentLabel = function(){
      return function(d) {
        return d.optionName; //you may add labels here
      };
    };

    $scope.pieSegmentValue = function(){
      return function(d){
        return d.value;
      };
    };

    $scope.pieSegmentColor = function() {
      return function(d, index) {
        //note that d is not the pieSegments entry, but some d3/svg object
        return $scope.pieSegments[index].color;
      };
    };

    var getOptionNameForId = function(id) {
      for (var i = 0; i < $scope.options.length; i++) {
        if ($scope.options[i].id === id) {
          return $scope.options[i].name;
        }
      }
    };

    var redrawChart = function() {
      //deep copy to get new object reference -> trigger angular watch expression -> update pie chart svg
      $scope.pieSegments = angular.copy($scope.pieSegments);
    }

    var initChart = function(votes) {
      $scope.pieSegments = [];
      votes.forEach(function(vote,index) {
        var optionSegment = {
          optionName: getOptionNameForId(vote.optionId),
          value: vote.value,
          color: VotingHelperService.optionColor(index)
        };
        $scope.pieSegments.push(optionSegment);
        redrawChart();
      });
    };

    var detectNewOption = function(data) {
      var newOptionFound = false;
      data.votes.forEach(function(vote) {
        var existingFound = false;
        $scope.options.forEach(function(option) {
          if (option.id === vote.optionId) {
            existingFound = true;
          }
        });
        if(!existingFound) {
          newOptionFound = true;
        }
      });
      return newOptionFound;
    };

    _webSocket.onmessage = function(event) {
      var data = angular.fromJson(event.data);
      $scope.voting.numberOfParticipants = data.numberOfParticipants;      
      if (detectNewOption(data)) {
        // FIXME reload options could be avoided by delivering all option details through websocket
        OptionService.byVotingId($scope.votingId, function(options) {
          $scope.options = options;
        });
      }
      initChart(data.votes);
      if(!$scope.$$phase) { //when using mock we are already in apply scope, but when using true websocket this will not be the case
        $scope.$apply();
      }
    };

    $scope.$on('$destroy', function() {
      if (_webSocket) {
        _webSocket.onclose = function () {};
        _webSocket.close();
      }
    });

    var updateCountdownLabelEverySecond = function() {
      $interval(function() {
        VotingHelperService.updateCountdownLabel($scope.voting);
      }, 1000);
    };

    VotingService.byId($scope.votingId, function(voting) {
      $scope.voting = voting.details;
      $scope.voting.numberOfParticipants = voting.numberOfParticipants;
      VotingHelperService.updateCountdownLabel($scope.voting);
      OptionService.byVotingId($scope.votingId, function(options) {
        $scope.options = options;
        initChart(voting.currentVoteDistribution);
        updateCountdownLabelEverySecond();
      });
      VoteService.latest($scope.votingId, function (latestVote) {
        $scope.isParticipant = (latestVote.votes.length > 0);
      });
    });

    $scope.voteFor = function() {
      $interval.cancel(updateCountdownLabelEverySecond);
      $location.path('/app/vote/' + $scope.votingId);
    };

  });

})();
