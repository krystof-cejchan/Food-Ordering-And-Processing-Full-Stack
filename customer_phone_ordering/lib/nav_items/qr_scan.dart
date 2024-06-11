import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:qrscan/qrscan.dart' as scanner;
import '../classes/table.dart' as t;

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
    return Center(
      child: Column(
        children: <Widget>[
          const SizedBox(height: 150),
          TextButton.icon(
            onPressed: _scan,
            icon: const Icon(
              Icons.qr_code_scanner_rounded,
              size: 75,
            ),
            label: const Text(
              'Scan',
              style: TextStyle(letterSpacing: 1.2, fontSize: 27),
            ),
            style: const ButtonStyle(alignment: Alignment.center),
          ),
          const SizedBox(height: 150),
          Center(
            child: Text(t.CurrentTable.table != null
                ? t.CurrentTable.table.toString()
                : "No table scanned yet."),
          )
        ],
      ),
    );
  }

  void _scan() async {
    await Permission.camera.request();
    _redirect(await scanner.scan());
  }

  void _redirect(String? s) {
    final tableSplit = s!.split(';');
    t.CurrentTable.table = t.Table(int.parse(tableSplit.first),
        int.parse(tableSplit[1]), tableSplit[2], int.parse(tableSplit[3]));
    setState(() {});
  }
}
