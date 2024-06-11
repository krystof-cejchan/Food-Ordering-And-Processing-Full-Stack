import 'dart:async';
import 'package:flutter/material.dart';
import 'package:stomp_dart_client/stomp_dart_client.dart';

// Declare the stompClient variable at a higher scope
StompClient? stompClient;

@Deprecated("used in [order_progress] directly")
void main() {
  const String destination = '/topic/orders/status-update';

  // Define the onConnect function within the main method scope
  void onConnect(StompFrame frame) {
    stompClient?.subscribe(
      destination: destination,
      callback: (frame) {
        String? result = frame.body!;
        debugPrint(result);
      },
    );
  }

  // Initialize the stompClient with the onConnect function
  stompClient = StompClient(
    config: StompConfig(
      url: 'ws://localhost:8080/ws',
      onConnect: onConnect,
      beforeConnect: () async {
        debugPrint('waiting to connect...');
        await Future.delayed(const Duration(milliseconds: 200));
        debugPrint('connecting...');
      },
      onWebSocketError: (dynamic error) => debugPrint(error.toString()),
    ),
  );

  // Activate the stompClient
  stompClient?.activate();
}
