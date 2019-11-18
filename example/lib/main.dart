import 'package:flutter/material.dart';
import 'package:torch_compat/torch_compat.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Torch plugin example app'),
        ),
        body: Center(
          child: Column(
            children: <Widget>[
              RaisedButton(
                  child: Text('Turn on'),
                  onPressed: () {
                    TorchCompat.turnOn();
                  }),
              RaisedButton(
                  child: Text('Turn off'),
                  onPressed: () {
                    TorchCompat.turnOff();
                  })
            ],
          ),
        ),
      ),
    );
  }

  @override
  void dispose() {
    // Mandatory for Camera 1 on Android
    TorchCompat.dispose();
    super.dispose();
  }
}
