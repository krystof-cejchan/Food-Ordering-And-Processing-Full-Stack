import 'dart:convert';

import 'package:customer_phone_ordering/classes/basket_order.dart';
import 'package:customer_phone_ordering/classes/response_extension.dart';
import 'package:customer_phone_ordering/food.dart';
import 'package:flutter/material.dart';
import 'package:flutter_slidable/flutter_slidable.dart';
import 'classes/table.dart' as t;
import 'package:http/http.dart' as http;

class RestaurantMenu extends StatefulWidget {
  RestaurantMenu(t.Table? table, {super.key});
  t.Table? table;
  @override
  RestaurantMenuState createState() => RestaurantMenuState();
}

class RestaurantMenuState extends State<RestaurantMenu>
    with SingleTickerProviderStateMixin {
  //get food from backend
  late List<Food> food = [];
  late final controller = SlidableController(this);
  @override
  initState() {
    super.initState();
    _fetchFood();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: ListView.builder(
      itemCount: food.length,
      itemBuilder: (_, index) {
        return Slidable(
          key: const ValueKey(0),
          /*startActionPane: ActionPane(
            motion: const ScrollMotion(),
            dismissible: DismissiblePane(onDismissed: () {}),
            children: const [
              SlidableAction(
                onPressed: null,
                backgroundColor: Color(0xFFFE4A49),
                foregroundColor: Colors.white,
                icon: Icons.delete,
                label: 'Delete',
              ),
              SlidableAction(
                onPressed: null,
                backgroundColor: Color(0xFF21B7CA),
                foregroundColor: Colors.white,
                icon: Icons.share,
                label: 'Share',
              ),
            ],
          ),*/
          endActionPane: ActionPane(
            dismissible: DismissiblePane(
              onDismissed: () => BasketItemHolder().add(food[index]),
              //TODO dismissed disappers
              confirmDismiss: () => Future.value(true),
            ),
            motion: const ScrollMotion(),
            children: [
              SlidableAction(
                flex: 2,
                onPressed: (_) => BasketItemHolder().add(food[index]),
                backgroundColor: const Color.fromARGB(255, 55, 135, 255),
                foregroundColor: Colors.white,
                icon: Icons.add_shopping_cart_rounded,
                label: 'Add',
              ),
            ],
          ),
          child: Container(
              color: Colors.white,
              child: ListTile(
                title: Text(food[index].title),
                subtitle: Text(food[index].price.toString()),
                leading: Text(food[index].id.toString()),
                onTap: () => BasketItemHolder().add(food[index]),
              )),
        );
      },
    ));
  }

  void _fetchFood() {
    http.get(Uri.http('localhost:8080', '/food/all')).then((value) => {
          if (value.ok)
            {
              setState(() => food = (jsonDecode(value.body) as List<dynamic>)
                  .map((json) => Food.fromJson(json as Map<String, dynamic>))
                  .toList())
            }
          else
            throw Exception('Failed to load food')
        });
  }
}
