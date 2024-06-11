import 'dart:convert';

import 'package:customer_phone_ordering/classes/order_status.dart';
import 'package:flutter/material.dart';
import 'package:stomp_dart_client/stomp_dart_client.dart';

class OrderProgress extends StatefulWidget {
  const OrderProgress(this.orderId, {super.key});
  final String orderId;

  @override
  OrderProgressState createState() => OrderProgressState();
}

class OrderProgressState extends State<OrderProgress> {
  OrderStatus? orderStatus;
  StompClient? stompClient;

  @override
  void initState() {
    super.initState();
    final String destination = '/topic/orders/status-update/${widget.orderId}';

    void onConnect(StompFrame frame) {
      stompClient?.subscribe(
        destination: destination,
        callback: (frame) {
          final decodedBody = jsonDecode(frame.body!);
          debugPrint('Message received: $decodedBody');
          setState(() {
            orderStatus = OrderStatus.values.singleWhere(
              (element) => element.name == decodedBody,
              orElse: () => OrderStatus.CANCELED,
            );
          });
        },
      );
    }

    stompClient = StompClient(
      config: StompConfig(
        url: 'ws://localhost:8080/ws',
        onConnect: onConnect,
        beforeConnect: () async {
          await Future.delayed(const Duration(milliseconds: 200));
        },
        onWebSocketError: (dynamic error) =>
            debugPrint('WebSocket error: $error'),
        onStompError: (dynamic error) => debugPrint('STOMP error: $error'),
        onDisconnect: (frame) => debugPrint('Disconnected: $frame'),
        onDebugMessage: (msg) => debugPrint('Debug: $msg'),
      ),
    );

    stompClient?.activate();
  }

  @override
  void dispose() {
    stompClient?.deactivate();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Text((orderStatus ?? OrderStatus.SENT).name),
    );
  }
}
