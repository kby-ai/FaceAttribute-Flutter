import Flutter
import UIKit

public class FacesdkPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "facesdk_plugin", binaryMessenger: registrar.messenger())
    let instance = FacesdkPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)

    registrar.register(FaceDetectionViewFactory(registrar: registrar), withId: "facedetectionview")
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    let args = call.arguments
    let myArgs = args as? [String: Any]
    switch call.method {
    case "getPlatformVersion":
      result("iOS " + UIDevice.current.systemVersion)
    case "setActivation":
      let license = myArgs?["license"] as! String
      print("args: ", license)
      var ret = FaceSDK.setActivation(license)
      result(ret)
    case "init":
      var ret = FaceSDK.initSDK()
      result(ret)
    case "setParam":
      result(0)
    case "extractFaces":
      let imagePath = myArgs?["imagePath"] as! String
      var faceBoxesMap = NSMutableArray()
      guard let image = UIImage(contentsOfFile: imagePath)?.fixOrientation() as? UIImage else {
        result(faceBoxesMap)
        return
      }

      let param = FaceDetectionParam()
      param.check_liveness = true
        param.check_eye_closeness = true
        param.check_mouth_opened = true
        param.check_face_occlusion = true
      let faceBoxes = FaceSDK.faceDetection(image, param: param)
      for face in (faceBoxes as NSArray as! [FaceBox]) {
          
          let templates = FaceSDK.templateExtraction(image, faceBox: face)          
          let faceImage = image.cropFace(faceBox: face)
          let faceJpg = faceImage!.jpegData(compressionQuality: CGFloat(1.0))

          var faceDic = Dictionary<String, Any>()
          faceDic["x1"] = face.x1
          faceDic["y1"] = face.y1
          faceDic["x2"] = face.x2
          faceDic["y2"] = face.y2
          faceDic["liveness"] = face.liveness
          faceDic["yaw"] = face.yaw
          faceDic["roll"] = face.roll
          faceDic["pitch"] = face.pitch
          faceDic["age"] = face.age
          faceDic["gender"] = face.gender
          faceDic["face_luminance"] = face.face_luminance
          faceDic["mouth_opened"] = face.face_mouth_opened
          faceDic["left_eye_closed"] = face.left_eye
          faceDic["right_eye_closed"] = face.right_eye
          faceDic["face_occlusion"] = face.face_occlusion
          faceDic["face_quality"] = face.face_quality
          faceDic["landmarks_68"] = face.landmark
          faceDic["templates"] = templates
          faceDic["faceJpg"] = faceJpg
          faceDic["frameWidth"] = Int(image.size.width)
          faceDic["frameHeight"] = Int(image.size.height)

          faceBoxesMap.add(faceDic)
      }

      var faceBoxesArray = faceBoxesMap as Array
      result(faceBoxesArray)
    case "similarityCalculation":
      let templates1 = myArgs?["templates1"] as! FlutterStandardTypedData
      let templates2 = myArgs?["templates2"] as! FlutterStandardTypedData

      let similarity = FaceSDK.similarityCalculation(templates1.data, templates2: templates2.data)
      result(similarity)
    default:
      result(FlutterMethodNotImplemented)
    }
  }
}

