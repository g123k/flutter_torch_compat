@file:Suppress("DEPRECATION")

package fr.g123k.torch_compat.impl

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Camera

class TorchCamera1Impl(private val context: Context) : BaseTorch() {

    private var camera: Camera? = null

    private fun initCamera(): Camera? {
        if (camera == null) {
            camera = try {
                Camera.open()
            } catch (e: Exception) {
                null
            }
        }

        return camera
    }

    override fun turnOn() {
        turn(on = true)
    }

    override fun turnOff() {
        turn(on = false)
    }

    private fun turn(on: Boolean) {
        val camera = initCamera() ?: return

        val params = camera.parameters
        params.flashMode = if (on) Camera.Parameters.FLASH_MODE_TORCH
        else Camera.Parameters.FLASH_MODE_OFF

        camera.parameters = params

        if (on) {
            camera.startPreview()
        } else {
            camera.stopPreview()
        }
    }

    override fun hasTorch(): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

    override fun dispose() {
        camera?.release()
        camera = null
    }

}