import 'dart:async';

import 'package:customer_phone_ordering/classes/order_status.dart';
import 'package:flutter/material.dart';
import 'package:stomp_dart_client/stomp_dart_client.dart' as sdc;

@Deprecated("used in [order_progress] directly")
OrderStatus? globalOrderStatus;
@Deprecated("used in [order_progress] directly")
String? globalOrderId;
var _sC = sdc.StompClient(
  config: sdc.StompConfig(
    url: 'ws://localhost:8080/ws',
    onConnect: (stompFrame) => _onConnect(stompFrame),
    beforeConnect: () async =>
        await Future.delayed(const Duration(milliseconds: 500)),
    onWebSocketError: (dynamic error) => debugPrint(error.toString()),
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
      debugPrint(globalOrderStatus.toString());
    },
  );
}

@Deprecated("used in [order_progress] directly")
run(String orderId) {
  globalOrderId = orderId;
  _sC.activate();
}

@Deprecated("used in [order_progress] directly")
OrderStatus? get orderStatus => globalOrderStatus;
