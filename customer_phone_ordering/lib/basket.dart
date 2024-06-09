import 'dart:async';
import 'dart:typed_data';

import 'package:customer_phone_ordering/classes/basket_order.dart';
import 'package:flutter/material.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:qrscan/qrscan.dart' as scanner;

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
    return Scaffold(body: _getWidgetBasedOnBasketLength());
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
                leading: Text(food.id.toString()),
                onTap: () => null,
              ));
        },
      );
    } else {
      return const Center(
        child: Text("Basket is empty",
            style: TextStyle(fontWeight: FontWeight.w300)),
      );
    }
  }
}
