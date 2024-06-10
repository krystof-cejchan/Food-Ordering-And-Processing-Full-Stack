import 'package:customer_phone_ordering/classes/basket_order.dart';
import 'package:customer_phone_ordering/classes/customer.dart';
import 'package:customer_phone_ordering/classes/food.dart';
import 'package:customer_phone_ordering/classes/table.dart' as t;

class Order {
  List<Food> food;
  t.Table table;
  Customer customer;
  Order(this.food, this.customer, this.table);
  Order.defFood(this.customer, this.table)
      : food = BasketItemHolder().basketContent;

  Map<String, dynamic> toJson() => {
        'items': food.map((f) => f.toJson()).toList(),
        'table': table.toJson(),
        'customer': customer.toJson(),
      };
}
