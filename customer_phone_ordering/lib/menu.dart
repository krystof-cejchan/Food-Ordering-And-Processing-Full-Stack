import 'package:customer_phone_ordering/food.dart';
import 'package:flutter/material.dart';
import './table.dart' as t;

class RestaurantMenu extends StatefulWidget {
  RestaurantMenu(t.Table? table, {super.key});
  t.Table? table;
  @override
  RestaurantMenuState createState() => RestaurantMenuState();
}

class RestaurantMenuState extends State<RestaurantMenu> {
  //get food from backend
  final List<Food> food = [Food(1, 9.9, "cheese")];
  @override
  initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: ListView.builder(
      itemCount: food.length,
      itemBuilder: (_, index) {
        return ListTile(
          title: Text(food[index].title),
          subtitle: Text(food[index].price.toString()),
          leading: Text(food[index].id.toString()),
          trailing: const Icon(Icons.arrow_forward),
          onTap: () {
            /*Navigator.push(
            context,
            //MaterialPageRoute(builder: (context) => DetailPage(index))
          );*/
          },
        );
      },
    ));
  }
}
