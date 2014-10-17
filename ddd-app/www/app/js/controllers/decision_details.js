(function () {
  "use strict";

  ddd.controller('DecisionDetailsCtrl', function ($scope, $stateParams, DecisionService, OptionService) {
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

    $scope.pieData = [
      { key: "Theaterhaus", y: 0, color: '#1f77b4' },
      { key: "Illuminati", y: 0, color: '#aec7e8' },
      { key: "Spice Of India", y: 0, color: '#ff7f0e' },
      { key: "NGA", y: 0, color: '#ffbb78' },
      { key: "Some", y: 0, color: '#2ca02c' },
      { key: "More", y: 0, color: '#98df8a' },
      { key: "Rages", y: 0, color: '#d62728' },
    ];

    $scope.votes = new Array(); //keeping votes separate from pieData to enable direct binding

    $scope.xFunction = function(){
      return function(d) {
        return d.key;
      };
    }

    $scope.yFunction = function(){
      return function(d){
        return d.y;
      };
    }
    
    $scope.colorFunction = function() {
      return function(d, i) {
        //d is not the pieData entry, but some d3/svg stuff
        //i is an index
        return $scope.pieData[i].color;
      };
    }

    $scope.pointsSpentOnOtherOptions = function(key) {
      var spentOnOthers = 0;
      for (var i = 0; i < $scope.votes.length; i++) {
        if ($scope.votes[i].key != key) {
          spentOnOthers += parseInt($scope.votes[i].val);
        }
      }
      return spentOnOthers;
    }

    $scope.maximizePointsForOption = function(key) {
      var spentOnOthers = $scope.pointsSpentOnOtherOptions(key);
      for (var i = 0; i < $scope.votes.length; i++) {
        if ($scope.pieData[i].key == key) {
          $scope.votes[i].val = TOTAL - spentOnOthers;
        }
      }
      $scope.updateVote(key);
    }

    $scope.updateVote = function(key) {
      /*for (var i = 0; i < $scope.initialData.length; i++) {
       $scope.exampleData[i].y = $scope.initialData[i].y;
       } */
      var spent = 0;
      var spentOnOthers = $scope.pointsSpentOnOtherOptions(key);

      for (var i = 0; i < $scope.votes.length; i++) {
        $scope.votes[i].max = TOTAL - spent;
        var current = Math.min(parseInt($scope.votes[i].max), parseInt($scope.votes[i].val));
        if ($scope.pieData[i].key == key) {
          current = Math.min(current, TOTAL - spentOnOthers);
        }
        $scope.votes[i].val = current;
        $scope.pieData[i].y = current;

        spent += current;
      }

      $scope.pieData[$scope.pieData.length-1].y = TOTAL - spent;

      d3.select('svg').select('#donutLabel').remove();
      var color = d3.scale.linear()
          .domain([0, 90, 100])
          .range(["#eee", "#777", "#000"]);
      d3.select('svg').append("text")
          .attr("id", "donutLabel")
          .attr("style", function(d){return 'text-anchor: middle; font-size: 30px; font-weight: bold; fill: ' + color(spent) + ";";})
          .attr("dx", function(d){return 150;})
          .attr("dy", function(d){return 160;})
          .text(function(d){return spent;});

      //deep copy values to get new object reference
      $scope.pieData = angular.copy($scope.pieData);
    }

    $scope.init = function() {

      for (var i = 0; i < $scope.pieData.length; i++) {
        var option = {
          cssSuffix : i,
          key : $scope.pieData[i].key,
          color : $scope.pieData[i].color,
          val : 0
        };
        $scope.votes.push(option);
      }

      var vacantPoints = { key: "", y: TOTAL, color: '#ffffff' };
      $scope.pieData.push(vacantPoints);

      d3.select('svg').select('#pieCenterLabel').remove();
      d3.select('svg').append("text")
          .attr("id", "donutLabel")
          .attr("style", function(d){return 'text-anchor: middle; font-size: 30px; font-weight: bold;'})
          .attr("dx", function(d){return 150;})
          .attr("dy", function(d){return 160;})
          .text(function(d){return "Vote!";});

      for (var i = 0; i < $scope.votes.length; i++) {
        injectStyles('.range.range-vote-'+$scope.votes[i].cssSuffix+' input::-webkit-slider-thumb:before { background: '+$scope.votes[i].color+'; }');
      }

      /*var pieChartSVG = jQuery("svg").first();
       pieChartSVG.css("position", "fixed");
       pieChartSVG.css("top", "100px");
       pieChartSVG.css("left", "100px");
       pieChartSVG.css("z-index", "-1");*/
    }

    function injectStyles(rule) {
      d3.select("body").append("style").text(rule);
    }

    $scope.init();

  });

})();
