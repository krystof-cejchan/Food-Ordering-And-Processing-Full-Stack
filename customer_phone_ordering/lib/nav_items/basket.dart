import 'dart:convert';

import 'package:customer_phone_ordering/classes/basket_order.dart';
import 'package:customer_phone_ordering/classes/order.dart';
import 'package:customer_phone_ordering/classes/table.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import '../classes/customer.dart';

class Basket extends StatefulWidget {
  const Basket({super.key});

  @override
  BasketState createState() => BasketState();
}

class BasketState extends State<Basket> {
  @override
  initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _getWidgetBasedOnBasketLength(),
      floatingActionButton: Visibility(
        //TODO table functionality needed to be added
        visible: /*widget.table != null &&*/ BasketItemHolder().isNotEmpty(),
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
                trailing: Text(food.price.toString()),
                leading: Text(food.id.toString()),
                onTap: () => null,
              ));
        },
      );
    } else {
      return Center(
        child: InkWell(
          child: const Text("Basket is empty",
              style: TextStyle(fontWeight: FontWeight.w300)),
          onTap: () {/*redirect to the menu*/},
        ),
      );
    }
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
      ).then((value) => _sentOfferResponse(value.statusCode));
    }
  }

  void _sentOfferResponse(int statuscode) {
    final scaffold = ScaffoldMessenger.of(context);
    if (statuscode == 201) {
      BasketItemHolder().clear();
      scaffold.showSnackBar(SnackBar(
        content: const Text(
          "Order Successfully Sent!",
          style: TextStyle(color: Colors.black87),
        ),
        duration: const Duration(seconds: 2),
        backgroundColor: Colors.lightGreenAccent,
        action: SnackBarAction(
            label: 'OK', onPressed: scaffold.hideCurrentSnackBar),
      ));
    } else {
      scaffold.showSnackBar(const SnackBar(
        content: Text("Order Failed to be sent"),
        backgroundColor: Colors.redAccent,
        duration: Duration(seconds: 3),
      ));
    }
  }

  bool _isOrderReady() =>
      CurrentTable.table != null && BasketItemHolder().isNotEmpty();
}
