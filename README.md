# Torch compat  
  
[![Pub](https://img.shields.io/pub/v/torch_compat.svg)](https://pub.dartlang.org/packages/torch_compat)
  
A plugin to enable or disable the torch of a device that works both on Android (including Android 4.x) and ioS.  

## Getting started
### 1) Dependency setup
  
First import the library to your project in your `pubspec.yaml`:  

```yaml
torch_compat: ^1.0.0
```

### 2) Import the library in your Dart code

```dart
import 'package:torch_compat/torch_compat.dart';
```  

### 3) Turn on or off the flash  
  
```dart
TorchCompat.turnOn();
TorchCompat.turnOff();  
``` 