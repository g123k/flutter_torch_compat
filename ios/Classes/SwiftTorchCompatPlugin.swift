import Flutter
import UIKit
import AVFoundation

public class SwiftTorchCompatPlugin: NSObject, FlutterPlugin {
    public static func register(with registrar: FlutterPluginRegistrar) {
        let channel = FlutterMethodChannel(name: "g123k/torch_compat", binaryMessenger: registrar.messenger())
        let instance = SwiftTorchCompatPlugin()
        registrar.addMethodCallDelegate(instance, channel: channel)
    }
    
    public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
        if (call.method == "turnOn") {
            if (!hasTorch()) {
                result(FlutterError(code: "NOTORCH", message: "This device does not have a torch", details: nil))
            } else {
                turnOn()
                result(true)
            }
        } else if (call.method == "turnOff") {
            if (!hasTorch()) {
                result(FlutterError(code: "NOTORCH", message: "This device does not have a torch", details: nil))
            } else {
                turnOff()
                result(true)
            }
        } else if (call.method == "hasTorch") {
            result(hasTorch())
        } else {
            result(FlutterMethodNotImplemented)
        }
    }
    
    func hasTorch() -> Bool {
        guard let device = AVCaptureDevice.default(for: .video) else {return false}
        return device.hasFlash && device.hasTorch
    }
    
    func turnOff() {
        guard let device = AVCaptureDevice.default(for: .video) else {return}
        if (device.hasFlash && device.hasTorch) {
            try! device.lockForConfiguration()
            device.torchMode = .off
            device.unlockForConfiguration()
        }
    }
    
    func turnOn() {
        guard let device = AVCaptureDevice.default(for: .video) else {return}
        if (device.hasFlash && device.hasTorch) {
            try! device.lockForConfiguration()
            device.torchMode = .on
            device.unlockForConfiguration()
        }
    }
}
