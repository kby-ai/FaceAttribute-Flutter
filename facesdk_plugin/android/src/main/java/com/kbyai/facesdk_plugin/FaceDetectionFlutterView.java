package com.kbyai.facesdk_plugin;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;



import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.platform.PlatformView;

import com.kbyai.facesdk.*;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class FaceDetectionFlutterView implements PlatformView, MethodCallHandler, CameraViewInterface {

    public static int livenessDetectionLevel = 0;
    public static boolean check_eye_closeness = true;
    public static boolean check_face_occlusion = true;
    public static boolean check_mouth_opened = true;
    public static boolean estimate_age_gender = true;

    private final MethodChannel channel;
    private final ActivityPluginBinding activityPluginBinding;
    private CameraBaseView cameraView;

    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private final Handler channelHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                @SuppressWarnings("unchecked")
                ArrayList<HashMap<String, Object>> faceBoxesMap = (ArrayList<HashMap<String, Object>>) msg.obj;
                channel.invokeMethod("onFaceDetected", faceBoxesMap);
            }
        }
    };



    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull final Result result) {
        switch (call.method) {
            case "startCamera":
                int cameraLens = Objects.requireNonNull(call.argument("cameraLens"));
                getCameraView().startCamera(cameraLens);
                result.success(null);
                break;
            case "stopCamera":
                getCameraView().stopCamera();
                result.success(null);
                break;
            default:
                result.notImplemented();
                break;
        }
    }

    private CameraBaseView getCameraView() {
        return cameraView;
    }

    public FaceDetectionFlutterView(ActivityPluginBinding activityPluginBinding, DartExecutor dartExecutor, int viewId) {
        this.channel = new MethodChannel(dartExecutor, "facedetectionview_" + viewId);
        this.activityPluginBinding = activityPluginBinding;
        this.channel.setMethodCallHandler(this);
        if (getCameraView() == null) {
            cameraView = new CameraBaseView(activityPluginBinding.getActivity());
            cameraView.setCameraViewInterface(this);

            activityPluginBinding.addRequestPermissionsResultListener(cameraView);
        }
    }

    @Override
    public View getView() {
        return getCameraView().getView();
    }

    @Override
    public void dispose() {
        if (getCameraView() != null) {
            getCameraView().dispose();
        }

        channelHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onFrame(Bitmap bitmap) {

        ArrayList<HashMap<String, Object>> faceBoxesMap = new ArrayList<HashMap<String, Object>>();
        FaceDetectionParam param = new FaceDetectionParam();
        param.check_liveness = true;
        param.check_eye_closeness = true;
        param.check_liveness_level = livenessDetectionLevel;
        param.check_eye_closeness = check_eye_closeness;
        param.check_face_occlusion = check_face_occlusion;
        param.check_mouth_opened = check_mouth_opened;
        param.estimate_age_gender = estimate_age_gender;

        List<FaceBox> faceBoxes = FaceSDK.faceDetection(bitmap, param);
        for (FaceBox faceBox : faceBoxes) {
            byte[] templates = FaceSDK.templateExtraction(bitmap, faceBox);
           Bitmap faceImage = Utils.cropFace(bitmap, faceBox);
           ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
           faceImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
           byte[] faceJpg = byteArrayOutputStream.toByteArray();

            HashMap<String, Object> e = new HashMap<>();
            e.put("x1", faceBox.x1);
            e.put("y1", faceBox.y1);
            e.put("x2", faceBox.x2);
            e.put("y2", faceBox.y2);
            e.put("liveness", faceBox.liveness);
            e.put("yaw", faceBox.yaw);
            e.put("roll", faceBox.roll);
            e.put("pitch", faceBox.pitch);
            e.put("templates", templates);
            e.put("face_quality", faceBox.face_quality);
            e.put("face_luminance", faceBox.face_luminance);
            e.put("left_eye_closed", faceBox.left_eye_closed);
            e.put("right_eye_closed", faceBox.right_eye_closed);
            e.put("face_occlusion", faceBox.face_occlusion);
            e.put("mouth_opened", faceBox.mouth_opened);
            e.put("age", faceBox.age);
            e.put("gender", faceBox.gender);
            e.put("landmarks_68", faceBox.landmarks_68);
            e.put("faceJpg", faceJpg);
            e.put("frameWidth", bitmap.getWidth());
            e.put("frameHeight", bitmap.getHeight());

            faceBoxesMap.add(e);
        }

        Message message = Message.obtain();
        message.what = 1;
        message.obj = faceBoxesMap;
        channelHandler.sendMessage(message);
    }
}
