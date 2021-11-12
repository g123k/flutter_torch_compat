package fr.g123k.torch_compat

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

import fr.g123k.torch_compat.impl.BaseTorch
import fr.g123k.torch_compat.impl.TorchCamera2Impl
import fr.g123k.torch_compat.impl.TorchCamera1Impl

import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding


/** TorchCompatPlugin */
class TorchCompatPlugin: FlutterPlugin, MethodCallHandler, ActivityAware {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel

  private lateinit var context: Context
  private lateinit var activity: Activity

  private var hasLamp: Boolean = false
  private lateinit var torchImpl: BaseTorch

  private fun attach(binding: ActivityPluginBinding) {
      activity = binding.activity
      hasLamp = activity.applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
      torchImpl = when {
          Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> TorchCamera2Impl(activity)
          else -> TorchCamera1Impl(activity)
      }

      // activity.application.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks() {
      //     override fun onActivityStopped(activity: Activity?) {
      //         torchImpl.dispose()
      //     }
      // })
  }

  private fun detach() {
    torchImpl.dispose()
  }

  override fun onDetachedFromActivity() {
    detach()
  }

  override fun onDetachedFromActivityForConfigChanges() {
    detach()
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    attach(binding)
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    attach(binding)
  }


  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "g123k/torch_compat")
    channel.setMethodCallHandler(this)
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } else if (call.method == "turnOn") {
      if (!hasLamp) {
        result.error("NOTORCH", "This device does not have a torch", null)
      } else {
        torchImpl.turnOn()
        result.success(true)
      }
    } else if (call.method == "turnOff") {
      if (!hasLamp) {
        result.error("NOTORCH", "This device does not have a torch", null)
      } else {
        torchImpl.turnOff()
        result.success(true)
      }
    } else if (call.method == "hasTorch") {
      result.success(hasLamp)
    } else if (call.method == "dispose") {
      torchImpl.dispose()
      result.success(true)
    } else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
    torchImpl.dispose()
  }
}
