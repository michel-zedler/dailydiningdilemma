(function () {
  "use strict";

  ddd.controller('DecisionListCtrl', function ($scope, GlobalDataService, DecisionService) {
      $scope.user = { name: GlobalDataService.user.name, email: GlobalDataService.user.email, key: GlobalDataService.user.sessionKey };
      $scope.decisions = DecisionService.all();

      $scope.exampleData = [
          { key: "Theaterhaus", y: 25 },
          { key: "Illuminati", y: 25 },
          { key: "NGA", y: 10 },
          { key: "Spice Of India", y: 40 }

      ];
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
  });

})();
