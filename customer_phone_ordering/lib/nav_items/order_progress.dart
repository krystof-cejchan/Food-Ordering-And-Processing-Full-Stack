import 'package:customer_phone_ordering/classes/order_status.dart';
import 'package:customer_phone_ordering/stomp/stomp_config.dart' as stomp;
import 'package:flutter/material.dart';

class OrderProgress extends StatefulWidget {
  const OrderProgress(this.orderId, {super.key});
  final String orderId;

  @override
  OrderProgressState createState() => OrderProgressState();
}

class OrderProgressState extends State<OrderProgress> {
  @override
  initState() {
    stomp.run(widget.orderId);
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Center(
        child: Text((stomp.orderStatus ?? OrderStatus.CANCELED).name));
  }
}
