# Torch compat  
  
[![Pub](https://img.shields.io/pub/v/torch_compat.svg)](https://pub.dartlang.org/packages/torch_compat)
  
A plugin to enable or disable the torch of a device that works both on Android (including Android 4.x) and ioS.  

## NOTE: For older Android devices

Some older Android devices have been [known to crash](https://stackoverflow.com/questions/36259930/which-permissions-are-required-for-flashlight) when requesting access to the device torch. This is the result of a bug on those devices requiring full camera access in order to control the camera flash.

This plugin by default only requests `android.permission.FLASHLIGHT`, but if you need your app to be compatible with more devices, you can add the following lines to your application's `AndroidManifest.xml`

```xml
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-feature android:name="android.hardware.camera" />
    <uses-feature android:name="android.hardware.camera.autofocus" />
    <uses-feature android:name="android.hardware.camera.flash" />
```



## Getting started
### 1) Dependency setup
  
First import the library to your project in your `pubspec.yaml`:  

```yaml
torch_compat: ^1.0.2
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
