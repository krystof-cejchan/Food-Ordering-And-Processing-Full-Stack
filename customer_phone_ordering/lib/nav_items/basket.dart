import 'package:customer_phone_ordering/classes/basket_order.dart';
import 'package:flutter/material.dart';
import '../classes/table.dart' as t;

class Basket extends StatefulWidget {
  const Basket(this.table, {super.key});
  final t.Table? table;

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
      floatingActionButton: FloatingActionButton.extended(
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

  void _sendOrder() {
    if (widget.table != null) {
      // throw Exception
    }
    final order = BasketItemHolder().basketContent;
    final table = widget.table!;

    // API request
  }
}
