(function () {
  "use strict";

  ddd.controller('VoteCtrl', function ($scope, $stateParams, $location, $ionicBackdrop, VotingService, OptionService, VoteService, VotingHelperService) {

    var TOTAL = 100;

    $scope.votingId = $stateParams.votingId;
    $scope.voting = {};
    $scope.vote = [];
    $scope.pieSegments = [];

    $scope.pieSegmentLabel = function(){
      return function(d) {
        return ''; //you may add labels here
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

    $scope.pointsSpentOnOtherOptions = function(optionId) {
      var result = 0;
      $scope.vote.forEach(function(vote) {
        if (vote.option.id !== optionId) {
          result += parseInt(vote.points);
        }
      });
      return result;
    };

    $scope.maximizePointsForOption = function(optionId) {
      var pointsSpentOnOthers = $scope.pointsSpentOnOtherOptions(optionId);
      $scope.vote.forEach(function(vote) {
        if (vote.option.id === optionId) {
          vote.points = TOTAL - pointsSpentOnOthers;
        }
      });
      $scope.updateVote(optionId);
    };

    $scope.allPointsSpent = function() {
      var pointsSpent = 0;
      $scope.vote.forEach(function(vote) {
        pointsSpent += vote.points;
      });
      return pointsSpent === TOTAL;
    };

    $scope.updateVote = function(optionId) {

      //update current and max points of each vote
      var pointsSpentInTotal = 0;
      var pointsSpentOnOthers = $scope.pointsSpentOnOtherOptions(optionId);

      $scope.vote.forEach(function(vote, index) {
        if (vote.option.id === optionId) {
          var max = TOTAL - pointsSpentOnOthers;
          vote.points = Math.min(max, parseInt(vote.points));
          $scope.pieSegments[index].value = vote.points;
        }
        pointsSpentInTotal += vote.points;
      });

      //update vacant points segment
      $scope.pieSegments[$scope.pieSegments.length-1].value = TOTAL - pointsSpentInTotal;

      //update donut label
      d3.select('svg').select('#donutLabel').remove();
      var color = d3.scale.linear()
          .domain([0, 90, 100])
          .range(["#eee", "#777", "#000"]);
      d3.select('svg').append("text")
          .attr("id", "donutLabel")
          .attr("style", function(d){return 'text-anchor: middle; font-size: 30px; font-weight: bold; fill: ' + color(pointsSpentInTotal) + ";";})
          .attr("dx", function(d){return 150;})
          .attr("dy", function(d){return 160;})
          .text(function(d){return pointsSpentInTotal;});

      //deep copy to get new object reference -> trigger angular watch expression -> update pie chart svg
      $scope.pieSegments = angular.copy($scope.pieSegments);
    }

    $scope.setVoteActive = function(optionId) {
      $scope.vote.forEach(function(vote) {
        if (vote.option.id === optionId) {
          vote.active = !vote.active;
        } else {
          vote.active = false;
        }
      });
    }

    $scope.submitVote = function() {
      if (!$scope.allPointsSpent()) {
        return;
      }
      $ionicBackdrop.retain();
      VoteService.store($scope.vote, $scope.votingId, function() {
        $ionicBackdrop.release();
        $location.path('/app/voting-details/' + $scope.votingId);
      });
    };

    var injectStyles = function(rule) {
      d3.select("body").append("style").text(rule);
    };

    var initVote = function(options) {
      options.forEach(function(option, index) {
        var vote = {
          option: option,
          points : 0,
          active: false
        };
        $scope.vote.push(vote);
      });
    }

    var initChart = function() {
      var pointsSpent = 0;
      $scope.vote.forEach(function(vote,index) {
        var optionSegment = {
          optionName: vote.option.name,
          value: vote.points,
          color: VotingHelperService.optionColor(index)
        };
        $scope.pieSegments.push(optionSegment);
        pointsSpent += vote.points;
      });

      var vacantSegment = { optionName: "", value: TOTAL - pointsSpent, color: '#ffffff' };
      $scope.pieSegments.push(vacantSegment);

      d3.select('svg').select('#pieCenterLabel').remove();
      d3.select('svg').append("text")
        .attr("id", "donutLabel")
        .attr("style", function(d){return 'text-anchor: middle; font-size: 30px; font-weight: bold;'})
        .attr("dx", function(d){return 150;})
        .attr("dy", function(d){return 160;})
        .text(function(d){return "Vote!";});

      $scope.vote.forEach(function(vote, index) {
          injectStyles('.range.range-vote-'+vote.option.id+' input::-webkit-slider-thumb:before { background: '+VotingHelperService.optionColor(index)+'; }');
        }
      );

    };

    (function() {
      $ionicBackdrop.retain();

      VotingService.byId($scope.votingId, function(voting) {
        $scope.voting = voting;

        OptionService.byVotingId($scope.votingId, function(options) {

          initVote(options);

          VoteService.latest($scope.votingId, function(latestVote) {
            latestVote.votes.forEach(function(latestVoteItem) {
              $scope.vote.forEach(function(vote) {
                if (latestVoteItem.optionId === vote.option.id) {
                  vote.points = latestVoteItem.value;
                }
              });
            });

            initChart();

            $ionicBackdrop.release();
          })

        });
      });
    })();


  });

})();
