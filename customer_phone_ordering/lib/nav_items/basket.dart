import 'dart:async';
import 'dart:convert';

import 'package:customer_phone_ordering/classes/basket_order.dart';
import 'package:customer_phone_ordering/classes/extensions/colour_extension.dart';
import 'package:customer_phone_ordering/classes/order.dart';
import 'package:customer_phone_ordering/classes/order_status.dart';
import 'package:customer_phone_ordering/classes/table.dart';
import 'package:floating_chat_button/floating_chat_button.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:stomp_dart_client/stomp_dart_client.dart';
import '../classes/customer.dart';

class Basket extends StatefulWidget {
  const Basket({super.key});

  @override
  BasketState createState() => BasketState();
}

class BasketState extends State<Basket> {
  static bool _activeOrder = false;
  StompClient? stompClient;
  var _orderStatus = OrderStatus.SENT;

  @override
  initState() {
    super.initState();
  }

  @override
  void dispose() {
    stompClient?.deactivate();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: !_activeOrder
          ? _getWidgetBasedOnBasketLength()
          : _wrapWithFloatingButton(_getWidgetBasedOnBasketLength()),
      floatingActionButton: Visibility(
        visible: CurrentTable.table != null && BasketItemHolder().isNotEmpty(),
        child: FloatingActionButton.extended(
          tooltip: "Confirm and send your order",
          heroTag: this,
          backgroundColor: Colors.green,
          foregroundColor: Colors.black87,
          onPressed: _sendOrder,
          label: const Text(
            'Send Order',
            style: TextStyle(letterSpacing: 1),
          ),
          icon: const Icon(Icons.send_rounded),
        ),
      ),
    );
  }

  Widget _getWidgetBasedOnBasketLength() {
    if (BasketItemHolder().length() > 0) {
      return ListView.builder(
        itemCount: BasketItemHolder().length(),
        itemBuilder: (_, index) {
          var food = BasketItemHolder().get(index);
          return Container(
            color: Colors.white,
            child: ListTile(
              title: Text(food.title),
              subtitle: Text(food.price.toString()),
              trailing: IconButton(
                icon: const Icon(
                  Icons.delete_forever_rounded,
                  color: Color.fromARGB(255, 255, 255, 255),
                ),
                style: ButtonStyle(
                  backgroundColor: MaterialStateProperty.all<Color>(Colors.red),
                  shape: MaterialStateProperty.all<OutlinedBorder>(
                      const CircleBorder()),
                ),
                onPressed: () {
                  setState(() {
                    BasketItemHolder().removeAt(index);
                  });
                },
              ),
              leading: Text(food.id.toString()),
            ),
          );
        },
      );
    } else {
      return Center(
        child: InkWell(
          child: const Text("Basket is empty",
              style: TextStyle(fontWeight: FontWeight.w300)),
          onTap: () {/*TODO redirect to the menu*/},
        ),
      );
    }
  }

  Widget _wrapWithFloatingButton(Widget child) {
    return FloatingChatButton(
      background: Container(padding: const EdgeInsets.all(4), child: child),
      onTap: (_) => _showModalBottomSheet,
      chatIconBorderColor: Colors.white,
    );
  }

  Future<dynamic> _showModalBottomSheet() {
    return showModalBottomSheet(
      enableDrag: true,
      isScrollControlled: true,
      isDismissible: false,
      backgroundColor: Colors.transparent,
      context: context,
      builder: (context) => StatefulBuilder(
        builder: (BuildContext context, StateSetter setModalState) => Container(
          color: Colors.transparent,
          child: SafeArea(
            bottom: true,
            child: Column(
              mainAxisSize: MainAxisSize.max,
              children: [
                const SizedBox(height: 60),
                Expanded(
                  child: ClipRRect(
                    borderRadius: const BorderRadius.only(
                        topRight: Radius.circular(20),
                        topLeft: Radius.circular(20)),
                    child: Container(
                      width: double.infinity,
                      color: Colors.white,
                      child: Center(
                        child: Padding(
                          padding: const EdgeInsets.all(20.0),
                          child: Text(_orderStatus.name,
                              style: const TextStyle(
                                  fontSize: 30, color: Colors.blue)),
                        ),
                      ),
                    ),
                  ),
                ),
                Container(
                  alignment: Alignment.bottomCenter,
                  color: Colors.white,
                  padding: const EdgeInsets.all(20),
                  child: Row(
                    crossAxisAlignment: CrossAxisAlignment.center,
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    children: [
                      TextButton(
                        //TODO
                        onPressed: () {
                          // Navigator.of(context).pop();
                        },

                        //TODO
                        child: const Text(
                          "Mark as delivered",
                          style: TextStyle(
                              fontSize: 20,
                              color: Color.fromARGB(255, 0, 255, 13)),
                        ),
                      ),
                      TextButton(
                        onPressed: () {
                          Navigator.of(context).pop();
                        },
                        child: const Text(
                          "Dismiss",
                          style: TextStyle(
                              fontSize: 20,
                              color: Color.fromARGB(255, 255, 0, 0)),
                        ),
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  ///sends an order in json file format
  ///e.g       "{"items":[{"id":2,"price":5.5,"title":"Pepsi"}],"table":{"restaurantId":1,"row":"E","column":50},"customer":{"customer_id":1}}"
  void _sendOrder() {
    if (!_isOrderReady()) {
      ScaffoldMessenger.of(context).showSnackBar(const SnackBar(
        content: Text(
          "Scan QR Code on the table Or select food from the menu",
          style: TextStyle(color: Colors.black87),
        ),
        duration: Duration(seconds: 2),
        backgroundColor: Color.fromARGB(255, 255, 0, 0),
      ));
    } else {
      final finalOrder =
          Order.defFood(/*TODO customer */ Customer.id(1), CurrentTable.table!);
      http.post(
        Uri.http('localhost:8080', '/order/add'),
        body: jsonEncode(finalOrder.toJson()),
        headers: {'Content-Type': 'application/json'},
      ).then((value) => _sentOfferResponse(value));
    }
  }

  void _sentOfferResponse(http.Response httpResponse) {
    final scaffold = ScaffoldMessenger.of(context);
    if (httpResponse.statusCode == 201) {
      scaffold.showSnackBar(SnackBar(
        content: const Text(
          "Order Successfully Sent!",
          style: TextStyle(color: Colors.black87),
        ),
        duration: const Duration(seconds: 5),
        backgroundColor: Colors.lightGreenAccent,
        action: SnackBarAction(
            label: 'OK', onPressed: scaffold.hideCurrentSnackBar),
      ));
      _connectToStomp(httpResponse.body);
      setState(() {
        BasketItemHolder().clear();
        _activeOrder = true;
      });
    } else {
      scaffold.showSnackBar(const SnackBar(
        content: Text("Order Failed to be sent"),
        backgroundColor: Colors.redAccent,
        duration: Duration(seconds: 5),
      ));
    }
  }

  void _connectToStomp(String orderId) {
    final String destination = '/topic/orders/status-update/$orderId';

    void onConnect(StompFrame frame) {
      stompClient?.subscribe(
        destination: destination,
        callback: (frame) {
          final decodedBody = jsonDecode(frame.body!);
          debugPrint('Message received: $decodedBody');
          // _streamController.add(
          OrderStatus.values.singleWhere(
            (element) => element.name == decodedBody,
            orElse: () => OrderStatus.SENT,
            //  ),
          );
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

  bool _isOrderReady() =>
      CurrentTable.table != null && BasketItemHolder().isNotEmpty();
}
