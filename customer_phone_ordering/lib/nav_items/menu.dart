import 'dart:convert';

import 'package:customer_phone_ordering/classes/basket_order.dart';
import 'package:customer_phone_ordering/classes/extensions/response_extension.dart';
import 'package:customer_phone_ordering/classes/food.dart';
import 'package:flutter/material.dart';
import 'package:flutter_slidable/flutter_slidable.dart';
import 'package:http/http.dart' as http;

class RestaurantMenu extends StatefulWidget {
  const RestaurantMenu({super.key});
  @override
  RestaurantMenuState createState() => RestaurantMenuState();
}

class RestaurantMenuState extends State<RestaurantMenu>
    with SingleTickerProviderStateMixin {
  late List<Food> food = []; // from backend via _fetchFood()
  late final controller = SlidableController(this);
  @override
  initState() {
    super.initState();
    _fetchFood();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: food.isNotEmpty
            ? ListView.builder(
                itemCount: food.length,
                itemBuilder: (_, index) {
                  return Slidable(
                    key: const ValueKey<int>(0),
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
                          backgroundColor:
                              const Color.fromARGB(255, 55, 135, 255),
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
              )
            : const Center(
                child: Text("Could not load the menu",
                    style: TextStyle(fontWeight: FontWeight.w300)),
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
