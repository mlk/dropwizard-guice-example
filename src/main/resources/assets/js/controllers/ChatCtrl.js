angular.module('app.ChatController',[])
  .controller('ChatCtrl',

  function ChatController($scope, atmosphereService) {
    $scope.model = {
      transport: 'websocket',
      messages: []
    };

    var socket;

    var request = {
      url: '/chat/',
      contentType: 'application/json',
      logLevel: 'debug',
      transport: 'websocket',
      trackMessageLength: true,
      reconnectInterval: 5000,
      enableXDR: true,
      timeout: 60000
    };

    request.onOpen = function (response) {
      $scope.model.transport = response.transport;
      $scope.model.connected = true;
      $scope.model.content = 'Connected using ' + response.transport;
      $scope.model.logged = true;
    };

    request.onClientTimeout = function (response) {
      $scope.model.content = 'Client closed the connection after a timeout. Reconnecting in ' + request.reconnectInterval;
      $scope.model.connected = false;
      socket.push(atmosphere.util.stringifyJSON({ originatingSystem: '', messagePayload: 'is inactive and closed the connection. Will reconnect in ' + request.reconnectInterval }));
      setTimeout(function () {
        socket = atmosphereService.subscribe(request);
      }, request.reconnectInterval);
    };

    request.onReopen = function (response) {
      $scope.model.connected = true;
      $scope.model.content = 'Atmosphere re-connected using ' + response.transport;

    };

    //For demonstration of how you can customize the fallbackTransport using the onTransportFailure function
    request.onTransportFailure = function (errorMsg, request) {
      atmosphere.util.info(errorMsg);
      request.fallbackTransport = 'long-polling';
      $scope.model.header = 'Tracking Server.';
    };

    request.onMessage = function (response) {
      var responseText = response.responseBody;
      try {
        var message = atmosphere.util.parseJSON(responseText);



            var date = typeof (message.time) === 'string' ? parseInt(message.time) : message.time;
            $scope.model.messages.push({ date: new Date(date), text: message.messagePayload });

      } catch (e) {
        console.error("Error parsing JSON: ", responseText);
        throw e;
      }
    };

    request.onClose = function (response) {
      $scope.model.connected = false;
      $scope.model.content = 'Server closed the connection after a timeout';
      socket.push(atmosphere.util.stringifyJSON({ originatingSystem: $scope.model.name, messagePayload: 'disconnecting' }));
    };

    request.onError = function (response) {
      $scope.model.content = "Sorry, but there's some problem with your socket or the server is down";
      $scope.model.logged = false;
    };

    request.onReconnect = function (request, response) {
      $scope.model.content = 'Connection lost. Trying to reconnect ' + request.reconnectInterval;
      $scope.model.connected = false;
    };

    socket = atmosphereService.subscribe(request);

    var input = $('#input');
    input.keydown(function (event) {
      var me = this;
      var msg = $(me).val();
      if (msg && msg.length > 0 && event.keyCode === 13) {
        $scope.$apply(function () {
          // First message is always the users name
          if (!$scope.model.name)
            $scope.model.name = msg;

          socket.push(atmosphere.util.stringifyJSON({ originatingSystem: 'Chat', messagePayload: msg }));
          $(me).val('');
        });
      }
    });

   
    inputSend.onclick = function (event) {
      var msg = input.val();
      
        // First message is always the originatingSystem's name
        if (!$scope.model.name)
          $scope.model.name = msg;

        socket.push(atmosphere.util.stringifyJSON({ originatingSystem: 'Chat', messagePayload: msg }));
        input.val('');
      
    };

  });
