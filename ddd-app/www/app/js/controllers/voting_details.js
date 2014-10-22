(function () {
  "use strict";

  ddd.controller('VotingDetailsCtrl', function ($scope, $interval, $location, $stateParams, VotingService, OptionService, VotingHelperService) {
    $scope.voting = {};
    $scope.options = [];
    $scope.votingId = $stateParams.votingId;

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

    var initChart = function() {
      var optionSegment1 = {
        optionName: "Hello",
        value: 80,
        color: VotingHelperService.optionColor(0)
      };
      $scope.pieSegments.push(optionSegment1);
      var optionSegment2 = {
        optionName: "World",
        value: 20,
        color: VotingHelperService.optionColor(1)
      };
      $scope.pieSegments.push(optionSegment2);
    };

    initChart();

    var updateCountdownLabelEverySecond = function() {
      $interval(function() {
        VotingHelperService.updateCountdownLabel($scope.voting);
      }, 1000);
    };

    VotingService.byId($scope.votingId, function(voting) {
      VotingHelperService.updateCountdownLabel(voting);
      $scope.voting = voting;
      updateCountdownLabelEverySecond();
    });

    OptionService.byVotingId($scope.votingId, function(options) {
      $scope.options = options;
    });

    $scope.voteFor = function() {
      $interval.cancel(updateCountdownLabelEverySecond);
      $location.path('/app/vote/' + $scope.votingId);
    };

  });

})();
