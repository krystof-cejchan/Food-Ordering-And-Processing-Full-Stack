import 'dart:async';
import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:qrscan/qrscan.dart' as scanner;

class Food extends StatefulWidget {
  const Food({super.key});

  @override
  FoodState createState() => FoodState();
}

class FoodState extends State<Food> {
  Uint8List bytes = Uint8List(0);

  @override
  initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.grey[300],
      body: Builder(
        builder: (BuildContext context) {
          return ListView(
            children: <Widget>[
              Container(
                color: Colors.white,
                child: Column(
                  children: <Widget>[
                    //  _buttonGroup(),
                  ],
                ),
              ),
            ],
          );
        },
      ),
    );
  }
}
