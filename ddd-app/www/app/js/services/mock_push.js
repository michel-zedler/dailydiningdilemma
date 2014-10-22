(function () {
  "use strict";

  ddd.factory('PushService', function($interval) {

    var dummyWebSocket = {};
    var onMessageCallbacks = [];

    dummyWebSocket.onmessage = function (evt) {
      onMessageCallbacks.forEach(function (callback) {
        callback(evt);
      });
    };

    var getRandomInt = function(min, max) {
      return Math.floor(Math.random() * (max - min + 1)) + min;
    }

    $interval(function() {
      var r = getRandomInt(0,100);
      var msg = {
        data:[]
      };
      msg.data.push({
        optionId:0,
        points:r
      });
      msg.data.push({
        optionId:1,
        points:(100-r)
      });

      dummyWebSocket.onmessage(msg);
    }, 2000);

    return {
      notifyOnMessage: function(callback) {
        onMessageCallbacks.push(callback);
      }
    }
  });


})();
