import 'dart:async';
import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:qrscan/qrscan.dart' as scanner;

class QrScan extends StatefulWidget {
  const QrScan({super.key});

  @override
  QrScanState createState() => QrScanState();
}

class QrScanState extends State<QrScan> {
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
                    _buttonGroup(),
                  ],
                ),
              ),
            ],
          );
        },
      ),
    );
  }

  Widget _buttonGroup() {
    return Row(
      children: <Widget>[
        Expanded(
          flex: 1,
          child: SizedBox(
            height: 120,
            child: InkWell(
              onTap: _scan,
              child: Card(
                child: Column(
                  children: <Widget>[
                    Expanded(
                      flex: 2,
                      child: Image.network(
                          'https://cdn-icons-png.flaticon.com/512/3082/3082421.png'),
                    ),
                    const Divider(height: 20),
                    const Expanded(flex: 1, child: Text("Scan")),
                  ],
                ),
              ),
            ),
          ),
        ),
      ],
    );
  }

  Future<String?> _scan() async {
    await Permission.camera.request();
    return await scanner.scan();
  }
}
