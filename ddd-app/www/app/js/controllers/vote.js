(function () {
  "use strict";

  ddd.controller('VoteCtrl', function ($scope, $stateParams, DecisionService, OptionService) {
    $scope.decision = {};
    $scope.options = [];
    $scope.decisionId = $stateParams.decisionId;

    DecisionService.byId($scope.decisionId, function(decision) {
      $scope.decision = decision;
    });

    OptionService.byDecisionId($scope.decisionId, function(options) {
      $scope.options = options;
    });


    var TOTAL = 100;
    var COLORS = [ '#1f77b4', '#aec7e8', '#ff7f0e', '#ffbb78', '#2ca02c', '#98df8a', '#d62728'];

    $scope.votes = []; //keeping votes separate from pieSegments to enable live updates

    $scope.pieSegments = [];
    $scope.options.forEach(function(option, index) {
      $scope.pieSegments.push({
        key: option.name,
        y: 0,
        color: COLORS[index]
      });
    });

    $scope.xFunction = function(){
      return function(d) {
        return ''; //do not label donut segments
      };
    };

    $scope.yFunction = function(){
      return function(d){
        return d.y;
      };
    };

    $scope.colorFunction = function() {
      return function(d, i) {
        //d is not the pieSegments entry, but some d3/svg stuff; i is the index
        return $scope.pieSegments[i].color;
      };
    };

    $scope.pointsSpentOnOtherOptions = function(key) {
      var result = 0;
      $scope.votes.forEach(function(vote) {
        if (vote.key != key) {
          result += parseInt(vote.val);
        }
      });
      return result;
    };

    $scope.maximizePointsForOption = function(key) {
      var pointsSpentOnOthers = $scope.pointsSpentOnOtherOptions(key);
      $scope.votes.forEach(function(vote) {
        if (vote.key == key) {
          vote.val = TOTAL - pointsSpentOnOthers;
        }
      });
      $scope.updateVote(key);
    };

    $scope.allPointsSpent = function() {
      var sum = 0;
      $scope.votes.forEach(function(vote) {
        sum += vote.val;
      });
      return sum === 100;
    };

    $scope.updateVote = function(key) {

      //update current and max points of each vote
      var pointsSpentInTotal = 0;
      var pointsSpentOnOthers = $scope.pointsSpentOnOtherOptions(key);

      $scope.votes.forEach(function(vote) {
        if (vote.key == key) {
          var max = TOTAL - pointsSpentOnOthers;
          vote.val = Math.min(max, parseInt(vote.val));
          $scope.pieSegments[vote.index].y = vote.val;
        }
        pointsSpentInTotal += vote.val;
      });

      //update vacant points segment
      $scope.pieSegments[$scope.pieSegments.length-1].y = TOTAL - pointsSpentInTotal;

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

    $scope.setVoteActive = function(key) {
      $scope.votes.forEach(function(vote) {
        if (vote.key == key) {
          vote.active = !vote.active;
        } else {
          vote.active = false;
        }
      });
    }

    var injectStyles = function(rule) {
      d3.select("body").append("style").text(rule);
    };

    var init = function() {
      var i = 0;
      $scope.pieSegments.forEach(function(item) {
            var vote = {
              index : i++,
              key : item.key,
              color : item.color,
              val : 0,
              active: false
            };
            $scope.votes.push(vote);
          }
      );

      var vacantPoints = { key: "", y: TOTAL, color: '#ffffff' };
      $scope.pieSegments.push(vacantPoints);

      d3.select('svg').select('#pieCenterLabel').remove();
      d3.select('svg').append("text")
          .attr("id", "donutLabel")
          .attr("style", function(d){return 'text-anchor: middle; font-size: 30px; font-weight: bold;'})
          .attr("dx", function(d){return 150;})
          .attr("dy", function(d){return 160;})
          .text(function(d){return "Vote!";});

      $scope.votes.forEach(function(vote) {
            injectStyles('.range.range-vote-'+vote.index+' input::-webkit-slider-thumb:before { background: '+vote.color+'; }');
          }
      );

    };

    init();

  });

})();
