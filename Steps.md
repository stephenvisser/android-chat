Setup Environment
==================
1. Download  and install [Android Studio](https://developer.android.com/sdk/installing/studio.html)
2. Optional : Download and install [Gradle](http://www.gradle.org/)
3. Add ANDROID_HOME variable to location of [sdk] (https://developer.android.com/sdk/installing/studio.html#Installing)
4. Open SDK Manager (from Android Studio, or typing in `android` at terminal prompt)
5. Install SDKs, typically all for `API 19` and `Tools` at the least.

Start Project
==============
1. Open Android Studio, select 'Start New Project'
 * Application Name : Parse Chat Client
 * Module Name : app
 * Package Name : com.andmobility [must be unique on a device]
 * Minimum SDK : Minimum platform version you want to target. Take a look at the [distribution dashboard](https://developer.android.com/about/dashboards/index.html). A sensible default is 14 if you're a free app, 16 if you're a paid app.
 * Target SDK : The newest possible API you will be using. This should almost always be the latest version available (currently 19).
2. build.gradle
 * Override Manifest for different versions (Play Store, Amazon App Store)
 * Dependencies

Components
==========
1. [Application Manifest](https://developer.android.com/guide/topics/manifest/manifest-intro.html).
 * Let's the device and user know information about the app.
 * Has the package name, permissions and list of all activities defined.
2. [Activity](https://developer.android.com/reference/android/app/Activity.html)
 * A single task that can be performed by the user.
 * Works on top [Window](https://developer.android.com/reference/android/view/Window.html) to integrate with the back stack, provide Action Bar API, etc.
3. [Application](https://developer.android.com/reference/android/app/Application.html)
 * For things that need to be setup for the entire application.
 * Likely you'll need one for more complex apps.
 * Point your Manifest to your custom application class.
4. [Fragment](https://developer.android.com/guide/components/fragments.html)
 * Can design a portion of an activity
 * Let's you create and reuse modular components (e.g. two pane layout)
 * Integrates into the back stack and can interact with the Action Bar
5. [Service](https://developer.android.com/guide/components/services.html)
 * Run long running operations in the background
 * Does not provide a UI (other than notifying other components or positing notifications)
 * Can run on the main thread (default) or kick off worker threads (like [IntentService](https://developer.android.com/reference/android/app/IntentService.html))
6. [View](https://developer.android.com/reference/android/view/package-summary.html)
 * Building block for UI components, used by Fragments and Activities
 * Can serve as replacement for Fragments, has a much easier lifecycle

App
===
1. [Add Parse](https://parse.com/docs/downloads)
 * Add jar
 * Point build.gradle to the jar folder
 * Add permissions in manifest
2. Custom Application
 * Point Manifest to Application
3. Update Manifest Permissions
4. Rename MainActivity to Chat
5. Fill in Chat
6. SafeAsyncTask
7. Push