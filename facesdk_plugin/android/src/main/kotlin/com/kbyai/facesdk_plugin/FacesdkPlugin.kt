package com.kbyai.facesdk_plugin

import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.platform.PlatformViewRegistry
import android.util.Log
import com.kbyai.facesdk.FaceBox
import com.kbyai.facesdk.FaceSDK
import com.kbyai.facesdk.FaceDetectionParam
import com.kbyai.facesdk_plugin.*
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.File
import java.io.ByteArrayOutputStream
import java.util.Base64

/** FacesdkPlugin */
class FacesdkPlugin: FlutterPlugin, MethodCallHandler, ActivityAware {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel
  private lateinit var registery: PlatformViewRegistry
  private lateinit var dartExecuter: DartExecutor
  private lateinit var context: Context


  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "facesdk_plugin")
    channel.setMethodCallHandler(this)

    context = flutterPluginBinding.applicationContext

    registery = flutterPluginBinding.getFlutterEngine().getPlatformViewsController().getRegistry();
    dartExecuter = flutterPluginBinding.getFlutterEngine().getDartExecutor();
    FaceDetectionFlutterView.livenessDetectionLevel = 0
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } else if (call.method == "setActivation") {
      val license: String? = call.argument("license")
      val ret = FaceSDK.setActivation(license);
      result.success(ret)
    } else if (call.method == "init") {
      val ret = FaceSDK.init(context.assets)
      result.success(ret)
    } else if (call.method == "setParam") {
      val check_liveness_level: Int? = call.argument("check_liveness_level")
      if(check_liveness_level != null)
        FaceDetectionFlutterView.livenessDetectionLevel = check_liveness_level!!

      val check_eye_closeness: Boolean? = call.argument("check_eye_closeness")
      if(check_eye_closeness != null)
        FaceDetectionFlutterView.check_eye_closeness = check_eye_closeness!!

      val check_face_occlusion: Boolean? = call.argument("check_face_occlusion")
      if(check_face_occlusion != null)
        FaceDetectionFlutterView.check_face_occlusion = check_face_occlusion!!

      val check_mouth_opened: Boolean? = call.argument("check_mouth_opened")
      if(check_mouth_opened != null)
        FaceDetectionFlutterView.check_mouth_opened = check_mouth_opened!!

      val estimate_age_gender: Boolean? = call.argument("estimate_age_gender")
      if(estimate_age_gender != null)
        FaceDetectionFlutterView.estimate_age_gender = estimate_age_gender!!
      result.success(0)
    } else if (call.method == "extractFaces") {
      val imagePath: String? = call.argument("imagePath")

      var bitmap: Bitmap? = BitmapFactory.decodeFile(imagePath)
      val param = FaceDetectionParam()
      param.check_liveness = true
      param.check_liveness_level = FaceDetectionFlutterView.livenessDetectionLevel
      param.check_eye_closeness = FaceDetectionFlutterView.check_eye_closeness
      param.check_face_occlusion = FaceDetectionFlutterView.check_face_occlusion
      param.check_mouth_opened = FaceDetectionFlutterView.check_mouth_opened
      param.estimate_age_gender = FaceDetectionFlutterView.estimate_age_gender

      var faceBoxes: List<FaceBox>? = FaceSDK.faceDetection(bitmap, param)

      val faceBoxesMap: ArrayList<HashMap<String, Any>> = ArrayList<HashMap<String, Any>>()
      if(!faceBoxes.isNullOrEmpty()) {
        for(face in faceBoxes!!) {
          val faceImage = Utils.cropFace(bitmap, face)
          val templates = FaceSDK.templateExtraction(bitmap, face)

          val byteArrayOutputStream = ByteArrayOutputStream()
          faceImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
          val faceJpg: ByteArray = byteArrayOutputStream.toByteArray()

          val e: HashMap<String, Any> = HashMap<String, Any>()
          e.put("x1", face.x1);
          e.put("y1", face.y1);
          e.put("x2", face.x2);
          e.put("y2", face.y2);
          e.put("liveness", face.liveness);
          e.put("yaw", face.yaw);
          e.put("roll", face.roll);
          e.put("pitch", face.pitch);
          e.put("face_quality", face.face_quality);
          e.put("face_luminance", face.face_luminance);
          e.put("left_eye_closed", face.left_eye_closed);
          e.put("right_eye_closed", face.right_eye_closed);
          e.put("face_occlusion", face.face_occlusion);
          e.put("mouth_opened", face.mouth_opened);
          e.put("age", face.age);
          e.put("gender", face.gender);
          e.put("landmarks_68", face.landmarks_68);
          e.put("templates", templates);
          e.put("faceJpg", faceJpg);
          e.put("frameWidth", bitmap!!.width);
          e.put("frameHeight", bitmap!!.height);
          faceBoxesMap.add(e)
        }
      }

      result.success(faceBoxesMap)
    } else if (call.method == "similarityCalculation") {
      val templates1: ByteArray? = call.argument("templates1")
      val templates2: ByteArray? = call.argument("templates2")

      val similarity: Float = FaceSDK.similarityCalculation(templates1!!, templates2!!)
      result.success(similarity)
    } else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  override fun onAttachedToActivity(@NonNull binding: ActivityPluginBinding) {
    if (binding.getActivity() != null) {
      registery
        .registerViewFactory(
          "facedetectionview", FaceDetectionViewFactory(binding, dartExecuter)
        )
    }
  }

  override fun onDetachedFromActivityForConfigChanges() {}

  override fun onReattachedToActivityForConfigChanges(@NonNull binding: ActivityPluginBinding) {}

  override fun onDetachedFromActivity() {}

}
