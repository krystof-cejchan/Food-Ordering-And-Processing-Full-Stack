import 'package:customer_phone_ordering/nav_items/basket.dart';
import 'package:customer_phone_ordering/nav_items/menu.dart';
import 'package:customer_phone_ordering/nav_items/qr_scan.dart';
import 'package:flutter/material.dart';
import 'package:google_nav_bar/google_nav_bar.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  MyAppState createState() => MyAppState();
}

class MyAppState extends State<MyApp> {
  int _selectedIndex = 0;

  final List<Widget> _widgetOptions = <Widget>[
    const QrScan(),
    const RestaurantMenu(),
    const Basket(),
  ];

  @override
  initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        backgroundColor: Theme.of(context).scaffoldBackgroundColor,
        appBar: AppBar(
          backgroundColor: Theme.of(context).colorScheme.background,
          elevation: 20,
          title: const Text("Olomouc Fried Chicken",
              style: TextStyle(fontWeight: FontWeight.w600)),
        ),
        body: _widgetOptions.elementAt(_selectedIndex),
        bottomNavigationBar: Container(
          decoration: BoxDecoration(
            color: Theme.of(context).primaryColor,
            boxShadow: [
              BoxShadow(
                blurRadius: 20,
                color: Colors.black.withOpacity(.1),
              )
            ],
          ),
          child: SafeArea(
            child: Padding(
              padding:
                  const EdgeInsets.symmetric(horizontal: 15.0, vertical: 8),
              child: GNav(
                rippleColor: const Color.fromARGB(110, 255, 255, 255),
                hoverColor: Theme.of(context).hoverColor,
                gap: 4,
                activeColor: Theme.of(context).primaryColor,
                iconSize: 25,
                padding:
                    const EdgeInsets.symmetric(horizontal: 10, vertical: 12),
                duration: const Duration(milliseconds: 400),
                tabBackgroundColor: Colors.white,
                color: Theme.of(context).cardColor,
                tabs: const [
                  GButton(
                    icon: Icons.home_rounded,
                    text: "Scan",
                  ),
                  GButton(
                    icon: Icons.restaurant_menu_rounded,
                    text: "Menu",
                  ),
                  GButton(
                    icon: Icons.shopping_basket_rounded,
                    text: "Basket",
                  ),
                ],
                selectedIndex: _selectedIndex,
                onTabChange: (index) {
                  //TODO this is where it is needed to be changed when routing through components
                  setState(() => _selectedIndex = index);
                },
              ),
            ),
          ),
        ),
      ),
    );
  }
}
