<p align="center">
  <a href="https://play.google.com/store/apps/dev?id=7086930298279250852" target="_blank">
    <img alt="" src="https://github-production-user-asset-6210df.s3.amazonaws.com/125717930/246971879-8ce757c3-90dc-438d-807f-3f3d29ddc064.png" width=500/>
  </a>  
</p>

#### 📚 Product & Resources - [Here](https://github.com/kby-ai/Product)
#### 🛟 Help Center - [Here](https://docs.kby-ai.com)
#### 💼 KYC Verification Demo - [Here](https://github.com/kby-ai/KYC-Verification-Demo-Android)
#### 🙋‍♀️ Docker Hub - [Here](https://hub.docker.com/u/kbyai)

# FaceAttribute-Flutter
## Overview
This repo performs `face auto-capture` functionality on real-time mobile camera.</br>
It also supports the following functionalities: `face liveness detection`, `face recognition`, `pose estimation`, `face quality calculation`, `face landmark detection`, `face occlusion detection`, `eye closure detection`, `age/gender estimation`.</br>

> In this repository, we integrated KBY-AI's `face premium SDK` into the Flutter project for both Android and iOS.</br>
### ◾FaceSDK(Mobile) Details

  | Basic      | Standard | 🔽 Premium |
  |------------------|------------------|------------------|
  | Face Detection        | Face Detection    | <b>Face Detection</b> |
  | Face Liveness Detection        | Face Liveness Detection    | <b>Face Liveness Detection</b> |
  | Pose Estimation        | Pose Estimation    | <b>Pose Estimation</b> |
  |         | Face Recognition    | <b>Face Recognition</b> |
  |         |         | <b>68 points Face Landmark Detection</b> |
  |         |         | <b>Face Quality Calculation</b> |
  |         |         | <b>Face Occlusion Detection</b> |
  |         |         | <b>Eye Closure Detection</b> |
  |         |         | <b>Age, Gender Estimation</b> |

### ◾FaceSDK(Mobile) Product List
  | No.      | Repository | SDK Details |
  |------------------|------------------|------------------|
  | 1        | [Face Liveness Detection - Android](https://github.com/kby-ai/FaceLivenessDetection-Android)    | Basic SDK |
  | 2        | [Face Liveness Detection - iOS](https://github.com/kby-ai/FaceLivenessDetection-iOS)    | Basic SDK |
  | 3        | [Face Recognition - Android](https://github.com/kby-ai/FaceRecognition-Android)    | Standard SDK |
  | 4        | [Face Recognition - iOS](https://github.com/kby-ai/FaceRecognition-iOS)    | Standard SDK |
  | 5        | [Face Recognition - Flutter](https://github.com/kby-ai/FaceRecognition-Flutter)        | <b>Standard SDK</b> |
  | 6        | [Face Recognition - React-Native](https://github.com/kby-ai/FaceRecognition-React-Native)        | Standard SDK |
  | 7        | [Face Attribute - Android](https://github.com/kby-ai/FaceAttribute-Android)        | Premium SDK |
  | 8        | [Face Attribute - iOS](https://github.com/kby-ai/FaceAttribute-iOS)        | Premium SDK |
  | ➡️        | <b>[Face Attribute - Flutter](https://github.com/kby-ai/FaceAttribute-Flutter)</b>        | Premium SDK |

 > To get Face SDK(server), please visit products [here](https://github.com/kby-ai/Product).<br/>

## Try with Demo App

### Google Play

<a href="https://play.google.com/store/apps/details?id=com.kbyai.facerecognition" target="_blank">
  <img alt="" src="https://user-images.githubusercontent.com/125717930/230804673-17c99e7d-6a21-4a64-8b9e-a465142da148.png" height=80/>
</a>

## Screenshots
<p float="left">
  <img src="https://github.com/kby-ai/FaceAttribute-Android/assets/125717930/8357b81f-757f-4300-a913-94e5530108b4" width=240/>
  <img src="https://user-images.githubusercontent.com/125717930/235572299-23989a6e-d6cc-41d3-b75c-a235018ee464.png" width=240/>
  <img src="https://user-images.githubusercontent.com/125717930/235572304-41714fd2-b117-4bcf-999d-32c6d5c1a0c5.png" width=240/>
</p>

<p float="left">
  <img src="https://github.com/kby-ai/FaceAttribute-Android/assets/125717930/0fc2851d-ccb3-4473-9748-83e025eb8409" width=240/>
  <img src="https://github.com/kby-ai/FaceAttribute-Android/assets/125717930/6ee607b9-dc11-4ac6-8481-27357357b630" width=240/>
  <img src="https://github.com/kby-ai/FaceAttribute-Android/assets/125717930/75dbda2c-729a-4823-a18f-30301257b84a" width=240/>
</p>

<p float="left">
  <img src="https://user-images.githubusercontent.com/125717930/235573436-6944cd3b-3b93-404d-b8c7-c02fc83d4bdc.png" width=240/>
  <img src="https://github.com/kby-ai/FaceAttribute-Android/assets/125717930/515157bd-39ab-4a94-a54c-b2224d69b9fc" width=240/>
  <img src="https://github.com/kby-ai/FaceAttribute-Android/assets/125717930/b1f64dba-7b58-4385-939d-949fb4be4d66" width=240/>
</p>

## SDK License

To run this repo successfully, license should be required based on each `application ID`.</br>
- The code below shows how to use the license: https://github.com/kby-ai/FaceAttribute-Flutter/blob/d5e05cd5b22bb7d57c55bcc26eb8cb29cff11993/lib/main.dart#L71-L79

- To request a license, please contact us:</br>
🧙`Email:` contact@kby-ai.com</br>
🧙`Telegram:` [@kbyai](https://t.me/kbyai)</br>
🧙`WhatsApp:` [+19092802609](https://wa.me/+19092802609)</br>
🧙`Skype:` [live:.cid.66e2522354b1049b](https://join.skype.com/invite/OffY2r1NUFev)</br>
🧙`Facebook:` https://www.facebook.com/KBYAI</br>

## How To Run
### 1. Flutter Setup
  Make sure you have Flutter installed. </br>
  This repo has been built with Flutter version `3.22.3`.</br> 
  If you don't have Flutter installed, please follow the instructions provided in the official Flutter documentation [here](https://docs.flutter.dev/get-started/install).</br>
### 2. Running the App
  Run the following commands:
  
  ```bash
  flutter pub upgrade
  flutter run
  ```  
  If you plan to run the iOS app, please refer to the following [link](https://docs.flutter.dev/deployment/ios) for detailed instructions.</br>
## About SDK
### 1. Setup
### 1.1 Setting Up Face SDK
  > Android

  -  Copy the SDK (folder `libfacesdk`) to the folder `android` in your project.</br>
  -  Add SDK to the project in `settings.gradle`.
  ```dart
  include ':libfacesdk'
  ```
#### 1.2 Setting Up Face SDK Plugin
  -  Copy the folder `facesdk_plugin` to the root folder of your project.</br>
  - Add the dependency in your `pubspec.yaml` file.
  ```dart
    facesdk_plugin:
      path: ./facesdk_plugin
  ```
  - Import the `facesdk_plugin` package.
  ```dart
    import 'package:facesdk_plugin/facesdk_plugin.dart';
    import 'package:facesdk_plugin/facedetection_interface.dart';
  ```
### 2 API Usages
#### 2.1 Facesdk Plugin
  - Activate the `FacesdkPlugin` by calling the `setActivation` method:
  ```dart
    final _facesdkPlugin = FacesdkPlugin();
    ...
    await _facesdkPlugin
            .setActivation(
                "Os8QQO1k4+7MpzJ00bVHLv3UENK8YEB04ohoJsU29wwW1u4fBzrpF6MYoqxpxXw9m5LGd0fKsuiK"
                "fETuwulmSR/gzdSndn8M/XrEMXnOtUs1W+XmB1SfKlNUkjUApax82KztTASiMsRyJ635xj8C6oE1"
                "gzCe9fN0CT1ysqCQuD3fA66HPZ/Dhpae2GdKIZtZVOK8mXzuWvhnNOPb1lRLg4K1IL95djy0PKTh"
                "BNPKNpI6nfDMnzcbpw0612xwHO3YKKvR7B9iqRbalL0jLblDsmnOqV7u1glLvAfSCL7F5G1grwxL"
                "Yo1VrNPVGDWA/Qj6Z2tPC0ENQaB4u/vXAS0ipg==")
            .then((value) => facepluginState = value ?? -1);  
  ```
  - Initialize the `FacesdkPlugin`:
  ```dart
  await _facesdkPlugin
            .init()
            .then((value) => facepluginState = value ?? -1)
  ```
  - Set parameters using the `setParam` method:
  ```dart
  await _facesdkPlugin.setParam({
        'check_liveness_level': livenessLevel ?? 0,
        'check_eye_closeness': true,
        'check_face_occlusion': true,
        'check_mouth_opened': true,
        'estimate_age_gender': true
      });
  ```
  - Extract faces using the `extractFaces` method:
  ```dart
  final faces = await _facesdkPlugin.extractFaces(image.path)
  ```
  - Calculate the similarity between faces using the `similarityCalculation` method:
  ```dart
  double similarity = await _facesdkPlugin.similarityCalculation(
                face['templates'], person.templates) ??
            -1;
  ```
#### 2.2 FaceDetectionInterface
  To build the native camera screen and process face detection, please refer to the [lib/facedetectionview.dart](https://github.com/kby-ai/FaceRecognition-Flutter/blob/main/lib/facedetectionview.dart) file in the repository. 
  
  This file contains the necessary code for implementing the camera screen and performing face detection.
  
