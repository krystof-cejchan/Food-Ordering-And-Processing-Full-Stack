import 'dart:async';

import 'package:customer_phone_ordering/classes/order_status.dart';
import 'package:stomp_dart_client/stomp_dart_client.dart' as sdc;

OrderStatus? globalOrderStatus;
String? globalOrderId;
var _sC = sdc.StompClient(
  config: sdc.StompConfig(
    url: 'ws://localhost:8080/ws',
    onConnect: (stompFrame) => _onConnect(stompFrame),
    beforeConnect: () async =>
        await Future.delayed(const Duration(milliseconds: 500)),
    onWebSocketError: (dynamic error) => print(error.toString()),
    heartbeatIncoming: Duration.zero,
    heartbeatOutgoing: const Duration(seconds: 20),
    reconnectDelay: const Duration(milliseconds: 500),
  ),
);

void _onConnect(sdc.StompFrame frame) {
  assert(globalOrderId != null);
  _sC.subscribe(
    destination: '/topic/orders/status-update/$globalOrderId',
    callback: (frame) {
      final OrderStatus orderStatus = OrderStatus.values.singleWhere(
          (e) => e.name.toString() == frame.body,
          orElse: () => OrderStatus.CANCELED);
      if (orderStatus == OrderStatus.CANCELED) return;

      globalOrderStatus = orderStatus;
      print(globalOrderStatus);
    },
  );
}

run(String orderId) {
  globalOrderId = orderId;
  _sC.activate();
}

OrderStatus? get orderStatus => globalOrderStatus;
