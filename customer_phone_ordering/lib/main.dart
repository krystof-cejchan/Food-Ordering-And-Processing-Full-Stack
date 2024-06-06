import 'package:customer_phone_ordering/qr_scan.dart';
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
          title: const Text("UniApp > ",
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
                  /*GButton(
                  icon: Icons.calendar_month_rounded,
                  text: "AppLocalizations.of(context)!.schedule",
                ),
                GButton(
                  icon: Icons.grading_rounded,
                  text: "AppLocalizations.of(context)!.exams",
                ),
                GButton(
                  icon: Icons.person_rounded,
                  text: "AppLocalizations.of(context)!.profile",
                ),
                GButton(
                  icon: Icons.settings_rounded,
                  text: " AppLocalizations.of(context)!.settings",
                ),*/
                ],
                selectedIndex: _selectedIndex,
                onTabChange: (index) {
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
