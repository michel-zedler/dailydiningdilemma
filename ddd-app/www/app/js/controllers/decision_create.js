(function () {
  "use strict";

  ddd.controller('DecisionCreateCtrl', function ($scope, $ionicPopup, DecisionService) {
    $scope.decision = {};

    var createDecision = function () {

    };

    var openDatePicker = function (mode) {
      $scope.tmp = {};
      $scope.tmp.date = $scope.decision.votingOpenDate;

      var popup = $ionicPopup.show({
        template: '<datetimepicker ng-model="decision.votingOpenDate"></datetimepicker>',
        title: "Start date",
        scope: $scope,
        buttons: [
          { text: 'Cancel' },
          {
            text: '<b>Save</b>',
            type: 'button-positive',
            onTap: function (e) {
              $scope.decision.votingOpenDate = $scope.tmp.date;
            }
          }
        ]
      });
    };

    return {
      submit: createDecision,
      openStartDatePicker: openDatePicker('start')
    }
  });


})();
